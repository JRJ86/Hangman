package dk.riis.jacob.hangman.fragment_controllers;


import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dk.riis.jacob.hangman.R;


/**
 * This fragment gets a name from the player and sends it o the game page along with the user
 */
public class InputName_frag extends Fragment implements View.OnClickListener {

    private EditText inputName;
    private Button hangHim;
    private static final String TAG = "InputName";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_name,container,false);

        Log.d(TAG,"onCreate: started");

        inputName = view.findViewById(R.id.inputName);

        // Change the background color of a certain EditText
        inputName.getBackground()
                .mutate()
                .setColorFilter(getResources().getColor(R.color.inputNameBackground),
                        PorterDuff.Mode.SRC_ATOP);
        inputName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            /**
             * This function removes the keyboard when the EditText no longer has focus
             * @param v
             * @param hasFocus
             */
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });

        hangHim = view.findViewById(R.id.onToGame);
        hangHim.setOnClickListener(this);

        return view;
        
    }

    @Override
    public void onClick(View choice) {

        String player = inputName.getText().toString();
        inputName.setError(null);

        if (choice == hangHim && player.length() == 0 ){
            inputName.setError("Input your name.");
            Toast.makeText(getActivity(),"Input a name, or the game won´t start.", Toast.LENGTH_LONG).show();
        }

        try {
            if (choice == hangHim){

                // create a bundle with data to send
                Bundle bundle = new Bundle();
                bundle.putString("name", player);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                GamePage_frag gamePage_frag = new GamePage_frag();
                gamePage_frag.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragContainer,gamePage_frag);
                fragmentTransaction.commit();

            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"Some error has ocurred: "+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
