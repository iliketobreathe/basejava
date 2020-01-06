package ru.basejava.iliketobreathe;

import ru.basejava.iliketobreathe.storage.SqlStorage;
import ru.basejava.iliketobreathe.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(getHomeDir(), "config\\resumes.properties");
    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Storage storage;


    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try(InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
