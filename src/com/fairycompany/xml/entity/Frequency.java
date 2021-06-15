package com.fairycompany.xml.entity;

public enum Frequency {
    DAILY,
    WEEKLY,
    MONTHLY;

    public String toString() {
        char firstLetter = name().charAt(0);
        StringBuilder string = new StringBuilder(name().toLowerCase());
        string.deleteCharAt(0).insert(0, firstLetter);
        return string.toString();
    }
}
