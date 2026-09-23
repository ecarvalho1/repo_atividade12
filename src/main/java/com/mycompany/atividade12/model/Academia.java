package com.mycompany.atividade12.model;

/**
 * representa a tabela "academia" do banco de dados
 */
public class Academia {

    private int id;
    private String nome;
    private String cidade;

    public Academia() {
    }

    public Academia(int id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}
