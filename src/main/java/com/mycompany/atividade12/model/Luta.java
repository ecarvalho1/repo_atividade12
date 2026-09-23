package com.mycompany.atividade12.model;

/**
 * representa a tabela "luta" do banco de dados.
 * registra o confronto entre dois atletas (atletaId e atleta2Id) dentro
 * de uma categoria e um campeonato
 */
public class Luta {

    private int id;
    private int campeonatoId;
    private String campeonatoNome;
    private int categoriaId;
    private String categoriaNome;
    private int atletaId;
    private String atletaNome;
    private int atleta2Id;
    private String atleta2Nome;
    private String resultado;

    public Luta() {
    }

    public Luta(int id, int campeonatoId, String campeonatoNome, int categoriaId, String categoriaNome,
                int atletaId, String atletaNome, int atleta2Id, String atleta2Nome, String resultado) {
        this.id = id;
        this.campeonatoId = campeonatoId;
        this.campeonatoNome = campeonatoNome;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.atletaId = atletaId;
        this.atletaNome = atletaNome;
        this.atleta2Id = atleta2Id;
        this.atleta2Nome = atleta2Nome;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAtleta2Id() {
        return atleta2Id;
    }

    public void setAtleta2Id(int atleta2Id) {
        this.atleta2Id = atleta2Id;
    }

    public String getAtleta2Nome() {
        return atleta2Nome;
    }

    public void setAtleta2Nome(String atleta2Nome) {
        this.atleta2Nome = atleta2Nome;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
