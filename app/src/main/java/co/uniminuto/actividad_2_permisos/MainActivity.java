package co.uniminuto.actividad_2_permisos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Codigo de validacion de permisos
    public static final int REQUESTCODE = 100;

    //Declaracion de objetos del activity
    private Button Checkpermissions;
    private Button RequestPermissions;
    private TextView tvCamera;
    private TextView tvCalls;
    private TextView tvMicrophone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Metodo que contiene la asociacion con los elementos del Activity
        begin();
        Checkpermissions.setOnClickListener(this::VerifyPermissions);
        //Metodo para agregar el on click en el boton de requerir permisos
        findViewById(R.id.btnequestpermissions).setOnClickListener(
                v -> Requestpermission()
        );
    }

    //Validación de permisos 0 otorgado y 1 no otorgado
    private void VerifyPermissions(View view) {
        //Valores base para comprobar si se tiene permiso (0) o no se tiene (1)
        int statusperCamera = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        int statuspermicrophone = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int statusperCalls = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        //Cargar el estado actual de cada textview una vez se oprima el botn de verificar
        tvCamera.setText("Status de permiso de Camara: " + statusperCamera);
        tvMicrophone.setText("Status de permiso de Microfono: " + statuspermicrophone);
        tvCalls.setText("Status de permiso de Telefono: " + statusperCalls);
        RequestPermissions.setEnabled(true);
    }

    //Metodo que contiene la asociacion con los elementos del Activity
    private void begin() {
        tvCamera = findViewById(R.id.tvCamera);
        tvMicrophone = findViewById(R.id.tvMicrophone);
        tvCalls = findViewById(R.id.tvCalls);
        Checkpermissions = findViewById(R.id.btncheckpermissions);
        RequestPermissions = findViewById(R.id.btnequestpermissions);
        //Deshabilitar el boton request
        RequestPermissions.setEnabled(false);
    }

    //Metodo de solicitud de permisos
    private void Requestpermission() {
        //Para iniciar el metodo vamos a validar si los permisos de la camara, telefono y Microfono.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUESTCODE);
        }else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUESTCODE);
        }else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUESTCODE);
        } else {
            //Ya se encuentran todos los permisos, mensaje en pantalla
            Toast.makeText(this, "Ya se cuenta con todos los permisos", Toast.LENGTH_LONG).show();
        }
    }

    //Metodo de que valida la respuesta del usuario
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //valida si se garantizo el permiso y si no ejecuta los pasos al interior del if
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == -1) {

                    //Metodo que crea un pop up que permite ir a ajustes
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("El permiso de la Camara esta denegado. Por favor habilite la opción desde ajustes. ")
                            .setTitle("Permiso requerido")
                            .setCancelable(false)
                            .setNegativeButton("No permitir", ((dialog, which) -> dialog.dismiss()))
                            .setPositiveButton("Ajustes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == -1) {

                    //Metodo que crea un pop up que permite ir a ajustes
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("El permiso de Registro Telefonico esta denegado. Por favor habilite la opción desde ajustes. ")
                            .setTitle("Permiso requerido")
                            .setCancelable(false)
                            .setNegativeButton("No permitir", ((dialog, which) -> dialog.dismiss()))
                            .setPositiveButton("Ajustes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == -1) {

                    //Metodo que crea un pop up que permite ir a ajustes
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("El permiso de Microfono esta denegado. Por favor habilite la opción desde ajustes. ")
                            .setTitle("Permiso requerido")
                            .setCancelable(false)
                            .setNegativeButton("No permitir", ((dialog, which) -> dialog.dismiss()))
                            .setPositiveButton("Ajustes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    startActivity(intent);

                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }

            }
        }
    }
}
