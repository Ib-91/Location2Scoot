package controllers;

import models.*;
import views.*;

import javax.swing.*;
import java.awt.*;

public class PermisModeleController {

    public static void init(PermisModeleview view, Modele modele) {
        reloadPermis(view, modele);

        view.ajouterPermisBtn.addActionListener(e -> {
            String selected = (String) view.permisComboBox.getSelectedItem();
            Permis newPermis = Permis.getPermisByNom(selected);
            if (modele.getPermis().contains(newPermis)) {
                JOptionPane.showMessageDialog(view, "Permis déjà associé au modèle !");
            } else {
                modele.addPermis(newPermis);
                reloadPermis(view, modele);
            }
        });
    }

    public static void reloadPermis(PermisModeleview view, Modele modele) {
        view.permisListPanel.removeAll();

        for (Permis permis : modele.getPermis()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel permisLabel = new JLabel(permis.getNom_permis());
            JButton supprimerBtn = new JButton("Supprimer");
            JButton detailsBtn = new JButton("Détails");

            row.add(permisLabel);
            row.add(supprimerBtn);
            row.add(detailsBtn);

            supprimerBtn.addActionListener(e -> {
                modele.removePermis(permis);
                reloadPermis(view, modele);
            });

            detailsBtn.addActionListener(e -> {
                String details = permis.permis_details();
                JOptionPane.showMessageDialog(view, details);
            });

            view.permisListPanel.add(row);
        }

        view.permisListPanel.revalidate();
        view.permisListPanel.repaint();
    }
}

