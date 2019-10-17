package dk.riis.jacob.hangman;

public class Highscore {

    private String name;
    private int score;

    public Highscore(String name) {
        this.name = name;
    }

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

    public int scoreSum(){
        int sum = getScore() + 1;
        return sum;
    }






}
