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
        if (size() == 0) {
            storage[size()] = r;
        } else if (size() != storage.length) {
            for (int i = 0; i <= size() - 1; i++) {
                if (r.uuid.equals(storage[i].uuid)) {
                    break;
                } else if (i == size() - 1) {
                    storage[size()] = r;
                }
            }
        }
    }

    Resume get(String uuid) {
        Resume requestedResume = new Resume();
        requestedResume.uuid = null;

        if (size() != 0) {
            for (int i = 0; i <= size() - 1; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    requestedResume.uuid = uuid;
                    break;
                }
            }
        }
        return requestedResume;
    }

    void delete(String uuid) {
        if (size() != 0) {
            for (int i = 0; i <= size() - 1; i++) {
                if (storage[i].uuid.equals(uuid)) {
                    if (size() - i >= 0) System.arraycopy(storage, i + 1, storage, i, size() - i);
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] all = new Resume[size()];
        for (int i = 0; i <= all.length - 1; i++) {
            all[i] = storage[i];
        }
        return all;
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
