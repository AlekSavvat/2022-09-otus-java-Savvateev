package ru.otus;

public class CustomInteger {

    private final int value;

    public CustomInteger(int value) {
        if(value < 0)
            throw new IllegalArgumentException(String.format("argument can't be lower than zero: %d", value));

        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
