package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File(".\\config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private static String dbConnection;
    private static String dbPassword;
    private static String dbUser;
    private final File storageDir;
    private Properties props = new Properties();

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            dbConnection = props.getProperty("db.url");
            dbPassword = props.getProperty("db.password");
            dbUser = props.getProperty("db.user");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public static String getDbConnection() {
        return dbConnection;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
