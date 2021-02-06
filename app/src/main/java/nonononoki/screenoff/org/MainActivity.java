package nonononoki.screenoff.org;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {

    private final int MIN_BRIGHTNESS = 1;
    private final int OFF_BRIGHTNESS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button offButton = findViewById(R.id.off_screen);
        Button minButton = findViewById(R.id.min_brightness);
        EditText delayET = findViewById(R.id.off_screen_delay);

        offButton.setOnClickListener(view -> {
            try {
                String[] cmds = {"sleep %d", "su -c 'echo %d > /sys/class/leds/lcd-backlight/brightness'"};
                int delay = Integer.parseInt(delayET.getText().toString());
                int brightness = OFF_BRIGHTNESS;
                Process p = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(p.getOutputStream());
                os.writeBytes(String.format(cmds[0], delay) + "\n");
                os.writeBytes(String.format(cmds[1], brightness) + "\n");
                os.writeBytes("exit\n");
                os.flush();
            } catch (Exception e) {}
        });

        minButton.setOnClickListener(view -> {
            try {
                String[] cmds = {"su -c 'echo %d > /sys/class/leds/lcd-backlight/brightness'"};
                int brightness = MIN_BRIGHTNESS;
                Process p = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(p.getOutputStream());
                os.writeBytes(String.format(cmds[0], brightness) + "\n");
                os.writeBytes("exit\n");
                os.flush();
            } catch (Exception e) {}
        });
    }

}