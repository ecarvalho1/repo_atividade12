package com.mycompany.atividade12.controller;

import com.mycompany.atividade12.dao.UsuarioDAO;
import com.mycompany.atividade12.dao.UsuarioDAOjdbc;
import com.mycompany.atividade12.model.Usuario;
import java.sql.SQLException;

public class ControlaUsuario {

    private final UsuarioDAO usuarioDAO = new UsuarioDAOjdbc();

    public Usuario autenticar(String login, String senha) {
        try {
            return usuarioDAO.autenticar(login, senha);
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void salvar(String nome, String login, String senha) {
        try {
            usuarioDAO.salvar(nome, login, senha);
        } catch (SQLException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário.", "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public String validar(String login, String senha) {
        if (login == null || login.trim().isEmpty()) {
            return "Informe o login.";
        }
        if (senha == null || senha.isEmpty()) {
            return "Informe a senha.";
        }
        return null;
    }
}
