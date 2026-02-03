package edu.aitu.oop3.db.db;

public class DatabaseConfig {
    private static DatabaseConfig instance;

    private final String url;
    private final String user;
    private final String password;

    private DatabaseConfig() {
        this.url = System.getenv("DB_URL");
        this.user = System.getenv("DB_USER");
        this.password = System.getenv("DB_PASSWORD");
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}