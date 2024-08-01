package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел.") {},
    SKYPE("Скайп") {
        @Override
        public String toHTML(String value) {
            return "<a href='skype: " + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта"){
        @Override
        public String toHTML(String value) {
            return "<a href='mailto: " + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHTML(String value) {
        return title + ":  " + value;
    }
}
