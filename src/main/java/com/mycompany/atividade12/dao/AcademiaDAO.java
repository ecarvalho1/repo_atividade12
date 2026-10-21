package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Academia;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Academia.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface AcademiaDAO {

    void inserir(Academia academia) throws SQLException;

    void atualizar(Academia academia) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Academia buscarPorId(int id) throws SQLException;

    List<Academia> listarTodos() throws SQLException;
}
