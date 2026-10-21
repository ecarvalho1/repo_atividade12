package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Categoria;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Categoria.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface CategoriaDAO {

    void inserir(Categoria categoria) throws SQLException;

    void atualizar(Categoria categoria) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Categoria buscarPorId(int id) throws SQLException;

    List<Categoria> listarTodos() throws SQLException;
}
