package dk.riis.jacob.hangman.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dk.riis.jacob.hangman.R;

/**
 * The Adapter binds the data to the view, and the ViewHolder holds the View or the Views.
 */
public class HighscoreAdapter extends RecyclerView.Adapter<HighscoreAdapter.HighscoreListViewHolder>{

    private ArrayList<Highscore> highscoreArrayList;
    private OnHighscoreListener onHighscoreListener;
    private Context mCtx;


    public HighscoreAdapter(ArrayList<Highscore> list, OnHighscoreListener onHighscoreListener, Context mCtx) {
        highscoreArrayList = list;
        this.onHighscoreListener = onHighscoreListener;
        this.mCtx = mCtx;

    }

    /**
     *
     * This will create a ViewHolder instance.
     * @param parent
     * @param viewType ui elements
     * @return an instance of the ViewHolder class
     */
    @NonNull
    @Override
    public HighscoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_list_item, parent,false);
        return new HighscoreListViewHolder(view,onHighscoreListener);
    }

    /**
     * This will bind data to the ViewHolder.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull HighscoreListViewHolder holder, int position) {
        Highscore currHighscore = highscoreArrayList.get(position);
        holder.highscoreName.setText(currHighscore.getName());
        holder.highscoreScore.setText(String.valueOf(currHighscore.getScore()));

    }

    /**
     * This will return the size of the list
     */
    @Override
    public int getItemCount() {
        return highscoreArrayList.size();
    }

    public interface OnHighscoreListener{
        void onHighscoreClick(int position);
    }

    class HighscoreListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView highscoreName, highscoreScore;
        OnHighscoreListener highscoreListener;


        public HighscoreListViewHolder(@NonNull View itemView, OnHighscoreListener highscoreListener) {
            super(itemView);
            highscoreName = itemView.findViewById(R.id.highscoreListNameItem);
            highscoreScore = itemView.findViewById(R.id.highscoreListScoreItem);
            this.highscoreListener = highscoreListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            highscoreListener.onHighscoreClick(getAdapterPosition());
        }
    }

}
