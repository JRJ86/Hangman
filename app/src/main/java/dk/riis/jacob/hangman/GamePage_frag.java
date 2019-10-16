package dk.riis.jacob.hangman;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage_frag extends Fragment implements View.OnClickListener {

    static Galgelogik galgelogik = new Galgelogik();

    private Button ok, clear, newGame, backToMain;
    private EditText input;
    private TextView hiddenWord, wrongLetters, info, playerName;
    private ImageView hangmanPic;
    private int count, tries, win;
    private String player, word;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_page_frag,container,false);

        word = galgelogik.getOrdet();
        System.out.println("The word: "+word);

        // Set the value of tries to 0 when the fragment is loaded
        tries = 0;

        // The win is set to 0, and if the player wins, it will be set to 1 and send in the bundle.
        win = 0;

        // Get the bundle1 with the players name from InputName_frag
        Bundle bundle = getArguments();
        player = bundle.getString("name");

        // The count variable is used to change the hangman picture according to wrong answers.
        count = galgelogik.getAntalForkerteBogstaver();
        System.out.println("Number of wrong letters: "+count);

        // Buttons
        ok = view.findViewById(R.id.confirmLetter);
        clear = view.findViewById(R.id.clearText);
        newGame = view.findViewById(R.id.newGame);
        backToMain = view.findViewById(R.id.backToMain);

        // Bar to input letters
        input = view.findViewById(R.id.inputLetter);
        input.getBackground()
                .mutate()
                .setColorFilter(getResources().getColor(R.color.inputNameBackground),
                        PorterDuff.Mode.SRC_ATOP);

        // The hidden word + information
        hiddenWord = view.findViewById(R.id.hiddenWord);
        wrongLetters = view.findViewById(R.id.wrongLetters);
        info = view.findViewById(R.id.information);

        // Hangman pictures
        hangmanPic = view.findViewById(R.id.hangman);

        // The players name
        playerName = view.findViewById(R.id.playerName);
        playerName.setText("Hello "+player);

        // The clickable views
        ok.setOnClickListener(this);
        clear.setOnClickListener(this);
        newGame.setOnClickListener(this);
        backToMain.setOnClickListener(this);

        galgelogik.logStatus();

        // Sets the TextView to display the hidden word with '*'
        hiddenWord.setText(galgelogik.getSynligtOrd());

        return view;
    }

    @Override
    public void onClick(View choices) {

        WinScreen_frag winScreen_frag;
        LooseScreen_frag looseScreen_frag;
        InputName_frag inputName_frag;
        FirstPage_frag firstPage_frag;
        Highscore_frag highscore_frag;
        Bundle bundle, bundle2, bundle3;
        String letter = input.getText().toString();
        input.setError(null);

        if (choices == ok && letter.length() != 1){
            input.setError("Your input must contain exactly 1 letter.");
            Toast.makeText(getActivity(), "Input a letter.", Toast.LENGTH_LONG).show();
            return;

        }else if (choices == ok && (letter.matches("^[a-zA-Z]'$"))){
            input.setError("Your input MUST be a letter.");
            Toast.makeText(getActivity(),"Input a letter.", Toast.LENGTH_LONG).show();
            return;

        }
        try{
            if (choices == ok) {
                galgelogik.gætBogstav(letter);
                tries += 1;
                input.setText("");
                info.setText("You got " +galgelogik.getAntalForkerteBogstaver()+ " wrong letters.");
                wrongLetters.setText("" +galgelogik.getBrugteBogstaver());
                hiddenWord.setText(galgelogik.getSynligtOrd());
                count = galgelogik.getAntalForkerteBogstaver();
                System.out.println(count);
                showHangmanPic(count);

                if (galgelogik.erSpilletVundet()) {
                    info.setText("You escaped the noose!");
                    winScreen_frag = new WinScreen_frag();
                    highscore_frag = new Highscore_frag();

                    bundle = new Bundle();
                    bundle.putInt("tries",tries);
                    winScreen_frag.setArguments(bundle);

//                    bundle2 = new Bundle();
//                    bundle2.putString("player", player);
//                    bundle2.putInt("win",win);
//                    highscore_frag.setArguments(bundle2);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer,winScreen_frag);
                    fragmentTransaction.commit();
                    GamePage_frag.galgelogik.nulstil();



                }
                if (galgelogik.erSpilletTabt()) {
                    info.setText("Game over!");
                    looseScreen_frag = new LooseScreen_frag();

                    bundle3 = new Bundle();
                    bundle3.putString("word", word);
                    looseScreen_frag.setArguments(bundle3);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer,looseScreen_frag);
                    fragmentTransaction.commit();
                    GamePage_frag.galgelogik.nulstil();

                }
            }
            if(choices == clear ){
                input.setText("");

            }
            if(choices == backToMain ){
                firstPage_frag = new FirstPage_frag();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragContainer,firstPage_frag);
                fragmentTransaction.commit();
                GamePage_frag.galgelogik.nulstil();

            }
            if(choices == newGame){
                inputName_frag = new InputName_frag();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragContainer,inputName_frag);
                fragmentTransaction.commit();
                GamePage_frag.galgelogik.nulstil();

            }
        }catch(Exception e){
            Toast.makeText(getActivity(),"Some error has ocurred: "+e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    /**
     * This function sets the hangman picture to a new version if the
     * player guesses wrong, otherwise it sets the picture to the same one
     *
     * @param count The amount of wrong guesses
     */
    private void showHangmanPic(int count){
        switch (count){
            case 0:
                hangmanPic.setImageResource(R.drawable.galge);
                break;

            case 1:
                hangmanPic.setImageResource(R.drawable.forkert1);
                break;

            case 2:
                hangmanPic.setImageResource(R.drawable.forkert2);
                break;

            case 3:
                hangmanPic.setImageResource(R.drawable.forkert3);
                break;

            case 4:
                hangmanPic.setImageResource(R.drawable.forkert4);
                break;

            case 5:
                hangmanPic.setImageResource(R.drawable.forkert5);
                break;

            case 6:
                hangmanPic.setImageResource(R.drawable.forkert6);
                break;

            default:
                System.out.println("Something went wrong!!");

        }
    }
}
