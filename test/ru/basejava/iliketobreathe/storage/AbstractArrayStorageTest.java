package ru.basejava.iliketobreathe.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.basejava.iliketobreathe.exception.StorageException;
import ru.basejava.iliketobreathe.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowException() throws Exception {
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