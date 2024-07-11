package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    @Override
    public void clear() {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.executeQuery("DELETE FROM resume", () -> {
            sqlHelper.preparedStatement.execute();
        });
    }

    @Override
    public void save(Resume r) {
        SQLHelper sqlHelper = new SQLHelper();
        try {
            sqlHelper.executeQuery("INSERT INTO resume (uuid, full_name) VALUES(?,?)", () -> {
                sqlHelper.preparedStatement.setString(1, r.getUuid());
                sqlHelper.preparedStatement.setString(2, r.getFullName());
                sqlHelper.preparedStatement.execute();
            });
        } catch (StorageException e) {
            throw new ExistsStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        SQLHelper sqlHelper = new SQLHelper();
        return sqlHelper.executeQuery("SELECT * FROM resume r WHERE r.uuid = ?", () -> {
            sqlHelper.preparedStatement.setString(1, uuid);
            ResultSet resultSet = sqlHelper.preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistsStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString(("full_name")));
        });
    }


    @Override
    public void delete(String uuid) {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.executeQuery("DELETE FROM resume r WHERE r.uuid = ?", () -> {
            sqlHelper.preparedStatement.setString(1, uuid);
            sqlHelper.preparedStatement.execute();
            if (sqlHelper.preparedStatement.getUpdateCount() == 0) {
                throw new NotExistsStorageException(uuid);
            }
        });
    }

    @Override
    public void update(Resume resume) {
        SQLHelper sqlHelper = new SQLHelper();
        sqlHelper.executeQuery("UPDATE resume SET full_name = ? WHERE uuid = ?", () -> {
            sqlHelper.preparedStatement.setString(1, resume.getFullName());
            sqlHelper.preparedStatement.setString(2, resume.getUuid());
            sqlHelper.preparedStatement.execute();
            if (sqlHelper.preparedStatement.getUpdateCount() == 0) {
                throw new NotExistsStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        SQLHelper sqlHelper = new SQLHelper();
        return sqlHelper.executeQuery("SELECT * FROM resume", () -> {
            ResultSet resultSet = sqlHelper.preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<Resume>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        SQLHelper sqlHelper = new SQLHelper();
        return sqlHelper.executeQuery("SELECT COUNT(*) FROM resume", () -> {
            ResultSet resultSet = sqlHelper.preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
