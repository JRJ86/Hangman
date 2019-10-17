package dk.riis.jacob.hangman;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Highscore_frag extends Fragment {

    Galgelogik galgelogik = new Galgelogik();

    private ArrayList<Highscore> highscores = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_highscore_frag, container, false);

        ListView listView = view.findViewById(R.id.listView);

//        if (galgelogik.erSpilletVundet()){
//            Bundle bundle = getArguments();
//            String player = bundle.getString("player");
//            int score = bundle.getInt("win");
//            System.out.println("1: "+player+" 2: "+score);
//        }

        HighscoreListAdapter adapter = new HighscoreListAdapter(this.getActivity(), R.layout.higscore_element, highscores);
        listView.setAdapter(adapter);

        return view;
    }

    /**
     * This method inputs som fake highscores into the Highscorelist
     */
    public void inputHighscores(){
        Highscore one = new Highscore("Valdemar", 10);
        Highscore two = new Highscore("Jacob", 20);
        Highscore three = new Highscore("Nick", 15);
        Highscore four = new Highscore("Morten", 2);
        Highscore five = new Highscore("Michael", 8);
        Highscore six = new Highscore("Jesper", 9);
        Highscore seven = new Highscore("Alexander", 17);
        Highscore eight = new Highscore("Malte", 11);
        Highscore nine = new Highscore("Jimmi", 5);
        Highscore ten = new Highscore("Andreas", 16);
        Highscore eleven = new Highscore("Kristian", 14);
        Highscore twelve = new Highscore("Simon", 10);
        Highscore thirteen = new Highscore("Claes", 19);
        Highscore fourteen = new Highscore("Jørgen", 17);
        Highscore fifteen = new Highscore("Svend", 4);
        Highscore sixteen = new Highscore("Mogens", 8);
        highscores.add(one);
        highscores.add(two);
        highscores.add(three);
        highscores.add(four);
        highscores.add(five);
        highscores.add(six);
        highscores.add(seven);
        highscores.add(eight);
        highscores.add(nine);
        highscores.add(ten);
        highscores.add(eleven);
        highscores.add(twelve);
        highscores.add(thirteen);
        highscores.add(fourteen);
        highscores.add(fifteen);
        highscores.add(sixteen);
    }

}
