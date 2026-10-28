package com.mycompany.atividade12.model;

public class Usuario {

    private int codigo;
    private String nome;
    private String login;

    public Usuario(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return nome + " (" + login + ")";
    }
}
