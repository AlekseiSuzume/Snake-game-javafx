package ru.suzume.snakefx.objects;

public enum Level {
    LOW(1),
    MEDIUM(0.6),
    HARD(0.3),
    HELL(0.15);

    public double difficult;
    Level(double i) {
        this.difficult = i;
    }
}
