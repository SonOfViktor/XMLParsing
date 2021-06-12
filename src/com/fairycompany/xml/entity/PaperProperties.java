package com.fairycompany.xml.entity;

import java.time.LocalDate;

public class PaperProperties {
    private int issue;
    private int pages;
    private double price;
    private LocalDate issueDate;

    public PaperProperties() {

    }

    public PaperProperties(int issue, int pages, double price, LocalDate issueDate) {
        this.issue = issue;
        this.pages = pages;
        this.price = price;
        this.issueDate = issueDate;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    @Override
    public String toString() {
        final StringBuilder properties = new StringBuilder();
        properties.append("\nProperties:\n\tIssue: ").append(issue)
                .append("\n\tPages: ").append(pages)
                .append("\n\tPrice: ").append(price)
                .append("\n\tIssue date: ").append(issueDate);
        return properties.toString();
    }
}
