package by.ssrlab.gadzinnik;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.TimeZone;

import by.ssrlab.gadzinnik.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    ActivityMainBinding binding;
    TimeReceiver receiver;
    TextToSpeech tts;
    Boolean isTTSInitialized = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tts = new TextToSpeech(this, this);
        tts.setLanguage(new Locale("ru", "BY"));

        receiver = new TimeReceiver(binding);
        receiver.register(this);

        initTime();
        handleTimeClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        receiver.unregister(this);
    }

    private void initTime() {
        TimeZone tz = TimeZone.getDefault();
        binding.watchText.setText(tz.getID());

        receiver.setHours();
        receiver.setMinutes();
    }

    private void handleTimeClick() {
        binding.timeRipple.setOnClickListener( v -> {
            if (isTTSInitialized) {
                String textToSay = "Бягучы час " +
                        binding.watchTimeHours.getText() +
                        "гадзин, " +
                        binding.watchTimeMinutes.getText() +
                        "хвилин.";

                tts.speak(textToSay, TextToSpeech.QUEUE_ADD, null, null);
            }
        });
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInitialized = true;
        }
    }
}