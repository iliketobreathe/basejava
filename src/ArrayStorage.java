import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private int storageElementNumber = 0;
    private Resume[] storage = new Resume[10_000];

/*    private boolean isInStorage(Resume resume) {
        storageElementNumber = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(resume.uuid)) {
                storageElementNumber++;
                return true;
            }
        }
        return false;
    }*/

    private boolean isExist(String uuid) {
        storageElementNumber = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storageElementNumber = i;
                return true;
            }
        }
        return false;
    }

    public void clear() {
        /*for (int i = 0; i < size; i++) {
            storage[i] = null;
        }*/
        Arrays.fill(storage, 0, size - 1, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (isExist(resume.uuid)) {
            storage[storageElementNumber] = resume;
            System.out.println("Resume with uuid " + resume.uuid + " is found in storage and updated");
            return;
        }
        System.out.println("ERROR: Resume with this uuid isn't in the storage");
    }

    public void save(Resume resume) {
        if (size != storage.length && resume.uuid != null) {
            if (isExist(resume.uuid)) {
                System.out.println("Resume with this uuid is already in storage");
                return;
            }
            storage[size] = resume;
            size++;
        }
        else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    public Resume get(String uuid) {
        if (isExist(uuid)) {
            return  storage[storageElementNumber];
        }
        System.out.println("ERROR: Resume with this uuid isn't in storage");
        return null;
    }

    public void delete(String uuid) {
        if (isExist(uuid)) {
            System.arraycopy(storage, storageElementNumber + 1, storage, storageElementNumber, size - 1 - storageElementNumber);
            size--;
            return;
        }
        System.out.println("ERROR: Resume with this uuid isn't in storage");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        if (size > 0) {
            System.arraycopy(storage, 0, resumes, 0, size);
        }
        return resumes;
    }

    public int size() {
        return size;
    }
}
