package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();
    @Override
    protected Object getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object index) {
        return storage.containsKey(index);
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveInStorage(Resume resume, Object index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(Object index) {
        storage.remove(index);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
