package com.authentec.java.ptapi.Technocrat.basicsample;

import com.upek.android.ptapi.PtConnectionI;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.callback.PtNavigationCallback;
import com.upek.android.ptapi.struct.PtNavigationData;

public abstract class OpNavigate extends Thread implements PtNavigationCallback {
    private PtConnectionI mConn;
    private OpNavigateSettings mSettings;

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    public OpNavigate(PtConnectionI conn, OpNavigateSettings settings) {
        super("NavigateThread");
        this.mConn = conn;
        this.mSettings = settings;
    }

    public byte navigationCallbackInvoke(PtNavigationData navigationData) {
        onDisplayMessage("dx:" + (-navigationData.dx) + "dy:" + navigationData.dy + "signalBits:" + Integer.toHexString(navigationData.signalBits));
        try {
            sleep(25);
        } catch (InterruptedException e) {
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
            this.mConn.navigate(-1, this, this.mSettings == null ? null : this.mSettings.serialize(false, 0));
        } catch (PtException e) {
            onDisplayMessage("Navigation failed - " + e.getMessage());
        } catch (Exception e2) {
            onDisplayMessage(e2.getLocalizedMessage());
        }
        onFinished();
    }
}
