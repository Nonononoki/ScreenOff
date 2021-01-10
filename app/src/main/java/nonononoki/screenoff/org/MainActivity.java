package nonononoki.screenoff.org;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        EditText delayET = findViewById(R.id.delay);
        EditText brightnessET = findViewById(R.id.brightness);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] cmds = {"sleep %d", "su -c 'echo %d > /sys/class/leds/lcd-backlight/brightness'"};
                    int delay = Integer.parseInt(delayET.getText().toString());
                    int brightness = Integer.parseInt(brightnessET.getText().toString());
                    Process p = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(p.getOutputStream());
                    os.writeBytes(String.format(cmds[0], delay) + "\n");
                    os.writeBytes(String.format(cmds[1], brightness) + "\n");
                    os.writeBytes("exit\n");
                    os.flush();
                } catch (Exception e) {}
            }
        });
    }

}