package views;
import javax.swing.*;
import java.awt.*;
import controllers.*;


public class Interface_principale extends JFrame {

    public JButton gestionButton;
    public JButton connexionButton;

    public Interface_principale(InterfaceController controller) {
        setTitle("Location 2 Scooter");
        setSize(400, 200);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(240, 250, 230)); 


        JLabel title = new JLabel("LOC2SCOOT", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica", Font.BOLD, 25));
        title.setForeground(new Color(50, 50, 180));
        add(title, BorderLayout.NORTH);

        //Panneau pour les boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));

        gestionButton = new JButton("Gestion");
        connexionButton = new JButton("Connexion");
        

        ImageIcon gestionIcon = new ImageIcon("C:\\Users\\barak\\Documents\\POO\\Projet\\Views\\gestion.jpg");
        ImageIcon clientIcon = new ImageIcon("C:\\Users\\barak\\Documents\\POO\\Projet\\Views\\cliennt.png");
        

        Image gestionImg = gestionIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image clientImg = clientIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        gestionButton.setIcon(new ImageIcon(gestionImg));
        connexionButton.setIcon(new ImageIcon(clientImg));

        gestionButton.setIconTextGap(10);
        connexionButton.setIconTextGap(10);

        buttonPanel.add(gestionButton);
        buttonPanel.add(connexionButton);

        add(buttonPanel, BorderLayout.CENTER);

        controller.initListeners(this, gestionButton, connexionButton);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
