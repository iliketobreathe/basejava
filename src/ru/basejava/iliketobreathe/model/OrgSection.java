package ru.basejava.iliketobreathe.model;

import java.util.List;

public class OrgSection extends Section {
    private final List<Org> organizations;

    public OrgSection(List<Org> organizations) {
        this.organizations = organizations;
    }

    public List<Org> getOrganizations() {
        return organizations;
    }

    @Override
    public String toString() {
        return "OrgSection{" +
                "organizations=" + organizations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgSection that = (OrgSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }
}
