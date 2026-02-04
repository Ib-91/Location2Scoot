package models;
import java.util.*;

public class Modele {

    public int id_model;
    public String nom_model;
    Marque marque;

    Vector<Scooter> scooters = new Vector<Scooter>();
    Vector<Permis> permis = new Vector<Permis>();
    
    public Modele(int id, String name, Marque m) {
        id_model = id;
        nom_model = name;
        marque=m;
        m.addModele(this);
    }

    public int getId_model() {
        return id_model;
    }

    public void setId_model(int newid) {
        id_model = newid;
    }

    public String getNom_model() {
        return nom_model;
    }

    public void setNom_model(String newnom) {
        nom_model = newnom;
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public void setScooters(Vector<Scooter> newscooters) {
        scooters = newscooters;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque newmarque) {
        marque = newmarque;
    }

    public Vector<Permis> getPermis() {
        return permis;
    }

    public void setPermis(Vector<Permis> newpermis) {
        permis = newpermis;
    }

    public boolean permis_requis(String permisnom) {
        if (permis.isEmpty()){
            return true;
        }
        Permis val = Permis.getPermisByNom(permisnom);
        if (val == null) {
            return false;
        }
        return true;
    }

    public void addScooter(Scooter s) {
        if(!scooters.contains(s)) {
            scooters.add(s);
        }
    }
    public void addPermis(Permis p) {
        if(!permis.contains(p)) {
            permis.add(p);
        }
    }
    
    public void removeScooter(Scooter s) {
        scooters.remove(s);
        s.setModele(null);
    }
    public void removePermis(Permis p) {
        permis.remove(p);
        p.removeModele(this);
    }

    public String toStringln(String s) {
        return s + "\n";
    }
    
    public String toString() {
        return this.getNom_model();
    }

    public String permis_necessaire() {
        String pr = toStringln("Permis requis pour conduire ce modèle :");
        for (Permis p : permis) {
            pr+= toStringln("- " + p.getNom_permis());
        }
        return pr;
    }

    public int modele_dispo() {
        int dispoCount = 0;
        for (Scooter s : scooters) {
            if (s.estDispo()) {
                dispoCount++;
            }
        }
        return dispoCount;
    }

    public String details_modele() {
        String details= ("--------------Détails Modele :------------------ \n");
        details+= toStringln("ID : " + id_model);
        details+= toStringln("Nom : " + nom_model);
        details+= toStringln("Marque : " + marque.getNom_marque());
        details+= permis_necessaire();
        details+= toStringln("Nombre de scooters disponibles : " + modele_dispo());
        return details;   
    }
}