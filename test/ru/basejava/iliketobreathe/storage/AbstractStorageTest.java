package ru.basejava.iliketobreathe.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.basejava.iliketobreathe.ResumeTestData;
import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\YandexDisk\\basejava\\storage");

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private Resume resume1 = ResumeTestData.resumeFill(UUID_1, "Name1");
    private Resume resume2 = ResumeTestData.resumeFill(UUID_2, "Name2");
    private Resume resume3 = ResumeTestData.resumeFill(UUID_3, "Name3");
    private Resume resume4 = ResumeTestData.resumeFill(UUID_4, "Name4");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(Arrays.asList(resume1, resume2, resume3), list);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_3, "New Name");
        storage.update(newResume);
        assertEquals(newResume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() {
        storage.save(resume4);
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resume1);
    }

    @Test
    public void get() {
        assertEquals(resume1, storage.get(UUID_1));
    }

    @Test (expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test (expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }
}