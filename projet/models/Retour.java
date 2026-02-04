package models;

import java.text.SimpleDateFormat;
import java.util.*;

public class Retour {

    public int id_retour;
    public int km_parcourut;

    public Date date_retour;
    public boolean Etat_retour;
    public int penalite;
    Location location;
    
    public Retour(int km, Date date, boolean etat, int pen, Location loc) {
        id_retour = loc.getId_location();
        km_parcourut = km;
        date_retour = date;
        Etat_retour = etat;
        penalite = loc.getScooter().getPenalite_pj();  
        location= loc;
    }
    
    public int getId_retour() {
        return id_retour;
    }
    public void setId_retour(int newid) {
        id_retour = newid;
    }
    public int getKm_parcourut() {
        return km_parcourut;
    }
    public void setKm_parcourut(int newkm) {
        km_parcourut = newkm;
    }
    public Date getDate_retour() {
        return date_retour;
    }
    public void setDate_retour(Date newdate_retour) {
        date_retour = newdate_retour;
    }
    public boolean getEtat_retour() {
        return Etat_retour;
    }
    public void setEtat_retour(boolean newetat) {
        Etat_retour = newetat;
    }
    public int getpenalite() {
        return penalite;
    }
    public void setpenalite(int newpenalite) {
        penalite = newpenalite;
    }
    
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location newlocation) {
        location = newlocation;
    }

    public String toStringln(String s) {
        return s + "\n";
    }

    public boolean estEnRetard() {
        Date dateLimite = location.getDate_fin();
        Date dateRetour = date_retour;
        if (dateRetour.after(dateLimite)) {
            return true;
        }
        return false;
    }

    public double calculerPenalite() {
        double pt = 0;
        if (estEnRetard()) {
            long diff = date_retour.getTime() - location.getDate_fin().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            pt = (int) (diffDays * penalite);
        }
        if (Etat_retour) {
            pt += 200;
        }
        return pt;
    }   

    public double prixTotal(){
        double pt=0;
        pt=calculerPenalite();
        pt+= location.getScooter().getPrix();
        return pt;
    }

    public boolean verifierReservation() {
        if (!location.getScooter().estDispo()) {
            return true;
        } else {
            return false;
        }
    }

    public static Location getLocationActu(Scooter scooter) {
        for (Location loc : scooter.getLocations()) {
            if (!loc.getScooter().estDispo() && loc.getRetour() == null) {
                return loc;
            }
        }
        return null;
    }

    public String retourScooter(Location loc) {
        if (!verifierReservation()) {
            return "Le scooter n'est pas réservé.";
        }
        loc.setRetour(this);
        loc.getScooter().setDispo(true);
        return "Le scooter a été retourné avec succès.";
    }

    public String details_retour() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String details= ("-----------------Détails du retour : ------------------\n");
        details+= toStringln("Retour du scooter :"+ location.getScooter().getModele().getNom_model());
        details+= toStringln("ID Location: " + location.getId_location());
        details+= toStringln("ID Retour: " + id_retour);
        details+= toStringln("Client: " + location.getClient().getNom());
        details+= toStringln("Date de location " + sdf.format(location.getDate_debut()));
        details+= toStringln("Date de retour prévue: " + sdf.format(location.getDate_fin()));
        details += toStringln("Date de retour: " + sdf.format(date_retour) + " - " + (estEnRetard() ? "Le scooter est en retard" : "Le scooter est à l'heure"));
        details+= toStringln("Pénalité retards/j: " + penalite +"€" );
        details+= toStringln("Kilomètres parcourus: " + km_parcourut);
        details+= toStringln("Le Scooter est endommagé ? " + Etat_retour);
        details+= toStringln("Prix Total: "+ prixTotal() + "€");
        return details;
    }
}