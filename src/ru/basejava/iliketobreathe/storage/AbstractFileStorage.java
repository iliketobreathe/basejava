package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
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
            writeInStorage(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }

    }

    @Override
    protected void saveInStorage(Resume resume, File file) {
        try {
            file.createNewFile();
            writeInStorage(resume, file);
        } catch (IOException e) {
            throw  new StorageException("IO error", file.getName(), e);
        }

    }

    protected abstract void writeInStorage(Resume resume, File file) throws IOException;

    @Override
    protected void deleteFromStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("IO error", file.getName());
        }
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return readFromStorage(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract Resume readFromStorage(File file) throws IOException;

    @Override
    protected List<Resume> getAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("IO error", null);
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
            throw new StorageException("IO error", null);
        }
        return fileList.length;
    }
}
