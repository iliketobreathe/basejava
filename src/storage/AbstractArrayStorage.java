package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;
    protected int index = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void childSave(Resume resume);

    protected abstract void childDelete();

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size != STORAGE_LIMIT && resume.getUuid() != null) {
            childSave(resume);
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    public void update(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            storage[index] = resume;
            System.out.println("Resume with uuid " + resume.getUuid() + " was updated");
            return;
        }
        System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " isn't in the storage");
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) >= 0) {
            return  storage[index];
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) >= 0) {
            childDelete();
        } else {
            System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
