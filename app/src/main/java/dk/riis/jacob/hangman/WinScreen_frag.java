package dk.riis.jacob.hangman;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class WinScreen_frag extends Fragment implements View.OnClickListener {

    private Button yes, no;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_win_screen,container,false);

        TextView numberOfTries;
        int tries;

        Bundle bundle = getArguments();
        tries = bundle.getInt("tries");

        yes = view.findViewById(R.id.winYes);
        no = view.findViewById(R.id.winNo);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        numberOfTries = view.findViewById(R.id.numberOfTries);
        numberOfTries.setText("You used "+tries+" tries to achieve victory.");

        return view;
    }

    @Override
    public void onClick(View choice) {

        WelcomePage_frag welcomePage_frag;
        InputName_frag inputName_frag;
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        if (choice == yes){
            inputName_frag = new InputName_frag();
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer,inputName_frag);
            fragmentTransaction.commit();
            GamePage_frag.galgelogik.nulstil();

        }else if (choice == no){
            welcomePage_frag = new WelcomePage_frag();
            fragmentManager = getActivity().getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragContainer, welcomePage_frag);
            fragmentTransaction.commit();
            GamePage_frag.galgelogik.nulstil();

        }
    }
//    public void skjulTastatur(View v) {
//        InputMethodManager imm = (InputMethodManager).getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//    }

}
