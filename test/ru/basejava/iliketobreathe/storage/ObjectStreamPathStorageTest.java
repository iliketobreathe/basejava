package ru.basejava.iliketobreathe.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR.getAbsolutePath()));
    }
}