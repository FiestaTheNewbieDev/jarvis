package fr.fiesta.jarvis.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    public static final String SETTINGS_KEY = "settings";
    public static final String ALARM_PHONE_KEY = "alarm_phone";

    public static String getAlarmPhone(Context context) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_KEY, context.MODE_PRIVATE);
        return settings.getString(ALARM_PHONE_KEY, "");
    }

    public static void setAlarmPhone(Context context, String alarmPhone) {
        SharedPreferences settings = context.getSharedPreferences(SETTINGS_KEY, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ALARM_PHONE_KEY, alarmPhone);
        editor.apply();
    }
}
