package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    protected abstract void writeInStorage(Resume resume, OutputStream os) throws IOException;

    protected abstract Resume readFromStorage(InputStream is) throws IOException;

    @Override
    protected Path getSearchKey(String uuid) {
        return new Path(directory, uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return file.exists();
    }

    @Override
    protected void updateElement(Resume resume, Path file) {
        try {
            writeInStorage(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveInStorage(Resume resume, Path file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw  new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateElement(resume, file);
    }

    @Override
    protected void deleteFromStorage(Path file) {
        if (!file.delete()) {
            throw new StorageException("Path delete error", file.getName());
        }
    }

    @Override
    protected Resume getElement(Path file) {
        try {
            return readFromStorage(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.getName(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        Path[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> fileList = new ArrayList<>();
        for (Path file : files) {
            fileList.add(getElement(file));
        }
        return fileList;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteFromStorage);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
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
