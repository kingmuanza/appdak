package com.upek.android.ptapi.struct;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PtBir implements Serializable {
    private static final long serialVersionUID = 8075809222703836514L;
    public byte[] data;
    public int factorsMask;
    public short formatID;
    public short formatOwner;
    public byte headerVersion;
    public byte purpose;
    public byte quality;
    public byte type;

    public byte[] getPtDataFormat() {
        int len = this.data.length + 16;
        ByteBuffer buffer = ByteBuffer.allocate(len);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(len);
        buffer.put(this.headerVersion);
        buffer.put(this.type);
        buffer.putShort(this.formatOwner);
        buffer.putShort(this.formatID);
        buffer.put(this.quality);
        buffer.put(this.purpose);
        buffer.putInt(this.factorsMask);
        buffer.put(this.data);
        return buffer.array();
    }
}
