package dk.riis.jacob.hangman;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Highscore_frag extends Fragment implements View.OnClickListener{

    private ArrayList<Highscore> highscores;//() = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore_frag, container, false);

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

        ListView listView = view.findViewById(R.id.listView);

        HighscoreListAdapter adapter = new HighscoreListAdapter(getActivity(), R.layout.higscore_element, highscores);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
