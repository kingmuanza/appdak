package com.upek.android.ptapi.callback;

import com.upek.android.ptapi.PtException;

public interface PtCommCallback {
    byte[] commCallbackInvoke(byte[] bArr) throws PtException;
}
