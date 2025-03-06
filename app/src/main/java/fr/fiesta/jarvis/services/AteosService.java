package fr.fiesta.jarvis.services;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

import fr.fiesta.jarvis.constants.ateos.Request;

public class AteosService {
    private final Context context;
    private final String phone;
    private final String password;

    public AteosService(Context context, String phone, String password) {
        this.context = context;
        this.phone = phone;
        this.password = password;
    }

    private void sendSms(String content) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phone, null, content, null, null);
    }

    public void sendStatusRequest() {
        try {
            sendSms(String.format(Request.STATUS.getValue(), password));
        } catch (Exception exception) {
            Log.e(AteosService.class.getName(), "Failed to send SMS", exception);
        }
    };
}
