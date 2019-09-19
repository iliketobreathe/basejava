package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid()))
                return i;
        }
        return -1;
    }

    @Override
    protected void updateElement(Resume resume, Integer searchKey) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void saveInStorage(Resume resume, Integer searchKey) {
        storage.add(resume);
    }

    @Override
    protected void deleteFromStorage(Integer searchKey) {
        storage.remove((searchKey).intValue());
    }

    @Override
    protected Resume getElement(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
