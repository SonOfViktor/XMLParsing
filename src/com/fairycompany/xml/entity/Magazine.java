package com.fairycompany.xml.entity;

import java.time.LocalDate;

public class Magazine extends AbstractPaper {
    private String direction;

    public Magazine() {

    }

    public Magazine(String name, String subscriptionIndex, String website, String ageCategory,
                    int circulation, PaperProperties paperProperties, String direction) {
        super(name, subscriptionIndex, website, ageCategory, circulation, paperProperties);
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        final StringBuilder magazine = new StringBuilder();
        magazine.append(super.toString())
                .append("\nDirection ").append(direction);
        return magazine.toString();
    }
}
