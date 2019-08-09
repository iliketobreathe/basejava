import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void update(Resume r) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                storage[i] = r;
            }
            else {
                System.out.println("ERROR: Resume with this uuid isn't in the storage");
            }
        }
    }

    void save(Resume r) {
        if (size != storage.length && r.uuid != null) {
            for (int i = 0; i < size; i++) {
                if (r.uuid.equals(storage[i].uuid)) {
                    return;
                }
            }
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return  storage[i];
                }
            }
        return null;
    }

    void delete(String uuid) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                        System.arraycopy(storage, i + 1, storage, i, size - 1 - i);
                        size--;
                        return;
                }
            }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (size > 0) {
            System.arraycopy(storage, 0, resumes, 0, size);
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
