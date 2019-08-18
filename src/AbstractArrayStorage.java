public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;
    protected int index = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        if (isExist(uuid) >= 0) {
            return  storage[index];
        }
        System.out.println("ERROR: Resume with uuid " + uuid + " isn't in storage");
        return null;
    }

    protected abstract int isExist(String uuid);
}
