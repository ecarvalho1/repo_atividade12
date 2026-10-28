package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.util.Conexao;
import com.mycompany.atividade12.model.Usuario;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAOjdbc implements UsuarioDAO {

    private static final String SQL_SALVAR
            = "INSERT INTO usuario (nome, login, senha_hash) VALUES (?, ?, ?)";

    @Override
    public Usuario autenticar(String login, String senha) throws SQLException {
        // FALHA DE SQL INJECTION: login entra direto na consulta.
        // Tente logar com    admin' --    para ver o problema.
        String sql = "SELECT id, nome, login FROM usuario WHERE login = '" + login
                + "' AND senha_hash = '" + gerarHash(senha) + "'";

        try (Connection conexao = Conexao.conectar();
             Statement comando = conexao.createStatement();
             ResultSet resultado = comando.executeQuery(sql)) {

            if (resultado.next()) {
                return mapearUsuario(resultado);
            }
            return null;
        }
    }

    @Override
    public void salvar(String nome, String login, String senha) throws SQLException {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement comando = conexao.prepareStatement(SQL_SALVAR)) {

            comando.setString(1, nome);
            comando.setString(2, login);
            comando.setString(3, gerarHash(senha));
            comando.executeUpdate();
        }
    }

    private Usuario mapearUsuario(ResultSet resultado) throws SQLException {
        Usuario usuario = new Usuario(
                resultado.getString("nome"),
                resultado.getString("login")
        );
        usuario.setCodigo(resultado.getInt("id"));
        return usuario;
    }

    private String gerarHash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponível na JVM", e);
        }
    }
}
