package fr.fiesta.jarvis.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentContainerView;

import java.util.regex.Pattern;

import fr.fiesta.jarvis.ISmsListener;
import fr.fiesta.jarvis.R;
import fr.fiesta.jarvis.SmsBroadcastReceiver;
import fr.fiesta.jarvis.constants.ateos.Response;
import fr.fiesta.jarvis.fragments.LoadingFragment;
import fr.fiesta.jarvis.services.AteosService;
import fr.fiesta.jarvis.utils.PermissionManager;
import fr.fiesta.jarvis.utils.PreferencesManager;

@SuppressLint("CustomSplashScreen")
public class FirstLaunchActivity extends AppCompatActivity implements ISmsListener {
    private SmsBroadcastReceiver smsBroadcastReceiver;

    private LoadingFragment loadingFragment;
    private EditText alarmPhoneInput;
    private EditText alarmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_first_launch);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initElements();

        SmsBroadcastReceiver.setListener(this);
    }

    private boolean validateInputs() {
        String alarmPhone = alarmPhoneInput.getText().toString().trim();
        String alarmPassword = alarmPasswordInput.getText().toString().trim();

        boolean valid = true;
        if (alarmPhone.isEmpty() || !Pattern.matches("\\+?\\d{1,3}[\\s-]?(\\(\\d{1,4}\\)|\\d{1,4})[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}[\\s-]?\\d{1,4}", alarmPhone)) {
            valid = false;
            alarmPhoneInput.setError(getString(R.string.error_invalid_phone));
        }
        if (alarmPassword.isEmpty() || !Pattern.matches("\\d{4}", alarmPassword)) {
            valid = false;
            alarmPasswordInput.setError(getString(R.string.error_invalid_password));
        }

        return valid;
    }

    private void handleContinueButton() {
        if (!validateInputs()) return;

        loadingFragment.show();

        PermissionManager.requestSmsPermission(this);
    }

    private void handleExitButton() {
        finish();
    }

    private void initElements() {
        loadingFragment = (LoadingFragment) getSupportFragmentManager().findFragmentById(R.id.loadingFragment);
        alarmPhoneInput = findViewById(R.id.inputAlarmPhone);
        alarmPasswordInput = findViewById(R.id.inputAlarmPassword);

        Button continueButton = findViewById(R.id.buttonContinue);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleContinueButton();
            }
        });

        Button exitButton = findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleExitButton();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(this.getClass().getName(), "Request permissions result received");

        switch (requestCode) {
            case PermissionManager.SMS_PERMISSION_REQUEST_CODE: {
                Log.d(this.getClass().getName(), "SMS permission granted");

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String alarmPhone = alarmPhoneInput.getText().toString().trim();
                    String alarmPassword = alarmPasswordInput.getText().toString().trim();

                    AteosService ateosService = new AteosService(this, alarmPhone, alarmPassword);
                    ateosService.sendStatusRequest();
                } else {
                    // TO DO -> Display error message
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onSmsReceived(String sender, String message) {
        String alarmPhone = alarmPhoneInput.getText().toString().trim();

        if (sender.equals(alarmPhone)) {
            for (String status : (String[]) Response.STATUS.getValue()) {
                if (message.contains(status)) {
                    try {
                        PreferencesManager.setAlarmPhone(this, alarmPhone);
                    } catch (Exception exception) {
                        Log.e(this.getClass().getName(), "Failed to save alarm phone number", exception);
                        loadingFragment.hide();
                        return;
                    }

                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
            }
        }

        loadingFragment.hide();
    }
}