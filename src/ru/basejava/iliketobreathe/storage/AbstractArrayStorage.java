package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void saveElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveInStorage(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (resume.getUuid() != null) {
            saveElement(resume, index);
            size++;
        }
    }

    @Override
    public void updateElement(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    public Resume getElement(int index) {
        return  storage[index];
    }

    @Override
    public void deleteFromStorage(int index) {
            deleteElement(index);
            storage[size - 1] = null;
            size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
