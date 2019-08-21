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
    public void save(Resume resume) {
        if (isStorageNotFullAndNotNullUuid(resume)) {
            if (getIndex(resume.getUuid()) >= 0) {
                System.out.println("Resume with uuid " + resume.getUuid() + " is already in storage");
                return;
            }
            storage[size] = resume;
            size++;
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    @Override
    public void delete(String uuid) {
        if (getIndex(uuid) >= 0) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
    }
}
