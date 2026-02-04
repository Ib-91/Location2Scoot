package controllers;

import views.*;
import models.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceController {
    Magasin magasin;

    public InterfaceController(Magasin magasin) {
        this.magasin = magasin;
    }
    
    public void initListeners(JFrame mainFrame, JButton gestion, JButton connexion) {
        connexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new loginview(magasin.getClients(), magasin).setVisible(true);
            }
        });

        gestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                new GestionController(magasin, InterfaceController.this); 
            }
        });
}
}
