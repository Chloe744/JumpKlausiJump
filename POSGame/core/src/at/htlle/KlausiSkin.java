package at.htlle;

public enum KlausiSkin {
    SPRITE_1("sprite1.png"),
    SPRITE_2("sprite2.png"),
    SPRITE_3("sprite3.png");

    private final String fileName;

    KlausiSkin(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
