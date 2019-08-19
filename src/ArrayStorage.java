import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected int isExist(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                return i;
            }
        }
        return -1;
    }
    @Override
    public void update(Resume resume) {
        if (isExist(resume.getUuid()) >= 0) {
            storage[index] = resume;
            System.out.println("Resume with uuid " + resume.getUuid() + " was updated");
            return;
        }
        System.out.println("ERROR: Resume with uuid " + resume.getUuid() + " isn't in the storage");
    }
    @Override
    public void save(Resume resume) {
        if (size != STORAGE_LIMIT && resume.getUuid() != null) {
            if (isExist(resume.getUuid()) >= 0) {
                System.out.println("Resume with uuid " + resume.getUuid() + " is already in storage");
                return;
            }
            storage[size] = resume;
            size++;
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }
}
