package ru.basejava.iliketobreathe.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULLNAME_1 = "Will Smith";
    private static final String FULLNAME_2 = "Keanu Reeves";
    private static final String FULLNAME_3 = "Angelina Jolie";
    private static final String FULLNAME_4 = "Brad Pitt";
    private Resume resume1 = new Resume(UUID_1, FULLNAME_1);
    private Resume resume2 = new Resume(UUID_2, FULLNAME_2);
    private Resume resume3 = new Resume(UUID_3, FULLNAME_3);
    private Resume resume4 = new Resume(UUID_4, FULLNAME_4);

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1, FULLNAME_1));
        storage.save(new Resume(UUID_2, FULLNAME_2));
        storage.save(new Resume(UUID_3, FULLNAME_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

/*    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Arrays.sort(array);
        assertEquals(3, array.length);
        assertEquals(resume1, array[0]);
        assertEquals(resume2, array[1]);
        assertEquals(resume3, array[2]);
    }*/

    @Test
    public void getAllSorted() {
        ArrayList<Resume> list;
        list = (ArrayList<Resume>) storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(resume3, list.get(0));
        assertEquals(resume2, list.get(1));
        assertEquals(resume1, list.get(2));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3, FULLNAME_3);
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() {
        storage.save(resume4);
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume1);
    }

    @Test
    public void get() {
        Assert.assertEquals(resume1, storage.get(UUID_1));
    }

    @Test (expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test (expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }
}