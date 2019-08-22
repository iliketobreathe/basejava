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
    public void saveElement(Resume resume) {
        if (Math.abs(index + 1) == size) {
            storage[size] = resume;
        } else if (size - Math.abs(index + 1) >= 0) {
            System.arraycopy(storage, Math.abs(index + 1), storage, Math.abs(index + 1) + 1, size - Math.abs(index + 1));
            storage[Math.abs(index + 1)] = resume;
        }
    }

    @Override
    public void deleteElement() {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
