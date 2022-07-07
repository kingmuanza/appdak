package com.upek.android.ptapi;

import android.util.Log;

import com.upek.android.ptapi.callback.PtCommCallback;
import com.upek.android.ptapi.struct.PtDeviceListItem;
import com.upek.android.ptapi.struct.PtGlobalInfo;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class PtGlobal implements PtGlobalI {
    public static final int apiVersion = 66304;
    public static int dllVersion = 0;
    private static boolean isLibraryLoaded = false;
    private static final long serialVersionUID = 8136932780925260899L;
    protected static List<PtConnection> watchQueue = new LinkedList();

    public native PtDeviceListItem[] enumerateDevices(String str) throws PtException;

    public native PtGlobalInfo globalInfo() throws PtException;

    public native void initialize() throws PtException;

    public native void initializeEx(int i) throws PtException;

    public native PtConnectionI open(String str) throws PtException;

    public native PtConnectionI openDevice(String str) throws PtException;

    public native PtConnectionI openRemote(PtCommCallback ptCommCallback) throws PtException;

    public native void terminate() throws PtException;

    /* access modifiers changed from: protected */
    public void watchConnection(PtConnection connection) {
        synchronized (watchQueue) {
            if (connection != null) {
                watchQueue.add(connection);
            }
        }
    }

    public static void removeWatchConnection(int handle) {
        synchronized (watchQueue) {
            Iterator<PtConnection> it = watchQueue.iterator();
            while (it.hasNext()) {
                if (it.next().hConnection == handle) {
                    it.remove();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void forceCloseConnections() {
        synchronized (watchQueue) {
            for (PtConnection item : watchQueue) {
                synchronized (item) {
                    try {
                        if (item.hConnection != 0) {
                            item.close();
                        }
                    } catch (PtException e) {
                    }
                }
            }
        }
    }

    public PtGlobal() {
        Log.d("MUANZA", "PtGlobal PtGlobal");
        synchronized (PtGlobal.class) {
            if (!isLibraryLoaded) {
                loadLibrary();
            }
        }
    }

    public static synchronized void loadLibrary() {
        Log.d("MUANZA", "loadLibrary synchronized");
        synchronized (PtGlobal.class) {
            try {
                System.loadLibrary("jniPtapi");
                isLibraryLoaded = true;
            } catch (UnsatisfiedLinkError e) {
                try {
                    Log.d("MUANZA", "e1 : " + e.toString());
                    Log.d("MUANZA", "e1 : " + e.getMessage());
                    System.out.println(e.toString());
                } catch (SecurityException e2) {
                    Log.d("MUANZA", "e2 : " + e2.toString());
                    Log.d("MUANZA", "e2 : " + e2.getMessage());
                    try {
                        System.out.println(String.valueOf(e2.toString()) + e2.getMessage());
                    } catch (RuntimeException e3) {
                        try {
                            Log.d("MUANZA", "e3 : " + e3.toString());
                            Log.d("MUANZA", "e3 : " + e3.getMessage());
                            System.out.println(e3.toString());
                        } catch (Exception e4) {
                            System.out.println(e4.toString());
                        }
                    }
                }
            }
        }
        return;
    }

    public static synchronized void libraryIsAlreadyLoaded() {
        synchronized (PtGlobal.class) {
            isLibraryLoaded = true;
        }
    }
}
