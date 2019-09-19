package ru.basejava.iliketobreathe.model;

public enum ContactType {
    PHONE("Тел."),
    SKYPE("Skype"),
    EMAIL("Email"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverFlow"),
    HOMEPAGE("Домашняя станица");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
