package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapOtherStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsValue(searchKey);
    }

    @Override
    protected void updateElement(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveInStorage(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(Resume resume) {
        storage.remove(resume.getUuid());
    }

    @Override
    protected Resume getElement(Resume resume) {
        return storage.get(resume.getUuid());
    }

    @Override
    public void clear() {
        storage.clear();
    }

/*    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }*/

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return storage.size();
    }
}
