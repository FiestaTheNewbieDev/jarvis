package fr.fiesta.jarvis.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    public static final String[] SMS_PERMISSIONS = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
    };
    public static final int SMS_PERMISSION_REQUEST_CODE = 1001;

    public static boolean hasSmsPermission(Context context) {
        for (String permission : SMS_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void requestSmsPermission(Activity activity) {
        Log.d(PermissionManager.class.getName(), String.format("Requesting SMS permission for activity %s", activity.getClass().getName()));

        ActivityCompat.requestPermissions(activity, SMS_PERMISSIONS, SMS_PERMISSION_REQUEST_CODE);
    }
}
