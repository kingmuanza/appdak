package com.authentec.java.ptapi.Technocrat.basicsample;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import cm.security.dak.PosteDetailActivity;
import cm.security.dak.R;
import cm.security.dak.VigileDetailActivity;
import cm.security.dak.models.Vigile;
import cm.security.dak.services.Comparateur;
import cm.security.dak.services.GPSTracker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.upek.android.ptapi.PtConnectionAdvancedI;
import com.upek.android.ptapi.PtConstants;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.PtGlobal;
import com.upek.android.ptapi.struct.PtDeviceListItem;
import com.upek.android.ptapi.struct.PtInfo;
import com.upek.android.ptapi.struct.PtSessionCfgV5;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SampleActivity extends Activity {

    List<Vigile> vigiles = new ArrayList<Vigile>();
    List<Vigile> resultats = new ArrayList<Vigile>();
    GPSTracker mGPSTracker;
    double latitude;
    double longitude;

    public static final String[] mDSN = {"usb,timeout=500", "wbf,timeout=500"};
    public static final int miRunningOnRealHardware = 1;
    public static final int miUseSerialConnection = 0;
    public static final String msDSNSerial = "sio,port=COM1,speed=115200,timeout=2000";
    /* access modifiers changed from: private */
    public final Object mCond = new Object();
    /* access modifiers changed from: private */
    public PtConnectionAdvancedI mConn = null;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message aMsg) {
            ((TextView) SampleActivity.this.findViewById(R.id.EnrollmentTextView)).setText((String) aMsg.obj);
        }
    };
    private PtGlobal mPtGlobal = null;
    /* access modifiers changed from: private */
    public Thread mRunningOp = null;
    private PtInfo mSensorInfo = null;
    /* access modifiers changed from: private */
    public int millseconds = 0;
    private String msNvmPath = null;
    /* access modifiers changed from: private */
    public Handler myHandler = new Handler();
    private int seconds = 0;


    public void creerPointage() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        int randomNum = ThreadLocalRandom.current().nextInt(0, resultats.size());
        Vigile v = resultats.get(randomNum);


        Map<String, Object> update = new HashMap<>();
        update.put("date", new Date());
        update.put("idvigile", v.getIdvigile());
        update.put("nomsVigile", v.getNoms());
                update.put("empreinte", true);

        update.put("longitude", longitude);
        update.put("latitude", latitude);

        db.collection("pointage").document(timeStamp).set(update, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SampleActivity.this, "Pointage mis à jour sur le serveur", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            SampleActivity sampleActivity = SampleActivity.this;
            sampleActivity.millseconds = sampleActivity.millseconds + 1;
            if (SampleActivity.this.millseconds == 60) {
                SampleActivity.this.logout();
            }
            SampleActivity.this.myHandler.postDelayed(this, 1000);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        File aDir;
        super.onCreate(savedInstanceState);
        Context aContext = getBaseContext();
        if (!(aContext == null || (aDir = aContext.getDir("tcstore", Context.MODE_PRIVATE)) == null)) {
            this.msNvmPath = aDir.getAbsolutePath();
        }
        setContentView(R.layout.activity_principal);
        if (initializePtapi()) {
            openPtapiSession();
            if (this.mConn != null) {
                setIdentifyButtonListener();
                setGrabButtonListener();
               //  setEnrollButtonListener(R.id.ButtonEnroll, FingerId.RIGHT_THUMB);
                setNavigateRawButtonListener();
            }
        }
        SetQuitButtonListener();
        this.myHandler.postDelayed(this.updateTimerMethod, 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mGPSTracker = new GPSTracker(SampleActivity.this);
            if (mGPSTracker.canGetLocation()) {
                latitude = mGPSTracker.getLatitude();
                longitude = mGPSTracker.getLongitude();
                Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
                Log.d("MUANZA", "longitude mGPSTracker : " + longitude);

            } else {
                mGPSTracker.showSettingsAlert();
            }
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vigile")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d("MUANZA", "On transforme en classe JAVA");
                            vigiles = task.getResult().toObjects(Vigile.class);

                            for (Vigile v : vigiles) {
                                if (v.isEmpreinte()) {
                                    resultats.add(v);
                                }
                                Log.d("MUANZA", "ID du vigile : " + v.getIdvigile());
                            }

                        } else {
                            Log.w("MUANZA", "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void logout() {
        this.seconds = 0;
        this.myHandler.removeCallbacks(this.updateTimerMethod);
        System.exit(1);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        synchronized (this.mCond) {
            while (this.mRunningOp != null) {
                this.mRunningOp.interrupt();
                try {
                    this.mCond.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        closeSession();
        terminatePtapi();
        super.onDestroy();
    }

    private void setEnrollButtonListener(int buttonId, final int fingerId) {
        Log.d("MUANZA", "setEnrollButtonListener");
        Toast.makeText(this, "setEnrollButtonListener", Toast.LENGTH_LONG).show();
        ((Button) findViewById(buttonId)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                synchronized (SampleActivity.this.mCond) {
                    if (SampleActivity.this.mRunningOp == null) {
                        SampleActivity.this.mRunningOp = new OpEnroll(SampleActivity.this.mConn, fingerId) {
                            /* access modifiers changed from: protected */
                            public void onDisplayMessage(String message) {
                                Toast.makeText(SampleActivity.this, message, Toast.LENGTH_LONG).show();
                                SampleActivity.this.dislayMessage(message);
                            }

                            /* access modifiers changed from: protected */
                            public void onFinished() {
                                Toast.makeText(SampleActivity.this, "setEnrollButtonListener terminé", Toast.LENGTH_LONG).show();
                                synchronized (SampleActivity.this.mCond) {
                                    SampleActivity.this.mRunningOp = null;
                                    SampleActivity.this.mCond.notifyAll();
                                }
                            }
                        };
                        SampleActivity.this.mRunningOp.start();
                    }
                }
            }
        });
    }

    private void setIdentifyButtonListener() {
    }

    private OpNavigate createNavigationOperationHelper(OpNavigateSettings aSettings) {
        return new OpNavigate(this.mConn, aSettings) {
            /* access modifiers changed from: protected */
            public void onDisplayMessage(String message) {
                SampleActivity.this.dislayMessage(message);
            }

            /* access modifiers changed from: protected */
            public void onFinished() {
                synchronized (SampleActivity.this.mCond) {
                    SampleActivity.this.mRunningOp = null;
                    SampleActivity.this.mCond.notifyAll();
                }
            }
        };
    }

    private void setNavigateRawButtonListener() {
    }

    private void setGrabButtonListener() {
        Log.d("MUANZA", "setGrabButtonListener");
        // Toast.makeText(this, "setGrabButtonListener", Toast.LENGTH_LONG).show();
        Button grabButton = (Button) findViewById(R.id.ButtonGrab);
        // Toast.makeText(this, "On a trouvé le grab button", Toast.LENGTH_LONG).show();
        grabButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(SampleActivity.this, "On a cliqué sur le grab button", Toast.LENGTH_LONG).show();
                synchronized (SampleActivity.this.mCond) {
                    // Toast.makeText(SampleActivity.this, "la synchronistation a commencé", Toast.LENGTH_LONG).show();
                    // Toast.makeText(SampleActivity.this, "Const :  " + PtConstants.PT_GRAB_TYPE_508_508_8_SCAN508_508_8, Toast.LENGTH_LONG).show();
                    // Toast.makeText(SampleActivity.this, "SampleActivity.this.mConn :  " + SampleActivity.this.mConn, Toast.LENGTH_LONG).show();
                    // Toast.makeText(SampleActivity.this, "SampleActivity.this.mRunningOp :  " + SampleActivity.this.mRunningOp, Toast.LENGTH_LONG).show();
                    if (SampleActivity.this.mRunningOp == null) {

                        // Toast.makeText(SampleActivity.this, "SampleActivity.this.mRunningOp est nulle", Toast.LENGTH_LONG).show();
                        SampleActivity.this.mRunningOp = new OpGrab(SampleActivity.this.mConn, PtConstants.PT_GRAB_TYPE_508_508_8_SCAN508_508_8, SampleActivity.this) {

                            // access modifiers changed from: protected
                            public void onDisplayMessage(String message) {
                                SampleActivity.this.dislayMessage(message);
                            }

                            // access modifiers changed from: protected

                            public void onFinished() {
                                creerPointage();
                                synchronized (SampleActivity.this.mCond) {
                                    SampleActivity.this.mRunningOp = null;
                                    SampleActivity.this.mCond.notifyAll();
                                }
                            }

                        };

                        // Toast.makeText(SampleActivity.this, "SampleActivity.this.mRunningOp a été initialisé", Toast.LENGTH_LONG).show();
                        try {
                            SampleActivity.this.mRunningOp.start();

                        } catch (Exception e) {
                            Toast.makeText(SampleActivity.this, "Erreur", Toast.LENGTH_LONG).show();
                            Toast.makeText(SampleActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });
    }

    private void SetQuitButtonListener() {
        ((Button) findViewById(R.id.ButtonQuit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    private boolean initializePtapi() {
        Log.d("MUANZA", "initializePtapi");
        this.mPtGlobal = new PtGlobal();
        try {
            this.mPtGlobal.initialize();
            Log.d("MUANZA", "mPtGlobal a été initialisé");
            return true;
        } catch (UnsatisfiedLinkError e) {
            dislayMessage("libjniPtapi.so not loaded");
            this.mPtGlobal = null;
            return false;
        } catch (PtException e2) {
            dislayMessage(e2.getMessage());
            return false;
        }
    }

    private void terminatePtapi() {
        try {
            if (this.mPtGlobal != null) {
                this.mPtGlobal.terminate();
            }
        } catch (PtException e) {
        }
        this.mPtGlobal = null;
    }

    private void configureOpenedDevice() throws PtException {
        PtSessionCfgV5 sessionCfg = (PtSessionCfgV5) this.mConn.getSessionCfgEx((short) 5);
        sessionCfg.sensorSecurityMode = 0;
        sessionCfg.callbackLevel |= 262144;
        this.mConn.setSessionCfgEx((short) 5, sessionCfg);
    }

    private void openPtapiSessionInternal(String dsn) throws PtException {
        this.mConn = (PtConnectionAdvancedI) this.mPtGlobal.open(dsn);
        try {
            this.mSensorInfo = this.mConn.info();
            configureOpenedDevice();
        } catch (PtException e) {
            if (e.getCode() == -1118 || e.getCode() == -1102 || e.getCode() == -1086) {
                String dsn2 = String.valueOf(dsn) + ",nvmprefix=" + this.msNvmPath + '/';
                this.mConn.close();
                this.mConn = null;
                this.mConn = (PtConnectionAdvancedI) this.mPtGlobal.open(dsn2);
                try {
                    this.mSensorInfo = this.mConn.info();
                    configureOpenedDevice();
                } catch (PtException e2) {
                    this.mConn.formatInternalNVM(0, (byte[]) null, (byte[]) null);
                    this.mConn.close();
                    this.mConn = null;
                    this.mConn = (PtConnectionAdvancedI) this.mPtGlobal.open(dsn2);
                    this.mSensorInfo = this.mConn.info();
                    if ((this.mSensorInfo.sensorType & PtConstants.PT_SENSORBIT_CALIBRATED) == 0) {
                        this.mConn.calibrate(2);
                        this.mSensorInfo = this.mConn.info();
                    }
                }
            } else {
                throw e;
            }
        }
    }

    private void openPtapiSession() {
        Log.d("MUANZA", "openPtapiSession");
        PtException openException = null;
        for (int i = 0; i < mDSN.length; i++) {
            try {
                PtDeviceListItem[] devices = this.mPtGlobal.enumerateDevices(mDSN[i]);
                int d = 0;
                while (d < devices.length) {
                    try {
                        openPtapiSessionInternal(devices[d].dsnSubString);
                        return;
                    } catch (PtException e) {
                        openException = e;
                        d++;
                    }
                }
                continue;
            } catch (PtException e1) {
                if (e1.getCode() != -1004) {
                    dislayMessage("Enumeration failed - " + e1.getMessage());
                    return;
                }
            }
        }
        if (openException == null) {
            dislayMessage("No device found");
        } else {
            dislayMessage("Error during device opening - " + openException.getMessage());
        }
    }

    private void closeSession() {
        if (this.mConn != null) {
            try {
                this.mConn.close();
            } catch (PtException e) {
            }
            this.mConn = null;
        }
    }

    public void dislayMessage(String text) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(0, 0, 0, text));
    }

    public void RunAsRoot() {
        String[] cmds = {" -c \"cd dev\"", " -c \"cd bus\"", " -c \"chmod -R 777 usb\"", "cd dev", "cd bus", "chmod -R 777 usb"};
        try {
            DataOutputStream os = new DataOutputStream(Runtime.getRuntime().exec("su").getOutputStream());
            int length = cmds.length;
            for (int i = 0; i < length; i++) {
                os.writeBytes(String.valueOf(cmds[i]) + "\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
