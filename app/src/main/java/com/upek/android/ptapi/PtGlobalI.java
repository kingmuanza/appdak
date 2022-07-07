package com.upek.android.ptapi;

import com.upek.android.ptapi.callback.PtCommCallback;
import com.upek.android.ptapi.struct.PtDeviceListItem;
import com.upek.android.ptapi.struct.PtGlobalInfo;

public interface PtGlobalI {
    PtDeviceListItem[] enumerateDevices(String str) throws PtException;

    PtGlobalInfo globalInfo() throws PtException;

    void initialize() throws PtException;

    void initializeEx(int i) throws PtException;

    PtConnectionI open(String str) throws PtException;

    PtConnectionI openDevice(String str) throws PtException;

    PtConnectionI openRemote(PtCommCallback ptCommCallback) throws PtException;

    void terminate() throws PtException;
}
