package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void saveInStorage(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    protected void deleteFromStorage(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume getElement(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
