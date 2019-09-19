package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract SK getSearchKey(String uuid);
    protected abstract boolean isExist(SK searchKey);
    protected abstract void updateElement(Resume resume, SK searchKey);
    protected abstract void saveInStorage(Resume resume, SK searchKey);
    protected abstract void deleteFromStorage(SK searchKey);
    protected abstract Resume getElement(SK searchKey);
    protected abstract List<Resume> getAll();

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        SK searchKey = getExistedSearchKey(resume.getUuid());
        updateElement(resume, searchKey);
    }

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        saveInStorage(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        return getElement(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = getAll();
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedSearchKey(uuid);
        deleteFromStorage(searchKey);
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
