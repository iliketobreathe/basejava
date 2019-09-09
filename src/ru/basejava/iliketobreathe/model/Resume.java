package ru.basejava.iliketobreathe.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private String fullName;

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

/*    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }*/

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        int a = this.getFullName().compareTo(o.getFullName());
        if (a != 0) {
            return a;
        } else {
            return this.getUuid().compareTo(o.getUuid());
        }
    }
}
