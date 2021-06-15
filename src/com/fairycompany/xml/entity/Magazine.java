package com.fairycompany.xml.entity;

public class Magazine extends AbstractPaper {
    private Direction direction;

    public Magazine() {

    }

    public Magazine(String name, String subscriptionIndex, String website, AgeCategory ageCategory,
                    int circulation, PaperProperties paperProperties, Direction direction) {
        super(name, subscriptionIndex, website, ageCategory, circulation, paperProperties);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        final StringBuilder magazine = new StringBuilder();
        magazine.append(super.toString())
                .append("\nDirection: ").append(direction);
        return magazine.toString();
    }
}
