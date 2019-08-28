package storage;

import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        for (int i = storage.size(); i < 10_000; i++) {
            storage.save(new Resume());
        }
        Assert.assertEquals(10_000, storage.size());
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid3");
        storage.update(resume);
        Assert.assertEquals("uuid3", resume.getUuid());
    }

    @Test
    public void get() {
        Assert.assertEquals("uuid1", storage.get("uuid1").getUuid());
    }

    @Test
    public void delete() {
        storage.delete("uuid1");
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] testStorage = new Resume[3];
        testStorage[0] = new Resume("uuid1");
        testStorage[1] = new Resume("uuid2");
        testStorage[2] = new Resume("uuid3");
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(testStorage[i], storage.getAll()[i]);
        }

    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void overflowExceptionGet() throws Exception {
        try {
            for (int i = storage.size(); i < 10_000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Storage overflow expected later");
        }
        storage.save(new Resume());
    }
}