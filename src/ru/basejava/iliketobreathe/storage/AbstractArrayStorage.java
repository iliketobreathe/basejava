package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void saveElement(Resume resume, int index);

    protected abstract void deleteElement(int index);

    @Override
    protected boolean isExist(Object index) {
        return (Integer)index >= 0;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveInStorage(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (resume.getUuid() != null) {
            saveElement(resume,  (Integer) index);
            size++;
        }
    }

    @Override
    public void updateElement(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    @Override
    public Resume getElement(Object index) {
        return  storage[(Integer) index];
    }

    @Override
    public void deleteFromStorage(Object index) {
            deleteElement((Integer) index);
            storage[size - 1] = null;
            size--;
    }

/*    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }*/

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(storage, 0, size)));
/*        Collections.addAll(list, storage);*/
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return size;
    }
}
