package br.edu.ifpb;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Listando dados no banco de dados COVID-BD:");

        try {
            Class.forName("org.postgresql.Driver");
            Dotenv dotenv = Dotenv.load();
            String DB_HOST = dotenv.get("DB_HOST");
            String DB_PORT = dotenv.get("DB_PORT");
            String DB_DATABASE = dotenv.get("DB_DATABASE");
            String DB_URL = "jdbc:postgresql://" + DB_HOST + ":" + DB_PORT + "/" + DB_DATABASE;
            String DB_USER = dotenv.get("DB_USER");
            String DB_PASSWORD = dotenv.get("DB_PASSWORD");

            try(Connection conection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
                Statement statement = conection.createStatement();
                ResultSet result = statement.executeQuery("SELECT count(city) FROM dados WHERE date = '2021-01-01'");

                if(result.next()){
                    System.out.println("Total de " + result.getInt(1) + " de cidades registradas.");
                }

                System.out.println();

                result = statement.executeQuery("SELECT * FROM dados WHERE date = '2021-01-01'");
                int i = 1;
                while(result.next()) {
                    System.out.println(i + " -> " + result.getString("city"));
                    i++;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}