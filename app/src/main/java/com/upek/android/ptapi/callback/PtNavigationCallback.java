package com.upek.android.ptapi.callback;

import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.struct.PtNavigationData;

public interface PtNavigationCallback {
    byte navigationCallbackInvoke(PtNavigationData ptNavigationData) throws PtException;
}
