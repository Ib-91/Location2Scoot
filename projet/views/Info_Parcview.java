package views;

import models.*;

import javax.swing.*;

import controllers.*;

import java.awt.*;

public class Info_Parcview extends JFrame {

    public JButton retourButton;
    private final JTabbedPane tabbedPane;
    private final GestionParcview parcView;
    GestionMarqueController marqueController;

    public Info_Parcview(Magasin magasin, JFrame parentFrame) {
        setTitle("Parc de scooters - Location 2 Scoot");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Parc de scooters", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        JPanel infosParcPanel = new JPanel();
        infosParcPanel.setLayout(new BoxLayout(infosParcPanel, BoxLayout.Y_AXIS));
        infosParcPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        String[] lignes = magasin.magasin_details().split("\n");
        for (String ligne : lignes) {
            JLabel infoLabel = new JLabel(ligne);
            infosParcPanel.add(infoLabel);
        }

        JScrollPane scrollPane = new JScrollPane(infosParcPanel);
        scrollPane.setBorder(null);

        retourButton = new JButton("Retour");
        JPanel retourPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        retourPanel.add(retourButton);

        JPanel tabContent = new JPanel(new BorderLayout());
        tabContent.add(scrollPane, BorderLayout.CENTER);
        tabContent.add(retourPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Infos Parc", tabContent);

        parcView = new GestionParcview(magasin);
        tabbedPane.addTab("Parc de scooter", parcView);

        GestionMarqueController marqueController = new GestionMarqueController(magasin);
        tabbedPane.addTab("Gestion Marque", marqueController.getView());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public GestionParcview getParcView() {
        return parcView;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
