package to.msn.wings.englishpracticeapp;

import java.io.Serializable;
/**
 * Created by Tomu on 2017/10/10.
 */

public class Answers implements Serializable{
    long id;
    private boolean result;
    private String word;
    private double playTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getResult(){
        return result;
    }

    public void setResult(boolean result){
        this.result = result;
    }

    public String getWord(){
        return word;
    }

    public void setWord(String word){
        this.word = word;
    }

    public double getPlayTime(){
        return playTime;
    }

    public void setPlayTime(double playTime){
        this.playTime = playTime;
    }
}
