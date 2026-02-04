package views;

import javax.swing.*;
import java.awt.*;

public class Gestionlocview extends JFrame {
    public JPanel locationListPanel;
    public JScrollPane scrollPane;

    public JButton louerButton;
    public JButton retourButton;
    public JButton retourEffectue;

    public Gestionlocview(JFrame parentFrame) {
        setTitle("Gestion des Locations");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel titrePanel = new JPanel();
        titrePanel.setLayout(new BoxLayout(titrePanel, BoxLayout.Y_AXIS));
        JLabel titre = new JLabel("Locations en Cours");
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titrePanel.add(Box.createVerticalStrut(10));
        titrePanel.add(titre);
        titrePanel.add(Box.createVerticalStrut(10));
        add(titrePanel, BorderLayout.NORTH);

        locationListPanel = new JPanel();
        locationListPanel.setLayout(new BoxLayout(locationListPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(locationListPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        louerButton = new JButton("Effectuer une location");
        retourEffectue = new JButton("Effectuer un retour");

        rightPanel.add(Box.createVerticalStrut(50));
        rightPanel.add(louerButton);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(retourEffectue);
        rightPanel.add(Box.createVerticalGlue());


        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        retourButton = new JButton("Retour");
        bottomPanel.add(retourButton);
        rightPanel.add(bottomPanel);

        add(rightPanel, BorderLayout.EAST);
    }
}
