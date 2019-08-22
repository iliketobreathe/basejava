package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        index = Arrays.binarySearch(storage, 0, size, searchKey);
        return index;
    }

    @Override
    public void childSave(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with uuid " + resume.getUuid() + " is already in storage");
        } else if (Math.abs(index + 1) == size) {
            storage[size] = resume;
            size++;
        } else if (size - Math.abs(index + 1) >= 0) {
            System.arraycopy(storage, Math.abs(index + 1), storage, Math.abs(index + 1) + 1, size - Math.abs(index + 1));
            storage[Math.abs(index + 1)] = resume;
            size++;
        }
    }

    @Override
    public void childDelete() {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        storage[size - 1] = null;
        size--;
    }
}
