package com.upek.android.ptapi;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PtUtil {
    public static final byte[] encodeUseFingerParams(byte[] prm1) {
        int i = 0 + 4;
        ByteBuffer result = ByteBuffer.allocate(prm1.length + 4);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(prm1.length);
        result.put(prm1);
        return result.array();
    }

    public static final byte[] encodeUseFingerParams(byte[] prm1, byte[] prm2) {
        int i = 0 + 4;
        ByteBuffer result = ByteBuffer.allocate(prm2.length + ((((prm1.length + 4) + 3) / 4) * 4) + 4);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(prm1.length);
        result.put(prm1);
        while (result.position() % 4 != 0) {
            result.put((byte) 0);
        }
        result.putInt(prm2.length);
        result.put(prm2);
        return result.array();
    }

    public static final byte[] encodeUseFingerParams(byte[] prm1, byte[] prm2, byte[] prm3) {
        int i = 0 + 4;
        int length = prm2.length;
        ByteBuffer result = ByteBuffer.allocate(prm3.length + ((((length + (((((prm1.length + 4) + 3) / 4) * 4) + 4)) + 3) / 4) * 4) + 4);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(prm1.length);
        result.put(prm1);
        while (result.position() % 4 != 0) {
            result.put((byte) 0);
        }
        result.putInt(prm2.length);
        result.put(prm2);
        while (result.position() % 4 != 0) {
            result.put((byte) 0);
        }
        result.putInt(prm3.length);
        result.put(prm3);
        return result.array();
    }

    public static final byte[] decodeUseFingerResult(byte[] result) {
        ByteBuffer buffer = ByteBuffer.wrap(result);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] decoded = new byte[buffer.getInt()];
        buffer.get(decoded);
        return decoded;
    }

    public static final int decodeUseFingerResultDWORD(byte[] result) {
        ByteBuffer buffer = ByteBuffer.wrap(result);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        if (buffer.getInt() == 4) {
            return buffer.getInt();
        }
        throw new Error("Invalid parameter.");
    }

    public static final void setAccessRightsBit(byte[] accessRights, int bit) {
        int i = bit >> 3;
        accessRights[i] = (byte) (accessRights[i] | (1 << (bit & 7)));
    }

    public static final void clearAccessRightsBit(byte[] accessRights, int bit) {
        int i = bit >> 3;
        accessRights[i] = (byte) (accessRights[i] & ((1 << (bit & 7)) ^ -1));
    }

    public static final boolean getAccessRightsBit(byte[] accessRights, int bit) {
        return ((accessRights[bit >> 3] >> (bit & 7)) & 1) != 0;
    }

    public static final byte[] encodeNumber(byte x) {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(x);
        return buffer.array();
    }

    public static final byte[] encodeNumber(short x) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort(x);
        return buffer.array();
    }

    public static final byte[] encodeNumber(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(x);
        return buffer.array();
    }

    public static final byte[] encodeNumber(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(x);
        return buffer.array();
    }

    public static final byte decodeByte(byte[] x) {
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.get();
    }

    public static final short decodeShort(byte[] x) {
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getShort();
    }

    public static final int decodeInt(byte[] x) {
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }

    public static final long decodeLong(byte[] x) {
        ByteBuffer buffer = ByteBuffer.wrap(x);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getLong();
    }

    public static final byte[] getAuthKey(int type, byte[] key) {
        int i;
        int i2 = 32;
        if (32 < key.length) {
            i = key.length;
        } else {
            i = 32;
        }
        ByteBuffer result = ByteBuffer.allocate(i + 8);
        result.order(ByteOrder.LITTLE_ENDIAN);
        result.putInt(type);
        result.putInt(key.length);
        if (key.length < 32) {
            i2 = key.length;
        }
        result.put(key, 0, i2);
        return result.array();
    }
}
