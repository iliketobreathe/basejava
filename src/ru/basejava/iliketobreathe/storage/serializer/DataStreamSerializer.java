package ru.basejava.iliketobreathe.storage.serializer;

import ru.basejava.iliketobreathe.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void writeInStorage(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {

            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();

            writeCollection(dos, contacts.entrySet(), (contactTypeStringEntry) -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSections();

            writeCollection(dos, sections.entrySet(), (sectionTypeAbstractSectionEntry) -> {
                SectionType sectionType = sectionTypeAbstractSectionEntry.getKey();
                AbstractSection section = sectionTypeAbstractSectionEntry.getValue();
                dos.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((StringSection)section).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection)section).getElements(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((OrganizationSection)section).getOrganizations(), (organization) -> {
                            dos.writeUTF(organization.getHomePage().getName());
                            dos.writeUTF(organization.getHomePage().getUrl());
                            writeCollection(dos, organization.getPeriods(), (period) -> {
                                writeDate(dos, period);
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                        break;
                }
            });
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
                                periods.add(new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()));
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

    private void writeDate(DataOutputStream dos, Organization.Period p) throws IOException {
        dos.writeInt(p.getStartDate().getYear());
        dos.writeInt(p.getStartDate().getMonth().getValue());
        dos.writeInt(p.getEndDate().getYear());
        dos.writeInt(p.getEndDate().getMonth().getValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private interface DataExporter<T> {
        void export(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, DataExporter<T> dataExporter) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            dataExporter.export(element);
        }
    }
}
