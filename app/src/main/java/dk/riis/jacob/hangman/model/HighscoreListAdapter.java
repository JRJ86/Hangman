package dk.riis.jacob.hangman.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import dk.riis.jacob.hangman.R;

/**
 * A custom adapter class for the highscore
 */
public class HighscoreListAdapter extends ArrayAdapter<Highscore>{

    private Context mContext;
    private int mResource;

    public HighscoreListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    /**
     * Get a View that displays the data at the specified position in the data set.
     *
     * @param position The position of the item within the adapter's data set of the item whose view we want.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Get the highscore information
        String name = getItem(position).getName();
        int score = getItem(position).getScore();

        String realScore = Integer.toString(score);

        // The Highscore object with the information
        Highscore highscore = new Highscore(name,score);

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        convertView = layoutInflater.inflate(mResource, parent, false);

        TextView players = (TextView)convertView.findViewById(R.id.playerName);
        TextView scores = (TextView)convertView.findViewById(R.id.playerScore);

        players.setText(name);
        scores.setText(realScore);

        return convertView;

    }
}
