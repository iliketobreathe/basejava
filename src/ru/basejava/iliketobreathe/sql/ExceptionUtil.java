package ru.basejava.iliketobreathe.sql;

import org.postgresql.util.PSQLException;
import ru.basejava.iliketobreathe.exception.ExistStorageException;
import ru.basejava.iliketobreathe.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
