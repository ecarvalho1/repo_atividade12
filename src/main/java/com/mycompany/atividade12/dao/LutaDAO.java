package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Luta;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Luta.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface LutaDAO {

    void inserir(Luta luta) throws SQLException;

    void atualizar(Luta luta) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Luta buscarPorId(int id) throws SQLException;

    List<Luta> listarTodos() throws SQLException;
}
