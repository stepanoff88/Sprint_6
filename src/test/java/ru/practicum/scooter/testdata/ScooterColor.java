package ru.practicum.scooter.testdata;

public enum ScooterColor {
    BLACK("чёрный жемчуг"),
    GREY("серая безысходность");

    private final String label;

    ScooterColor(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
