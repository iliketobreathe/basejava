package ru.basejava.iliketobreathe.model;

import java.time.YearMonth;

public class Org {
    private final String title;
    private final String description;
    private final String position;
    private final String webLink;
    private final YearMonth startDate;
    private final YearMonth endDate;

    public Org(String title, String description, String position, String webLink, YearMonth startDate, YearMonth endDate) {
        this.title = title;
        this.description = description;
        this.position = position;
        this.webLink = webLink;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPosition() {
        return position;
    }

    public String getWebLink() {
        return webLink;
    }

    public YearMonth getStartDate() {
        return startDate;
    }

    public YearMonth getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "Org{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", position='" + position + '\'' +
                ", webLink='" + webLink + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Org org = (Org) o;

        if (!title.equals(org.title)) return false;
        if (!description.equals(org.description)) return false;
        if (!position.equals(org.position)) return false;
        if (!webLink.equals(org.webLink)) return false;
        if (!startDate.equals(org.startDate)) return false;
        return endDate.equals(org.endDate);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + webLink.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}
