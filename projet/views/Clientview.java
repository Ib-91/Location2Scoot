package views;
import javax.swing.*;
import java.awt.*;
import models.*;

public class Clientview extends JFrame {
    public Client client;
    public JButton infoButton;
    public JButton historiqueScooterButton;
    public JButton retourAccueilButton;

    public Clientview(Client client) {
        this.client = client;
        setTitle("Espace Client - Loc2Scoot");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    public void initUI() {
        infoButton = new JButton("Informations détails");
        historiqueScooterButton = new JButton("Historique des scooters loués");
        retourAccueilButton = new JButton("Retour à l'accueil");


        setLayout(new GridLayout(8, 1, 10, 10));

        add(new JLabel("Espace Client - " + client.getNom(), SwingConstants.CENTER));
        
        add(infoButton);
        add(historiqueScooterButton);
        add(retourAccueilButton);
        
    }
}
