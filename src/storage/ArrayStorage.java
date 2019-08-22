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
    protected void childSave(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with uuid " + resume.getUuid() + " is already in storage");
            return;
        }
        storage[size] = resume;
        size++;
    }

    @Override
    protected void childDelete() {
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }
}
