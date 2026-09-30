package com.mycompany.atividade12.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * classe responsável por abrir a conexão com o banco de dados
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/campeonato_jiujitsu";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "jiujitsu123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
