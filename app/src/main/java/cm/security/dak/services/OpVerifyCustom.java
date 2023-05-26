package cm.security.dak.services;

import com.authentec.java.ptapi.Technocrat.basicsample.PtHelper;
import com.upek.android.ptapi.PtConnectionI;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.resultarg.PtBirArgI;
import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtFingerListItem;
import com.upek.android.ptapi.struct.PtGuiSampleImage;
import com.upek.android.ptapi.struct.PtInputBir;

import java.util.ArrayList;
import java.util.List;

import cm.security.dak.models.Vigile;
import cm.security.dak.models.VigileEmpreinte;

public abstract class OpVerifyCustom extends Thread implements PtGuiStateCallback {

    private PtConnectionI mConn;
    public PtInputBir empreinteSerialisee = null;
    public List<VigileEmpreinte> vigilesEmpreintes = new ArrayList<>();
    public Vigile vigile = null;

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    public OpVerifyCustom(PtConnectionI conn) {
        super("VerifyAllThread");
        this.mConn = conn;
    }

    public OpVerifyCustom(PtConnectionI conn, PtInputBir empreinteSerialisee) {
        super("VerifyAllThread");
        this.mConn = conn;
        this.empreinteSerialisee = empreinteSerialisee;
    }

    public OpVerifyCustom(PtConnectionI conn, List<VigileEmpreinte> vigilesEmpreintes) {
        super("VerifyAllThread");
        this.mConn = conn;
        this.vigilesEmpreintes = vigilesEmpreintes;
    }

    public byte guiStateCallbackInvoke(int guiState, int message, byte progress, PtGuiSampleImage sampleBuffer, byte[] data) {
        onDisplayMessage("Aucun message provenant du lecteur d'empreinte");
        String s = PtHelper.GetGuiStateCallbackMessage(guiState, message, progress);
        if (s != null) {
            onDisplayMessage(s);
        }
        return isInterrupted() ? (byte) 0 : 1;
    }

    public void interrupt() {
        super.interrupt();
        try {
            this.mConn.cancel(0);
        } catch (PtException e) {
        }
    }

    public void run() {
        String message = "";
        try {
            this.mConn.setGUICallbacks((PtGuiStreamingCallback) null, this);

/*
            PtFingerListItem[] fingerList = this.mConn.listAllFingers();
            if (fingerList == null || fingerList.length == 0) {
                message += "\nNo templates enrolled";
                onDisplayMessage(message);
                onFinished();
            } else {
                message += "\nIl ya des empreintes enregistrées : " + fingerList.length;
            }
*/
            boolean b = verifierParmiPlusieursEmpreintes(vigilesEmpreintes);
            // message += "\n" + "resultat : " + b;
            if (b) {
                message += "Vigile : " + vigile.getNoms();
            }
            onDisplayMessage(message);
            onFinished();

        } catch (PtException e) {
            onDisplayMessage("Verification failed - " + e.getMessage());
        }
    }

    private boolean verifierUneEmpreinte(PtInputBir empreinte) throws PtException {
        boolean b = false;
        if (empreinte != null) {
            b = this.mConn.verify((Integer) null, (Integer) null, (Boolean) null, empreinte, (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null, -2, true, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);

        }
        return b;
    }

    private boolean verifierUneEmpreinteVigile(VigileEmpreinte vg) throws PtException {
        boolean b = false;
        if (vg.getEmpreinte() != null) {
            b = this.mConn.verify((Integer) null, (Integer) null, (Boolean) null, vg.getEmpreinte(), (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null, -2, true, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);

        }
        return b;
    }

    private boolean verifierParmiPlusieursEmpreintes(List<VigileEmpreinte> empreintesVigiles) throws PtException {
        boolean b = false;
        if (!empreintesVigiles.isEmpty()) {
            for (int i = 0; i < empreintesVigiles.size(); i++) {
                PtInputBir empreinte = empreintesVigiles.get(i).getEmpreinte();
                if (verifierUneEmpreinte(empreinte)) {
                    vigile = empreintesVigiles.get(i).getVigile();
                    return true;
                }
            }
        }
        return b;
    }

    private static PtInputBir MakeInputBirFromBir(PtBir aBir) {
        PtInputBir aInputBir = new PtInputBir();
        aInputBir.form = 3;
        aInputBir.bir = aBir;
        return aInputBir;
    }


}
