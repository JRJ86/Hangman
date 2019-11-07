package dk.riis.jacob.hangman.fragment_controllers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dk.riis.jacob.hangman.R;

/**
 * The first fragment that will be displayed. You can start a new game or checkout the highscores
 */
public class WelcomePage_frag extends Fragment implements View.OnClickListener {

    private Button play, highscore;
    private TextView headline;
    private static final String TAG = "WelcomePage";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome_page,container,false);

        Log.d(TAG,"onCreate: started");

        play = view.findViewById(R.id.goToGame);
        play.setOnClickListener(this);

        highscore = view.findViewById(R.id.goToHighscore);
        highscore.setOnClickListener(this);

        headline = view.findViewById(R.id.headline);
        headline.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View choice) {

        Highscore_frag highscore_frag;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        if (choice == headline ) {
            if (headline.getText().toString() == "Beware the rope!!") {
                headline.setText("Hangman");
            } else {
                headline.setText("Beware the rope!!");
            }
        }else if (choice == play){
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragContainer, new InputName_frag())
                    .addToBackStack(null)
                    .commit();

        }else if (choice == highscore){
            highscore_frag = new Highscore_frag();
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer,highscore_frag);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    }
}
