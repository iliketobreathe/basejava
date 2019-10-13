package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private Serializer serializer;

    public FileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }

        this.serializer = serializer;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void updateElement(Resume resume, File file) {
        try {
            serializer.writeInStorage(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveInStorage(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw  new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateElement(resume, file);
    }

    @Override
    protected void deleteFromStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return serializer.readFromStorage(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> fileList = new ArrayList<>();
        for (File file : files) {
            fileList.add(getElement(file));
        }
        return fileList;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteFromStorage(file);
            }
        }
    }

    @Override
    public int size() {
        String[] fileList = directory.list();
        if (fileList == null) {
            throw new StorageException("Directory read error", null);
        }
        return fileList.length;
    }
}
