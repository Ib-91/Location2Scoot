package models;
import java.util.*;

public class Scooter {

    Vector<Location> locations = new Vector<Location>();
    Magasin magasin;
    Modele modele;
    public int num_idt;
    public boolean dispo;
    public double prix;
    public int penalite_pj;

    /**
     * Default constructor
     */
    public Scooter(int id, boolean d, double p, Magasin m, Modele mod, int pen) {
        num_idt = id;
        dispo = d;
        prix = p;
        magasin = m;
        modele = mod;
        penalite_pj = pen;
    }

    public Scooter(int id, boolean d, int p, Magasin m, Modele mod) {
        num_idt = id;
        dispo = d;
        prix = p;
        magasin = m;
        modele = mod;
        penalite_pj = 20;
    }

    public Vector<Location> getLocations() {
        return locations;
    }

    public void setLocations(Vector<Location> newloc) {
        locations = newloc;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin newmag) {
        magasin = newmag;
    }

    public Modele getModele() {
        return modele;
    }

    public void setModele(Modele newmod) {
        modele = newmod;
    }

    public int getNum_idt() {
        return num_idt;
    }

    public void setNum_idt(int newnum_idt) {
        num_idt = newnum_idt;
    }
    public int getPenalite_pj() {
        return penalite_pj;
    }
    public void setPenalite_pj(int newpenalite) {
        penalite_pj = newpenalite;
    }

    public boolean estDispo() {
        return dispo;
    }

    public void setDispo(boolean newdispo) {
        dispo = newdispo;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double newprix) {
        prix = newprix;
    }
    public void addLocation(Location l) {
        locations.add(l);
    }
    public void removeLocation(Location l) {
        locations.remove(l);
    }

    public String toStringln(String s) {
        return s + "\n";
    }

    public int nbscoot_loc() {
        return locations.size();
    }

    public String historique_loc() {
        String hl = toStringln("Locations du scooter N°" + getNum_idt());
        for (Location l : locations) {
            hl+=toStringln("du " + l.getDate_debut() + " au " + l.getDate_fin());
            hl+=toStringln("Id Location: " + l.getId_location()); 
            hl+=toStringln("par le client " + l.getClient().getNom());
        }
        return hl;
    }

    public Vector<Client> ancien_Clients() {
        Vector<Client> historique = new Vector<Client>();
        for (Location loc : getLocations()) {
            historique.add(loc.getClient());
        }
        return historique;
    }

    public int getkm() {
        int km = 0;
        for (Location loc : locations) {
            Retour retour = loc.getRetour();
            if (retour != null) {
                km += retour.getKm_parcourut();
            }
        }
        return km;
    }

    public String dernierEtat() {
        Retour dernierRetour = null;
        for (Location loc : locations) {
            if (loc.getRetour() != null) {
                if (dernierRetour == null || loc.getRetour().getDate_retour().after(dernierRetour.getDate_retour())) {
                    dernierRetour = loc.getRetour();
                }
            }
        }
    
        if (dernierRetour == null) {
            return "Neuf";
        }
        return dernierRetour.getEtat_retour() ? "Bon état" : "Endommagé";
    }
    
    public double argent_genere() {
        double argent = 0;
        for (Location loc : locations) {
            Retour retour = loc.getRetour();
            if (retour != null) {
                double prixBase = loc.getScooter().getPrix();
                double penalite = retour.calculerPenalite();
                argent += prixBase + penalite;
            }
        }
        return argent;
    }
    
    public String details_scooter() {
        String details = ("-----------------Détails du scooter : ------------------ \n");
        details+= toStringln("ID : " + num_idt);
        details+= toStringln("Marque : " + modele.getMarque().getNom_marque());
        details+= toStringln("Modèle : " + modele.getNom_model());
        details+= toStringln("Kilométrage : " + getkm());
        details+= toStringln("Prix : " + prix);
        details+= toStringln("État : " + dernierEtat());
        details+= toStringln("Pénalité par jour : " + penalite_pj);
        details+= toStringln("Disponible : " + (dispo ? "Oui" : "Non"));
        details+= toStringln("Scooter loué"+ nbscoot_loc() +"fois");
        details+= toStringln("Argent généré : " + argent_genere() + "€");
        details+= toStringln("Ancien clients : ");
        for (Client c : ancien_Clients()) {
            details+=toStringln("- " + c.getNom());
        }
        return details;
    }
}