package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

/**
 * Array based ru.basejava.iliketobreathe.storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveElement(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElement(int index) {
        storage[index] = storage[size - 1];
    }
}
