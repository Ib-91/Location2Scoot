package controllers;

import models.Client;
import views.Gestionclientview;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class GestionClientController {

    static Client selectedClient = null;
    static String valeurFiltre = "";

    public static void init(Gestionclientview view, Vector<Client> clients) {
        reloadClientlist(view, clients);

        view.retourBtn.addActionListener(e -> {
            view.dispose();
            JFrame parent = view.getParentFrame();
            if (parent != null) parent.setVisible(true);
        });

        view.searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search_name(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search_name(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search_name(); }

            public void search_name() {
                String search = view.searchField.getText().toLowerCase().trim();
                Vector<Client> trouve = new Vector<>();
                for (Client c : clients) {
                    if (c.getNom().toLowerCase().contains(search)) {
                        trouve.add(c);
                    }
                }
                reloadClientlist(view, trouve);
            }
        });

        view.critereComboBox.addActionListener(e -> {
            view.critereValeurPanel.removeAll();
            String critere = (String) view.critereComboBox.getSelectedItem();

            if ("Aucun".equals(critere)) {
                valeurFiltre = "";
                applyFilter(view, clients);
            } else if ("Permis".equals(critere)) {
                JComboBox<String> permisCombo = new JComboBox<>(new String[]{"A", "A1", "A2", "AM", "B"});
                view.critereValeurPanel.add(permisCombo);

                permisCombo.addActionListener(evt -> {
                    valeurFiltre = (String) permisCombo.getSelectedItem();
                    applyFilter(view, clients);
                });

            } else {
                JTextField valueField = new JTextField(5);//Taille du champ texte
                view.critereValeurPanel.add(valueField);

                valueField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                    public void update() {
                        valeurFiltre = valueField.getText().trim();
                        applyFilter(view, clients);
                    }
                    public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
                    public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
                    public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
                });
            }
            view.critereValeurPanel.revalidate();
            view.critereValeurPanel.repaint();
        });

        view.ajouterClientBtn.addActionListener(e -> {
            String nom = JOptionPane.showInputDialog(view, "Nom du nouveau client :");
            if (nom != null && !nom.trim().isEmpty()) {
                Client newClient = new Client(nom.trim());
                clients.add(newClient);
                reloadClientlist(view, clients);
            }
        });

        view.supprimerClientBtn.addActionListener(e -> {
            if (selectedClient != null) {
                int confirm = JOptionPane.showConfirmDialog(view, "Supprimer le client : " + selectedClient.getNom() + " ?");
                if (confirm == JOptionPane.YES_OPTION) {
                    clients.remove(selectedClient);
                    selectedClient = null;
                    reloadClientlist(view, clients);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Veuillez sélectionner un client.");
            }
        });
    }

    public static void reloadClientlist(Gestionclientview view, Vector<Client> clients) {
        view.clientListPanel.removeAll();

        for (Client client : clients) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Ligne client
            JLabel nameLabel = new JLabel(client.getNom());

            JButton detailsBtn = new JButton("Détails");
            JButton modifierBtn = new JButton("Modifier");

            row.add(nameLabel);
            row.add(detailsBtn);
            row.add(modifierBtn);

            row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            row.setBackground(selectedClient == client ? Color.lightGray : view.getBackground());

            row.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    selectedClient = client;
                    reloadClientlist(view, clients);
                }
            });

            detailsBtn.addActionListener(e -> {
                String details = client.details_client();
                JOptionPane.showMessageDialog(view, details);
            });

            modifierBtn.addActionListener(e -> {
                String[] options = {"Modifier le nom", "Modifier les permis"};
                int choix = JOptionPane.showOptionDialog(view,
                        "Que voulez-vous faire pour " + client.getNom() + " ?",
                        "Modifier client",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choix == 0) {
                    String nouveauNom = JOptionPane.showInputDialog(view, "Modifier le nom :", client.getNom());
                    if (nouveauNom != null && !nouveauNom.trim().isEmpty()) {
                        client.setNom(nouveauNom.trim());
                        reloadClientlist(view, clients);
                    }
                } else if (choix == 1) {
                    views.PermisClientView permisView = new views.PermisClientView(client);
                    controllers.PermisClientController.init(permisView, client);
                    permisView.setVisible(true);
                }
            });

            view.clientListPanel.add(row);
        }
        view.clientListPanel.revalidate();
        view.clientListPanel.repaint();
    }

    public static void applyFilter(Gestionclientview view, Vector<Client> clients) {
        String critere = (String) view.critereComboBox.getSelectedItem();
        Vector<Client> filteredClients = new Vector<>();
        if ("Aucun".equals(critere)) {
            filteredClients = clients;
        } else {
            for (Client client : clients) {
                if ("Permis".equals(critere) && client.getPermisByNom(valeurFiltre) != null) {
                    filteredClients.add(client);
                } else if ("Nombre de locations".equals(critere) && client.nbclient_loc() == parseIntSafe(valeurFiltre)) {
                    filteredClients.add(client);
                } else if ("KM parcourus".equals(critere) && client.km_client() == parseIntSafe(valeurFiltre)) {
                    filteredClients.add(client);
                } else if ("Somme dépensée".equals(critere) && client.depense_client() == parseDoubleSafe(valeurFiltre)) {
                    filteredClients.add(client);
                }
            }
        }
        reloadClientlist(view, filteredClients);
    }

    public static int parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return -1; }
    }
    public static double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return -1; }
    }
}