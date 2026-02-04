package controllers;

import views.*;
import models.*;
import javax.swing.*;
import java.util.*;


public class GestionController {

    Gestionview gestionView;
    Vector<Client> clients;
    Vector<models.Scooter> scooters;
    Vector<models.Location> locations;
    Magasin magasin;

    InterfaceController parentController;

    public GestionController(Magasin magasin, InterfaceController parentController) {
        this.magasin = magasin;
        this.parentController = parentController;
        this.gestionView = new Gestionview();
        
        initListeners(gestionView, gestionView.ClientButton, gestionView.ParcScooterButton, gestionView.retourButton, gestionView.LocationsButton);
        gestionView.setVisible(true);
    }

    public void initListeners(JFrame mainFrame, JButton ClientButton, JButton ParcScooterButton, JButton retourButton, JButton LocationsButton) {
        
        ClientButton.addActionListener(e -> {
            gestionView.dispose();
            new Gestionclientview(magasin.getClients(), gestionView).setVisible(true);        
        });

        ParcScooterButton.addActionListener(e -> {
            gestionView.dispose();
            new InfoParcController(magasin, gestionView);
        });

        LocationsButton.addActionListener(e -> {
            gestionView.dispose();
            new LocationController(magasin, mainFrame); 
        });

        gestionView.retourButton.addActionListener(e -> {
            gestionView.dispose();            
            new Interface_principale(new InterfaceController(magasin)).setVisible(true);
        });
        
     }
}
