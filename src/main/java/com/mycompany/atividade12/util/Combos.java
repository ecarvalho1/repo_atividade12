package com.mycompany.atividade12.util;

import java.awt.Component;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * Utilitário para as combo boxes das telas: elas sempre abrem sem nenhuma
 * opção selecionada e mostram o texto "Selecione..." enquanto estão vazias.
 */
public class Combos {

    private static final String TEXTO_VAZIO = "Selecione...";

    private static class RendererComPlaceholder extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value == null) {
                setText(TEXTO_VAZIO);
            }
            return this;
        }
    }

    /** Troca os itens da combo e deixa ela sem nada selecionado. */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void carregar(JComboBox combo, List<?> itens) {
        combo.setRenderer(new RendererComPlaceholder());
        combo.setModel(new DefaultComboBoxModel(itens.toArray()));
        combo.setSelectedIndex(-1);
    }

    /** Desmarca a opção atual*/
    public static void limpar(JComboBox<?> combo) {
        combo.setSelectedIndex(-1);
    }
}
