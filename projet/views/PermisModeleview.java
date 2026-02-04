package views;

import models.*;

import javax.swing.*;
import java.awt.*;

public class PermisModeleview extends JFrame {

    public JPanel permisListPanel;
    public JComboBox<String> permisComboBox;
    public JButton ajouterPermisBtn;
    public JLabel titreLabel;

    public PermisModeleview(Modele modele) {
        setTitle("Permis du modèle " + modele.getNom_model());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        titreLabel = new JLabel("Permis pour " + modele.getNom_model(), SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titreLabel, BorderLayout.NORTH);

        permisListPanel = new JPanel();
        permisListPanel.setLayout(new BoxLayout(permisListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(permisListPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        permisComboBox = new JComboBox<>(new String[]{"A", "A1", "A2", "AM", "B"});
        ajouterPermisBtn = new JButton("Ajouter");
        bottomPanel.add(permisComboBox);
        bottomPanel.add(ajouterPermisBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}

