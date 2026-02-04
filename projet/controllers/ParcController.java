package controllers;

import models.*;
import views.GestionParcview;

import javax.swing.*;

import java.util.*;
import java.util.stream.Collectors;
import java.awt.GridLayout;

public class ParcController {

    Magasin magasin;
    GestionParcview view;

    public ParcController(Magasin magasin, GestionParcview view) {
        this.magasin = magasin;
        this.view = view;

        setupListeners();
        loadScooters();
    }

    public void setupListeners() {
        view.getComboDispo().addActionListener(e -> loadScooters());
        
        view.getTextFieldMarque().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
        });

        view.getTextFieldModele().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { loadScooters(); }
        });
        
        view.getComboPermis().addActionListener(e -> loadScooters());

        view.getDetailsButton().addActionListener(e -> DetailsClick());

        view.getModifierButton().addActionListener(e -> ModifierClick());
        view.getSupprimerButton().addActionListener(e -> SupprimerClick());
        view.getAjouterButton().addActionListener(e -> AjouterClick());


        view.getLocationButton().addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
            new LocationController(magasin, parentFrame);
        });
    }

    public void loadScooters() {
        view.clearTable();

        Vector<Scooter> scooters = magasin.getScooters();

        String dispo = view.getComboDispo().getSelectedItem().toString();
        String marque = view.getTextFieldMarque().getText().trim().toLowerCase();
        String modele = view.getTextFieldModele().getText().trim().toLowerCase();
        String permis = view.getComboPermis().getSelectedItem().toString();

        List<Scooter> filtered = scooters.stream().filter(s -> {
            boolean ok = true;

            if (!dispo.equals("Tous")) {
                ok &= (dispo.equals("Disponible") == s.estDispo());
            }

            if (!marque.isEmpty()) {
                ok &= s.getModele().getMarque().getNom_marque().toLowerCase().contains(marque);
            }
            if (!modele.isEmpty()) {
                ok &= s.getModele().getNom_model().toLowerCase().contains(modele);
            }

            if (!permis.equals("Tous")) {
                if (s.getModele().getPermis().isEmpty()) {
                    ok = false;
                } else {
                    //On vérifie si le permis demandé est bien dans la liste des permis du modèle
                    boolean permisTrouve = false;
                    for (Permis p : s.getModele().getPermis()) {
                        if (p.getNom_permis().equalsIgnoreCase(permis.trim())) {
                            permisTrouve = true;
                            break;
                        }
                    }
                    ok &= permisTrouve;
                }
            }

            return ok;
        }).collect(Collectors.toList());

        filtered.forEach(s -> view.addScooterToTable(s));
    }

    public void DetailsClick() {
        int row = view.getTableScooters().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter ");
            return;
        }
        int id = view.getScooterIdAt(row);
        Scooter s = magasin.ScooterById(id);

        JOptionPane.showMessageDialog(view, s.details_scooter(), "Détails", JOptionPane.INFORMATION_MESSAGE);
    }
    
        public void ModifierClick() {
            int row = view.getTableScooters().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter ");
                return;
            }

            int id = view.getScooterIdAt(row);
            Scooter scooter = magasin.ScooterById(id);

            JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

            JTextField idField = new JTextField(String.valueOf(scooter.getNum_idt()));
            panel.add(new JLabel("ID du scooter :"));
            panel.add(idField);

            Vector<Marque> marques = magasin.getMarques();
            JComboBox<Marque> marqueCombo = new JComboBox<>(marques);
            
            marqueCombo.setSelectedItem(scooter.getModele().getMarque());
            panel.add(new JLabel("Marque :"));
            panel.add(marqueCombo);

            Vector<Modele> modeles = magasin.getModelesByMarque(scooter.getModele().getMarque().getNom_marque());
            JComboBox<Modele> modeleCombo = new JComboBox<>(modeles);
            modeleCombo.setSelectedItem(scooter.getModele());
            panel.add(new JLabel("Modèle :"));
            panel.add(modeleCombo);

            JTextField prixField = new JTextField(String.valueOf(scooter.getPrix()));
            panel.add(new JLabel("Prix :"));
            panel.add(prixField);

            JTextField penaliteField = new JTextField(String.valueOf(scooter.getPenalite_pj()));
            panel.add(new JLabel("Pénalité par jour de retard :"));
            panel.add(penaliteField);

            //Met à jour les modèles quand on change de marque
            marqueCombo.addActionListener(e -> {
                Marque selectedMarque = (Marque) marqueCombo.getSelectedItem();
                Vector<Modele> nouveauxModeles = selectedMarque.modelesByMarque();
                modeleCombo.setModel(new DefaultComboBoxModel<>(nouveauxModeles));
            });

            int result = JOptionPane.showConfirmDialog(view, panel, "Modifier le scooter",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int newId = Integer.parseInt(idField.getText());
                    double prix = Double.parseDouble(prixField.getText());
                    int penalite = Integer.parseInt(penaliteField.getText());
                    Modele modele = (Modele) modeleCombo.getSelectedItem();

                    if (newId != scooter.getNum_idt() && magasin.ScooterById(newId) != null) {
                        JOptionPane.showMessageDialog(view, "Cet ID est déjà utilisé !");
                        return;
                    }

                    scooter.setNum_idt(newId);
                    scooter.setModele(modele);
                    scooter.setPrix(prix);
                    scooter.setPenalite_pj(penalite);

                    JOptionPane.showMessageDialog(view, "Scooter modifié avec succès !");
                    loadScooters();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Champs numériques invalides !");
                }
            }
        }

        public void AjouterClick() {

            JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

            JTextField idField = new JTextField();
            panel.add(new JLabel("ID du scooter :"));
            panel.add(idField);

            Vector<Marque> marques = magasin.getMarques();
            JComboBox<Marque> marqueCombo = new JComboBox<>(marques);
            panel.add(new JLabel("Marque :"));
            panel.add(marqueCombo);

            Vector<Modele> modeles = magasin.getModelesByMarque(marques.get(0).getNom_marque());
            JComboBox<Modele> modeleCombo = new JComboBox<>(modeles);
            panel.add(new JLabel("Modèle :"));
            panel.add(modeleCombo);

            JTextField prixField = new JTextField();
            panel.add(new JLabel("Prix :"));
            panel.add(prixField);

            JTextField penaliteField = new JTextField();
            panel.add(new JLabel("Pénalité par jours de retard:"));
            panel.add(penaliteField);


            marqueCombo.addActionListener(e -> {
                Marque selectedMarque = (Marque) marqueCombo.getSelectedItem();
                Vector<Modele> nouveauxModeles = selectedMarque.modelesByMarque();
                modeleCombo.setModel(new DefaultComboBoxModel<>(nouveauxModeles));
            });

            int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter un scooter",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    double prix = Double.parseDouble(prixField.getText());
                    int penalite = Integer.parseInt(penaliteField.getText());
                    Modele modele = (Modele) modeleCombo.getSelectedItem();

                    if (magasin.ScooterById(id) != null) {
                        JOptionPane.showMessageDialog(view, "Cet ID est déjà utilisé !");
                        return;
                    }

                    Scooter newScooter = new Scooter(id, true, prix, magasin, modele, penalite);
                    magasin.addScooter(newScooter);
                    loadScooters();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Veuillez entrer des valeurs numériques valides !");
                }
            }
        }


    public void SupprimerClick() {
        int row = view.getTableScooters().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un scooter.");
            return;
        }

        int id = view.getScooterIdAt(row);
        Scooter scooter = magasin.ScooterById(id);

        int confirm = JOptionPane.showConfirmDialog(view, "Êtes-vous sûr de vouloir supprimer le scooter "+scooter.getModele().getNom_model() +" ID: " + scooter.getNum_idt() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            magasin.removeScooter(scooter);
            loadScooters();
            JOptionPane.showMessageDialog(view, "Scooter supprimé avec succès !");
        }
    }
    
    public void resetFilters() {
    view.getComboDispo().setSelectedIndex(0);  
    view.getComboPermis().setSelectedIndex(0);   
}
}