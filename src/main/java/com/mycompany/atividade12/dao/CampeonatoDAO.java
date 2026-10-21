package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Campeonato;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Campeonato.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface CampeonatoDAO {

    void inserir(Campeonato campeonato) throws SQLException;

    void atualizar(Campeonato campeonato) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Campeonato buscarPorId(int id) throws SQLException;

    List<Campeonato> listarTodos() throws SQLException;
}
