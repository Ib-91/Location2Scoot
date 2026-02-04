package models;

import java.text.SimpleDateFormat;
import java.util.*;



public class Location {

    public static int nextId = 1;
    
    public int id_location;
    public Date date_debut;
    public Date date_fin;

    Retour retour;
    Client client;
    Scooter scooter;   

    /**
     * Default constructor
     */
    public Location(Date d1, Date d2, Retour r, Client c, Scooter s) {
        id_location = nextId++;
        date_debut = d1;
        date_fin = d2;
        retour= r;
        client= c;
        scooter = s;
    }

    public int getId_location() {
        return id_location;
    }
    public void setId_location(int newid) {
        id_location = newid;
    }
    public Date getDate_debut() {
        return date_debut;
    }
    public void setDate_debut(Date newdate_debut) {
        date_debut = newdate_debut;
    }
    public Date getDate_fin() {
        return date_fin;
    }
    public void setDate_fin(Date newdate_fin) {
        date_fin = newdate_fin;
    }
    public Retour getRetour() {
        return retour;
    }
    public void setRetour(Retour newretour) {
        this.retour = newretour;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client newclient) {
        client = newclient;
    }
    public Scooter getScooter() {
        return scooter;
    }
    public void setScooter(Scooter newscooter) {
        scooter = newscooter;
    }

    public String toStringln(String s){
        return s+"\n" ;
    }


    public boolean reserverScooter() {
        if (scooter.dispo) {
            scooter.addLocation(this);
            client.addLocation(this);
            scooter.setDispo(false);
            return true;
        }else {
            return false;
        }
    }


    public String details_location() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String detail = "-------------Détails de la location------------------ \n";
        detail+= toStringln("ID Location: " + id_location);
        detail+= toStringln("Client: " + client.getNom());
        detail+= toStringln("Date de début : " + sdf.format(date_debut));
        detail+= toStringln("Date de retour prévu : " + sdf.format(date_fin));
        detail+= toStringln("Scooter: " + scooter.getModele().getNom_model()+ " ID: " + scooter.getNum_idt());
        detail+= toStringln("Prix de la location: " + scooter.getPrix() + "€");
        detail+= toStringln("--------------------------------------------------");
        return detail;
    }
}
