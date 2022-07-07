package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtSessionCfgV1 implements Serializable, Cloneable {
    private static final long serialVersionUID = -486115449974180875L;
    public int antispoofingLevel;
    public int callbackLevel;
    public boolean latentDetect;
    public boolean multipleEnroll;
    public byte[] reserved = new byte[20];
    public int securitySetting;
    public boolean sensorDisabling;
    public boolean subSample;

    public PtSessionCfgV1 clone() throws CloneNotSupportedException {
        PtSessionCfgV1 copy = (PtSessionCfgV1) super.clone();
        copy.reserved = new byte[10];
        for (int i = 0; i < 10; i++) {
            copy.reserved[i] = this.reserved[i];
        }
        return copy;
    }
}
