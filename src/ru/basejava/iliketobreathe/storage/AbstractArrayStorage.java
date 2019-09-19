package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void saveElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveInStorage(Resume resume, Integer index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (resume.getUuid() != null) {
            saveElement(resume,  index);
            size++;
        }
    }

    @Override
    public void updateElement(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    public Resume getElement(Integer index) {
        return  storage[index];
    }

    @Override
    public void deleteFromStorage(Integer index) {
            deleteElement(index);
            storage[size - 1] = null;
            size--;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }
}
