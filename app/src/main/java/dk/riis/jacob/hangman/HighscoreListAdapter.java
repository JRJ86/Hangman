package dk.riis.jacob.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom adapter class for the highscore
 */
public class HighscoreListAdapter extends ArrayAdapter<Highscore>{

    private static final String TAG = "HighscoreListAdapter";

    private Context context;
    private int resource;

    /**
     * Default constructor for the HighscoreListAdapter
     *
     * @param context  The activity / fragment.
     * @param resource The the resource used. In this case the layout.
     * @param objects The list of names and scores.
     */
    public HighscoreListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the highscore information
        String name = getItem(position).getName();
        int score = getItem(position).getScore();

        // The Highscore object with the information
        Highscore highscore = new Highscore(name,score);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView players = (TextView)convertView.findViewById(R.id.playerName);
        TextView scores = (TextView)convertView.findViewById(R.id.playerScore);

        return convertView;

    }
}
