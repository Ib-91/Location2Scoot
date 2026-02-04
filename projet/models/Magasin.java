package models;
import java.util.*;


public class Magasin {
    public int id_magasin;
    public String nom;

    Vector<Marque> marques = new Vector<Marque>();
    Vector<Scooter> scooters = new Vector<Scooter>();
    Vector<Client> clients = new Vector<Client>();

    /**
     * Default constructor
     */
    public Magasin(int id, String name) {
        id_magasin = id;
        nom = name;
    }
    
    public int getId_magasin() {
        return id_magasin;
    }

    public void setId_magasin(int newid) {
        id_magasin = newid;
    }

    public String getNom(){
        return nom;
    }

    public void setNom(String newnom) {
        nom = newnom;
    }

    public Vector<Marque> getMarques() {
        return marques;
    }

    public void setMarques(Vector<Marque> newmarques) {
        marques = newmarques;
    }

    public Vector<Scooter> getScooters() {
        return scooters;
    }

    public void setScooters(Vector<Scooter> newscooters) {
        scooters = newscooters;
    }

    public Vector<Client> getClients() {
        return clients;
    }

    public void setClients(Vector<Client> newclients) {
        clients = newclients;
    }

    public String toStringln(String s) {
        return s + "\n";
    }

    public void addMarque(Marque m) {
        if (!marques.contains(m)) {
        marques.add(m);
        m.setMagasins(this);
        }
    }

    public void addScooter(Scooter s) {
        if(!scooters.contains(s)) {
        scooters.add(s);
        s.setMagasin(this);
        }
    }

    public void addClient(Client c) {
        clients.add(c);
    }

    public void removeMarque(Marque m) {
        if (marques.contains(m)) {
        marques.remove(m);
        m.setMagasins(null);
    }
}

    public void removeScooter(Scooter s) {
        scooters.remove(s);
    }

    public void removeClient(Client c) {
        clients.remove(c);
    }

    
    public int nb_clients() {
        return clients.size();
    }
    public int nb_scooters() {
        return scooters.size();
    }
    public int nb_marques() {
        return marques.size();
    }
    public int nb_locations() {
        int nb = 0;
        for (Client c : clients) {
            nb += c.nbclient_loc();
        }
        return nb;
    }

    public Location louerScooter(Client client,int scooterid, Date dateDebut, Date dateFin) {
        Scooter scooter = ScooterById(scooterid);
        if (client == null || scooter == null) {
            throw new IllegalArgumentException("Client ou scooter invalide.");
        }
        if (!scooters.contains(scooter)) {
            throw new IllegalArgumentException("Le scooter n'est pas disponible dans ce magasin.");
        }
        if (!scooter.estDispo()) {
            throw new IllegalStateException("Le scooter n'est pas disponible.");
        }
        if(!scooter.dispo){
            throw new IllegalStateException("Le scooter n'est pas disponible.");
        }
        Location location = new Location(dateDebut, dateFin, null, client, scooter);    
        if (location.reserverScooter()) {
            return location;
        } else {
            throw new IllegalStateException("Échec de la réservation du scooter.");        }
    }   
    
    public Retour retourScooter(int scooterId, int kmParcourus, boolean endommage) {
        Scooter scooter = ScooterById(scooterId);
        if (scooter == null) {
            throw new IllegalArgumentException("Erreur : Scooter introuvable.");
        }
        if (scooter.estDispo()) {
            throw new IllegalStateException("Le scooter n'était pas loué.");
        }

        Location locactu = Retour.getLocationActu(scooter);
        if (locactu == null) {
            throw new IllegalStateException("Aucune location trouvée pour ce scooter.");
        }    

        Retour retour = new Retour(kmParcourus, new Date(), endommage, scooter.getPenalite_pj(), locactu);
        retour.retourScooter(locactu);
        
        return retour;
    }

    
    public boolean containsModele(Vector<Modele> liste, Modele m) {
        for (Modele existant : liste) {
            if (existant.getId_model() == m.getId_model()) {
                return true;
            }
        }
        return false;
    }    

    public Vector<Modele> getallmodeles() {
        Vector<Modele> allModeles = new Vector<Modele>();

        for (Marque marque : marques) {
           for (Modele modele : marque.getModeles()) {
                if (!containsModele(allModeles, modele)) {
                    allModeles.add(modele);
                }
                }
            }

            return allModeles;
        }


    public Vector<Modele> getModelesByMarque(String nomMarque) {
        Vector<Modele> result = new Vector<>();
        for (Modele m : getallmodeles()) {
            if (m.getMarque().getNom_marque().equals(nomMarque)) {
                result.add(m);
            }
        }
        return result;
    }


