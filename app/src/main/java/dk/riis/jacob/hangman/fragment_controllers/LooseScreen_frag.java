package dk.riis.jacob.hangman.fragment_controllers;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.luolc.emojirain.EmojiRainLayout;

import dk.riis.jacob.hangman.R;

/**
 * The fragment that is displayed when a game is lost.
 * The correct word is displayed, sent from the game page fragment
 */
public class LooseScreen_frag extends Fragment implements View.OnClickListener {

    private EmojiRainLayout emojiRainLayout;

    private Button yes, no;
    private static final String TAG = "LooseScreen";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loose_screen, container, false);

        Log.d(TAG,"onCreate: started");

        TextView rightWord;
        String word;

        Bundle bundle = getArguments();
        word = bundle.getString("word");

        yes = view.findViewById(R.id.looseYes);
        no = view.findViewById(R.id.looseNo);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        rightWord = view.findViewById(R.id.rightWord);
        rightWord.setText("The right word was: "+word);

        emojiRainLayout = view.findViewById(R.id.rain);
        emojiRainLayout.addEmoji(R.drawable.skull);
        emojiRainLayout.addEmoji(R.drawable.skull2);
        emojiRainLayout.addEmoji(R.drawable.bones);

        emojiRainLayout.setPer(10);             //
        emojiRainLayout.setDuration(7200);      //
        emojiRainLayout.setDropDuration(2400);  //
        emojiRainLayout.setDropFrequency(500);  //
        emojiRainLayout.startDropping();        //

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

    /**
     * This function hides the keyboard, if it is present when you enter this fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
