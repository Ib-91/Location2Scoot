package controllers;

import views.*;
import models.*;

import javax.swing.*;
import java.awt.*;


public class GestionMarqueController {
    
    private Magasin magasin;
    private GestionMarqueview view;

    public GestionMarqueController(Magasin magasin) {
        this.magasin = magasin;
        this.view = new GestionMarqueview(magasin);

        initListeners();
        updateModelesList();
        updateMarquesList();
    }

    public GestionMarqueview getView() {
        return view;
    }

    public void initListeners() {

        view.getListMarques().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                updateModelesList();
            }
        });

        view.getDetailsMarqueBtn().addActionListener(e -> {
            int index = view.getListMarques().getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez une marque pour voir ses détails.");
                return;
            }
            Marque marque = magasin.getMarques().get(index);
            JOptionPane.showMessageDialog(view, marque.details_marque());
        });

        view.getModifierMarqueBtn().addActionListener(e -> {
            int index = view.getListMarques().getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez une marque à modifier.");
                return;
            }
            Marque marque = magasin.getMarques().get(index);

            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField idField = new JTextField(String.valueOf(marque.getId_marque()));
            JTextField nomField = new JTextField(marque.getNom_marque());
            
            panel.add(new JLabel("ID de la marque :"));
            panel.add(idField);
            panel.add(new JLabel("Nom de la marque :"));
            panel.add(nomField);

            int option = JOptionPane.showConfirmDialog(view, panel, "Modifier Marque", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
           
            if (option ==JOptionPane.OK_OPTION) {
                String nouveauNom = nomField.getText();
                String nouvelId = idField.getText();

                if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
                    for (Marque m : magasin.getMarques()) {
                        if (m != marque && m.getNom_marque().equalsIgnoreCase(nouveauNom.trim())) {
                            JOptionPane.showMessageDialog(view, "Une autre marque a déjà ce nom.");
                            return;
                        }
                    }
                    marque.setNom_marque(nouveauNom.trim());
                
                }if (nouvelId != null && !nouvelId.trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(nouvelId.trim());
                        for (Marque m : magasin.getMarques()) {
                            if (m != marque && m.getId_marque() == id) {
                                JOptionPane.showMessageDialog(view, "Une autre marque a déjà cet ID.");
                                return;
                            }
                        }
                        marque.setId_marque(id);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view, "L'ID doit être un entier.");
                    }
                }
                updateModelesList();
                updateMarquesList();
            }
        });

        view.getDetailsModeleBtn().addActionListener(e -> {
            int indexMarque = view.getListMarques().getSelectedIndex();
            int indexModele = view.getListModeles().getSelectedIndex();
            
            if (indexMarque == -1 || indexModele == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez un modèle pour voir ses détails.");
                return;
            }

            Marque marque = magasin.getMarques().get(indexMarque);
            Modele modele = marque.getModeles().get(indexModele);
            
            JOptionPane.showMessageDialog(view, modele.details_modele());
        });

        view.getModifierModeleBtn().addActionListener(e -> {
            int indexMarque = view.getListMarques().getSelectedIndex();
            int indexModele = view.getListModeles().getSelectedIndex();
            if (indexMarque == -1 || indexModele == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez un modèle à modifier.");
                return;
            }

            Marque marque = magasin.getMarques().get(indexMarque);
            Modele modele = marque.getModeles().get(indexModele);
            String[] options = {"Modifier le nom et/ou l'ID", "Modifier les permis"};
            int choix = JOptionPane.showOptionDialog(view,
                    "Que voulez-vous faire pour " + modele.getNom_model() + " ?",
                    "Modifier Modèle",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (choix == 0) {

                JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
                JTextField idModeleField = new JTextField(String.valueOf(modele.getId_model()));
                JTextField nomModeleField = new JTextField(modele.getNom_model());
                
                panel.add(new JLabel("ID du modèle :"));
                panel.add(idModeleField);
                panel.add(new JLabel("Nom du modèle :"));
                panel.add(nomModeleField);

                int option = JOptionPane.showConfirmDialog(view, panel, "Modifier Modèle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (option == JOptionPane.OK_OPTION) {
                    String nouveauNom = nomModeleField.getText();
                    String nouvelId = idModeleField.getText();

                    if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
                        for (Modele m : magasin.getallmodeles()) {
                        if (m != modele && m.getNom_model().equalsIgnoreCase(nouveauNom.trim())) {
                            JOptionPane.showMessageDialog(view, "Un autre modèle a déjà ce nom.");
                            return;
                        }
                    }  
                        modele.setNom_model(nouveauNom.trim());
                    }if (nouvelId != null && !nouvelId.trim().isEmpty()) {
                        try {
                            int id = Integer.parseInt(nouvelId.trim());
                            for (Modele m : magasin.getallmodeles()) {
                                if (m != modele && m.getId_model() == id) {
                                    JOptionPane.showMessageDialog(view, "Un autre modèle a déjà cet ID.");
                                    return;
                                }
                            }
                            modele.setId_model(id);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(view, "L'ID doit être un entier.");
                        }
                    }
                }
            }else if (choix == 1) {
                views.PermisModeleview permisView = new views.PermisModeleview(modele);
                controllers.PermisModeleController.init(permisView, modele);
                permisView.setVisible(true);
            }
            updateModelesList();
        });

        view.getAjouterMarqueBtn().addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField idField = new JTextField();
            JTextField nomField = new JTextField();

            panel.add(new JLabel("ID de la marque :"));
            panel.add(idField);
            panel.add(new JLabel("Nom de la marque :"));
            panel.add(nomField);

            int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter une Marque", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String idText = idField.getText().trim();
                String nom = nomField.getText().trim();

                if (idText.isEmpty() || nom.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "L'ID et le nom ne peuvent pas être vides.");
                    return;
                }

                int id;
                try {
                    id = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "L'ID doit être un nombre entier.");
                    return;
                }

                for (Marque m : magasin.getMarques()) {
                    if (m.getId_marque() == id) {
                        JOptionPane.showMessageDialog(view, "Cet ID est déjà utilisé.");
                        return;
                    }
                    if (m.getNom_marque().equalsIgnoreCase(nom)) {
                        JOptionPane.showMessageDialog(view, "Ce nom de marque est déjà utilisé.");
                        return;
                    }
                }

                Marque nouvelleMarque = new Marque(id, nom);
                magasin.getMarques().add(nouvelleMarque);
                view.getModelMarques().addElement(nouvelleMarque.getNom_marque());
            }
        });

        view.getSupprimerMarqueBtn().addActionListener(e -> {
            int index = view.getListMarques().getSelectedIndex();
            if (index == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez une marque à supprimer.");
                return;
            }
            Marque m = magasin.getMarques().get(index);
            int conf = JOptionPane.showConfirmDialog(view,
                    "Voulez-vous vraiment supprimer la marque :\nID = " + m.getId_marque() + "\nNom = " + m.getNom_marque() + "?",
                    "Confirmer suppression",
                    JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                magasin.getMarques().remove(index);
                view.getModelMarques().remove(index);
                updateModelesList();
            }
        });

        view.getAjouterModeleBtn().addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
            JComboBox<String> comboMarques = new JComboBox<>();
            for (Marque marque : magasin.getMarques()) {
                comboMarques.addItem(marque.getNom_marque());
            }

            String[] permisOptions = {"A", "B", "A1", "A2", "AM"};
            JComboBox<String> comboPermis = new JComboBox<>(permisOptions);

            JTextField idModeleField = new JTextField();
            JTextField nomModeleField = new JTextField();

            panel.add(new JLabel("Marque :"));
            panel.add(comboMarques);
            panel.add(new JLabel("Permis nécessaire :"));
            panel.add(comboPermis);
            panel.add(new JLabel("ID du modèle :"));
            panel.add(idModeleField);
            panel.add(new JLabel("Nom du modèle :"));
            panel.add(nomModeleField);

            int result = JOptionPane.showConfirmDialog(view, panel, "Ajouter un Modèle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int selectedIndexMarque = comboMarques.getSelectedIndex();
                if (selectedIndexMarque == -1) {
                    JOptionPane.showMessageDialog(view, "Sélectionnez une marque.");
                    return;
                }
                String idText = idModeleField.getText().trim();
                String nomModele = nomModeleField.getText().trim();
                String permis = (String) comboPermis.getSelectedItem();

                if (idText.isEmpty() || nomModele.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "L'ID et le nom du modèle ne peuvent pas être vides.");
                    return;
                }
                int idModele;
                try {
                    idModele = Integer.parseInt(idText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "L'ID du modèle doit être un nombre entier.");
                    return;
                }

                Marque marqueChoisie = magasin.getMarques().get(selectedIndexMarque);

                for (Modele m : magasin.getallmodeles()) {
                    if (m.getId_model() == idModele) {
                        JOptionPane.showMessageDialog(view, "Cet ID de modèle est déjà utilisé.");
                        return;
                    }
                    if (m.getNom_model().equalsIgnoreCase(nomModele)) {
                        JOptionPane.showMessageDialog(view, "Ce nom de modèle est déjà utilisé.");
                        return;
                    }
                }
                Modele nouveauModele = new Modele(idModele, nomModele, marqueChoisie);
                Permis newpermis = Permis.getPermisByNom(permis);
                nouveauModele.addPermis(newpermis);
                updateModelesList();
            }
        });

        view.getSupprimerModeleBtn().addActionListener(e -> {
            int indexMarque = view.getListMarques().getSelectedIndex();
            int indexModele = view.getListModeles().getSelectedIndex();
            if (indexMarque == -1 || indexModele == -1) {
                JOptionPane.showMessageDialog(view, "Sélectionnez un modèle à supprimer.");
                return;
            }
            Marque marque = magasin.getMarques().get(indexMarque);
            Modele modele = marque.getModeles().get(indexModele);

            int conf = JOptionPane.showConfirmDialog(view,
                    "Voulez-vous vraiment supprimer le modèle : ID = " + modele.getId_model() + " Nom = " + modele.getNom_model() + "?",
                    "Confirmer suppression",
                    JOptionPane.YES_NO_OPTION);

            if (conf == JOptionPane.YES_OPTION) {
                marque.getModeles().remove(indexModele);
                updateModelesList();
            }
        });
    }

    public void updateModelesList() {
        view.getModelModeles().clear();
        int indexMarque = view.getListMarques().getSelectedIndex();
        if (indexMarque != -1) {
            Marque marque = magasin.getMarques().get(indexMarque);
            for (Modele modele : marque.getModeles()) {
                view.getModelModeles().addElement(modele.getNom_model());
            }
        }
    }

    public void updateMarquesList() {
        view.getModelMarques().clear();
        for (Marque marque : magasin.getMarques()) {
            view.getModelMarques().addElement(marque.getNom_marque());
        }
    }

}
