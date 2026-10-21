package com.mycompany.atividade12.util;

import java.sql.SQLException;

/**
 * Converte erros do PostgreSQL (pelo SQLState) em mensagens que o usuário entende.
 */
public class ErrosBanco {

    public static String traduzir(SQLException ex) {
        String estado = ex.getSQLState() == null ? "" : ex.getSQLState();
        switch (estado) {
            case "23503":
                return "Operação bloqueada: o registro está ligado a outros registros.\n"
                        + "Exemplo: uma academia com atletas, ou um atleta com inscrições ou lutas, não pode ser excluído.";
            case "23505":
                return "Já existe um registro com esses dados.";
            case "22003":
                return "Algum valor numérico está fora do limite permitido.";
            case "08001":
            case "08004":
            case "08006":
            case "28P01":
            case "3D000":
                return "Não foi possível conectar ao banco de dados.\n"
                        + "Verifique se o PostgreSQL está rodando e se o nome do banco, o usuário e a senha estão corretos.";
            default:
                return "Erro: " + ex.getMessage();
        }
    }
}
