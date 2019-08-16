import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int size = 0;
    private int index = 0;
    private Resume[] storage = new Resume[10_000];

    private int isExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        if (isExist(resume.uuid) >= 0) {
            storage[index] = resume;
            System.out.println("Resume with uuid " + resume.uuid + " was updated");
            return;
        }
        System.out.println("ERROR: Resume with uuid " + resume.uuid + " isn't in the storage");
    }

    public void save(Resume resume) {
        if (size != storage.length && resume.uuid != null) {
            if (isExist(resume.uuid) >= 0) {
                System.out.println("Resume with uuid " + resume.uuid + " is already in storage");
                return;
            }
            storage[size] = resume;
            size++;
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }

    public Resume get(String uuid) {
        if (isExist(uuid) >= 0) {
            return  storage[index];
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
        return null;
    }

    public void delete(String uuid) {
        if (isExist(uuid) >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
            storage[size - 1] = null;
            size--;
            return;
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
