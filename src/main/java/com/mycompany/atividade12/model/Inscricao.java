package com.mycompany.atividade12.model;

/**
 * representa a tabela "inscricao" do banco de dados
 * os campos *Nome não existem na tabela, vêm de JOIN só para exibição
 */
public class Inscricao {

    private int id;
    private int atletaId;
    private String atletaNome;
    private int categoriaId;
    private String categoriaNome;
    private int campeonatoId;
    private String campeonatoNome;

    public Inscricao() {
    }

    public Inscricao(int id, int atletaId, String atletaNome, int categoriaId,
                      String categoriaNome, int campeonatoId, String campeonatoNome) {
        this.id = id;
        this.atletaId = atletaId;
        this.atletaNome = atletaNome;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.campeonatoId = campeonatoId;
        this.campeonatoNome = campeonatoNome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAtletaId() {
        return atletaId;
    }

    public void setAtletaId(int atletaId) {
        this.atletaId = atletaId;
    }

    public String getAtletaNome() {
        return atletaNome;
    }

    public void setAtletaNome(String atletaNome) {
        this.atletaNome = atletaNome;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public int getCampeonatoId() {
        return campeonatoId;
    }

    public void setCampeonatoId(int campeonatoId) {
        this.campeonatoId = campeonatoId;
    }

    public String getCampeonatoNome() {
        return campeonatoNome;
    }

    public void setCampeonatoNome(String campeonatoNome) {
        this.campeonatoNome = campeonatoNome;
    }
}
