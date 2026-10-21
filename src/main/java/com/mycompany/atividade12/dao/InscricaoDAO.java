package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Inscricao;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Inscricao.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface InscricaoDAO {

    void inserir(Inscricao inscricao) throws SQLException;

    void atualizar(Inscricao inscricao) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Inscricao buscarPorId(int id) throws SQLException;

    List<Inscricao> listarTodos() throws SQLException;
}
