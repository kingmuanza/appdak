package cm.security.dak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.authentec.java.ptapi.Technocrat.basicsample.FingerId;
import com.authentec.java.ptapi.Technocrat.basicsample.OpEnroll;
import com.authentec.java.ptapi.Technocrat.basicsample.OpVerifyAll;
import com.upek.android.ptapi.PtConnectionAdvancedI;
import com.upek.android.ptapi.PtConstants;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.PtGlobal;
import com.upek.android.ptapi.struct.PtDeviceListItem;
import com.upek.android.ptapi.struct.PtInfo;
import com.upek.android.ptapi.struct.PtInputBir;
import com.upek.android.ptapi.struct.PtSessionCfgV5;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import cm.security.dak.models.Affectation;
import cm.security.dak.services.EmpreinteService;
import cm.security.dak.services.VigileService;

public class VigileDetailActivity extends AppCompatActivity {
    int id;
    Toolbar toolbar;
    ImageView imageViewEmpreinte;
    TextView maConsoleTextView;
    TextView derniereAffectation;
    TextView derniereAffectationCoordonnees;
    PtInputBir empreinteEnregistree;
    EmpreinteService mEmpreinteService = new EmpreinteService(this);
    public List<Affectation> mAffectations = new ArrayList<Affectation>();
    Affectation mAffectation = null;

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
            ((TextView) VigileDetailActivity.this.findViewById(R.id.EnrollmentTextView)).setText((String) aMsg.obj);
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

