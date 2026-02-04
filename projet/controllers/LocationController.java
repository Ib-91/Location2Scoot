package controllers;

import models.*;
import views.*;
import java.awt.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationController {

    Magasin magasin;
    Gestionlocview locationView;
    JFrame parentFrame;

    public LocationController(Magasin magasin, JFrame parentFrame) {
        this.magasin = magasin;
        this.locationView = new Gestionlocview(parentFrame);
        this.parentFrame = parentFrame;
        locationView.setVisible(true);

        initListeners();
        afficherLocationsEnCours();
    }

    public void afficherLocationsEnCours() {
        locationView.locationListPanel.removeAll();


        for (Location loc : magasin.getLocationsEnCours()) {
            JPanel locPanel = new JPanel(new BorderLayout());
            locPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            locPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

            JLabel idLabel = new JLabel("Location N°" + loc.getId_location());
            idLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            JButton detailsBtn = new JButton("Détails");

            detailsBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(locationView, loc.details_location(), "Détails de la Location", JOptionPane.INFORMATION_MESSAGE);
            });

            locPanel.add(idLabel, BorderLayout.WEST);
            locPanel.add(detailsBtn, BorderLayout.EAST);

            locationView.locationListPanel.add(locPanel);
        }

        locationView.locationListPanel.revalidate();
        locationView.locationListPanel.repaint();
    }


    public void initListeners() {
        locationView.louerButton.addActionListener(e -> effectuerLocation());
        locationView.retourEffectue.addActionListener(e -> effectuerRetour());
        locationView.retourButton.addActionListener(e -> RetourController.retour(locationView, parentFrame));
    }

    public void effectuerLocation() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            String idScooterStr = JOptionPane.showInputDialog(locationView, "Entrez l'ID du scooter :");
            if (idScooterStr == null) return;
            int idScooter = Integer.parseInt(idScooterStr);
            Scooter scooter = magasin.ScooterById(idScooter);

            if (scooter == null) {
                JOptionPane.showMessageDialog(locationView, "Erreur : Scooter introuvable.");
                return;
            }

            if (!scooter.dispo) {
                JOptionPane.showMessageDialog(locationView, "Scooter actuellement non disponible.");
                return;
            }

            String idClientStr = JOptionPane.showInputDialog(locationView, "Entrez l'ID du client :");
            if (idClientStr == null) return;
            int idClient = Integer.parseInt(idClientStr);
            Client client = magasin.ClientById(idClient);

            if (client == null) {
                JOptionPane.showMessageDialog(locationView, "Erreur : Client introuvable.");
                return;
            }
            
            Modele scooterModele = scooter.getModele();
            if (!client.peutConduire(scooterModele)) {
                JOptionPane.showMessageDialog(locationView, "Erreur : Le client n'a pas le permis nécessaire pour louer ce scooter.");
                return;
            }

            String dateDebutStr = JOptionPane.showInputDialog(locationView, "Date de début (jj/mm/aaaa) :", sdf.format(new Date()));
            if (dateDebutStr == null) return;
            String dateFinStr = JOptionPane.showInputDialog(locationView, "Date de fin (jj/mm/aaaa) :");
            if (dateFinStr == null) return;

            Date dateDebut = sdf.parse(dateDebutStr);
            Date dateFin = sdf.parse(dateFinStr);

            if (dateDebut.after(dateFin)) {
                JOptionPane.showMessageDialog(locationView, "Erreur : La date de début doit être avant la date de fin.");
                return;
            }

            Location location = magasin.louerScooter(client, idScooter, dateDebut, dateFin);
            JOptionPane.showMessageDialog(locationView, "Location effectuée :\n" + location.details_location());
            afficherLocationsEnCours();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(locationView, "Entrée invalide. Veuillez entrer un nombre.");
        } catch (java.text.ParseException ex) {
            JOptionPane.showMessageDialog(locationView, "Format de date incorrect. Utilisez jj/mm/aaaa.");
        } catch (IllegalStateException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(locationView, "Erreur : " + ex.getMessage());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(locationView, "Erreur inattendue : " + ex.getMessage());
        }
    }

    public void effectuerRetour() {
        try {
            String idScooterStr = JOptionPane.showInputDialog(locationView, "Entrez l'ID du scooter à retourner :");
            if (idScooterStr == null) return;
            int idScooter = Integer.parseInt(idScooterStr);

            String kmStr = JOptionPane.showInputDialog(locationView, "Entrez les kilomètres parcourus :");
            if (kmStr == null) return;
            int kmParcourus = Integer.parseInt(kmStr);

            int endommageOption = JOptionPane.showConfirmDialog(locationView,
                    "Le scooter est-il endommagé ?", "État du scooter",
                    JOptionPane.YES_NO_OPTION);

            if (endommageOption == JOptionPane.CLOSED_OPTION) return;
            boolean endommage = (endommageOption == JOptionPane.YES_OPTION);

            Retour retour = magasin.retourScooter(idScooter, kmParcourus, endommage);
            JOptionPane.showMessageDialog(locationView, "Retour effectuée :\n" + retour.details_retour());
            afficherLocationsEnCours();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(locationView, "Entrée invalide. Veuillez entrer un nombre.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            JOptionPane.showMessageDialog(locationView, "Erreur : " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(locationView, "Erreur inattendue : " + e.getMessage());
        }
    }
}
