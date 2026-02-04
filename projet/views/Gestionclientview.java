package views;

import models.Client;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Gestionclientview extends JFrame {
    JFrame parentFrame;
    public JComboBox<String> critereComboBox;
    public JPanel critereValeurPanel;

    public JTextField searchField;
    public JPanel clientListPanel;
    public JButton ajouterClientBtn;

    public JButton supprimerClientBtn;
    public JScrollPane scrollPane;
    public JButton retourBtn;


    public Gestionclientview(Vector<Client> clients, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setTitle("Gestion des clients");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());


        clientListPanel = new JPanel();
        clientListPanel.setLayout(new BoxLayout(clientListPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(clientListPanel);

        add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        ajouterClientBtn = new JButton("Ajouter un client");
        supprimerClientBtn = new JButton("Supprimer un client");

        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(ajouterClientBtn);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(supprimerClientBtn);
        rightPanel.add(Box.createVerticalGlue());

        add(rightPanel, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        topPanel.add(new JLabel("Rechercher : "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel filtrePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filtreLabel = new JLabel("Filtrer par :");

        String[] criteres = {"Aucun", "Permis", "Nombre de locations", "KM parcourus", "Somme dépensée"};
        critereComboBox = new JComboBox<>(criteres);
        filtrePanel.add(filtreLabel);
        filtrePanel.add(critereComboBox);

        critereValeurPanel = new JPanel();
        filtrePanel.add(critereValeurPanel);
        topPanel.add(filtrePanel, BorderLayout.SOUTH); 
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourBtn = new JButton("Retour");
        bottomPanel.add(retourBtn);
        add(bottomPanel, BorderLayout.SOUTH);


        controllers.GestionClientController.init(this, clients);
    }
    public JFrame getParentFrame() {
        return parentFrame;
    }
}
