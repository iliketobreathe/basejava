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
        int insertIndex = -index - 1;
/*        if (binarySearchIndex == size) {
            storage[binarySearchIndex] = resume;
        } */
            if (size - insertIndex >= 0) {
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;
        }
    }

    @Override
    public void deleteElement() {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
