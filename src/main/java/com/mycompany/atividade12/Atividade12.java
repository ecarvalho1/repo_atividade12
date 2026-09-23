package com.mycompany.atividade12;

import com.mycompany.atividade12.view.CategoriaFrame;

import javax.swing.SwingUtilities;

public class Atividade12 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoriaFrame().setVisible(true));
    }
}
