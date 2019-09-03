package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();
    private String uuidMap = "";
    @Override
    protected int getIndex(String uuid) {
        /*for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                return 1;
            }
        }
        return -1;*/
        uuidMap = uuid;
        if (storage.containsKey(uuid)) {
            return 1;
        }
        return -1;
    }

    @Override
    protected void updateElement(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveInStorage(Resume resume, int index) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteFromStorage(int index) {
        storage.remove(uuidMap);
    }

    @Override
    protected Resume getElement(int index) {
        return storage.get(uuidMap);
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
