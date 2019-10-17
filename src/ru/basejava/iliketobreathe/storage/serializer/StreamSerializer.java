package ru.basejava.iliketobreathe.storage.serializer;

import ru.basejava.iliketobreathe.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void writeInStorage(Resume resume, OutputStream os) throws IOException;

    Resume readFromStorage(InputStream is) throws IOException;
}
