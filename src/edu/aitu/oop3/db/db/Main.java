package edu.aitu.oop3.db.db;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        IDatabase database = new SupabaseDatabase(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD")
        );

        try (Connection c = database.getConnection()) {
            System.out.println("CONNECTED Architecture OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}