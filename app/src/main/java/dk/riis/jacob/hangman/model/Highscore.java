package dk.riis.jacob.hangman.model;

/**
 * This class represents a highscore element
 * A player with a name, and the number of times he has won
 */
public class Highscore implements Comparable {

    private String name;
    private int score;


    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
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

    /**
     * Used to sort objects in list after what the score is.
     *
     * @param o The Highscore object
     * @return the compared score
     */
    @Override
    public int compareTo(Object o) {
        int compareScore = ((Highscore)o).getScore();
        return compareScore - this.score;
    }
}
