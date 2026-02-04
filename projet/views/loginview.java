package views;
import controllers.logincontroller;
import models.Client;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import models.Magasin;
public class loginview extends JFrame {


private JButton connexion;
private JButton retour;
private JTextField idField;
private Vector<Client> clients;
public logincontroller controller;

public loginview(Vector<Client> clients, Magasin magasin) {
    this.clients = clients; 
    this.controller = new logincontroller(magasin);
    setTitle("Connexion - Loc2Scoot");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setLocationRelativeTo(null);

    initUI();
    setVisible(true);
}

public void initUI() {
    idField = new JTextField();
    connexion = new JButton("Se connecter");
    retour = new JButton("Retour");
    

    setLayout(new GridLayout(5, 1, 10, 10));
    add(new JLabel("Entrez votre identifiant ou prénom", SwingConstants.CENTER));
    add(idField);
    add(connexion);
    add(retour);
    
    controller.initListeners(this, connexion, retour, clients);
}
    public String getidField() {
        return idField.getText();
    }

    public void resetFields() {
        idField.setText("");
    }

}