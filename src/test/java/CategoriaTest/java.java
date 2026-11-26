
package com.mycompany.atividade12.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void deveCriarCategoriaComConstrutorCompleto() {
        Categoria categoria = new Categoria(
            1, "Adulto", "Azul", 60.0, 70.0
        );

        assertEquals(1, categoria.getId());
        assertEquals("Adulto", categoria.getNome());
        assertEquals("Azul", categoria.getFaixa());
        assertEquals(60.0, categoria.getPesoMin());
        assertEquals(70.0, categoria.getPesoMax());
    }

    @Test
    void deveCriarCategoriaComConstrutorVazio() {
        Categoria categoria = new Categoria();

        assertEquals(0, categoria.getId());
        assertNull(categoria.getNome());
        assertNull(categoria.getFaixa());
        assertEquals(0.0, categoria.getPesoMin());
        assertEquals(0.0, categoria.getPesoMax());
    }

    @Test
    void deveAlterarDadosDaCategoria() {
        Categoria categoria = new Categoria();

        categoria.setId(2);
        categoria.setNome("Infantil");
        categoria.setFaixa("Branca");
        categoria.setPesoMin(30.0);
        categoria.setPesoMax(40.0);

        assertEquals(2, categoria.getId());
        assertEquals("Infantil", categoria.getNome());
        assertEquals("Branca", categoria.getFaixa());
        assertEquals(30.0, categoria.getPesoMin());
        assertEquals(40.0, categoria.getPesoMax());
    }

    @Test
    void deveRetornarNomeNoToString() {
        Categoria categoria = new Categoria(
            1, "Adulto", "Azul", 60.0, 70.0
        );

        assertEquals("Adulto", categoria.toString());
    }
}
