import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                storage[i] = null;
            }
        }
    }

    void save(Resume r) {
        int size = size();
        if (size != storage.length && r.uuid != null) {
            for (int i = 0; i < size; i++) {
                if (r.uuid.equals(storage[i].uuid)) {
                    return;
                }
            }
            storage[size] = r;
        }
    }

    Resume get(String uuid) {
        int size = size();
        if (size != 0) {
            for (int i = 0; i <= size - 1; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    return  storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        int size = size();
        if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(uuid)) {
                        System.arraycopy(storage, i + 1, storage, i, size - i);
                        return;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int size = size();
        Resume[] resumes = new Resume[size];
        if (size > 0) {
            resumes = new Resume[size];
            System.arraycopy(storage, 0, resumes, 0, size);
        }
        return resumes;
    }

    int size() {
        int resumeAmount = 0;
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null) {
                resumeAmount++;
            }
        }
        return resumeAmount;
    }
}
