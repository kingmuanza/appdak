package com.upek.android.ptapi;

import com.upek.android.ptapi.callback.PtCommCallback;
import com.upek.android.ptapi.callback.PtGuiStateCallback;
import com.upek.android.ptapi.callback.PtGuiStreamingCallback;
import com.upek.android.ptapi.callback.PtIdleCallback;
import com.upek.android.ptapi.callback.PtNavigationCallback;
import com.upek.android.ptapi.resultarg.ByteArrayArgI;
import com.upek.android.ptapi.resultarg.IntegerArgI;
import com.upek.android.ptapi.resultarg.PtBirArgI;
import com.upek.android.ptapi.resultarg.PtSwipeInfoArgI;
import com.upek.android.ptapi.struct.PtBir;
import com.upek.android.ptapi.struct.PtExtendedInfo;
import com.upek.android.ptapi.struct.PtFingerListItem;
import com.upek.android.ptapi.struct.PtImgFormat;
import com.upek.android.ptapi.struct.PtInfo;
import com.upek.android.ptapi.struct.PtInputBir;
import com.upek.android.ptapi.struct.PtMatchOnHostParams;

public class PtConnection implements PtConnectionAdvancedI {
    private static final long serialVersionUID = -6027604008532932894L;
    protected PtCommCallback commCB = null;
    protected int continueFlag = 1;
    protected int globalReferenceToThis = 0;
    protected PtGuiStateCallback guiStateCB = null;
    protected PtGuiStreamingCallback guiStreamingCB = null;
    protected int hConnection;

    public native void authentify(int i, byte[] bArr) throws PtException;

    public native void authentifyAdv(int i, byte[] bArr, byte[] bArr2, byte[] bArr3, int i2, int i3, int i4, int i5) throws PtException;

    public native void authentifyEx(int i, byte[] bArr) throws PtException;

    public native void calibrate(int i) throws PtException;

    public native void cancel(int i) throws PtException;

