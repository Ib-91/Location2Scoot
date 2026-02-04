package controllers;

import models.Client;
import models.Permis;
import views.PermisClientView;

import javax.swing.*;
import java.awt.*;

public class PermisClientController {

    public static void init(PermisClientView view, Client client) {
        reloadPermis(view, client);

        view.ajouterPermisBtn.addActionListener(e -> {
            String selected = (String) view.permisComboBox.getSelectedItem();
            Permis newPermis = Permis.getPermisByNom(selected);
            
            if (client.getPermis().contains(newPermis)) {
                JOptionPane.showMessageDialog(view, "Permis déjà acquis !");
            } else {
                client.addPermis(newPermis);
                reloadPermis(view, client);
            }
        });
    }

    public static void reloadPermis(PermisClientView view, Client client) {
        view.permisListPanel.removeAll();

        for (Permis permis : client.getPermis()) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel permisLabel = new JLabel(permis.getNom_permis());
            JButton supprimerBtn = new JButton("Supprimer");
            JButton detailsBtn = new JButton("Détails");

            row.add(permisLabel);
            row.add(supprimerBtn);
            row.add(detailsBtn);

            supprimerBtn.addActionListener(e -> {
                client.removePermis(permis);
                reloadPermis(view, client);
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
