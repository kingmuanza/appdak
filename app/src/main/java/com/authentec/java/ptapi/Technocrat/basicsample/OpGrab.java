package com.authentec.java.ptapi.Technocrat.basicsample;

import android.app.Activity;
import android.content.Intent;
import com.upek.android.ptapi.PtConnectionI;
import com.upek.android.ptapi.PtConstants;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.struct.PtGuiSampleImage;
import android.graphics.Bitmap;
import android.graphics.Color;

import cm.security.dak.services.Comparateur;

public abstract class OpGrab extends Thread implements PtGuiStateCallback {
    private Activity mActivity;
    private PtConnectionI mConn;
    private byte mbyGrabType;

    public OpGrab(PtConnectionI conn, byte byGrabType, Activity aActivity) {
        super("GrabThread");
        this.mConn = conn;
        this.mActivity = aActivity;
        this.mbyGrabType = byGrabType;
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

    public static Bitmap CreateBitmap(byte [] aImageData,int iWidth)
    {
        int[] data = new int[aImageData.length];
        int iLength = aImageData.length;
        int iHeight = iLength / iWidth;
        for(int i=0; i<iLength; i++)
        {
            int color = (int)aImageData[i];
            if(color < 0)
            {
                color = 256 + color;
            }
            data[i] = Color.rgb(color,color,color);
        }
        return Bitmap.createBitmap(data, iWidth, iHeight, Bitmap.Config.ARGB_8888);
    }

    private void ShowImage(byte[] aImage, int iWidth) {

        Comparateur comparateur;
        Comparateur.empreinteScannee = aImage;

        FPDisplay.mImage = CreateBitmap(aImage, iWidth);
        FPDisplay.msTitle = "Fingerprint Image";
        this.mActivity.startActivityForResult(new Intent(this.mActivity, FPDisplay.class), 0);
    }

    /**
     * Grab execution code.
     */
    @Override
    public void run()
    {
        try
        {
            // Optional: Set session configuration
            ModifyConfig();

            // Obtain finger template
            byte [] image = GrabImage(mbyGrabType);
            int iWidth = (mConn.info().imageWidth);
            onDisplayMessage("Image grabbed");
            switch(mbyGrabType)
            {
                case PtConstants.PT_GRAB_TYPE_THREE_QUARTERS_SUBSAMPLE:
                    iWidth = (iWidth * 3)>>2;
                case PtConstants.PT_GRAB_TYPE_508_508_8_SCAN508_508_8:
                    ShowImage(image,iWidth);
                    break;
                default:
                    // unsupported image type for displaying
            }
        }
        catch (PtException e)
        {

        }

        // Un-cancel session
        try
        {
            mConn.cancel(1);
        }
        catch (PtException e1) {}

        onFinished();
    }

    private void ModifyConfig() throws PtException {
    }

    private byte[] GrabImage(byte byType) throws PtException {
        try {
            this.mConn.setGUICallbacks((PtGuiStreamingCallback) null, this);
            return this.mConn.grab(byType, -2, true, (byte[]) null, (ByteArrayArgI) null);
        } catch (PtException e) {
            onDisplayMessage("Grab failed - " + e.getMessage());
            throw e;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static Bitmap CreateBitmap2(byte[] r10, int r11) {
        /*
            int r8 = r10.length
            int[] r2 = new int[r8]
            int r6 = r10.length
            int r5 = r6 / r11
            r4 = 0
        L_0x0007:
            if (r4 < r6) goto L_0x0062
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0071 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0071 }
            java.io.File r9 = android.os.Environment.getExternalStorageDirectory()     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = r9.getAbsolutePath()     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x0071 }
            r8.<init>(r9)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = "/Android"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0071 }
            r3.<init>(r8)     // Catch:{ Exception -> 0x0071 }
            r3.mkdir()     // Catch:{ Exception -> 0x0071 }
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ Exception -> 0x0071 }
            android.graphics.Bitmap r0 = android.graphics.Bitmap.createBitmap(r2, r11, r5, r8)     // Catch:{ Exception -> 0x0071 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0071 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = r3.getAbsolutePath()     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ Exception -> 0x0071 }
            r8.<init>(r9)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r9 = "/finger.bmp"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ Exception -> 0x0071 }
            java.lang.String r8 = r8.toString()     // Catch:{ Exception -> 0x0071 }
            r7.<init>(r8)     // Catch:{ Exception -> 0x0071 }
            android.graphics.Bitmap$CompressFormat r8 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ Exception -> 0x0071 }
            r9 = 100
            r0.compress(r8, r9, r7)     // Catch:{ Exception -> 0x0071 }
            r7.flush()     // Catch:{ Exception -> 0x0071 }
            r7.close()     // Catch:{ Exception -> 0x0071 }
        L_0x005b:
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r8 = android.graphics.Bitmap.createBitmap(r2, r11, r5, r8)
            return r8
        L_0x0062:
            byte r1 = r10[r4]
            if (r1 >= 0) goto L_0x0068
            int r1 = r1 + 256
        L_0x0068:
            int r8 = android.graphics.Color.rgb(r1, r1, r1)
            r2[r4] = r8
            int r4 = r4 + 1
            goto L_0x0007
        L_0x0071:
            r8 = move-exception
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.authentec.java.ptapi.Technocrat.basicsample.OpGrab.CreateBitmap(byte[], int):android.graphics.Bitmap");
    }

    /* access modifiers changed from: protected */
    public abstract void onDisplayMessage(String str);

    /* access modifiers changed from: protected */
    public abstract void onFinished();

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run2() {
        /*
            r4 = this;
            r4.ModifyConfig()     // Catch:{ PtException -> 0x002d }
            byte r2 = r4.mbyGrabType     // Catch:{ PtException -> 0x002d }
            byte[] r1 = r4.GrabImage(r2)     // Catch:{ PtException -> 0x002d }
            com.upek.android.ptapi.PtConnectionI r2 = r4.mConn     // Catch:{ PtException -> 0x002d }
            com.upek.android.ptapi.struct.PtInfo r2 = r2.info()     // Catch:{ PtException -> 0x002d }
            short r0 = r2.imageWidth     // Catch:{ PtException -> 0x002d }
            java.lang.String r2 = "Image grabbed"
            r4.onDisplayMessage(r2)     // Catch:{ PtException -> 0x002d }
            byte r2 = r4.mbyGrabType     // Catch:{ PtException -> 0x002d }
            switch(r2) {
                case 2: goto L_0x0025;
                case 34: goto L_0x0029;
                default: goto L_0x001b;
            }
        L_0x001b:
            com.upek.android.ptapi.PtConnectionI r2 = r4.mConn     // Catch:{ PtException -> 0x002f }
            r3 = 1
            r2.cancel(r3)     // Catch:{ PtException -> 0x002f }
        L_0x0021:
            r4.onFinished()
            return
        L_0x0025:
            int r2 = r0 * 3
            int r0 = r2 >> 2
        L_0x0029:
            r4.ShowImage(r1, r0)     // Catch:{ PtException -> 0x002d }
            goto L_0x001b
        L_0x002d:
            r2 = move-exception
            goto L_0x001b
        L_0x002f:
            r2 = move-exception
            goto L_0x0021
        */
        throw new UnsupportedOperationException("Method not decompiled: com.authentec.java.ptapi.Technocrat.basicsample.OpGrab.run():void");
    }

}
