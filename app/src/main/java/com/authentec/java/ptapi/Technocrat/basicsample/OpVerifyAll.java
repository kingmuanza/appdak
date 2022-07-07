package com.authentec.java.ptapi.Technocrat.basicsample;

import com.upek.android.ptapi.PtConnectionI;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.resultarg.PtBirArgI;
import com.upek.android.ptapi.struct.PtFingerListItem;
import com.upek.android.ptapi.struct.PtGuiSampleImage;

public abstract class OpVerifyAll extends Thread implements PtGuiStateCallback {
    private PtConnectionI mConn;

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    public OpVerifyAll(PtConnectionI conn) {
        super("VerifyAllThread");
        this.mConn = conn;
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
            this.mConn.setGUICallbacks((PtGuiStreamingCallback) null, this);
            PtFingerListItem[] fingerList = this.mConn.listAllFingers();
            if (fingerList == null || fingerList.length == 0) {
                onDisplayMessage("No templates enrolled");
                onFinished();
            }
            int index = this.mConn.verifyAll((Integer) null, (Integer) null, (Boolean) null, (PtBirArgI) null, (IntegerArgI) null, (IntegerArgI) null, (ByteArrayArgI) null, -2, true, (PtBirArgI) null, (byte[]) null, (ByteArrayArgI) null);
            if (-1 != index) {
                for (int i = 0; i < fingerList.length; i++) {
                    PtFingerListItem item = fingerList[i];
                    if (item.slotNr == index) {
                        byte[] fingerData = item.fingerData;
                        if (fingerData == null || fingerData.length < 1) {
                            onDisplayMessage("No fingerData set");
                        } else {
                            onDisplayMessage(String.valueOf(FingerId.NAMES[item.fingerData[0]]) + " finger matched");
                        }
                    }
                }
            } else {
                onDisplayMessage("No match found.");
            }
            onFinished();
        } catch (PtException e) {
            onDisplayMessage("Verification failed - " + e.getMessage());
        }
    }
}
