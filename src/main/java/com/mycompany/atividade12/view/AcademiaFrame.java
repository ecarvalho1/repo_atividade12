package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.AcademiaDAO;
import com.mycompany.atividade12.model.Academia;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

/**
 * tela de CRUD completo (criar, listar, editar, excluir) para Academia
 */
public class AcademiaFrame extends JFrame {

    private final AcademiaDAO dao = new AcademiaDAO();

    private JTextField campoNome;
    private JTextField campoCidade;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = -1;

    public AcademiaFrame() {
        setTitle("Cadastro de Academias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        painelForm.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Cidade:"));
        campoCidade = new JTextField();
        painelForm.add(campoCidade);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Cidade"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.getSelectionModel().addListSelectionListener(e -> preencherFormularioComLinhaSelecionada());
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        btnSalvar.addActionListener(e -> salvar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparFormulario());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            List<Academia> lista = dao.listarTodos();
            for (Academia a : lista) {
                modeloTabela.addRow(new Object[]{a.getId(), a.getNome(), a.getCidade()});
            }
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            idSelecionado = (int) modeloTabela.getValueAt(linha, 0);
            campoNome.setText((String) modeloTabela.getValueAt(linha, 1));
            campoCidade.setText((String) modeloTabela.getValueAt(linha, 2));
        }
    }

    private void salvar() {
         if (!camposPreenchidos()) {
        JOptionPane.showMessageDialog(this, "Preencha nome e cidade antes de salvar");
        return;
         }
        try {
            Academia academia = new Academia();
            academia.setNome(campoNome.getText());
            academia.setCidade(campoCidade.getText());
            dao.inserir(academia);
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
         if (!camposPreenchidos()) {
        JOptionPane.showMessageDialog(this, "Preencha nome e cidade antes de salvar");
        return;
         }
         
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma academia na tabela primeiro.");
            return;
        }
        try {
           Academia academia = new Academia();
           academia.setNome(campoNome.getText());
           academia.setCidade(campoCidade.getText());
           dao.inserir(academia);
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma academia na tabela primeiro.");
            return;
        }
        try {
            dao.excluir(idSelecionado);
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void limparFormulario() {
        idSelecionado = -1;
        campoNome.setText("");
        campoCidade.setText("");
        tabela.clearSelection();
    }

    private boolean camposPreenchidos() {
        return !campoNome.getText().trim().isEmpty() && !campoCidade.getText().trim().isEmpty();
    }
    
    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
