package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveElement(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void deleteElement() {
        storage[index] = storage[size - 1];
    }
}
