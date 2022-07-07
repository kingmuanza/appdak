package com.upek.android.ptapi.callback;

import com.upek.android.ptapi.PtException;

public interface PtIdleCallback {
    byte idleCallbackInvoke() throws PtException;
}
