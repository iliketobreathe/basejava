package ru.basejava.iliketobreathe.storage;

import ru.basejava.iliketobreathe.exception.NotExistStorageException;
import ru.basejava.iliketobreathe.model.*;
import ru.basejava.iliketobreathe.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
           try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
               ps.setString(1, resume.getFullName());
               ps.setString(2, resume.getUuid());
               if (ps.executeUpdate() == 0) {
                   throw new NotExistStorageException(resume.getUuid());
               }
           }

           doDelete(resume, conn, "DELETE from contact WHERE resume_uuid = ?");
           doDelete(resume, conn, "DELETE from section WHERE resume_uuid = ?");

           setContacts(conn, resume);
           setSections(conn, resume);
           return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            setContacts(conn, resume);
            setSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
               ps.setString(1, uuid);
               ResultSet rs = ps.executeQuery();
               if (!rs.next()) {
                   throw new NotExistStorageException(uuid);
               }
               r = new Resume(uuid, rs.getString("full_name"));
            }

            doGet(r, uuid, conn, "SELECT * FROM contact c WHERE c.resume_uuid = ?");
            doGet(r, uuid, conn, "SELECT * FROM section s WHERE s.resume_uuid = ?");

            return r;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            doGetAll(resumes, conn, "SELECT * FROM contact");
            doGetAll(resumes, conn, "SELECT * FROM section");

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void setContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void setSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                SectionType type = e.getKey();
                ps.setString(1, resume.getUuid());
                ps.setString(2, type.name());
                switch (type) {
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) e.getValue()).getElements();
                        String listToString = String.join("\n", list);
                        ps.setString(3, listToString);
                        break;
                    case PERSONAL:
                    case OBJECTIVE:
                        StringSection ss = (StringSection) e.getValue();
                        ps.setString(3, ss.getText());
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    r.setSection(type, new StringSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] strArray = value.split("\n");
                    r.setSection(type, new ListSection(Arrays.asList(strArray)));
                    break;
            }
        }
    }

    private void doDelete(Resume r, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void doGet(Resume r, String uuid, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (sql.contains("contact")) {
                while (rs.next()) {
                    addContact(rs, r);
                }
            } else {
                while (rs.next()) {
                    addSection(rs, r);
                }
            }
        }
    }

    private void doGetAll(Map<String, Resume> resumes, Connection conn, String sql) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (sql.contains("contact")) {
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addContact(rs, resumes.get(uuid));
                }
            } else {
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    addSection(rs, resumes.get(uuid));
                }
            }
        }
    }
}
