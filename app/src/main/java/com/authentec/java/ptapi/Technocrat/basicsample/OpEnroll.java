package com.authentec.java.ptapi.Technocrat.basicsample;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.upek.android.ptapi.PtConnectionI;
import com.upek.android.ptapi.PtConstants;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.resultarg.PtBirArg;
import com.upek.android.ptapi.resultarg.PtBirArgI;
import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtFingerListItem;
import com.upek.android.ptapi.struct.PtGuiSampleImage;
import com.upek.android.ptapi.struct.PtInputBir;
import com.upek.android.ptapi.struct.PtSessionCfgV5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

import cm.security.dak.services.Comparateur;

public abstract class OpEnroll extends Thread implements PtGuiStateCallback {
    private static short SESSION_CFG_VERSION = 5;
    private PtConnectionI mConn;
    private int mFingerId;
    public PtInputBir empreinteSerialisee;

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    public OpEnroll(PtConnectionI conn, int fingerId) {
        super("EnrollmentThread" + fingerId);
        this.mConn = conn;
        this.mFingerId = fingerId;
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
        try {
            modifyEnrollmentType();
            PtInputBir template = enroll();
            if (testAndClean()) {
                addFinger(template);
            }
        } catch (PtException e) {
            e.getCode();
        }
        try {
            this.mConn.cancel(1);
        } catch (PtException e2) {
        }
        onFinished();
    }

    private void modifyEnrollmentType() throws PtException {
        try {
            PtSessionCfgV5 sessionCfg = (PtSessionCfgV5) this.mConn.getSessionCfgEx(SESSION_CFG_VERSION);
            sessionCfg.enrollMinTemplates = 3;
            sessionCfg.enrollMaxTemplates = 5;
            this.mConn.setSessionCfgEx(SESSION_CFG_VERSION, sessionCfg);
        } catch (PtException e) {
            onDisplayMessage("Unable to set session cfg - " + e.getMessage());
            throw e;
        }
    }

    private static PtInputBir MakeInputBirFromBir(PtBir aBir) {
        PtInputBir aInputBir = new PtInputBir();
        aInputBir.form = 3;
        aInputBir.bir = aBir;
        return aInputBir;
    }

    private PtInputBir enroll() throws PtException {
        PtBirArg newTemplate = new PtBirArg();
        try {
            this.mConn.setGUICallbacks((PtGuiStreamingCallback) null, this);
            this.mConn.enroll((byte) 3, (PtInputBir) null, newTemplate, (IntegerArgI) null, (byte[]) null, -2, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);
            return MakeInputBirFromBir(newTemplate.value);
        } catch (PtException e) {
            onDisplayMessage("Enrollment failed - " + e.getMessage());
            throw e;
        }
    }

    private boolean testAndClean() {
        try {
            PtFingerListItem[] fingerList = this.mConn.listAllFingers();
            if (fingerList != null) {
                for (PtFingerListItem item : fingerList) {
                    byte[] fingerData = item.fingerData;
                    if (fingerData != null && fingerData.length >= 1) {
                        byte fingerId = item.fingerData[0];
                        if (fingerId == this.mFingerId) {
                            this.mConn.deleteFinger(item.slotNr);
                        } else {
                            PtInputBir comparedBir = new PtInputBir();
                            comparedBir.form = PtConstants.PT_SLOT_INPUT;
                            comparedBir.slotNr = item.slotNr;
                            if (this.mConn.verifyMatch((Integer) null, (Integer) null, (Boolean) null, (PtInputBir) null, comparedBir, (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null)) {
                                onDisplayMessage("Finger already enrolled as " + FingerId.NAMES[fingerId]);
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        } catch (PtException e) {
            onDisplayMessage("testAndClean failed - " + e.getMessage());
            return false;
        }
    }

    private void addFinger(PtInputBir template) {
        onDisplayMessage("empreinteSerialisee = template");
        empreinteSerialisee = template;
        onDisplayMessage("On récupère le doigt");
        Log.d("MUANZA", "On récupère le doigt");
        byte[] data = template.bir.data;
        byte[] dataFormat = template.bir.getPtDataFormat();

        if (data == null) {
            onDisplayMessage("Il y a exactement zéro donnée");
        } else {
            String str = new String(data, StandardCharsets.UTF_8);
            // onDisplayMessage(str);
        }
        Comparateur comparateur;
        Comparateur.empreinteEnrollee = data;


        try {
            int storeFingerID = this.mConn.storeFinger(template);
            byte b = (byte) this.mFingerId;
            String message = "On a récupèré le doigt : " + storeFingerID + " " + this.mFingerId + " " + b;
            message += "\n" + "template.form : " + template.form;
            message += "\n" + "template.slotNr : " + template.slotNr;
            message += "\n" + "template.bir.factorsMask : " + template.bir.factorsMask;
            message += "\n" + "template.bir.formatID : " + template.bir.formatID;
            message += "\n" + "template.bir.formatOwner : " + template.bir.formatOwner;
            message += "\n" + "template.bir.headerVersion : " + template.bir.headerVersion;
            message += "\n" + "template.bir.purpose : " + template.bir.purpose;
            message += "\n" + "template.bir.quality : " + template.bir.quality;
            message += "\n" + "template.bir.type : " + template.bir.type;

            onDisplayMessage(message);
            this.mConn.setFingerData(storeFingerID, new byte[]{
                    (byte) this.mFingerId
            });
        } catch (PtException e) {
            onDisplayMessage("addFinger failed - " + e.getMessage());
        }
    }

    public File sauvegarderDansUnFichier(PtInputBir template, File mydir) throws IOException {
        Log.d("MUANZA", "sauvegarderDansUnFichier");
        Log.d("MUANZA", mydir.getAbsolutePath());
        File fichier = new File(mydir + "/empreinte.ser");

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
        oos.writeObject(template);

        return fichier;
    }


    public boolean comparer(PtInputBir empreinteStockee) {
        boolean idem = false;
        PtInputBir empreinteSerialisee = this.empreinteSerialisee;

        PtBirArgI ptBirArgI = new PtBirArgI() {
            @Override
            public PtBir getValue() {
                return null;
            }

            @Override
            public void setValue(PtBir ptBir) {

            }
        };
        IntegerArgI integerArgI = new IntegerArgI() {
            @Override
            public int getValue() {
                return mFingerId;
            }

            @Override
            public void setValue(int i) {

            }
        };
        IntegerArgI integerArgI2 = new IntegerArgI() {
            @Override
            public int getValue() {
                return mFingerId;
            }

            @Override
            public void setValue(int i) {

            }
        };
        ByteArrayArgI byteArrayArgI = new ByteArrayArgI() {
            @Override
            public byte[] getValue() {
                return new byte[0];
            }

            @Override
            public void setValue(byte[] bArr) {

            }
        };
        if (empreinteSerialisee != null && empreinteStockee != null) {
            try {
                this.mConn.verifyMatch(mFingerId, mFingerId, true, empreinteStockee, empreinteSerialisee, ptBirArgI, integerArgI, integerArgI2, byteArrayArgI);
            } catch (PtException e) {
                Log.d("MUANZA", e.getMessage());
            }
        }
        return idem;
    }

}
