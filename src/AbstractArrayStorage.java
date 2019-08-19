import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;
    protected int index = 0;

    protected abstract int isExist(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
