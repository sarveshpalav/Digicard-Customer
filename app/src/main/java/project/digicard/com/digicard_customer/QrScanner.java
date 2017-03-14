package project.digicard.com.digicard_customer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by sarveshpalav on 24/12/16.
 */


import static android.Manifest.permission.CAMERA;
public class QrScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private static final int PERMISSION_REQUEST_CODE = 200;
    public  String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityqrreader);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!checkPermission()) {
                requestPermission();
            }

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();


    }


    private boolean checkPermission() {

        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);

        return result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {


                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted) {
                        Toast.makeText(QrScanner.this, "Permission Granted", Toast.LENGTH_LONG).show();
                        mScannerView = new ZXingScannerView(this);
                        setContentView(mScannerView);
                        mScannerView.setResultHandler(this);
                        mScannerView.startCamera();

                    } else {

                        Toast.makeText(QrScanner.this, "Permission Granted", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow Camera Permission to Move Forward",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(QrScanner.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        otp = result.getText().toString().trim();
        getbilldetails(otp);

    }


    public void getbilldetails(String otp) {
        final RequestHandler ruc = new RequestHandler();
        class getbilldetails extends AsyncTask<String, String, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(QrScanner.this, "Fetching Your Bill", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                loading.dismiss();
                Toast.makeText(QrScanner.this, s, Toast.LENGTH_LONG).show();

                try {
                    json_handle(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("otp", params[0]);
                data.put("cardid", "100".trim());


                String result = ruc.sendPostRequest(CONFIG.GET_BILL, data);
                return result;
            }
        }
        getbilldetails getbilldetails = new getbilldetails();
        getbilldetails.execute(otp);
    }


    public void json_handle(String result) throws JSONException {

        JSONObject jsonObject;
        jsonObject = new JSONObject(result);
        JSONArray contacts = jsonObject.getJSONArray("result");

        for (int i = 0; i < contacts.length(); i++) {

            JSONObject c = contacts.getJSONObject(i);

            String Sid = c.getString("S_id");
            String Bill = c.getString("Bill");
            String CardName = c.getString("Card_name");
            Bundle bundle = new Bundle();
            bundle.putString("Sid",Sid);
            bundle.putString("bill",Bill);
            bundle.putString("cardname",CardName);
            bundle.putString("otp",otp);

            Intent intent = new Intent(QrScanner.this,PinActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);


            Toast.makeText(QrScanner.this,"Pay"+Bill+"to"+CardName+Sid,Toast.LENGTH_LONG).show();

        }
    }
}
