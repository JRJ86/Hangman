package dk.riis.jacob.hangman;

import android.os.Bundle;
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

public class FirstPage_frag extends Fragment implements View.OnClickListener {

    private Button play, highscore;
    private TextView headline;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page,container,false);

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
                headline.setText("Hangman!");
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
