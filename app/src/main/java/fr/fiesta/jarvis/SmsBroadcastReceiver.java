package fr.fiesta.jarvis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Objects;

public class SmsBroadcastReceiver extends BroadcastReceiver {
    private static ISmsListener listener;

    public SmsBroadcastReceiver() {
        super();
    }

    public static void setListener(ISmsListener listener) {
        SmsBroadcastReceiver.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Objects.equals(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED")) {
            return;
        }

        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");

            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = message.getDisplayOriginatingAddress();
                    String body = message.getDisplayMessageBody();

                    Log.d(this.getClass().getName(), "Listener: " + listener);

                    if (listener != null) {
                        listener.onSmsReceived(sender, body);
                    }
                }
            }
        }
    }
}
