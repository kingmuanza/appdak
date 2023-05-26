package cm.security.dak.models;

public class Changement {

    int idswitch;

    Vigile idvigileBase;
    Vigile idvigileSwitch;

    Affectation idaffectation;

    String date;

    public int getIdswitch() {
        return idswitch;
    }

    public void setIdswitch(int idswitch) {
        this.idswitch = idswitch;
    }

    public Vigile getIdvigileBase() {
        return idvigileBase;
    }

    public void setIdvigileBase(Vigile idvigileBase) {
        this.idvigileBase = idvigileBase;
    }

    public Vigile getIdvigileSwitch() {
        return idvigileSwitch;
    }

    public void setIdvigileSwitch(Vigile idvigileSwitch) {
        this.idvigileSwitch = idvigileSwitch;
    }

    public Affectation getIdaffectation() {
        return idaffectation;
    }

    public void setIdaffectation(Affectation idaffectation) {
        this.idaffectation = idaffectation;
    }

    public String getDate() {
        return date.split("T")[0]+ " " + date.split("T")[1].substring(0, 8) ;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
