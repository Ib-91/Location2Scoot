package views;
import javax.swing.*;
import java.awt.*;


public class Gestionview extends JFrame {

    public JButton ClientButton;
    public JButton ParcScooterButton;
    public JButton retourButton;
    public JButton LocationsButton;

    public Gestionview() {
        setTitle("Espace Gestion - Location 2 Scoot");
        setSize(450, 400);
        setLocationRelativeTo(null);

        ClientButton = new JButton("Client");
        ParcScooterButton = new JButton("Parc de Scooters");
        LocationsButton = new JButton("Locations");
        retourButton = new JButton("Retour à l'accueil");
        

        setLayout(new GridLayout(8, 1, 10, 10));
        add(new JLabel("Espace Gestionnaire", SwingConstants.CENTER));
        add(ClientButton);
        add(ParcScooterButton);
        add(LocationsButton);
        add(retourButton);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
}
