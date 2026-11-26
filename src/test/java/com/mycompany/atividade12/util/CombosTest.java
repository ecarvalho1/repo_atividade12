
package com.mycompany.atividade12.util;

import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombosTest {

    @Test
    void deveCarregarItensSemSelecionarNenhum() {
        JComboBox<String> combo = new JComboBox<>();

        Combos.carregar(
            combo,
            Arrays.asList("Atleta", "Academia", "Categoria")
        );

        assertEquals(3, combo.getItemCount());
        assertEquals(-1, combo.getSelectedIndex());
    }

    @Test
    void deveLimparSelecaoDaCombo() {
        JComboBox<String> combo =
            new JComboBox<>(new String[]{"Atleta", "Academia"});

        combo.setSelectedIndex(0);

        Combos.limpar(combo);

        assertEquals(-1, combo.getSelectedIndex());
    }

    @Test
    void deveMostrarPlaceholderQuandoValorForNulo() {
        JComboBox<String> combo = new JComboBox<>();

        Combos.carregar(combo, Arrays.asList("Atleta"));

        JLabel label = (JLabel) combo.getRenderer()
            .getListCellRendererComponent(
                new JList<String>(), null, -1, false, false
            );

        assertEquals("Selecione...", label.getText());
    }
}
