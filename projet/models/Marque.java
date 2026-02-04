package models;
import java.util.*;

public class Marque {
    public int id_marque;
    public String nom_marque;
    
    Vector<Modele> modeles = new Vector<Modele>();
    public Magasin magasin; 

    public Marque(int id, String name) {
        id_marque = id;
        nom_marque = name;
        magasin = null;
    }

    public int getId_marque() {
        return id_marque;
    }

    public void setId_marque(int newid) {
        id_marque = newid;
    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String newnom) {
        nom_marque = newnom;
    }

    public Vector<Modele> getModeles() {
        return modeles;
    }

    public void setModeles(Vector<Modele> newmodeles) {
        modeles = newmodeles;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasins(Magasin newmagasin) {
        magasin = newmagasin;
    }
    
    public void addModele(Modele m) {
        if(!modeles.contains(m)){
            modeles.add(m);
            m.setMarque(this);
        }
    }

    public void removeModele(Modele m) {
        modeles.remove(m);
        m.setMarque(null);
    }

    public String toStringln(String s) {
        return s + "\n";
    } 
    public String toString() {
        return this.getNom_marque();
    }
    
    public int nb_modeles() {
        return modeles.size();
    }

    public String allmodeles() {
        String allm = toStringln("Modeles disponibles: ");
        for (Modele m : modeles) {
            allm+= toStringln("-"+m.getNom_model());
        }
        return allm;
    }

    public Vector <Modele> modelebypermis(Permis p) {
        Vector<Modele> modelesbypermis = new Vector<Modele>();
        for (Modele m : modeles) {
            if (m.getPermis().contains(p)) {
                modelesbypermis.add(m);
            }
        }
        return modelesbypermis;
    }

    public Vector<Modele> modelesByMarque() {
        Vector<Modele> modelesbymarque = new Vector<Modele>();
        for (Modele m : modeles) {
            if (m.getMarque().equals(this)) {
                modelesbymarque.add(m);
            }
        }
        return modelesbymarque;
    }

    public String details_marque() {
        String details= ("--------------Détails Marque :------------------ \n");
        details+= toStringln("Marque: " + nom_marque);
        details+= toStringln("ID: " + id_marque);
        details+= toStringln("Nombre de modeles: " + nb_modeles());
        details+= allmodeles();
        return details;
    }
}