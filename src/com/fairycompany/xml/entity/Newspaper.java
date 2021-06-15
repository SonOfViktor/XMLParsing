package com.fairycompany.xml.entity;

import java.time.LocalDate;

public class Newspaper extends AbstractPaper {
    private boolean color;
    private Frequency frequency;

    public Newspaper() {

    }

    public Newspaper(String name, String subscriptionIndex, String website, String ageCategory,
                     int circulation, PaperProperties paperProperties, boolean color, Frequency frequency) {
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

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = Frequency.valueOf(frequency.toUpperCase());
    }

    @Override
    public String toString() {
        final StringBuilder newspaper = new StringBuilder();
        newspaper.append(super.toString())
                .append("\nFrequency: ").append(frequency);
        if (color) {
            newspaper.append("\nColor: Polychrome");
        } else {
            newspaper.append("\nColor: Monochrome");
        }
        return newspaper.toString();
    }
}
