package com.upek.android.ptapi.callback;

import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.struct.PtGuiSampleImage;

public interface PtGuiStateCallback {
    byte guiStateCallbackInvoke(int i, int i2, byte b, PtGuiSampleImage ptGuiSampleImage, byte[] bArr) throws PtException;
}
