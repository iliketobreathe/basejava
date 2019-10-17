package ru.basejava.iliketobreathe.storage.serializer;

import ru.basejava.iliketobreathe.model.*;
import ru.basejava.iliketobreathe.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializer {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser (
            Resume.class, Organization.class, Organization.Period.class, OrganizationSection.class, Link.class, StringSection.class, ListSection.class);
    }

    @Override
    public void writeInStorage(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume readFromStorage(InputStream is) throws IOException {
        try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }
}
