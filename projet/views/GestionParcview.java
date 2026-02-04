package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import models.*;

public class GestionParcview extends JPanel {
    Magasin magasin;
    JTable tableScooters;
    DefaultTableModel tableModel;
    JButton detailsButton;
    JButton Location;
    JButton modifierButton;
    JButton supprimerButton;
    JButton ajouterButton;
    
    JTextField textFieldMarque;
    JTextField textFieldModele;
    JComboBox<String> comboPermis;
    JComboBox<String> comboDispo;

    public GestionParcview(Magasin magasin) {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Gestion du parc de scooters");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new GridLayout(2, 6, 10, 5));

        filterPanel.add(new JLabel("Disponibilité :"));
        comboDispo = new JComboBox<>(new String[] {"Tous", "Disponible", "Indisponible"});
        filterPanel.add(comboDispo);

        filterPanel.add(new JLabel("Marque :"));
        textFieldMarque = new JTextField();
        filterPanel.add(textFieldMarque);
        
        filterPanel.add(new JLabel("Modèle :"));
        textFieldModele = new JTextField();
        filterPanel.add(textFieldModele);
        filterPanel.add(textFieldModele);

        filterPanel.add(new JLabel("Permis :"));
        comboPermis = new JComboBox<>();
        comboPermis.addItem("Tous");
        Vector<Permis> permisList = Permis.getallpermis();
        for (Permis p : permisList) {
            comboPermis.addItem(p.getNom_permis());
        }
        filterPanel.add(comboPermis);

        add(filterPanel, BorderLayout.NORTH);

        String[] colonnes = {"ID", "Marque", "Modèle", "Kilométrage", "Prix", "Disponible"};
        tableModel = new DefaultTableModel(colonnes, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableScooters = new JTable(tableModel);
        tableScooters.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tableScooters);
        add(scrollPane, BorderLayout.CENTER);

        detailsButton = new JButton("Voir Détails");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        ajouterButton = new JButton("Ajouter");
        Location = new JButton("Page Location");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(detailsButton);
        buttonPanel.add(Location);
        buttonPanel.add(modifierButton);
        buttonPanel.add(ajouterButton);
        buttonPanel.add(supprimerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public JTable getTableScooters() { return tableScooters; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getDetailsButton() { return detailsButton; }
    public JButton getLocationButton() { return Location; }
    public JButton getModifierButton() { return modifierButton; }
    public JButton getSupprimerButton() { return supprimerButton; }
    public JButton getAjouterButton() { return ajouterButton; }

    public JComboBox<String> getComboDispo() { return comboDispo; }
    public JTextField getTextFieldMarque() { return textFieldMarque; }
    public JTextField getTextFieldModele() { return textFieldModele; }
    public JComboBox<String> getComboPermis() { return comboPermis; }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addScooterToTable(Scooter s) {
        Object[] row = {
            s.getNum_idt(),
            s.getModele().getMarque().getNom_marque(),
            s.getModele().getNom_model(),
            s.getkm(),
            s.getPrix(),
            s.estDispo() ? "Oui" : "Non"
        };
        tableModel.addRow(row);
    }

    public int getScooterIdAt(int row) {
        return (int) tableModel.getValueAt(row, 0);
    }
}
