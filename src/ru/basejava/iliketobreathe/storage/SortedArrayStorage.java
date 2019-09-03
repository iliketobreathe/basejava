package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void saveElement(Resume resume, Object index) {
        int insertIndex = -(int)index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    public void deleteElement(Object index) {
        int numMoved = size - (int)index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, (int)index + 1, storage, (int)index, numMoved);
        }
    }
}
