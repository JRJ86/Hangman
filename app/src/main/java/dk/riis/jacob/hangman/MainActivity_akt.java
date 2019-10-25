package dk.riis.jacob.hangman;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity_akt extends AppCompatActivity implements View.OnClickListener {

    private static  final String TAG = "MainActivity";

    private Button btn;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started.");

        if (savedInstanceState == null){
            Fragment fragment = new WelcomePage_frag();
            getSupportFragmentManager().beginTransaction().add(R.id.fragContainer,fragment).commit();
        }


    }

    @Override
    public void onClick(View choice) {

        if (choice == btn){

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
