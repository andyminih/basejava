package com.urise.webapp.storage.sql;

import com.urise.webapp.exception.ExistsStorageException;
import com.urise.webapp.exception.NotExistsStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final static String PSQL_UNIQUE_VIOLATION_ERROR = "23505";
    private final SQLHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("DELETE FROM resume");
            preparedStatement.execute();
        });
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.executeQuery(() -> {
                PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("INSERT INTO resume (uuid, full_name) VALUES(?,?)");
                preparedStatement.setString(1, r.getUuid());
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
            });
        } catch (StorageException e) {
            if (e.getCause().getClass().getSimpleName().equals("PSQLException")
                    && ((PSQLException) e.getCause()).getSQLState().equals(PSQL_UNIQUE_VIOLATION_ERROR)) {
                throw new ExistsStorageException(r.getUuid());
            }
            throw e;
        }
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?");
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
        sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("DELETE FROM resume r WHERE r.uuid = ?");
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 0) {
                throw new NotExistsStorageException(uuid);
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?");
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 0) {
                throw new NotExistsStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("SELECT * FROM resume ORDER BY full_name ASC, uuid ASC");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery(() -> {
            PreparedStatement preparedStatement = sqlHelper.getConnection().prepareStatement("SELECT COUNT(*) FROM resume");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        });
    }
}
