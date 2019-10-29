package ru.basejava.iliketobreathe.storage.serializer;

import ru.basejava.iliketobreathe.exception.StorageException;
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
                                writeDate(dos, period.getStartDate());
                                writeDate(dos, period.getEndDate());
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

            readData(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readData(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate localDate) throws IOException {
        dos.writeInt(localDate.getYear());
        dos.writeInt(localDate.getMonth().getValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private interface DataExporter<T> {
        void exportData(T t) throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, DataExporter<T> dataExporter) throws IOException {
        dos.writeInt(collection.size());
        for (T element : collection) {
            dataExporter.exportData(element);
        }
    }

    private interface DataImporter {
        void importData() throws IOException;
    }

    private void readData(DataInputStream dis, DataImporter dataImporter) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            dataImporter.importData();
        }
    }

    private <T> AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new StringSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readListFromData(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(readListFromData(dis, () ->
                        new Organization(new Link(dis.readUTF(), dis.readUTF()), readListFromData(dis, () ->
                                new Organization.Period(readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF())))));
            default:
                throw new StorageException("Error of reading" + sectionType.name() + "from data", sectionType.name());
        }
    }

    private interface ListImporter<T> {
        T listElementImport() throws IOException;
    }

    private <T> List<T> readListFromData(DataInputStream dis, ListImporter<T> listImporter) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(listImporter.listElementImport());
        }
        return list;
    }
}
