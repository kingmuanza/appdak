package cm.security.dak.models;

public class Vigile {

    int idvigile;
    String noms;
    String nom;
    String prenom;
    boolean empreinte;

    public int getIdvigile() {
        return idvigile;
    }

    public void setIdvigile(int idvigile) {
        this.idvigile = idvigile;
    }

    public String getNoms() {
        return noms;
    }

    public void setNoms(String noms) {
        this.noms = noms;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public boolean isEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(boolean empreinte) {
        this.empreinte = empreinte;
    }
}
