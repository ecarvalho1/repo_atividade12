package com.mycompany.atividade12.model;

import java.time.LocalDate;

/**
 * Representa a tabela "atleta" do banco de dados.
 * O campo academiaNome não existe na tabela, é só um dado auxiliar
 * (vem de um JOIN com "academia") para facilitar a exibição na tela.
 */
public class Atleta {

    private int id;
    private int academiaId;
    private String academiaNome;
    private String nome;
    private LocalDate dataNasc;
    private String faixa;
    private double peso;

    public Atleta() {
    }

    public Atleta(int id, int academiaId, String academiaNome, String nome,
                  LocalDate dataNasc, String faixa, double peso) {
        this.id = id;
        this.academiaId = academiaId;
        this.academiaNome = academiaNome;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.faixa = faixa;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcademiaId() {
        return academiaId;
    }

    public void setAcademiaId(int academiaId) {
        this.academiaId = academiaId;
    }

    public String getAcademiaNome() {
        return academiaNome;
    }

    public void setAcademiaNome(String academiaNome) {
        this.academiaNome = academiaNome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return nome;
    }
}
