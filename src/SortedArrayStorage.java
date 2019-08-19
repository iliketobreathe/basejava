import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int isExist(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        index = Arrays.binarySearch(storage, 0, size, searchKey);
        return index;
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
            } else if (Math.abs(index + 1) == size) {
                storage[size] = resume;
                size++;
            } else {
                if (size - Math.abs(index + 1) >= 0)
                    System.arraycopy(storage, Math.abs(index + 1), storage, Math.abs(index + 1) + 1, size - Math.abs(index + 1));
                storage[Math.abs(index + 1)] = resume;
                size++;
            }
        } else {
            System.out.println("ERROR: Storage is full or you entered empty uuid");
        }
    }
}
