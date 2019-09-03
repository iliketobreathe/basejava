package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract int getIndex(String uuid);
    protected abstract void updateElement(Resume resume, int index);
    protected abstract void saveInStorage(Resume resume, int index);
    protected abstract void deleteFromStorage(int index);
    protected abstract Resume getElement(int index);

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(resume, index);
        }
    }

    @Override
    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveInStorage(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getElement(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteFromStorage(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
