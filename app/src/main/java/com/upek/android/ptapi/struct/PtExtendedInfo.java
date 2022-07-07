package com.upek.android.ptapi.struct;

import java.io.Serializable;

public class PtExtendedInfo implements Serializable {
    private static final long serialVersionUID = 7904896066049295840L;
    public byte[] guid = new byte[16];
    public int systemId;
    public String systemName;
    public int version;
}
