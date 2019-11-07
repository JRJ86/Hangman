package dk.riis.jacob.hangman.fragment_controllers;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import dk.riis.jacob.hangman.R;
import dk.riis.jacob.hangman.model.Highscore;
import dk.riis.jacob.hangman.model.HighscoreListAdapter;

/**
 * This fragment contains the ListView with the highscore list
 */
public class Highscore_frag extends Fragment {

    private static final String TAG = "Highscore";
    private ArrayList<Highscore> highscores = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore, container, false);

        Log.d(TAG,"onCreate: started");

//------Loading the list from SharedPreferences and putting it in the new list----------------------

        System.out.println("Loading pref");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json = sharedPreferences.getString("highscoreList",null);
        if (!(sharedPreferences.contains("highscoreList"))){
            System.out.println("Nothing in list");
        }else {
            Type type = new TypeToken<ArrayList<Highscore>>(){}.getType();
            highscores = gson.fromJson(json,type);
            System.out.println("Loaded json");
        }

        System.out.println("Scores: ");
        for (int i = 0; i < highscores.size(); i++) {
            System.out.println(highscores.get(i));
        }

//------Adding the list to the list view with the adapter-------------------------------------------

        ListView listView = view.findViewById(R.id.listView);

        HighscoreListAdapter adapter = new HighscoreListAdapter(getActivity(), R.layout.higscore_element, highscores);
        listView.setAdapter(adapter);

        return view;
    }
}
