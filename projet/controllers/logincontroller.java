package controllers;
import views.*;
import models.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public class logincontroller {
    Magasin magasin;
    Vector<Client> clients;

    public logincontroller(Magasin magasin) {
        this.magasin = magasin;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void initListeners(loginview loginFrame, JButton connexion, JButton retour, Vector<Client> clients) {
        this.clients = clients;

        connexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String valentr = loginFrame.getidField().trim();

                if (valentr.isEmpty()) {
                    JOptionPane.showMessageDialog(loginFrame, "Veuillez entrer un identifiant ou un prénom.");
                    return;
                }

                Client client = client_trouver(valentr);
                if (client != null) {
                    JOptionPane.showMessageDialog(loginFrame, "Connexion réussie pour : " + client.getNom());
                    loginFrame.dispose();
                    new ClientController(client, magasin); 
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Client introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        retour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                new Interface_principale(new InterfaceController(magasin)).setVisible(true);
            }
        });
    }

    public Client client_trouver(String valentr) {
        for (Client c : clients) {
            if (String.valueOf(c.getId_client()).equals(valentr) || c.getNom().equalsIgnoreCase(valentr)) {
                return c;
            }
        }
        return null;
    }
}
