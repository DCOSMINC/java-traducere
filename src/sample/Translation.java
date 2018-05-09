package sample;

public class Translation {
    private String tagName;
    private String translation1;
    private String translation2;

    public static int numberOfTranslations = 2;

    public Translation(String tagName, String translation1, String translation2) {
        this.tagName = tagName;
        this.translation1 = translation1;
        this.translation2 = translation2;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTranslation1() {
        return translation1;
    }

    public void setTranslation1(String translation1) {
        this.translation1 = translation1;
    }

    public String getTranslation2() {
        return translation2;
    }

    public void setTranslation2(String translation2) {
        this.translation2 = translation2;
    }



    public static int getNumberOfTranslations() {
        return numberOfTranslations;
    }

    public static void setNumberOfTranslations(int numberOfTranslations) {
        Translation.numberOfTranslations = numberOfTranslations;
    }
}