    private Runnable updateTimerMethod = new Runnable() {
        public void run() {
            VigileDetailActivity sampleActivity = VigileDetailActivity.this;
            sampleActivity.millseconds = sampleActivity.millseconds + 1;
            if (VigileDetailActivity.this.millseconds == 60) {
                VigileDetailActivity.this.logout();
            }
            VigileDetailActivity.this.myHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File aDir;
        Context aContext = getBaseContext();
        if (!(aContext == null || (aDir = aContext.getDir("tcstore", Context.MODE_PRIVATE)) == null)) {
            this.msNvmPath = aDir.getAbsolutePath();
        }

        setContentView(R.layout.activity_vigile_detail);

        toolbar = findViewById(R.id.vigile_detail_activity_toolbar);
        imageViewEmpreinte = findViewById(R.id.vigile_detail_activity_img_empreinte);
        maConsoleTextView = findViewById(R.id.vigile_detail_activity_console);
        derniereAffectation = findViewById(R.id.vigile_detail_derniere_affectation);
        derniereAffectationCoordonnees = findViewById(R.id.vigile_detail_derniere_affectation_coordonnees);

        setSupportActionBar(toolbar);

        initialiserLesInformations();

        initialiserLecteurEmpreinte();

    }

    private void initialiserLecteurEmpreinte() {
        if (initializePtapi()) {
            openPtapiSession();
            if (this.mConn != null) {
                setIdentifyButtonListener();
                setEnrollButtonListener(R.id.ButtonEnroll, FingerId.RIGHT_THUMB);
                setVerifyAllButtonListener();
                setNavigateRawButtonListener();
            }
        }
        SetQuitButtonListener();
        this.myHandler.postDelayed(this.updateTimerMethod, 1000);
    }

    private void initialiserLesInformations() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("nom");
            id = extras.getInt("id");

            if (id != 0) {
                getAffectations();
            }

            Log.d("MUANZA", "Nom du vigile : " + nom);
            Log.d("MUANZA", "ID du vigile : " + id);
            toolbar.setTitle(nom);

            String messageDeMaConsole = "";
            File fichierEmpreinte = getFichierEmpreinte(id);
            if (fichierEmpreinte != null) {
                messageDeMaConsole += "\n" + "Le fichier d'empreinte est présent dans cet appareil ";
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichierEmpreinte));
                    PtInputBir template = (PtInputBir) ois.readObject();
                    if (template != null) {
                        empreinteEnregistree = template;
                    }
                } catch (IOException e) {
                    messageDeMaConsole += "\n" + "Problème ObjectInputStream ";
                    messageDeMaConsole += "\n" + e.getMessage();
                } catch (ClassNotFoundException e) {
                    messageDeMaConsole += "\n" + "Problème de conversion de l'objet ";
                    messageDeMaConsole += "\n" + e.getMessage();
                }
            } else {
                messageDeMaConsole += "\n" + "On n'a pas trouvé le fichier d'empreinte dans cet appareil ";
            }
            maConsoleTextView.setText(messageDeMaConsole);
        }
    }

    private void getAffectations() {
        Log.d("MUANZA", "getAffectations du vigile : " + id);
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {
                mAffectations = this.affectations;
                mAffectation = this.getAffectationEnCours();
                if (mAffectation != null) {
                    if (mAffectation.getIdposte() != null) {
                        Log.d("MUANZA", "le poste n'est pas nul : ");
                        derniereAffectation.setText(mAffectation.getIdposte().getLibelle());
                        String coordonnees = "Longitude : " + mAffectation.getIdposte().getLongitude();
                        coordonnees += " ;  Latitude : " + mAffectation.getIdposte().getLatitude();
                        derniereAffectationCoordonnees.setText(coordonnees);
                    } else {
                        Log.d("MUANZA", "le poste est nul : ");
                    }
                }
                if (!mAffectations.isEmpty()) {
                    for (int i = 0; i < mAffectations.size(); i++) {
                        Affectation affectation = mAffectations.get(i);
                        Log.d("MUANZA", "Affectation : " + affectation.getIdaffectation());
                        Log.d("MUANZA", "Affectation arret : " + affectation.getArret());
                        Log.d("MUANZA", "Affectation poste : " + affectation.getIdposte().getLibelle());
                    }
                }
            }
        };
        vigileService.getAffectations(id);
    }

    public void sauvegarderEmpreinte(PtInputBir empreinteSerialisee) throws IOException {
        mEmpreinteService.sauvegarderEmpreinte(empreinteSerialisee, id);
    }

    public File getFichierEmpreinte(int id) {
        return mEmpreinteService.getFichierEmpreinte(id);
    }

    public void updateVigile(int id) {
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {
                Toast.makeText(VigileDetailActivity.this, "Mis à jour sur le serveur", Toast.LENGTH_LONG).show();
            }
        };
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

    private void setIdentifyButtonListener() {
    }

    private void setNavigateRawButtonListener() {
    }

    private void setVerifyAllButtonListener() {
        Button boutonVerification = (Button) findViewById(R.id.ButtonVerify);
        boutonVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (empreinteEnregistree != null) {
                    OpVerifyAll opVerifyAll = new OpVerifyAll(mConn, empreinteEnregistree) {
                        @Override
                        public void onDisplayMessage(String message) {
                            VigileDetailActivity.this.dislayMessage(message);
                        }

                        @Override
                        public void onFinished() {
                            synchronized (VigileDetailActivity.this.mCond) {

                                VigileDetailActivity.this.mRunningOp = null;
                                VigileDetailActivity.this.mCond.notifyAll();
                            }
                        }
                    };
                    VigileDetailActivity.this.mRunningOp = opVerifyAll;
                    opVerifyAll.start();

                } else {
                    OpVerifyAll opVerifyAll = new OpVerifyAll(mConn) {
                        @Override
                        public void onDisplayMessage(String message) {
                            VigileDetailActivity.this.dislayMessage(message);
                        }

                        @Override
                        public void onFinished() {
                            synchronized (VigileDetailActivity.this.mCond) {

                                VigileDetailActivity.this.mRunningOp = null;
                                VigileDetailActivity.this.mCond.notifyAll();
                            }
                        }
                    };
                    VigileDetailActivity.this.mRunningOp = opVerifyAll;
                    opVerifyAll.start();
                }
            }
        });
        if (empreinteEnregistree != null) {
            boutonVerification.setVisibility(View.VISIBLE);
        } else {
            boutonVerification.setVisibility(View.GONE);
        }
    }

    private void setEnrollButtonListener(int buttonId, final int fingerId) {
        String message = "";
        Log.d("MUANZA", "setEnrollButtonListener");
        Toast.makeText(this, "setEnrollButtonListener", Toast.LENGTH_LONG).show();
        ((Button) findViewById(buttonId)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                synchronized (VigileDetailActivity.this.mCond) {
                    if (VigileDetailActivity.this.mRunningOp == null) {
                        OpEnroll opEnroll = new OpEnroll(VigileDetailActivity.this.mConn, fingerId) {
                            /* access modifiers changed from: protected */
                            public void onDisplayMessage(String message) {
                                // Toast.makeText(VigileDetailActivity.this, message, Toast.LENGTH_LONG).show();
                                VigileDetailActivity.this.dislayMessage(message);
                            }

                            /* access modifiers changed from: protected */
                            public void onFinished() {
                                Log.d("MUANZA", "On a fini la prise d'empreinte");
                                String message = "On a fini la prise d'empreinte\n";
                                PtInputBir empreinteSerialisee = this.empreinteSerialisee;

                                if (empreinteSerialisee != null) {
                                    message += "On a une empreinte sérailisée !!!\n";
                                    VigileDetailActivity.this.dislayMessage(message);
                                    try {
                                        sauvegarderEmpreinte(empreinteSerialisee);
                                    } catch (IOException e) {
                                        Log.d("MUANZA", "erreur : " + e.getMessage());
                                    }

                                } else {
                                    VigileDetailActivity.this.dislayMessage("NDEM, On n'a pas d'empreinte sérailisée !!!");
                                }

                                synchronized (VigileDetailActivity.this.mCond) {
                                    VigileDetailActivity.this.mRunningOp = null;
                                    VigileDetailActivity.this.mCond.notifyAll();
                                }
                            }
                        };
                        VigileDetailActivity.this.mRunningOp = opEnroll;
                        opEnroll.start();

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