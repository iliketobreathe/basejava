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
        int binarySearchIndex = Math.abs(index + 1);
        if (binarySearchIndex == size) {
            storage[binarySearchIndex] = resume;
        } else if (size - binarySearchIndex >= 0) {
            System.arraycopy(storage, binarySearchIndex, storage, binarySearchIndex + 1, size - binarySearchIndex);
            storage[binarySearchIndex] = resume;
        }
    }

    @Override
    public void deleteElement() {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
