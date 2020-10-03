package social.media.Models;

import java.util.List;

public class Like {

//    @DocumentId
    String wid;
    String liker;
    String reaction;
    String wew;
    List<Sample> sample;
    Like(){
    }

    public Like(String wid, String liker, String reaction, String wew, List<Sample> sample) {
        this.wid = wid;
        this.liker = liker;
        this.reaction = reaction;
        this.wew = wew;
        this.sample = sample;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getWew() {
        return wew;
    }

    public void setWew(String wew) {
        this.wew = wew;
    }

    public String getLiker() {
        return liker;
    }

    public String getReaction() {
        return reaction;
    }

    public List<Sample> getSample() {
        return sample;
    }

    public void setSample(List<Sample> sample) {
        this.sample = sample;
    }
}
