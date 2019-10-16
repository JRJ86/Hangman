package dk.riis.jacob.hangman;

import android.widget.ImageView;

public class Highscore {

//    private ImageView img;
    private String name;
    private int score;

//    public static String[] players = new String[]{
//            "Jacob", "Valdemar", "Nick", "Morten", "Alexander", "Malte",
//            "Andreas", "Simon", "Kristian", "Claes", "Michael", "Jesper"
//    };
//    public static int[] winScore = new int[]{
//            10, 20, 5, 8, 12, 4, 1, 17, 9, 10, 13, 2
//    };
//
//    public static int pic = R.drawable.noose;


    public Highscore(String name, int score) {
        this.name = name;
        this.score = score;
    }

//    public Highscore(ImageView img, String name, int score) {
//        this.img = img;
//        this.name = name;
//        this.score = score;
//    }
//
//    public ImageView getImg() {
//        return img;
//    }
//
//    public void setImg(ImageView img) {
//        this.img = img;
//    }

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
