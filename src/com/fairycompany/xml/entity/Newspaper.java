package com.fairycompany.xml.entity;

import java.time.LocalDate;

public class Newspaper extends AbstractPaper {
    private boolean color;
    private String frequency;

    public Newspaper() {

    }

    public Newspaper(String name, String subscriptionIndex, String website, String ageCategory,
                     int circulation, PaperProperties paperProperties, boolean color, String frequency) {
        super(name, subscriptionIndex, website, ageCategory, circulation, paperProperties);
        this.color = color;
        this.frequency = frequency;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        final StringBuilder newspaper = new StringBuilder();
        newspaper.append(super.toString())
                .append("\nFrequency ").append(frequency);
        if (color) {
            newspaper.append("\nColor polychrome");
        } else {
            newspaper.append("\nColor monochrome");
        }
        return newspaper.toString();
    }
}
