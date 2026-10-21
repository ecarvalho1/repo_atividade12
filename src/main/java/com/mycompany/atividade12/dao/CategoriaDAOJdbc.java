package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Categoria;
import com.mycompany.atividade12.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação JDBC de {@link CategoriaDAO} para a tabela "categoria".
 * Todas as operações usam PreparedStatement para evitar SQL Injection.
 */
public class CategoriaDAOJdbc implements CategoriaDAO {

    @Override
    public void inserir(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categoria (nome, faixa, peso_min, peso_max) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getFaixa());
            stmt.setDouble(3, categoria.getPesoMin());
            stmt.setDouble(4, categoria.getPesoMax());
            stmt.executeUpdate();
        }
    }

    @Override
    public void atualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categoria SET nome = ?, faixa = ?, peso_min = ?, peso_max = ? WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getFaixa());
            stmt.setDouble(3, categoria.getPesoMin());
            stmt.setDouble(4, categoria.getPesoMax());
            stmt.setInt(5, categoria.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Categoria buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearCategoria(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Categoria> listarTodos() throws SQLException {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY nome";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearCategoria(rs));
            }
        }
        return lista;
    }

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        return new Categoria(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("faixa"),
                rs.getDouble("peso_min"),
                rs.getDouble("peso_max")
        );
    }
}
