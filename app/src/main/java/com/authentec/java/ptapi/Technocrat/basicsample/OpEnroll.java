package com.authentec.java.ptapi.Technocrat.basicsample;

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

public abstract class OpEnroll extends Thread implements PtGuiStateCallback {
    private static short SESSION_CFG_VERSION = 5;
    private PtConnectionI mConn;
    private int mFingerId;

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

        try {
            this.mConn.setFingerData(this.mConn.storeFinger(template), new byte[]{(byte) this.mFingerId});
        } catch (PtException e) {
            onDisplayMessage("addFinger failed - " + e.getMessage());
        }
    }
}
