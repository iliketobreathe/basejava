import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private int storageElementNumber = 0;
    private Resume[] storage = new Resume[10000];

    private boolean isInStorage(Resume r) {
        storageElementNumber = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(r.uuid)) {
                storageElementNumber++;
                return true;
            }
        }
        return false;
    }

    private boolean isInStorage(String uuid) {
        storageElementNumber = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storageElementNumber++;
                return true;
            }
        }
        return false;
    }

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void update(Resume r) {
        if (isInStorage(r)) {
            storage[storageElementNumber] = r;
            System.out.println("Resume with uuid" + r.uuid + "is found in storage and updated");
            return;
        }
        System.out.println("ERROR: Resume with this uuid isn't in the storage");
    }

    void save(Resume r) {
        if (size != storage.length && r.uuid != null) {
            if (isInStorage(r)) {
                System.out.println("Resume with this uuid is already in storage");
                return;
            }
            storage[size] = r;
            size++;
        }
        else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    Resume get(String uuid) {
        if (isInStorage(uuid)) {
            return  storage[storageElementNumber];
        }
        System.out.println("ERROR: Resume with this uuid isn't in storage");
        return null;
    }

    void delete(String uuid) {
        if (isInStorage(uuid)) {
            System.arraycopy(storage, storageElementNumber + 1, storage, storageElementNumber, size - 1 - storageElementNumber);
            size--;
            return;
        }
        System.out.println("ERROR: Resume with this uuid isn't in storage");
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
