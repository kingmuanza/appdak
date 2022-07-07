package com.upek.android.ptapi;

public interface PtConnectionManufacturingI extends PtConnectionI {
    byte[] firmwareGetCfg(int i) throws PtException;

    void firmwareRequestReboot(boolean z) throws PtException;

    void firmwareSetCfg(int i, byte[] bArr) throws PtException;
}
