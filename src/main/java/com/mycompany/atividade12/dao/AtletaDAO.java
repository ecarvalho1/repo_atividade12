package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Atleta;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de acesso a dados (DAO) da tabela "atleta".
 * Todas as operações usam PreparedStatement para evitar SQL Injection.
 */
public class AtletaDAO {

    public void inserir(Atleta atleta) throws SQLException {
        String sql = "INSERT INTO atleta (academia_id, nome, data_nasc, faixa, peso) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, atleta.getAcademiaId());
            stmt.setString(2, atleta.getNome());
            stmt.setDate(3, Date.valueOf(atleta.getDataNasc()));
            stmt.setString(4, atleta.getFaixa());
            stmt.setDouble(5, atleta.getPeso());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Atleta atleta) throws SQLException {
        String sql = "UPDATE atleta SET academia_id = ?, nome = ?, data_nasc = ?, faixa = ?, peso = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, atleta.getAcademiaId());
            stmt.setString(2, atleta.getNome());
            stmt.setDate(3, Date.valueOf(atleta.getDataNasc()));
            stmt.setString(4, atleta.getFaixa());
            stmt.setDouble(5, atleta.getPeso());
            stmt.setInt(6, atleta.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM atleta WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Atleta buscarPorId(int id) throws SQLException {
        String sql = "SELECT a.*, ac.nome AS academia_nome FROM atleta a "
                + "JOIN academia ac ON a.academia_id = ac.id WHERE a.id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAtleta(rs);
                }
            }
        }
        return null;
    }

    public List<Atleta> listarTodos() throws SQLException {
        List<Atleta> lista = new ArrayList<>();
        String sql = "SELECT a.*, ac.nome AS academia_nome FROM atleta a "
                + "JOIN academia ac ON a.academia_id = ac.id ORDER BY a.nome";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearAtleta(rs));
            }
        }
        return lista;
    }

    private Atleta mapearAtleta(ResultSet rs) throws SQLException {
        return new Atleta(
                rs.getInt("id"),
                rs.getInt("academia_id"),
                rs.getString("academia_nome"),
                rs.getString("nome"),
                rs.getDate("data_nasc").toLocalDate(),
                rs.getString("faixa"),
                rs.getDouble("peso")
        );
    }
}
