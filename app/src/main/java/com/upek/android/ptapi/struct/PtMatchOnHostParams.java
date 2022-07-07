package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtMatchOnHostParams implements Serializable {
    private static final long serialVersionUID = -6655798274480592924L;
    public byte identificationThreshold;
    public int securitySetting;
    public int version;
}
