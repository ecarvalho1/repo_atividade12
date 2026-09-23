package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Inscricao;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * camada de acesso a dados (DAO) da tabela "inscricao"
 * todas as operações usam PreparedStatement para evitar SQL Injection
 */
public class InscricaoDAO {

    private static final String SELECT_BASE =
            "SELECT i.*, a.nome AS atleta_nome, c.nome AS categoria_nome, cp.nome AS campeonato_nome "
                    + "FROM inscricao i "
                    + "JOIN atleta a ON i.atleta_id = a.id "
                    + "JOIN categoria c ON i.categoria_id = c.id "
                    + "JOIN campeonato cp ON i.campeonato_id = cp.id";

    public void inserir(Inscricao inscricao) throws SQLException {
        String sql = "INSERT INTO inscricao (atleta_id, categoria_id, campeonato_id) VALUES (?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, inscricao.getAtletaId());
            stmt.setInt(2, inscricao.getCategoriaId());
            stmt.setInt(3, inscricao.getCampeonatoId());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Inscricao inscricao) throws SQLException {
        String sql = "UPDATE inscricao SET atleta_id = ?, categoria_id = ?, campeonato_id = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, inscricao.getAtletaId());
            stmt.setInt(2, inscricao.getCategoriaId());
            stmt.setInt(3, inscricao.getCampeonatoId());
            stmt.setInt(4, inscricao.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM inscricao WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Inscricao buscarPorId(int id) throws SQLException {
        String sql = SELECT_BASE + " WHERE i.id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearInscricao(rs);
                }
            }
        }
        return null;
    }

    public List<Inscricao> listarTodos() throws SQLException {
        List<Inscricao> lista = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY i.id";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearInscricao(rs));
            }
        }
        return lista;
    }

    private Inscricao mapearInscricao(ResultSet rs) throws SQLException {
        return new Inscricao(
                rs.getInt("id"),
                rs.getInt("atleta_id"),
                rs.getString("atleta_nome"),
                rs.getInt("categoria_id"),
                rs.getString("categoria_nome"),
                rs.getInt("campeonato_id"),
                rs.getString("campeonato_nome")
        );
    }
}
