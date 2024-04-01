package by.ssrlab.gadzinnik;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import by.ssrlab.gadzinnik.databinding.ActivityMainBinding;

public class TimeReceiver extends BroadcastReceiver {

    private final ActivityMainBinding binding;

    public TimeReceiver(ActivityMainBinding binding) {
        this.binding = binding;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        setMinutes();
        setHours();
    }

    public void register(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        context.registerReceiver(this, intentFilter);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public void setHours() {
        binding.watchTimeHours.setText(getTime("HH"));
    }

    public void setMinutes() {
        binding.watchTimeMinutes.setText(getTime("mm"));
    }

    private String getTime(String pattern) {
        Date currentDate = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat(pattern, Locale.ROOT);

        return df.format(currentDate);
    }
}