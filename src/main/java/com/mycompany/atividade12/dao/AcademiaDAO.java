package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Academia;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * camada de acesso a dados (DAO) da tabela "academia"
 * todas as operações usam PreparedStatement para evitar SQL Injection
 */
public class AcademiaDAO {

    public void inserir(Academia academia) throws SQLException {
        String sql = "INSERT INTO academia (nome, cidade) VALUES (?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, academia.getNome());
            stmt.setString(2, academia.getCidade());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Academia academia) throws SQLException {
        String sql = "UPDATE academia SET nome = ?, cidade = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, academia.getNome());
            stmt.setString(2, academia.getCidade());
            stmt.setInt(3, academia.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM academia WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Academia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM academia WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Academia(rs.getInt("id"), rs.getString("nome"), rs.getString("cidade"));
                }
            }
        }
        return null;
    }

    public List<Academia> listarTodos() throws SQLException {
        List<Academia> lista = new ArrayList<>();
        String sql = "SELECT * FROM academia ORDER BY nome";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Academia(rs.getInt("id"), rs.getString("nome"), rs.getString("cidade")));
            }
        }
        return lista;
    }
}
