package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getIndex(String uuid);
    protected abstract boolean isExist(Object index);
    protected abstract void updateElement(Resume resume, Object index);
    protected abstract void saveInStorage(Resume resume, Object index);
    protected abstract void deleteFromStorage(Object index);
    protected abstract Resume getElement(Object index);

    public abstract void clear();
    public abstract Resume[] getAll();
    public abstract int size();

    @Override
    public void update(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (!isExist(index)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateElement(resume, index);
        }
    }

    @Override
    public void save(Resume resume) {
        Object index = getIndex(resume.getUuid());
        if (isExist(index)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveInStorage(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            return getElement(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            deleteFromStorage(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
