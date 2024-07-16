package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery(
                "SELECT * FROM resume r " +
                        "    LEFT JOIN contact c ON c.resume_uuid = r.uuid " +
                        "        WHERE r.uuid = ?",
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
                    try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CONTACT WHERE resume_uuid = ?")) {
                        preparedStatement.setString(1, resume.getUuid());
                        preparedStatement.execute();
                    }
                    saveContacts(connection, resume);
                    return null;
                }
        );
    }

    @Override
    public List<Resume> getAllSorted() {
        final List<Resume> resumeList = sqlHelper.executeQuery("SELECT * FROM resume ORDER BY full_name ASC, uuid ASC", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        });

        sqlHelper.executeQuery("SELECT * FROM contact", (preparedStatement) -> {
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
                resume.putContact(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
            }
        });

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

    private void saveContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES(?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, e.getKey().name());
                preparedStatement.setString(3, e.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
