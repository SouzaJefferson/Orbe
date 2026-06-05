package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoDB {

    private static final String URL = "jdbc:mysql://localhost:3308/void";
    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    private ConexaoDB(){}

    // Agora, TODA VEZ que alguém chamar conectar(), forjamos uma conexão NOVA.
    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados. O Docker está rodando? Erro: " + e.getMessage());
        }
    }
}