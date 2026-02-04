package controllers;

import models.*;
import views.*;

import javax.swing.*;

public class InfoParcController {

    private final Magasin magasin;
    private final Info_Parcview infoParcView;
    JFrame parentFrame;


    public InfoParcController(Magasin magasin, JFrame parentFrame) {
        this.magasin = magasin;
        this.parentFrame = parentFrame;
        
        this.infoParcView = new Info_Parcview(magasin, parentFrame);

        infoParcView.retourButton.addActionListener(e -> {
            infoParcView.dispose();
            parentFrame.setVisible(true);
        });
        new ParcController(magasin, infoParcView.getParcView());
        infoParcView.setVisible(true);
    }

}


