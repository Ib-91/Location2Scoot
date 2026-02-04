package controllers;

import views.Clientview;
import views.Interface_principale;
import models.Client;
import models.Magasin;

import javax.swing.*;

public class ClientController {

    private final Client client;
    private final Magasin magasin;
    private final Clientview clientView;

    public ClientController(Client client, Magasin magasin) {
        this.client = client;
        this.magasin = magasin;
        this.clientView = new Clientview(client);

        initListeners();
        clientView.setVisible(true);
    }

    private void initListeners() {

        clientView.infoButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(clientView, client.details_client());
        });

        clientView.historiqueScooterButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(clientView, client.historique_loc());
        });

        clientView.retourAccueilButton.addActionListener(e -> {
            clientView.dispose();
            new Interface_principale(new InterfaceController(magasin)).setVisible(true);
        });
    }
}
