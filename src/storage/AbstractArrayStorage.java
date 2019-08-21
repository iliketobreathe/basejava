package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;
    protected int index = 0;

    protected abstract int isExist(String uuid);

    public abstract void save(Resume resume);

    public abstract void delete(String uuid);

    boolean isStorageNotFullAndNotNullUuid(Resume resume) {
        return size != STORAGE_LIMIT && resume.getUuid() != null;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (isExist(resume.getUuid()) >= 0) {
            storage[index] = resume;
            System.out.println("Resume with uuid " + resume.getUuid() + " was updated");
            return;
        }
        System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " isn't in the storage");
    }

    public Resume get(String uuid) {
        if (isExist(uuid) >= 0) {
            return  storage[index];
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
