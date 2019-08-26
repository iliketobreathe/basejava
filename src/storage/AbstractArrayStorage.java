package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;
    protected int index = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void saveElement(Resume resume);

    protected abstract void deleteElement();

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        getIndex(resume.getUuid());
        if (size != STORAGE_LIMIT) {
            if (index >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                saveElement(resume);
                size++;
            }
        } else {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
    }

    public void update(Resume resume) {
        getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        getIndex(uuid);
        if (index >= 0) {
            return  storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        getIndex(uuid);
        if (index >= 0) {
            deleteElement();
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