    public Vector<Scooter> ScootersDispo() {
        Vector<Scooter> disponibles = new Vector<Scooter>();
        for (Scooter s : scooters) {
            if (s.estDispo()) {
                disponibles.add(s);
            }
        }
        return disponibles;
    }

    public Vector<Scooter> ScootersNonDispo() {
        Vector<Scooter> nonDispo = new Vector<Scooter>();
        for (Scooter s : scooters) {
            if (!s.estDispo()) {
                nonDispo.add(s);
            }
        }
        return nonDispo;
    }

    public Scooter ScooterById(int id) {
        for (Scooter s : scooters) {
            if (s.getNum_idt() == id) {
                return s;
            }
        }
        return null;
    }

        public Location LocationById(int id) {
        for (Scooter s : scooters) {
            for (Location l : s.getLocations()) {
                if (l.getId_location() == id) {
                    return l;
                }
            }
        }
        return null;
    }

    public Client ClientById(int id) {
        for (Client c : clients) {
            if (c.getId_client() == id) {
                return c;
            }
        }
        return null;
    }

    public Marque MarqueById(int id) {
        for (Marque m : marques) {
            if (m.getId_marque() == id) {
                return m;
            }
        }
        return null;
    }

    public String Client_avec_reservation() {
        String clr= "" ;
        for (Client c : clients) {
            if (c.nbclient_loc() > 0) {
                clr+= toStringln(c.getNom());
            }
        }
        return clr;
    }

    public Client client_trouver(String val) {
        for (Client c : clients) {
            if (String.valueOf(c.getId_client()).equals(val) || c.getNom().equalsIgnoreCase(val)) {
                return c;
            }
        }
        return null;
    }


    public Vector<Location> getallLocations() {
        Vector<Location> allLocations = new Vector<Location>();
        for (Scooter s : scooters) {
            for (Location l : s.getLocations()) {
                allLocations.add(l);
            }
        }
        return allLocations;
    }

    public Vector<Location> getLocationsEnCours() {
        Vector<Location> enCours = new Vector<>();
        for (Client c : clients) {
            for (Location l : c.getLocations()) {
                if (l.getRetour() == null) {
                    enCours.add(l);
                }
            }
        }
        return enCours;
    }

    public String locationsEnCours() {
        String lc= "";
        int cpt = 0;
        for (Client c : clients) {
            for (Location l : c.getLocations()) {
                if (l.getRetour() == null) {
                    lc+= toStringln("-"+c.getNom() + " loue le Scooter N°" + l.getScooter().getNum_idt() + "( un "+ l.getScooter().getModele().getNom_model()+")");
                    cpt++;
                }
            }
        }
        if(cpt == 0){
            lc+= toStringln("Aucune location en cours");
        }
        lc+= toStringln("Nombre de locations en cours : " + cpt);
        return lc;
    }

    public String retour_effectue() {
        String re ="";
        int cpt = 0;
        for (Client c : clients) {
            for (Location l : c.getLocations()) {
                if (l.getRetour() != null) {
                    re += toStringln(l.getScooter().getModele().getNom_model() + " par " + c.getNom());
                    cpt++;
                }
            }
        }
        if(cpt == 0){
            re+= toStringln("Aucun retour effectué");
        }
        re+= toStringln("Nombre de retours effectués : " + cpt);
        return re;
    }


    public String magasin_details() {
        String details = ("--------------Résumé du Parc de Scooters------------------ \n");
        details+= toStringln("Nombre de scooters total : " + scooters.size());
        details+= toStringln("Scooters disponibles : " + ScootersDispo().size());
        if(ScootersDispo().size() == 0){
            details+= toStringln("Aucun scooter disponible");
        }else{
            details+= toStringln("Liste des scooters disponibles : ");
        for (Scooter s : ScootersDispo()) {
            details += toStringln("Scooter N°"+s.getNum_idt());
        }
    }
        details+= toStringln("Nombre de scooters non disponibles : " + ScootersNonDispo().size());
        if(ScootersNonDispo().size() == 0){
            details+= toStringln("Aucun scooter non disponible");
        }else{
        details+= toStringln("Scooter en location : ");
        for (Scooter s : ScootersNonDispo()) {
                details += toStringln("Scooter N°"+s.getNum_idt());
        }
        }
        int kmtotal = 0;
        int nbScoot = 0;
        for (Scooter s : scooters) {
            kmtotal += s.getkm();
            nbScoot++;
        }
        double kmoyen = kmtotal / nbScoot;
        details+= toStringln("Kilometrage moyen des scooters: " + kmoyen);
        details+= toStringln("Nombre de clients : " + clients.size());
        details+= toStringln("Nombre de marques : " + marques.size());
        details+= toStringln("Locations effectuées : " + getallLocations().size());
        details+= locationsEnCours();
        details+= retour_effectue();
        details+= toStringln("----------------------------------------------------------");
    return details;
    }
}