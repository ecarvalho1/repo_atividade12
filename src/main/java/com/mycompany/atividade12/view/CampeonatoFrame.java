package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.CampeonatoDAO;
import com.mycompany.atividade12.model.Campeonato;

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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * tela de CRUD completo para Campeonato
 * padrão da data: AAAA-MM-DD
 */
public class CampeonatoFrame extends JFrame {

    private final CampeonatoDAO dao = new CampeonatoDAO();

    private JTextField campoNome;
    private JTextField campoData;
    private JTextField campoLocal;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private int idSelecionado = -1;

    public CampeonatoFrame() {
        setTitle("Cadastro de Campeonatos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 420);
        setLocationRelativeTo(null);
        montarTela();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        painelForm.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Data (AAAA-MM-DD):"));
        campoData = new JTextField();
        painelForm.add(campoData);
        painelForm.add(new JLabel("Local:"));
        campoLocal = new JTextField();
        painelForm.add(campoLocal);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Data", "Local"}, 0) {
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
            List<Campeonato> lista = dao.listarTodos();
            for (Campeonato campeonato : lista) {
                modeloTabela.addRow(new Object[]{
                        campeonato.getId(), campeonato.getNome(),
                        campeonato.getData(), campeonato.getLocal()
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
            campoData.setText(String.valueOf(modeloTabela.getValueAt(linha, 2)));
            campoLocal.setText((String) modeloTabela.getValueAt(linha, 3));
        }
    }

    private boolean camposPreenchidos() {
        return !campoNome.getText().trim().isEmpty()
                && !campoData.getText().trim().isEmpty()
                && !campoLocal.getText().trim().isEmpty();
    }

    private void salvar() {
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }
        try {
            Campeonato campeonato = new Campeonato();
            campeonato.setNome(campoNome.getText());
            campeonato.setData(LocalDate.parse(campoData.getText().trim()));
            campeonato.setLocal(campoLocal.getText());
            dao.inserir(campeonato);
            carregarTabela();
            limparFormulario();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato AAAA-MM-DD (ex: 2026-11-20).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um campeonato na tabela primeiro.");
            return;
        }
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de atualizar.");
            return;
        }
        try {
            Campeonato campeonato = new Campeonato();
            campeonato.setId(idSelecionado);
            campeonato.setNome(campoNome.getText());
            campeonato.setData(LocalDate.parse(campoData.getText().trim()));
            campeonato.setLocal(campoLocal.getText());
            dao.atualizar(campeonato);
            carregarTabela();
            limparFormulario();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato AAAA-MM-DD (ex: 2026-11-20).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um campeonato na tabela primeiro.");
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
        campoData.setText("");
        campoLocal.setText("");
        tabela.clearSelection();
    }

    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