    public native synchronized void capture(byte b, PtBirArgI ptBirArgI, int i, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native synchronized void close() throws PtException;

    public native synchronized void closeDevice() throws PtException;

    public native void closeSession() throws PtException;

    public native void controlLastMatchData(int i, byte[] bArr) throws PtException;

    public native PtBir convertTemplate(PtInputBir ptInputBir, int i, boolean z) throws PtException;

    public native byte[] convertTemplateEx(int i, int i2, byte[] bArr, int i3, int i4, byte[] bArr2, int i5) throws PtException;

    public native void copyFingerData(PtInputBir ptInputBir, int i, int i2, int i3, int i4) throws PtException;

    public native PtBir copyFingerDataWrapped(PtInputBir ptInputBir, int i, PtInputBir ptInputBir2, int i2, int i3) throws PtException;

    public native byte[] createProfileWrapped(byte[] bArr, byte[] bArr2) throws PtException;

    public native void deleteAllFingers() throws PtException;

    public native void deleteFinger(int i) throws PtException;

    public native synchronized void detectFingerEx(int i, int i2) throws PtException;

    public native byte[] deviceControl(int i, byte[] bArr) throws PtException;

    public native byte[] diagnostics(byte[] bArr, IntegerArgI integerArgI) throws PtException;

    public native synchronized void enroll(byte b, PtInputBir ptInputBir, PtBirArgI ptBirArgI, IntegerArgI integerArgI, byte[] bArr, int i, PtBirArgI ptBirArgI2, byte[] bArr2, ByteArrayArgI byteArrayArgI) throws PtException;

    public native PtExtendedInfo extendedInfo() throws PtException;

    public native byte[] firmwareGetCfg(int i) throws PtException;

    public native void firmwareRequestReboot(boolean z) throws PtException;

    public native void firmwareSetCfg(int i, byte[] bArr) throws PtException;

    public native void formatInternalNVM(int i, byte[] bArr, byte[] bArr2) throws PtException;

    public native byte[] generateDeviceKeyPair(int i, int i2, int i3, int i4) throws PtException;

    public native byte[] getAccessRights(int i) throws PtException;

    public native byte[] getAppData(int i) throws PtException;

    public native int getAvailableMemory(int i) throws PtException;

    public native byte[] getFingerData(int i) throws PtException;

    public native byte[] getFingerDataEx(PtInputBir ptInputBir, int i) throws PtException;

    public native PtImgFormat getImageFormat(int i) throws PtException;

    public native void getLED(IntegerArgI integerArgI, IntegerArgI integerArgI2, IntegerArgI integerArgI3) throws PtException;

    public native Object getSessionCfgEx(short s) throws PtException;

    public native void getSwipeInfo(PtSwipeInfoArgI ptSwipeInfoArgI, ByteArrayArgI byteArrayArgI, ByteArrayArgI byteArrayArgI2) throws PtException;

    public native synchronized byte[] grab(byte b, int i, boolean z, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native synchronized byte[] grabWindow(byte b, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native short identifyMatchOnHost(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir[] ptInputBirArr, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, PtMatchOnHostParams ptMatchOnHostParams) throws PtException;

    public native PtInfo info() throws PtException;

    public native boolean isStrongEncryptionEnabled() throws PtException;

    public native PtFingerListItem[] listAllFingers() throws PtException;

    public native PtBir loadFinger(int i, boolean z) throws PtException;

    public native PtBir loadFingerEx(PtInputBir ptInputBir, boolean z) throws PtException;

    public native void navigate(int i, PtNavigationCallback ptNavigationCallback, byte[] bArr) throws PtException;

    public native void navigateWithSleep(int i, int i2, PtNavigationCallback ptNavigationCallback, byte[] bArr) throws PtException;

    public native void openSession(String str) throws PtException;

    public native int queryFingerData(PtInputBir ptInputBir, int i) throws PtException;

    public native int scanQuality() throws PtException;

    public native void secureChannel(int i, int i2, int i3) throws PtException;

    public native void secureChannelEx(int i, int i2, int i3, int i4) throws PtException;

    public native void setAppData(int i, byte[] bArr) throws PtException;

    public native void setDevicePrivateKey(int i, byte[] bArr) throws PtException;

    public native void setFingerData(int i, byte[] bArr) throws PtException;

    public native void setFingerDataEx(int i, int i2, int i3, byte[] bArr) throws PtException;

    public native PtBir setFingerDataExWrapped(PtInputBir ptInputBir, int i, int i2, byte[] bArr) throws PtException;

    public native void setFingerPayload(int i, byte[] bArr) throws PtException;

    public native synchronized void setGUICallbacks(PtGuiStreamingCallback ptGuiStreamingCallback, PtGuiStateCallback ptGuiStateCallback) throws PtException;

    public native void setImageFormat(int i, PtImgFormat ptImgFormat, int i2) throws PtException;

    public native void setLED(int i, int i2, int i3) throws PtException;

    public native void setProfileWrapped(byte[] bArr) throws PtException;

    public native void setSessionCfgEx(short s, Object obj) throws PtException;

    public native int sleep(int i, PtIdleCallback ptIdleCallback) throws PtException;

    public native void sleepThenCapture(PtIdleCallback ptIdleCallback, byte b, int i, IntegerArgI integerArgI, IntegerArgI integerArgI2, PtBirArgI ptBirArgI, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native void sleepThenGrab(PtIdleCallback ptIdleCallback, byte b, int i, boolean z, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    public native int storeFinger(PtInputBir ptInputBir) throws PtException;

    public native PtBir storeFingerWrapped(PtInputBir ptInputBir) throws PtException;

    public native synchronized byte[] transact(byte[] bArr) throws PtException;

    public native void updateFingerPayload(int i, int i2, byte[] bArr) throws PtException;

    public native byte[] useFingerData(PtInputBir ptInputBir, int i, int i2, byte[] bArr) throws PtException;

    public native byte[] useSetFingerData(int i, int i2, int i3, int i4, byte[] bArr) throws PtException;

    public native PtBir useSetFingerDataWrapped(PtInputBir ptInputBir, int i, int i2, int i3, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native byte[] useUpdateFingerData(int i, int i2, int i3, byte[] bArr) throws PtException;

    public native PtBir useUpdateFingerDataWrapped(PtInputBir ptInputBir, int i, int i2, byte[] bArr, ByteArrayArgI byteArrayArgI) throws PtException;

    public native synchronized boolean verify(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    public native synchronized int verifyAll(Integer num, Integer num2, Boolean bool, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    public native synchronized short verifyEx(Integer num, Integer num2, Boolean bool, PtInputBir[] ptInputBirArr, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, int i, boolean z, PtBirArgI ptBirArgI2, byte[] bArr, ByteArrayArgI byteArrayArgI2) throws PtException;

    public native boolean verifyMatch(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir ptInputBir2, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI) throws PtException;

    public native boolean verifyMatchOnHost(Integer num, Integer num2, Boolean bool, PtInputBir ptInputBir, PtInputBir ptInputBir2, PtBirArgI ptBirArgI, IntegerArgI integerArgI, IntegerArgI integerArgI2, ByteArrayArgI byteArrayArgI, PtMatchOnHostParams ptMatchOnHostParams) throws PtException;

    protected PtConnection(int hConnection2) {
        this.hConnection = hConnection2;
    }
}
