package social.media.Models;

public class Sample {

    String text;
    String title;

    public Sample(){

    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Sample(String text, String title) {
        this.text = text;
        this.title = title;
    }
}
