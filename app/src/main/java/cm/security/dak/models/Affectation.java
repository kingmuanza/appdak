package cm.security.dak.models;

public class Affectation {
    int idaffectation;
    String arret;
    String dateAffectation;
    String horaire;
    String jourRepos;

    Vigile idvigile;
    Vigile remplacant;
    Poste idposte;

    public int getIdaffectation() {
        return idaffectation;
    }

    public void setIdaffectation(int idaffectation) {
        this.idaffectation = idaffectation;
    }

    public String getArret() {
        return arret;
    }

    public void setArret(String arret) {
        this.arret = arret;
    }

    public String getDateAffectation() {
        return dateAffectation;
    }

    public void setDateAffectation(String dateAffectation) {
        this.dateAffectation = dateAffectation;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public String getJourRepos() {
        return jourRepos;
    }

    public void setJourRepos(String jourRepos) {
        this.jourRepos = jourRepos;
    }

    public Vigile getIdvigile() {
        return idvigile;
    }

    public void setIdvigile(Vigile idvigile) {
        this.idvigile = idvigile;
    }

    public Vigile getRemplacant() {
        return remplacant;
    }

    public void setRemplacant(Vigile remplacant) {
        this.remplacant = remplacant;
    }

    public Poste getIdposte() {
        return idposte;
    }

    public void setIdposte(Poste idposte) {
        this.idposte = idposte;
    }
}
