package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();
    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey.toString());
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected void saveInStorage(Resume resume, Object searchKey) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected void deleteFromStorage(Object searchKey) {
        storage.remove(searchKey.toString());
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey.toString());
    }

    @Override
    public void clear() {
        storage.clear();
    }

/*    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }*/

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(storage.values());
        Collections.sort(list);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
