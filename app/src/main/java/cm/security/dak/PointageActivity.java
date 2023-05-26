package cm.security.dak;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.authentec.java.ptapi.Technocrat.basicsample.OpEnroll;
import com.upek.android.ptapi.PtConnectionAdvancedI;
import com.upek.android.ptapi.PtConstants;
import com.upek.android.ptapi.PtException;
import com.upek.android.ptapi.PtGlobal;
import com.upek.android.ptapi.struct.PtDeviceListItem;
import com.upek.android.ptapi.struct.PtInfo;
import com.upek.android.ptapi.struct.PtInputBir;
import com.upek.android.ptapi.struct.PtSessionCfgV5;

import cm.security.dak.models.Affectation;
import cm.security.dak.models.Vigile;
import cm.security.dak.models.VigileEmpreinte;
import cm.security.dak.services.EmpreinteService;
import cm.security.dak.services.GPSTracker;
import cm.security.dak.services.OpVerifyCustom;
import cm.security.dak.services.VigileService;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class PointageActivity extends AppCompatActivity {

    int id;
    Toolbar toolbar;
    ImageView imageViewEmpreinte;
    TextView maConsoleTextView;
    TextView derniereAffectation;
    PtInputBir empreinteEnregistree;
    EmpreinteService mEmpreinteService = new EmpreinteService(this);
    public List<VigileEmpreinte> vigilesEmpreintes = new ArrayList<>();
    Vigile vigileQuiPointe = null;
    public List<Affectation> mAffectations = new ArrayList<Affectation>();
    Affectation mAffectation = null;
    double latitude = -1;
    double longitude = -1;

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
            ((TextView) PointageActivity.this.findViewById(R.id.EnrollmentTextView)).setText((String) aMsg.obj);
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
            PointageActivity sampleActivity = PointageActivity.this;
            sampleActivity.millseconds = sampleActivity.millseconds + 1;
            if (PointageActivity.this.millseconds == 60) {
                PointageActivity.this.logout();
            }
            PointageActivity.this.myHandler.postDelayed(this, 1000);
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

        setContentView(R.layout.activity_pointage);

        toolbar = findViewById(R.id.pointage_activity_toolbar);
        maConsoleTextView = findViewById(R.id.pointage_description);
        derniereAffectation = findViewById(R.id.activity_pointage_affectation_localisation);

        setSupportActionBar(toolbar);

        initialiserLesInformations();

        initialiserLecteurEmpreinte();

        getVigilesAndEmpreintes();

    }

    private void initialiserLecteurEmpreinte() {
        if (initializePtapi()) {
            openPtapiSession();
            if (this.mConn != null) {
                setIdentifyButtonListener();
                setVerifyAllButtonListener();
                setNavigateRawButtonListener();
            }
        }
        // SetQuitButtonListener();
        this.myHandler.postDelayed(this.updateTimerMethod, 1000);
    }

    private void initialiserLesInformations() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nom = extras.getString("nom");
            id = extras.getInt("id");
            Log.d("MUANZA", "Nom du vigile : " + nom);
            Log.d("MUANZA", "ID du vigile : " + id);
            toolbar.setTitle(nom);

            String messageDeMaConsole = "";
            File fichierEmpreinte = getFichierEmpreinte(id);
            if (fichierEmpreinte != null) {
                messageDeMaConsole += "\n" + "Le fichier d'empreinte est présent dans cet appareil ";
                try {
                    empreinteEnregistree = deserialiser(fichierEmpreinte);
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

    private PtInputBir deserialiser(File fichierEmpreinte) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichierEmpreinte));
        PtInputBir template = (PtInputBir) ois.readObject();
        return template;
    }

    public void sauvegarderEmpreinte(PtInputBir empreinteSerialisee) throws IOException {
        mEmpreinteService.sauvegarderEmpreinte(empreinteSerialisee, id);
    }

    public File getFichierEmpreinte(int id) {
        return mEmpreinteService.getFichierEmpreinte(id);
    }

    public void getVigilesAndEmpreintes() {
        Log.d("MUANZA", "getVigilesAndEmpreintes");
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {
                List<Vigile> vigiles = this.vigiles;
                Log.d("MUANZA", "On a récupéré les vigiles");
                for (int i = 0; i < vigiles.size(); i++) {
                    Vigile v = vigiles.get(i);

                    File f = getFichierEmpreinte(v.getIdvigile());
                    if (f != null) {
                        Log.d("MUANZA", "Le vigile " + v.getIdvigile() + " a ses empreintes enregistrées : " + f.getAbsolutePath());
                        try {
                            PtInputBir empreinte = deserialiser(f);
                            VigileEmpreinte vg = new VigileEmpreinte(v, empreinte);
                            vigilesEmpreintes.add(vg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.d("MUANZA", "Nous avons " + vigilesEmpreintes.size() + " empreintes enregistrées");
            }
        };
        vigileService.getVigiles();
    }

    public void updateVigile(int id) {
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {
                Toast.makeText(PointageActivity.this, "Mis à jour sur le serveur", Toast.LENGTH_LONG).show();
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
        Button boutonVerification = (Button) findViewById(R.id.pointage_acivity_bouton_pointer);
        boutonVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maConsoleTextView.setVisibility(View.VISIBLE);
                maConsoleTextView.setText("Veuillez mettre votre pouce droit sur le lecteur d'empreinte");
                {
                    OpVerifyCustom opVerifyAll = new OpVerifyCustom(mConn, vigilesEmpreintes) {
                        @Override
                        public void onDisplayMessage(String message) {
                            PointageActivity.this.dislayMessage(message);
                        }

                        @Override
                        public void onFinished() {

                            vigileQuiPointe = this.vigile;
                            if (vigileQuiPointe != null) {
                                getAffectations();
                            } else {

                            }
                            synchronized (PointageActivity.this.mCond) {

                                PointageActivity.this.mRunningOp = null;
                                PointageActivity.this.mCond.notifyAll();
                            }
                        }
                    };
                    PointageActivity.this.mRunningOp = opVerifyAll;
                    opVerifyAll.start();
                }
            }
        });
    }

    private void getAffectations() {

        Log.d("MUANZA", "getAffectations du vigile : " + id);
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {

                mAffectation = this.getAffectationEnCours();
                if (mAffectation != null) {
                    if (mAffectation.getIdposte() != null) {
                        if (mAffectation.getIdposte().getLibelle() != null) {
                            Log.d("MUANZA", "le poste n'est pas nul : ");
                            derniereAffectation.setText("Affectation : " + mAffectation.getIdposte().getLibelle());
                            String coordonnees = "Longitude : " + mAffectation.getIdposte().getLongitude();
                            coordonnees += " ;  Latitude : " + mAffectation.getIdposte().getLatitude();

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                String lescoordonnees = getLocation(PointageActivity.this);
                                derniereAffectation.setText(derniereAffectation.getText() + "\n" + lescoordonnees);
                                sauvegarderPointage();
                            }
                        } else {
                            Log.d("MUANZA", "le poste est nul : ");
                            String estRemplacant = "\nRemplacant";

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                derniereAffectation.setText(derniereAffectation.getText() + "\n" + estRemplacant);
                                sauvegarderPointage();
                            }
                        }
                    } else {
                        Log.d("MUANZA", "le poste est nul : ");
                        String estRemplacant = "\nRemplacant";

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            derniereAffectation.setText(derniereAffectation.getText() + "\n" + estRemplacant);
                            sauvegarderPointage();
                        }
                    }
                }
                // maConsoleTextView.setVisibility(View.GONE);
                // derniereAffectation.setText("");
                if (!mAffectations.isEmpty()) {
                    for (int i = 0; i < mAffectations.size(); i++) {
                        Affectation affectation = mAffectations.get(i);
                        Log.d("MUANZA", "Affectation : " + affectation.getIdaffectation());
                        Log.d("MUANZA", "Affectation arret : " + affectation.getArret());
                        Log.d("MUANZA", "Affectation arret : " + affectation.getIdposte());
                    }
                }
            }
        };
        vigileService.getAffectations(vigileQuiPointe.getIdvigile());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public String getLocation(Context c) {
        // Log.d("MUANZA", "getLocation mGPSTracker : ");
        GPSTracker gPSTracker = new GPSTracker(c);

        if (gPSTracker.canGetLocation()) {
            latitude = gPSTracker.getLatitude();
            longitude = gPSTracker.getLongitude();
            // Log.d("MUANZA", "latitude mGPSTracker : " + latitude);
            // Log.d("MUANZA", "longitude mGPSTracker : " + longitude);
            if (longitude != -1 && latitude != -1) {
            } else {
                // Toast.makeText(view.getContext(), "Impossible de récupérer les coordonnées", Toast.LENGTH_LONG).show();
            }

        } else {
            gPSTracker.showSettingsAlert();
        }
        return "Latitude : " + latitude + "; Longitude : " + longitude;
    }

    public void sauvegarderPointage() {
        VigileService vigileService = new VigileService() {
            @Override
            public void actionOnFinish() {
                Toast.makeText(PointageActivity.this, "Pointage enregistré en ligne", Toast.LENGTH_LONG).show();
            }
        };
        vigileService.creerPointage(vigileQuiPointe, longitude, latitude);
    }

    private void setEnrollButtonListener(int buttonId, final int fingerId) {
        String message = "";
        Log.d("MUANZA", "setEnrollButtonListener");
        Toast.makeText(this, "setEnrollButtonListener", Toast.LENGTH_LONG).show();
        ((Button) findViewById(buttonId)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                synchronized (PointageActivity.this.mCond) {
                    if (PointageActivity.this.mRunningOp == null) {
                        OpEnroll opEnroll = new OpEnroll(PointageActivity.this.mConn, fingerId) {
                            /* access modifiers changed from: protected */
                            public void onDisplayMessage(String message) {
                                // Toast.makeText(PointageActivity.this, message, Toast.LENGTH_LONG).show();
                                PointageActivity.this.dislayMessage(message);
                            }

                            /* access modifiers changed from: protected */
                            public void onFinished() {
                                Log.d("MUANZA", "On a fini la prise d'empreinte");
                                String message = "On a fini la prise d'empreinte\n";
                                PtInputBir empreinteSerialisee = this.empreinteSerialisee;

                                if (empreinteSerialisee != null) {
                                    message += "On a une empreinte sérailisée !!!\n";
                                    PointageActivity.this.dislayMessage(message);
                                    try {
                                        sauvegarderEmpreinte(empreinteSerialisee);
                                    } catch (IOException e) {
                                        Log.d("MUANZA", "erreur : " + e.getMessage());
                                    }

                                } else {
                                    PointageActivity.this.dislayMessage("NDEM, On n'a pas d'empreinte sérailisée !!!");
                                }

                                synchronized (PointageActivity.this.mCond) {
                                    PointageActivity.this.mRunningOp = null;
                                    PointageActivity.this.mCond.notifyAll();
                                }
                            }
                        };
                        PointageActivity.this.mRunningOp = opEnroll;
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