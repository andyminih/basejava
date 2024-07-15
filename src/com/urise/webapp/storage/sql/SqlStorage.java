package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery("DELETE FROM resume", null, PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.executeQuery("INSERT INTO resume (uuid, full_name) VALUES(?,?)", r.getUuid(), (preparedStatement) -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery("SELECT * FROM resume r WHERE r.uuid = ?", (preparedStatement) -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString(("full_name")));
        });
    }


    @Override
    public void delete(String uuid) {
        sqlHelper.executeQuery("DELETE FROM resume r WHERE r.uuid = ?", null, (preparedStatement) -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistsStorageException(uuid);
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeQuery("UPDATE resume SET full_name = ? WHERE uuid = ?", null, (preparedStatement) -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistsStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery("SELECT * FROM resume ORDER BY full_name ASC, uuid ASC", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT COUNT(*) FROM resume", (preparedStatement) -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
