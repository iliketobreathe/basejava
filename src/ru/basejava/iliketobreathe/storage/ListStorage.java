package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isTrue(Object index) {
        return (int)index >= 0;
    }

    @Override
    protected Object getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid()))
                return i;
        }
        return -1;
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    protected void saveInStorage(Resume resume, Object index) {
        if (resume.getUuid() != null) {
            storage.add(resume);
        }
    }

    @Override
    protected void deleteFromStorage(Object index) {
        storage.remove((int)index);
    }

    @Override
    protected Resume getElement(Object index) {
        return storage.get((int) index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
