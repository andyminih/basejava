package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume", preparedStatement -> {
            preparedStatement.execute();
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES(?,?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            saveContacts(connection, resume);
            saveSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.executeQuery(
                "SELECT * FROM resume r " +
                        "LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                        "WHERE r.uuid = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistsStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, resultSet.getString(("full_name")));
                    do {
                        r.putContact(ContactType.valueOf(resultSet.getString(("type"))), resultSet.getString(("value")));
                    } while (resultSet.next());

                    return r;
                });
        sqlHelper.executeQuery("SELECT * FROM section s WHERE s.resume_uuid = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        putSection(resume, SectionType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    }
                });
        return resume;
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume r WHERE r.uuid = ?", (preparedStatement) -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistsStorageException(resume.getUuid());
                }
            }
            deleteResumeData(connection, resume, "contact");
            saveContacts(connection, resume);
            deleteResumeData(connection, resume, "section");
            saveSections(connection, resume);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        final List<Resume> resumeList = sqlHelper.executeQuery("SELECT * FROM resume ORDER BY full_name ASC, uuid ASC", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, Resume> resumeMap = new LinkedHashMap<>();
            while (resultSet.next()) {
                resumeMap.put(resultSet.getString("uuid"), new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return new ArrayList<>(resumeMap.values());
        });

        getAllResumesDataFromTable(resumeList, "contact",
                (Resume resume, ResultSet resultSet) -> resume.putContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value")));

        getAllResumesDataFromTable(resumeList, "section",
                (Resume resume, ResultSet resultSet) -> putSection(resume, SectionType.valueOf(resultSet.getString("type")), resultSet.getString("value")));

        return resumeList;
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT COUNT(*) FROM resume", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }

    private <K, V> void insertResumeData(Connection connection, Resume resume, String tableName,
                                         Map<K, V> map, BiConsumerNoException<Map.Entry<K, V>, PreparedStatement> consumer) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + tableName + " (resume_uuid, type, value) VALUES(?,?,?)")) {
            for (Map.Entry<K, V> e : map.entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                consumer.accept(e, preparedStatement);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void saveContacts(Connection connection, Resume resume) throws SQLException {
        insertResumeData(connection, resume, "contact", resume.getContacts(), (e, ps) -> {
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue());
        });
    }

    private void saveSections(Connection connection, Resume resume) throws SQLException {
        insertResumeData(connection, resume, "section", resume.getSections(), (e, ps) -> {
            ps.setString(2, e.getKey().name());
            switch (e.getKey()) {
                case PERSONAL, OBJECTIVE:
                    ps.setString(3, e.getValue().toString());
                    break;
                case ACHIEVEMENTS, QUALIFICATIONS:
                    ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getList()));
                    break;
            }
        });
    }

    private void getAllResumesDataFromTable(List<Resume> resumeList, String tableName, BiConsumerNoException<Resume, ResultSet> resumeConsumer) {
        sqlHelper.executeQuery("SELECT * FROM " + tableName + " ORDER BY resume_uuid ASC", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            Resume resume = null;
            while (resultSet.next()) {
                if (resume == null || !resume.getUuid().equals(resultSet.getString("resume_uuid"))) {
                    resume = (resumeList.stream().filter(r -> {
                        try {
                            return r.getUuid().equals(resultSet.getString("resume_uuid"));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList().getFirst());
                }
                resumeConsumer.accept(resume, resultSet);
            }
        });
    }

    private void deleteResumeData(Connection connection, Resume resume, String tableName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void putSection(Resume resume, SectionType sectionType, String value) {
        Section section = switch (sectionType) {
            case PERSONAL, OBJECTIVE -> new TextSection(value);
            case ACHIEVEMENTS, QUALIFICATIONS -> new ListSection(Arrays.stream(value.split("\n")).toList());
            default -> null;
        };
        resume.putSection(sectionType, section);
    }


}
