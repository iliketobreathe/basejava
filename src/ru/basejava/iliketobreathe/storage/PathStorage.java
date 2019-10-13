package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private Serializer serializer;

    public PathStorage(String dir, Serializer serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializer = serializer;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void updateElement(Resume resume, Path path) {
        try {
            serializer.writeInStorage(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void saveInStorage(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw  new StorageException("Path create error", path.getFileName().toString(), e);
        }
        updateElement(resume, path);
    }

    @Override
    protected void deleteFromStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw  new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return serializer.readFromStorage(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.list(directory).map(this::getElement).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Path read error", null, e);
        }
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
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Path read error", null);
        }
    }
}
