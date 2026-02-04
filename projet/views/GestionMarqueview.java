package views;

import javax.swing.*;
import java.awt.*;
import models.*;

public class GestionMarqueview extends JPanel {

    JList<String> listMarques;
    DefaultListModel<String> modelMarques;

    JList<String> listModeles;
    DefaultListModel<String> modelModeles;

    JButton detailsMarqueBtn;
    JButton modifierMarqueBtn;

    JButton detailsModeleBtn;
    JButton modifierModeleBtn;

    JButton ajouterMarqueBtn;
    JButton ajouterModeleBtn;

    JButton supprimerMarqueBtn;
    JButton supprimerModeleBtn;

    public GestionMarqueview(Magasin magasin) {
        setLayout(new BorderLayout(10, 10));

        JPanel marquePanel = new JPanel(new BorderLayout(5, 5));
        modelMarques = new DefaultListModel<>();
        for (Marque m : magasin.getMarques()) {
            modelMarques.addElement(m.getNom_marque());
        }
        listMarques = new JList<>(modelMarques);
        listMarques.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        marquePanel.add(new JLabel("Marques:"), BorderLayout.NORTH);
        marquePanel.add(new JScrollPane(listMarques), BorderLayout.CENTER);

        JPanel marqueBtnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        detailsMarqueBtn = new JButton("Détails Marque");
        modifierMarqueBtn = new JButton("Modifier Marque");
        marqueBtnPanel.add(detailsMarqueBtn);
        marqueBtnPanel.add(modifierMarqueBtn);
        marquePanel.add(marqueBtnPanel, BorderLayout.SOUTH);

        JPanel modelePanel = new JPanel(new BorderLayout(5, 5));
        modelModeles = new DefaultListModel<>();
        listModeles = new JList<>(modelModeles);
        listModeles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modelePanel.add(new JLabel("Modèles:"), BorderLayout.NORTH);
        modelePanel.add(new JScrollPane(listModeles), BorderLayout.CENTER);

        JPanel modeleBtnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        detailsModeleBtn = new JButton("Détails Modèle");
        modifierModeleBtn = new JButton("Modifier Modèle");
        modeleBtnPanel.add(detailsModeleBtn);
        modeleBtnPanel.add(modifierModeleBtn);
        modelePanel.add(modeleBtnPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(marquePanel);
        centerPanel.add(modelePanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        ajouterMarqueBtn = new JButton("Ajouter une Marque");
        ajouterModeleBtn = new JButton("Ajouter un Modèle");
        supprimerMarqueBtn = new JButton("Supprimer une Marque");
        supprimerModeleBtn = new JButton("Supprimer un Modèle");

        rightPanel.add(ajouterMarqueBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(ajouterModeleBtn);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(supprimerMarqueBtn);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(supprimerModeleBtn);
        rightPanel.add(Box.createVerticalStrut(30));

        add(rightPanel, BorderLayout.EAST);
    }

    public JList<String> getListMarques() { return listMarques; }
    public DefaultListModel<String> getModelMarques() { return modelMarques; }

    public JList<String> getListModeles() { return listModeles; }
    public DefaultListModel<String> getModelModeles() { return modelModeles; }

    public JButton getDetailsMarqueBtn() { return detailsMarqueBtn; }
    public JButton getModifierMarqueBtn() { return modifierMarqueBtn; }

    public JButton getDetailsModeleBtn() { return detailsModeleBtn; }
    public JButton getModifierModeleBtn() { return modifierModeleBtn; }

    public JButton getAjouterMarqueBtn() { return ajouterMarqueBtn; }
    public JButton getAjouterModeleBtn() { return ajouterModeleBtn; }

    public JButton getSupprimerMarqueBtn() { return supprimerMarqueBtn; }
    public JButton getSupprimerModeleBtn() { return supprimerModeleBtn; }
}
