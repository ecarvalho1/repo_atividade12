package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Campeonato;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * camada de acesso a dados (DAO) da tabela "campeonato"
 * todas as operações usam PreparedStatement para evitar SQL Injection
 */
public class CampeonatoDAO {

    public void inserir(Campeonato campeonato) throws SQLException {
        String sql = "INSERT INTO campeonato (nome, data, local) VALUES (?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, campeonato.getNome());
            stmt.setDate(2, Date.valueOf(campeonato.getData()));
            stmt.setString(3, campeonato.getLocal());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Campeonato campeonato) throws SQLException {
        String sql = "UPDATE campeonato SET nome = ?, data = ?, local = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, campeonato.getNome());
            stmt.setDate(2, Date.valueOf(campeonato.getData()));
            stmt.setString(3, campeonato.getLocal());
            stmt.setInt(4, campeonato.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM campeonato WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Campeonato buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM campeonato WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCampeonato(rs);
                }
            }
        }
        return null;
    }

    public List<Campeonato> listarTodos() throws SQLException {
        List<Campeonato> lista = new ArrayList<>();
        String sql = "SELECT * FROM campeonato ORDER BY data";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCampeonato(rs));
            }
        }
        return lista;
    }

    private Campeonato mapearCampeonato(ResultSet rs) throws SQLException {
        return new Campeonato(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getDate("data").toLocalDate(),
                rs.getString("local")
        );
    }
}
