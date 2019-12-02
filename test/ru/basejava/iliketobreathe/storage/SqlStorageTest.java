package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.Config;

import static org.junit.Assert.*;

public class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }
}