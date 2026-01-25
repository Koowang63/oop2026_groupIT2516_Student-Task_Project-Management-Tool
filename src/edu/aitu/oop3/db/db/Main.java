package edu.aitu.oop3.db.db;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        IDatabase database = new SupabaseDatabase(
                "jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:5432/postgres?sslmode=require",
                "postgres.xouujdmgcoflekqlgqwm",
                "bainazarproject"
        );

        try (Connection c = database.getConnection()) {
            System.out.println("CONNECTED Architecture OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}