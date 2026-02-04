package models;
import java.text.SimpleDateFormat;
import java.util.*;



public class Client {
    static int nextId = 4;
    public int id_client;
    public String nom;
    public Magasin magasins ;
    
    Vector<Location> locations = new Vector<Location>();
    Vector<Permis> permis = new Vector<Permis>();
    
    /**
     * Default constructor
     */
    public Client(int id, String name) {
        id_client = id;
        nom = name;
    }
    
    public Client(String name) {
        id_client = nextId++;
        nom = name;
    }

    public int getId_client() {
        return id_client;
    }

    public String getNom() {
        return nom;
    }

    public void setId_client(int newid) {
        id_client = newid;
    }

    public void setNom(String newnom) {
        nom = newnom;
    }

    public Magasin getMagasins() {
        return magasins;
    }

    public void setMagasins(Magasin newmag) {
        magasins = newmag;
    }

    public Vector<Location> getLocations() {
        return locations;
    }

    public void setLocations(Vector<Location> newloc) {
        locations = newloc;
    }

    public Vector<Permis> getPermis() {
        return permis;
    }

    public void setPermis(Vector<Permis> newpermis) {
        permis = newpermis;
    }
    
    public void addLocation(Location loc) {
        locations.add(loc);
    }

    public void addPermis(Permis per) {
        if(!permis.contains(per)){
            permis.add(per);
        }
    }

    public void removeLocation(Location loc) {
        locations.remove(loc);
    }

    public void removePermis(Permis per) {
        permis.remove(per);
        per.removeClient(this);
    }

    public String toStringln(String s){
        return s+"\n" ;
    }

    public String permis_client() {
        String perm = toStringln("Permis détenus : ");
        for (Permis p: permis) {
            perm+= toStringln("-"+p.getNom_permis());
        }
        return perm;
    }

    public int nbclient_loc(){
        return locations.size();
    }

    public String historique_loc() {
        int nb_loc=0;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hl = toStringln("-Historique des locations"+"\n" + "Vous en avez fait "+ nbclient_loc() + "\n");
        for (Location l : locations) {
            nb_loc++;
            String date_debut = sdf.format(l.getDate_debut());
            String date_fin = sdf.format(l.getDate_fin());
            hl += toStringln("Location N° " +nb_loc+  " : " + l.getScooter().getModele().getNom_model() + " du " + date_debut + " au " + date_fin + " Id Location :" + l.getId_location() + "\n");
            }
            return hl;
    }

    public Permis getPermisByNom(String nom) {
        for (Permis p : permis) {
            if (p.getNom_permis().equals(nom)) {
                return p;
            }
        }
        return null;
    }

    public boolean peutConduire(Modele modele) {
        if (modele.getPermis().isEmpty()) {
            return true;
        }
        for (Permis p : permis) {
            for (Modele m : p.getModeles()) {
                if (m.equals(modele)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int km_client() {
        int km = 0;
        for (Location loc : locations) {
            Retour r= loc.getRetour();
            if (r != null) {
                km += r.getKm_parcourut();
            }
        }
        return km;
    }

    public double depense_client(){
        double tp = 0;
        for (Location loc : locations) {
            Retour r= loc.getRetour();
            if (r != null) {
                tp += r.prixTotal();
            }
        }
        return tp;
    }

    
    public String details_client() {
        String detail = toStringln("--------------Détails du client :------------------");
        detail+= toStringln("ID Client: " + id_client);
        detail+= toStringln("Nom: " + nom);
        detail+= permis_client();
        detail+= toStringln("Vous avez parcouru " + km_client() + " km avec nos scooters");
        detail+= historique_loc();        
        detail+= toStringln("Kilomètres parcourus: " + km_client());
        detail+= toStringln("Vous avez dépensé au Total: " + depense_client() + " €");
    return detail;
    }
}