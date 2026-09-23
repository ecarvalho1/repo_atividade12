package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Luta;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * camada de acesso a dados (DAO) da tabela "luta"
 * todas as operações usam PreparedStatement para evitar SQL Injection
 */
public class LutaDAO {

    private static final String SELECT_BASE =
            "SELECT l.*, cp.nome AS campeonato_nome, c.nome AS categoria_nome, "
                    + "at1.nome AS atleta_nome, at2.nome AS atleta2_nome "
                    + "FROM luta l "
                    + "JOIN campeonato cp ON l.campeonato_id = cp.id "
                    + "JOIN categoria c ON l.categoria_id = c.id "
                    + "JOIN atleta at1 ON l.atleta_id = at1.id "
                    + "JOIN atleta at2 ON l.atleta2_id = at2.id";

    public void inserir(Luta luta) throws SQLException {
        String sql = "INSERT INTO luta (campeonato_id, categoria_id, atleta_id, atleta2_id, resultado) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, luta.getCampeonatoId());
            stmt.setInt(2, luta.getCategoriaId());
            stmt.setInt(3, luta.getAtletaId());
            stmt.setInt(4, luta.getAtleta2Id());
            stmt.setString(5, luta.getResultado());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Luta luta) throws SQLException {
        String sql = "UPDATE luta SET campeonato_id = ?, categoria_id = ?, atleta_id = ?, "
                + "atleta2_id = ?, resultado = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, luta.getCampeonatoId());
            stmt.setInt(2, luta.getCategoriaId());
            stmt.setInt(3, luta.getAtletaId());
            stmt.setInt(4, luta.getAtleta2Id());
            stmt.setString(5, luta.getResultado());
            stmt.setInt(6, luta.getId());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM luta WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Luta buscarPorId(int id) throws SQLException {
        String sql = SELECT_BASE + " WHERE l.id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearLuta(rs);
                }
            }
        }
        return null;
    }

    public List<Luta> listarTodos() throws SQLException {
        List<Luta> lista = new ArrayList<>();
        String sql = SELECT_BASE + " ORDER BY l.id";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearLuta(rs));
            }
        }
        return lista;
    }

    private Luta mapearLuta(ResultSet rs) throws SQLException {
        return new Luta(
                rs.getInt("id"),
                rs.getInt("campeonato_id"),
                rs.getString("campeonato_nome"),
                rs.getInt("categoria_id"),
                rs.getString("categoria_nome"),
                rs.getInt("atleta_id"),
                rs.getString("atleta_nome"),
                rs.getInt("atleta2_id"),
                rs.getString("atleta2_nome"),
                rs.getString("resultado")
        );
    }
}
