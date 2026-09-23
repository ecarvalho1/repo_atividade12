package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.CategoriaDAO;
import com.mycompany.atividade12.model.Categoria;

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
 * tela de CRUD completo para categoria
 */
public class CategoriaFrame extends JFrame {

    private final CategoriaDAO dao = new CategoriaDAO();

    private JTextField campoNome;
    private JTextField campoFaixa;
    private JTextField campoPesoMin;
    private JTextField campoPesoMax;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = -1;

    public CategoriaFrame() {
        setTitle("Cadastro de Categorias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 420);
        setLocationRelativeTo(null);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        painelForm.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Faixa:"));
        campoFaixa = new JTextField();
        painelForm.add(campoFaixa);
        painelForm.add(new JLabel("Peso mínimo (kg):"));
        campoPesoMin = new JTextField();
        painelForm.add(campoPesoMin);
        painelForm.add(new JLabel("Peso máximo (kg):"));
        campoPesoMax = new JTextField();
        painelForm.add(campoPesoMax);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Faixa", "Peso mín.", "Peso máx."}, 0) {
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
            List<Categoria> lista = dao.listarTodos();
            for (Categoria categoria : lista) {
                modeloTabela.addRow(new Object[]{
                        categoria.getId(), categoria.getNome(), categoria.getFaixa(),
                        categoria.getPesoMin(), categoria.getPesoMax()
                });
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
            campoFaixa.setText((String) modeloTabela.getValueAt(linha, 2));
            campoPesoMin.setText(String.valueOf(modeloTabela.getValueAt(linha, 3)));
            campoPesoMax.setText(String.valueOf(modeloTabela.getValueAt(linha, 4)));
        }
    }

    private boolean camposPreenchidos() {
        return !campoNome.getText().trim().isEmpty()
                && !campoFaixa.getText().trim().isEmpty()
                && !campoPesoMin.getText().trim().isEmpty()
                && !campoPesoMax.getText().trim().isEmpty();
    }

    private void salvar() {
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }
        try {
            Categoria categoria = new Categoria();
            categoria.setNome(campoNome.getText());
            categoria.setFaixa(campoFaixa.getText());
            categoria.setPesoMin(Double.parseDouble(campoPesoMin.getText()));
            categoria.setPesoMax(Double.parseDouble(campoPesoMax.getText()));
            dao.inserir(categoria);
            carregarTabela();
            limparFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso mínimo e máximo devem ser números (ex: 76.5).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela primeiro.");
            return;
        }
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de atualizar.");
            return;
        }
        try {
            Categoria categoria = new Categoria();
            categoria.setId(idSelecionado);
            categoria.setNome(campoNome.getText());
            categoria.setFaixa(campoFaixa.getText());
            categoria.setPesoMin(Double.parseDouble(campoPesoMin.getText()));
            categoria.setPesoMax(Double.parseDouble(campoPesoMax.getText()));
            dao.atualizar(categoria);
            carregarTabela();
            limparFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso mínimo e máximo devem ser números (ex: 76.5).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela primeiro.");
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
        campoFaixa.setText("");
        campoPesoMin.setText("");
        campoPesoMax.setText("");
        tabela.clearSelection();
    }

    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
