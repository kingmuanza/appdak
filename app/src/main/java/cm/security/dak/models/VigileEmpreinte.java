package cm.security.dak.models;

import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtInputBir;

public class VigileEmpreinte {

    Vigile vigile;
    PtInputBir empreinte;

    public VigileEmpreinte(Vigile vigile, PtInputBir empreinte) {
        this.vigile = vigile;
        this.empreinte = empreinte;
    }

    public Vigile getVigile() {
        return vigile;
    }

    public void setVigile(Vigile vigile) {
        this.vigile = vigile;
    }

    public PtInputBir getEmpreinte() {
        return empreinte;
    }

    public void setEmpreinte(PtInputBir empreinte) {
        this.empreinte = empreinte;
    }
}
