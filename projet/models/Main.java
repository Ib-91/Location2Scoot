package models;

import java.util.*;
import javax.swing.SwingUtilities;
import controllers.InterfaceController;
import views.Interface_principale;


public class Main {
public static void main(String[] args) {
        Magasin m1 = new Magasin(1, "LOCATION2SCOOT");

        Marque marque1= new Marque(1, "Kawasaki");
        Marque marque2= new Marque(2, "Yamaha");
        Marque marque3= new Marque(3, "Honda");
        Marque marque4= new Marque(4, "Suzuki");
        Marque marque5= new Marque(5, "KTM");
        
        m1.addMarque(marque1);
        m1.addMarque(marque2);
        m1.addMarque(marque3);
        m1.addMarque(marque4);
        m1.addMarque(marque5);
        
        
        Modele modele1 = new Modele(1, "ZII", marque1);
        Modele modele2 = new Modele(2, "MT-07", marque2);
        Modele modele3 = new Modele(3, "T-MAX", marque2);
        Modele modele4 = new Modele(4, "GSX-R1000", marque4);
        Modele modele5 = new Modele(5, "390 Duke", marque5);
        Modele modele6 = new Modele(6, "Ninja H2", marque1);
        Modele modele7 = new Modele(7, "R1", marque2);
        Modele modele8 = new Modele(8, "CB500X", marque3);
        Modele modele9 = new Modele(9, "V-Strom 650", marque4);
        Modele modele10 = new Modele(10, "Duke 790", marque5);
        

        Scooter s1 = new Scooter(1, true, 100, m1, modele1);
        Scooter s2 = new Scooter(2, true, 150, m1, modele2);
        Scooter s3 = new Scooter(3, true, 125, m1, modele3);
        Scooter s4 = new Scooter(4, true, 200, m1, modele4);
        Scooter s5 = new Scooter(5, true, 100, m1, modele5);
        Scooter s6 = new Scooter(6, true, 125, m1, modele6);
        Scooter s7 = new Scooter(7, true, 150, m1, modele7);
        Scooter s8 = new Scooter(8, true, 50, m1, modele8);
        Scooter s9 = new Scooter(9, true, 200, m1, modele9);
        Scooter s10 = new Scooter(10, true, 125, m1, modele10);
        
        m1.addScooter(s1);
        m1.addScooter(s2);
        m1.addScooter(s3);
        m1.addScooter(s4);
        m1.addScooter(s5);
        m1.addScooter(s6);
        m1.addScooter(s7);
        m1.addScooter(s8);
        m1.addScooter(s9);
        m1.addScooter(s10);


        Client c1 = new Client(1, "Onizuka");
        Client c2= new Client(2, "Mister");
        Client c3= new Client(3, "TESTEUR2LOC");
        m1.addClient(c1);
        m1.addClient(c2);
        m1.addClient(c3);


        
        Permis permisA = Permis.getPermisByNom("A");
        Permis permisB = Permis.getPermisByNom("B");
        
        c1.addPermis(permisA);
        c2.addPermis(permisB);
        
        Location l1 = new Location(new Date(), new Date(), null, c1, s1);
        l1.reserverScooter();

        permisA.addModele(modele1);
        permisB.addModele(modele2);
            
        SwingUtilities.invokeLater(() -> 
        new Interface_principale(new InterfaceController(m1)).setVisible(true));    
}
}