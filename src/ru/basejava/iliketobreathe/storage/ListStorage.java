package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storage = new ArrayList<>();
    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid()))
                return i;
        }
        return -1;
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void saveInStorage(Resume resume, int index) {
        if (resume.getUuid() != null) {
            storage.add(resume);
            size++;
        }
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage.remove(index);
        size--;
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(index);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }
}
