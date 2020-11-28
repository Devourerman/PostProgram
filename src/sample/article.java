package sample;

import java.io.Serializable;

public class article implements Serializable {

    private String titleAr;
    private String intro;

    public article(String title, String intro) {
        this.titleAr = title;
        this.intro = intro;
    }

    public String getTitle() {
        return titleAr;
    }

    public String getIntro() {
        return intro;
    }
}
