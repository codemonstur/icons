package model;

public enum IconBucket {
    small("16x16"),
    large("32x32"),
    scalable("svg"),
    other("other");

    private final String dirName;
    IconBucket(final String dirName) {
        this.dirName = dirName;
    }

    public String toDir() {
        return dirName;
    }
}