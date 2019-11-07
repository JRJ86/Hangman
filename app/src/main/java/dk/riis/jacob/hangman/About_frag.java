package dk.riis.jacob.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * This class gives functionality to the About fragment
 */
public class About_frag extends Fragment implements View.OnClickListener {

    private Button back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);

        back = view.findViewById(R.id.backFromAbout);
        back.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        WelcomePage_frag welcomePage_frag = new WelcomePage_frag();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragContainer, welcomePage_frag);
        fragmentTransaction.commit();

    }
}
