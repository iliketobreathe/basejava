package ru.basejava.iliketobreathe.storage.serializer;

import ru.basejava.iliketobreathe.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void writeInStorage(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((StringSection) entry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) entry.getValue();
                        dos.writeInt(listSection.getElements().size());
                        for (String s : listSection.getElements()) {
                            dos.writeUTF(s);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                        dos.writeInt(organizationSection.getOrganizations().size());
                        for (Organization o : organizationSection.getOrganizations()) {
                            dos.writeUTF(o.getHomePage().getName());
                            dos.writeUTF(o.getHomePage().getUrl());
                            List<Organization.Period> periods = o.getPeriods();
                            dos.writeInt(periods.size());
                            for (Organization.Period p : periods) {
                                dos.writeInt(p.getStartDate().getYear());
                                dos.writeInt(p.getStartDate().getMonth().getValue());
                                dos.writeInt(p.getEndDate().getYear());
                                dos.writeInt(p.getEndDate().getMonth().getValue());
                                dos.writeUTF(p.getTitle());
                                dos.writeUTF(p.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }


    @Override
    public Resume readFromStorage(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.setSection(sectionType, new StringSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int size = dis.readInt();
                        List<String> list = new ArrayList<>(size);
                        for (int j = 0; j < size; j++) {
                            list.add(dis.readUTF());
                        }
                        resume.setSection(sectionType, new ListSection(list));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int organizationSectionSize = dis.readInt();
                        List<Organization> organizations = new ArrayList<>(organizationSectionSize);
                        for (int j = 0; j < organizationSectionSize; j++) {
                            Link link = new Link(dis.readUTF(), dis.readUTF());
                            int periodSize = dis.readInt();
                            List<Organization.Period> periods = new ArrayList<>(periodSize);
                            for (int k = 0; k < periodSize; k++) {
                                periods.add(new Organization.Period(LocalDate.of(dis.readInt(), dis.readInt(), 1), LocalDate.of(dis.readInt(), dis.readInt(), 1), dis.readUTF(), dis.readUTF()));
                            }
                            organizations.add(new Organization(link, periods));
                        }
                        resume.setSection(sectionType, new OrganizationSection(organizations));
                        break;
                }
            }
            return resume;
        }
    }
}
