package com.mycompany.atividade12.model;

/**
 * representa a tabela "categoria" do banco de dados
 */
public class Categoria {

    private int id;
    private String nome;
    private String faixa;
    private double pesoMin;
    private double pesoMax;

    public Categoria() {
    }

    public Categoria(int id, String nome, String faixa, double pesoMin, double pesoMax) {
        this.id = id;
        this.nome = nome;
        this.faixa = faixa;
        this.pesoMin = pesoMin;
        this.pesoMax = pesoMax;
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

    public String getFaixa() {
        return faixa;
    }

    public void setFaixa(String faixa) {
        this.faixa = faixa;
    }

    public double getPesoMin() {
        return pesoMin;
    }

    public void setPesoMin(double pesoMin) {
        this.pesoMin = pesoMin;
    }

    public double getPesoMax() {
        return pesoMax;
    }

    public void setPesoMax(double pesoMax) {
        this.pesoMax = pesoMax;
    }

    @Override
    public String toString() {
        return nome;
    }
}
