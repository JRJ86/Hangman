package dk.riis.jacob.hangman.fragment_controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import dk.riis.jacob.hangman.R;
import dk.riis.jacob.hangman.model.Galgelogik;
import dk.riis.jacob.hangman.model.Highscore;

/**
 * This class gives functionality to the game portion of the app
 */
public class GamePage_frag extends Fragment implements View.OnClickListener {

    private static final String TAG = "GamePage";

    // Instead of a singleton, the object is made static to secure on one instance of it.
    static Galgelogik galgelogik = new Galgelogik();

    private Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,ae,oe,aa;
    private Button backToMain;
    public TextView hiddenWord, wrongLetters;
    private ImageView hangmanPic;
    private int count, tries;
    private String player, word;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Highscore> savedHighscores;
    private Gson gson;
    private boolean playerFound = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game_page,container,false);

        Log.d(TAG,"onCreate: started");

        // Set the value of tries to 0 when the fragment is loaded
        tries = 0;

        // Get the bundle1 with the players name from InputName_frag
        Bundle bundle = getArguments();
        player = bundle.getString("name");

        // The count variable is used to change the hangman picture according to wrong answers.
        count = galgelogik.getAntalForkerteBogstaver();
        System.out.println("Number of wrong letters: "+count);

        // Keyboard
        a = view.findViewById(R.id.aBtn);
        b = view.findViewById(R.id.bBtn);
        c = view.findViewById(R.id.cBtn);
        d = view.findViewById(R.id.dBtn);
        e = view.findViewById(R.id.eBtn);
        f = view.findViewById(R.id.fBtn);
        g = view.findViewById(R.id.gBtn);
        h = view.findViewById(R.id.hBtn);
        i = view.findViewById(R.id.iBtn);
        j = view.findViewById(R.id.jBtn);
        k = view.findViewById(R.id.kBtn);
        l = view.findViewById(R.id.lBtn);
        m = view.findViewById(R.id.mBtn);
        n = view.findViewById(R.id.nBtn);
        o = view.findViewById(R.id.oBtn);
        p = view.findViewById(R.id.pBtn);
        q = view.findViewById(R.id.qBtn);
        r = view.findViewById(R.id.rBtn);
        s = view.findViewById(R.id.sBtn);
        t = view.findViewById(R.id.tBtn);
        u = view.findViewById(R.id.uBtn);
        v = view.findViewById(R.id.vBtn);
        w = view.findViewById(R.id.wBtn);
        x = view.findViewById(R.id.xBtn);
        y = view.findViewById(R.id.yBtn);
        z = view.findViewById(R.id.zBtn);
        ae = view.findViewById(R.id.aeBtn);
        oe = view.findViewById(R.id.oeBtn);
        aa = view.findViewById(R.id.aaBtn);

        // Buttons
        backToMain = view.findViewById(R.id.backToMainFromGame);

        // The hidden word + information
        hiddenWord = view.findViewById(R.id.hiddenWord);
        wrongLetters = view.findViewById(R.id.wrongLetters);

        // Hangman pictures
        hangmanPic = view.findViewById(R.id.hangman);

        // The players name
        TextView playerName = view.findViewById(R.id.playerName);
        playerName.setText("Hello " +player);

        // The clickable views
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);
        j.setOnClickListener(this);
        k.setOnClickListener(this);
        l.setOnClickListener(this);
        m.setOnClickListener(this);
        n.setOnClickListener(this);
        o.setOnClickListener(this);
        p.setOnClickListener(this);
        q.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        t.setOnClickListener(this);
        u.setOnClickListener(this);
        v.setOnClickListener(this);
        w.setOnClickListener(this);
        x.setOnClickListener(this);
        y.setOnClickListener(this);
        z.setOnClickListener(this);
        ae.setOnClickListener(this);
        oe.setOnClickListener(this);
        aa.setOnClickListener(this);
        backToMain.setOnClickListener(this);

        // Prints in the terminal what the hidden word is and other information
        galgelogik.logStatus();

//------To get the hidden word from dr.dk´s website AsyncTask is used-------------------------------


        class HiddenWord extends AsyncTask<String,String,String>{

            public HiddenWord() {
                super();
            }

            /**
             * What happens after the background thread is done-
             *
             * @param theHiddenWord The hidden word
             */
            @Override
            protected void onPostExecute(String theHiddenWord) {
                hiddenWord.setText(theHiddenWord);
                word = galgelogik.getOrdet();
                System.out.println("The word: "+word);
            }

            /**
             * The background thread that fetches the word via network communication
             *
             * @return The hidden word from som online source specified in Galgelogik
             */
            @Override
            protected String doInBackground(String... strings) {
                try {
                    galgelogik.hentOrdFraDr();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return galgelogik.getSynligtOrd();
            }
        }
        new HiddenWord().execute();

        return view;
    }

    /**
     * Method that reacts when you click on a view
     *
     * @param choices the view you click on.
     */
    @Override
    public void onClick(View choices) {

        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

// -----The objects for the change fragment functions-----------------------------------------------

        WinScreen_frag winScreen_frag;
        LooseScreen_frag looseScreen_frag;
        InputName_frag inputName_frag;
        WelcomePage_frag welcomePage_frag;

// -----Various variables---------------------------------------------------------------------------

        int win;
        Bundle bundle, bundle1;
//        String letter = input.getText().toString();
//        input.setError(null);

// -----Input control-------------------------------------------------------------------------------

//        if (choices == ok && letter.length() != 1){
//            input.setError("Your input must contain exactly 1 letter.");
//            Toast.makeText(getActivity(), "Input a letter.", Toast.LENGTH_LONG).show();
//            return;
//
//        }else if (choices == ok && (letter.matches("^[a-zA-Z]'$"))){
//            input.setError("Your input MUST be a letter.");
//            Toast.makeText(getActivity(),"Input a letter.", Toast.LENGTH_LONG).show();
//            return;
//
//        }

// -----When you want to press a letter and press ok------------------------------------------------

        try{
            if (choices == a) {

//                galgelogik.gætBogstav(letter);
//
//                // Increment the tries for each press
//                tries += 1;
//
//                // Various stuff
//                input.setText("");
//                info.setText("You got " +galgelogik.getAntalForkerteBogstaver()+ " wrong letters.");
                wrongLetters.setText("" +galgelogik.getBrugteBogstaver());
                hiddenWord.setText(galgelogik.getSynligtOrd());

                // Number of wrong choices
                count = galgelogik.getAntalForkerteBogstaver();
                System.out.println(count);
                showHangmanPic(count);

// -------------If the game is lost-----------------------------------------------------------------

                if (galgelogik.erSpilletVundet()) {
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
                        Highscore highscoreElement = new Highscore(winningPlayer,win);
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

//            if(choices == newGame){
//                inputName_frag = new InputName_frag();
//                fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragContainer,inputName_frag);
//                fragmentTransaction.commit();
//                GamePage_frag.galgelogik.nulstil();
//            }

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
