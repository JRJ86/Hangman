package dk.riis.jacob.hangman;

import android.widget.ImageView;

public class Highscore implements Comparable {

    private String name;
    private int score;
    private ImageView pic;

    public Highscore(String name) {
        this.name = name;
    }

    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Highscore(String name, int score, ImageView pic) {
        this.name = name;
        this.score = score;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Highscore{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        int compareScore = ((Highscore)o).getScore();
        return compareScore - this.score;
    }
}
