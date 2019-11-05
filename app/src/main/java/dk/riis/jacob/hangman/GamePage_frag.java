package dk.riis.jacob.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePage_frag extends Fragment implements View.OnClickListener {

    // Instead of a singleton, the object is made static to secure on one instance of it.
    static Galgelogik galgelogik = new Galgelogik();

    private Button ok, clear, newGame, backToMain;
    private EditText input;
    private TextView hiddenWord, wrongLetters, info, playerName;
    private ImageView hangmanPic;
    private int count, tries, win;
    private String player, word;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private SharedPreferences sharedPreferences;
    private Highscore highscoreElement;
    private SharedPreferences.Editor editor;
    private ArrayList<Highscore> savedHighscores;
    private Gson gson;
    private boolean playerFound = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_page_frag,container,false);

        // Set the value of tries to 0 when the fragment is loaded
        tries = 0;

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

        // Prints in the terminal what the hidden word is and other information
        galgelogik.logStatus();

        // Sets the TextView to display the hidden word with '*'
        class GetHiddenWord extends AsyncTask {

            @Override
            protected void onPostExecute(Object result) {
                hiddenWord.setText(""+result);
                word = galgelogik.getOrdet();
                System.out.println("The word: "+word);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    galgelogik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return galgelogik.getSynligtOrd();
            }

            @Override
            protected void onProgressUpdate(Object[] values) {

            }
        }
        new GetHiddenWord().execute();

        return view;
    }

    /**
     * Method that reacts when you click on a view
     *
     * @param choices the view you click on.
     */
    @Override
    public void onClick(View choices) {

// -----The objects for the change fragment functions-----------------------------------------------

        WinScreen_frag winScreen_frag;
        LooseScreen_frag looseScreen_frag;
        InputName_frag inputName_frag;
        WelcomePage_frag welcomePage_frag;

// -----Various variables---------------------------------------------------------------------------

        win = 0;
        Bundle bundle, bundle1;
        String letter = input.getText().toString();
        input.setError(null);

// -----Input control-------------------------------------------------------------------------------

        if (choices == ok && letter.length() != 1){
            input.setError("Your input must contain exactly 1 letter.");
            Toast.makeText(getActivity(), "Input a letter.", Toast.LENGTH_LONG).show();
            return;

        }else if (choices == ok && (letter.matches("^[a-zA-Z]'$"))){
            input.setError("Your input MUST be a letter.");
            Toast.makeText(getActivity(),"Input a letter.", Toast.LENGTH_LONG).show();
            return;

        }

// -----When you want to press a letter and press ok------------------------------------------------

        try{
            if (choices == ok) {

                galgelogik.gætBogstav(letter);

                // Increment the tries for each press
                tries += 1;

                // Various stuff
                input.setText("");
                info.setText("You got " +galgelogik.getAntalForkerteBogstaver()+ " wrong letters.");
                wrongLetters.setText("" +galgelogik.getBrugteBogstaver());
                hiddenWord.setText(galgelogik.getSynligtOrd());

                // Number of wrong choices
                count = galgelogik.getAntalForkerteBogstaver();
                System.out.println(count);
                showHangmanPic(count);

// -------------If the game is lost-----------------------------------------------------------------

                if (galgelogik.erSpilletVundet()) {
                    info.setText("You escaped the noose!");
                    winScreen_frag = new WinScreen_frag();
                    gson = new Gson();
                    win = 1;
                    String winningPlayer = player;

// -----------------Load the highscore list from SharedPreferences----------------------------------

                    loadFromPrefs();

// -----------------Check if the list contains the player with the corresponding name---------------

                    for (Highscore highscore: savedHighscores){
                        if (highscore.getName().equals(winningPlayer)){
                            highscore.setName(winningPlayer);
                            highscore.setScore(highscore.getScore() + 1);
                            playerFound = true;
                        }
                    }

                    if (!playerFound){
                        highscoreElement = new Highscore(winningPlayer,win);
                        savedHighscores.add(highscoreElement);
                    }

                    Collections.sort(savedHighscores);

// -----------------Save the updated list to SharedPreferences--------------------------------------

                    saveToPrefs(savedHighscores);

                    if (sharedPreferences.contains("highscoreList")){
                        Toast.makeText(getActivity(),"Highscore added", Toast.LENGTH_LONG).show();
                        printHighscoreList();
                    }

// -----------------Send data to win screen---------------------------------------------------------

                    bundle = new Bundle();
                    bundle.putInt("tries",tries);
                    winScreen_frag.setArguments(bundle);

// -----------------Go to win screen and nullify the galgelogik object------------------------------

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer,winScreen_frag);
                    fragmentTransaction.commit();
                    GamePage_frag.galgelogik.nulstil();
                }

// -------------If the game is won------------------------------------------------------------------

                if (galgelogik.erSpilletTabt()) {
                    info.setText("Game over!");
                    looseScreen_frag = new LooseScreen_frag();

                    bundle1 = new Bundle();
                    bundle1.putString("word", word);
                    looseScreen_frag.setArguments(bundle1);

                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragContainer,looseScreen_frag);
                    fragmentTransaction.commit();
                    GamePage_frag.galgelogik.nulstil();
                }
            }

// ---------Clear the EditText----------------------------------------------------------------------

            if(choices == clear ){
                input.setText("");
            }

// ---------Back to main menu-----------------------------------------------------------------------

            if(choices == backToMain ){
                welcomePage_frag = new WelcomePage_frag();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragContainer, welcomePage_frag);
                fragmentTransaction.commit();
                GamePage_frag.galgelogik.nulstil();
            }

// ---------Start a new game------------------------------------------------------------------------

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

    /**
     * Prints the current highscores when loaded from PreferenceManager
     */
    private void printHighscoreList(){
        for (int i = 0; i < savedHighscores.size(); i++) {
            System.out.println(savedHighscores.get(i));
        }
    }

    /**
     * Removes the highscore list when loaded from PreferenceManager
     */
    private void removeFromList(){
        for (int i = 0; i < savedHighscores.size(); i++){
            savedHighscores.remove(i);
        }
    }

    /**
     * Save a list to PreferenceManager
     */
    private void saveToPrefs(ArrayList list){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("highscoreList", json);
        editor.apply();
    }

    /**
     * Load a the highscore list from PreferenceManager
     */
    private void loadFromPrefs(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        gson = new Gson();
        String json = sharedPreferences.getString("highscoreList", null);
        Type type = new TypeToken<ArrayList<Highscore>>() {}.getType();
        savedHighscores = gson.fromJson(json,type);

        if (savedHighscores == null){
            savedHighscores = new ArrayList<>();
        }
    }

// -Alternate methods to save and load lists to PreferenceManager-----------------------------------

    private void savePrefs(){
        sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
        String json = gson.toJson(savedHighscores);
        editor.putString("highscoreList", json);
        editor.apply();
    }

    private void loadPrefs(){
        sharedPreferences = getActivity().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        gson = new Gson();
        String json = sharedPreferences.getString("highscoreList", null);
        Type type = new TypeToken<ArrayList<Highscore>>() {}.getType();
        savedHighscores = gson.fromJson(json,type);

        if (savedHighscores == null){
            savedHighscores = new ArrayList<>();
        }
    }


}
