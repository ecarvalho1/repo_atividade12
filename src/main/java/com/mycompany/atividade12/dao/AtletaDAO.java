package com.mycompany.atividade12.dao;

import com.mycompany.atividade12.model.Atleta;

import java.sql.SQLException;
import java.util.List;

/**
 * Contrato de acesso a dados da entidade Atleta.
 * O resto do sistema depende só desta interface, nunca de JDBC.
 */
public interface AtletaDAO {

    void inserir(Atleta atleta) throws SQLException;

    void atualizar(Atleta atleta) throws SQLException;

    void excluir(int id) throws SQLException;

    /** @return a entidade ou null se não existir */
    Atleta buscarPorId(int id) throws SQLException;

    List<Atleta> listarTodos() throws SQLException;
}
