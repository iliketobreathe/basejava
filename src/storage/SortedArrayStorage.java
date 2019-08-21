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
    public void save(Resume resume) {
        super.save(resume);
        if (isStorageNotFullAndNotNullUuid(resume)) {
            if (index >= 0) {
                System.out.println("Resume with uuid " + resume.getUuid() + " is already in storage");
            } else if (Math.abs(index + 1) == size) {
                storage[size] = resume;
                size++;
            } else {
                if (size - Math.abs(index + 1) >= 0)
                    System.arraycopy(storage, Math.abs(index + 1), storage, Math.abs(index + 1) + 1, size - Math.abs(index + 1));
                storage[Math.abs(index + 1)] = resume;
                size++;
            }
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
    }
}
