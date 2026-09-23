package com.mycompany.atividade12.model;

import java.time.LocalDate;

/**
 * representa a tabela "campeonato" do banco de dados
 */
public class Campeonato {

    private int id;
    private String nome;
    private LocalDate data;
    private String local;

    public Campeonato() {
    }

    public Campeonato(int id, String nome, LocalDate data, String local) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return nome;
    }
}
