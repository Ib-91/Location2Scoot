package models;

import java.util.*;

public class Permis {

    public int Type_permis;
    public String nom_permis;
    Vector<Client> clients = new Vector<Client>();
    Vector<Modele> modeles = new Vector<Modele>();

    static Vector<Permis> allPermis = new Vector<>();

    static {
        allPermis.add(new Permis(1, "A"));
        allPermis.add(new Permis(2, "B"));
        allPermis.add(new Permis(3, "A1"));
        allPermis.add(new Permis(4, "A2"));
        allPermis.add(new Permis(5, "AM"));
    }

    public Permis(int id, String name) {
        Type_permis = id;
        nom_permis = name;
    }

    public int getType_permis() {
        return Type_permis;
    }

    public void setType_permis(int newtype_permis) {
        Type_permis = newtype_permis;
    }

    public String getNom_permis() {
        return nom_permis;
    }

    public void setNom_permis(String newnom_permis) {
        nom_permis = newnom_permis;
    }

    public Vector<Client> getClients() {
        return clients;
    }

    public void setClients(Vector<Client> newclients) {
        clients = newclients;
    }

    public Vector<Modele> getModeles() {
        return modeles;
    }

    public void setModeles(Vector<Modele> newmodeles) {
        modeles = newmodeles;
    }

    public static Vector<Permis> getallpermis(){
        return allPermis;
    }

    public void addClient(Client c) {
        if(!clients.contains(c)){
            clients.add(c);
            c.addPermis(this);
        }
    }

    public void addModele(Modele m) {
        if(!modeles.contains(m)){
            modeles.add(m);
            m.addPermis(this);
        }
    }

    public void removeClient(Client c) {
        clients.remove(c);
    }

    public void removeModele(Modele m) {
        modeles.remove(m);
    }

    public String toStringln(String s) {
        return s + "\n";
    }

    public boolean permis_modele(Modele m){
        for (Modele mod : modeles) {
            if (mod.getId_model() == m.getId_model()) {
                return true;
            }
        }
        return false;
    }

    public static Permis getPermisByNom(String nom) {
        for (Permis p : allPermis) {
            if (p.getNom_permis().equals(nom)) {
                return p;
            }
        }
        return null;
    }

    public String permis_details() {
        String details = ("--------------Détails du permis :------------------\n");
        details+= toStringln("Type de permis: " + Type_permis);
        details+= toStringln("Nom du permis: " + nom_permis);
        details+= toStringln("Modeles de scooter necessitant ce permis: ");
        for (Modele m : modeles) {
            details+= (m.getNom_model());
        }
        return details;
    }
}