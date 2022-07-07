package com.upek.android.ptapi;

import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtInputBir;

public interface PtConnectionAdvancedI extends PtConnectionManufacturingI {
    void controlLastMatchData(int i, byte[] bArr) throws PtException;

    void copyFingerData(PtInputBir ptInputBir, int i, int i2, int i3, int i4) throws PtException;

    PtBir copyFingerDataWrapped(PtInputBir ptInputBir, int i, PtInputBir ptInputBir2, int i2, int i3) throws PtException;

    byte[] createProfileWrapped(byte[] bArr, byte[] bArr2) throws PtException;

    byte[] deviceControl(int i, byte[] bArr) throws PtException;

    byte[] diagnostics(byte[] bArr, IntegerArgI integerArgI) throws PtException;

    byte[] generateDeviceKeyPair(int i, int i2, int i3, int i4) throws PtException;

    byte[] getFingerDataEx(PtInputBir ptInputBir, int i) throws PtException;

    PtBir loadFingerEx(PtInputBir ptInputBir, boolean z) throws PtException;

    int queryFingerData(PtInputBir ptInputBir, int i) throws PtException;

    void setDevicePrivateKey(int i, byte[] bArr) throws PtException;

    void setFingerDataEx(int i, int i2, int i3, byte[] bArr) throws PtException;

    PtBir setFingerDataExWrapped(PtInputBir ptInputBir, int i, int i2, byte[] bArr) throws PtException;

    void setProfileWrapped(byte[] bArr) throws PtException;

    PtBir storeFingerWrapped(PtInputBir ptInputBir) throws PtException;

    byte[] useFingerData(PtInputBir ptInputBir, int i, int i2, byte[] bArr) throws PtException;

    byte[] useSetFingerData(int i, int i2, int i3, int i4, byte[] bArr) throws PtException;

    PtBir useSetFingerDataWrapped(PtInputBir ptInputBir, int i, int i2, int i3, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    byte[] useUpdateFingerData(int i, int i2, int i3, byte[] bArr) throws PtException;

    PtBir useUpdateFingerDataWrapped(PtInputBir ptInputBir, int i, int i2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;
}
