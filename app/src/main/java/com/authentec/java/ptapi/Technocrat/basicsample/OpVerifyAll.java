package com.authentec.java.ptapi.Technocrat.basicsample;

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

public abstract class OpVerifyAll extends Thread implements PtGuiStateCallback {
    private PtConnectionI mConn;
    public PtInputBir empreinteSerialisee = null;

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    public OpVerifyAll(PtConnectionI conn) {
        super("VerifyAllThread");
        this.mConn = conn;
    }

    public OpVerifyAll(PtConnectionI conn, PtInputBir empreinteSerialisee) {
        super("VerifyAllThread");
        this.mConn = conn;
        this.empreinteSerialisee = empreinteSerialisee;
    }

    public byte guiStateCallbackInvoke(int guiState, int message, byte progress, PtGuiSampleImage sampleBuffer, byte[] data) {
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


            PtFingerListItem[] fingerList = this.mConn.listAllFingers();
            if (fingerList == null || fingerList.length == 0) {
                message += "\nNo templates enrolled";
                onDisplayMessage(message);
                onFinished();
            } else {
                message += "\nIl ya des empreintes enregistrées : " + fingerList.length;
            }

            if (empreinteSerialisee != null) {
                boolean b = this.mConn.verify((Integer) null, (Integer) null, (Boolean) null, empreinteSerialisee, (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null, -2, true, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);
                message += "\nLe resultat du boolean : " + b;
                if (b) {
                    onDisplayMessage("Les empreintes correspondent");
                    onFinished();
                } else {
                    onDisplayMessage("Les empreintes ne correspondent pas. \nS'il s'agit bien du vigile concerné, veuillez l'enregistrer à nouveau");
                    onFinished();
                }
            } else {
                onDisplayMessage("Les empreintes du vigile n'ont pas été sauvegardées dans cet appareil");
                onFinished();
            }

            /*
            int index = this.mConn.verifyAll((Integer) null, (Integer) null, (Boolean) null, (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null, -2, true, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);
            message += "\nIndex : " + index;

            if (-1 != index) {
                for (int i = 0; i < fingerList.length; i++) {
                    message += "\ni : " + i;
                    PtFingerListItem item = fingerList[i];

                    message += "\nitem : " + item.fingerData.length;
                    message += "\nslotNr : " + item.slotNr;
                    if (item.slotNr == index) {
                        byte[] fingerData = item.fingerData;
                        if (fingerData == null || fingerData.length < 1) {
                            message += "\nNo fingerData set";
                            onDisplayMessage(message);
                        } else {
                            message += "\nitem.fingerData[0] : " + item.fingerData[0];
                            message += "\nMessage final : " + String.valueOf(FingerId.NAMES[item.fingerData[0]]) + " finger matched";
                            onDisplayMessage(message);
                        }
                    }
                }
            } else {
                message += "\nNo match found.";
                onDisplayMessage(message);
            }
            onDisplayMessage(message);
            onFinished();
            */
        } catch (PtException e) {
            onDisplayMessage("Verification failed - " + e.getMessage());
        }
    }

    private static PtInputBir MakeInputBirFromBir(PtBir aBir) {
        PtInputBir aInputBir = new PtInputBir();
        aInputBir.form = 3;
        aInputBir.bir = aBir;
        return aInputBir;
    }


}
