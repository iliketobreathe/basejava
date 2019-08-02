import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        if (size() != storage.length) {
                    storage[size()] = r;
        }
    }

    Resume get(String uuid) {
        return null;
    }

    void delete(String uuid) {
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size()];
        for (int i = 0; i < all.length - 1; i++) {
                all[i] = storage[i];
        }
        return all;
    }

    int size() {
        int storageSize = 0;
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null) {
                storageSize++;
            }
        }
        return storageSize;
    }
}
