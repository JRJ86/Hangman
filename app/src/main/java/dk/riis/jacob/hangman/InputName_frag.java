package dk.riis.jacob.hangman;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 */
public class InputName_frag extends Fragment implements View.OnClickListener {

    private EditText inputName;
    private Button hangHim;
    private String player;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_name,container,false);

        inputName = view.findViewById(R.id.inputName);

        // Change the background color of a certain EditText
        inputName.getBackground()
                .mutate()
                .setColorFilter(getResources().getColor(R.color.inputNameBackground),
                        PorterDuff.Mode.SRC_ATOP);

        hangHim = view.findViewById(R.id.onToGame);
        hangHim.setOnClickListener(this);

        return view;
        
    }

    /**
     * Does something when you click on a clackable view
     *
     * @param choice the clickable view you click on
     */
    @Override
    public void onClick(View choice) {

        player = inputName.getText().toString();
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

                // This adds the fragment to the stack
//                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"Some error has ocurred: "+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
