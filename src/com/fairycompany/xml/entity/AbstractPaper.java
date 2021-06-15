package com.fairycompany.xml.entity;

import java.time.LocalDate;

public abstract class AbstractPaper {
    private String name;
    private String subscriptionIndex;
    private String website;
    private AgeCategory ageCategory;
    private int circulation;
    private PaperProperties paperProperties = new PaperProperties();

    public AbstractPaper() {

    }

    public AbstractPaper(String name, String subscriptionIndex, String website, AgeCategory ageCategory,
             int circulation, PaperProperties paperProperties) {
        this.name = name;
        this.subscriptionIndex = subscriptionIndex;
        this.website = website;
        this.ageCategory = ageCategory;
        this.circulation = circulation;
        this.paperProperties = paperProperties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubscriptionIndex() {
        return subscriptionIndex;
    }

    public void setSubscriptionIndex(String subscriptionIndex) {
        this.subscriptionIndex = subscriptionIndex;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public void setAgeCategory(String ageCategory) {
        this.ageCategory = AgeCategory.valueOf(ageCategory.toUpperCase());      // can I do this?
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }

    public PaperProperties getPaperProperties() {
        return paperProperties;
    }

    public void setPaperProperties(PaperProperties paperProperties) {
        this.paperProperties = paperProperties;
    }

    @Override
    public String toString() {
        final StringBuilder paper = new StringBuilder("");
        if (getWebsite() != null) {
            paper.append("\nWebsite: ").append(website);
        }
        paper.append("\nName: ").append(name)
                .append("\nAge category: ").append(ageCategory)
                .append("\nSubscription index: ").append(subscriptionIndex)
                .append(paperProperties)
                .append("\nCirculation: ").append(circulation);
        return paper.toString();
    }
}
