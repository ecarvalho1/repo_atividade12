package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.AcademiaDAO;
import com.mycompany.atividade12.dao.AtletaDAO;
import com.mycompany.atividade12.model.Academia;
import com.mycompany.atividade12.model.Atleta;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Tela de CRUD completo (criar, listar, editar, excluir) para Atleta.
 * A academia é escolhida por um combobox, que carrega as academias já cadastradas.
 */
public class AtletaFrame extends JFrame {

    private final AtletaDAO dao = new AtletaDAO();
    private final AcademiaDAO academiaDao = new AcademiaDAO();

    private JTextField campoNome;
    private JTextField campoDataNasc;
    private JTextField campoFaixa;
    private JTextField campoPeso;
    private JComboBox<Academia> comboAcademia;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private List<Atleta> listaAtual;
    private int idSelecionado = -1;

    public AtletaFrame() {
        setTitle("Cadastro de Atletas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        montarTela();
        carregarAcademias();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.add(new JLabel("Nome:"));
        campoNome = new JTextField();
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Data de nascimento (AAAA-MM-DD):"));
        campoDataNasc = new JTextField();
        painelForm.add(campoDataNasc);
        painelForm.add(new JLabel("Faixa:"));
        campoFaixa = new JTextField();
        painelForm.add(campoFaixa);
        painelForm.add(new JLabel("Peso (kg):"));
        campoPeso = new JTextField();
        painelForm.add(campoPeso);
        painelForm.add(new JLabel("Academia:"));
        comboAcademia = new JComboBox<>();
        painelForm.add(comboAcademia);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Nascimento", "Faixa", "Peso", "Academia"}, 0) {
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

    private void carregarAcademias() {
        try {
            List<Academia> academias = academiaDao.listarTodos();
            comboAcademia.setModel(new DefaultComboBoxModel<>(academias.toArray(new Academia[0])));
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            listaAtual = dao.listarTodos();
            for (Atleta atleta : listaAtual) {
                modeloTabela.addRow(new Object[]{
                        atleta.getId(), atleta.getNome(), atleta.getDataNasc(),
                        atleta.getFaixa(), atleta.getPeso(), atleta.getAcademiaNome()
                });
            }
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0 && listaAtual != null) {
            Atleta atleta = listaAtual.get(linha);
            idSelecionado = atleta.getId();
            campoNome.setText(atleta.getNome());
            campoDataNasc.setText(atleta.getDataNasc().toString());
            campoFaixa.setText(atleta.getFaixa());
            campoPeso.setText(String.valueOf(atleta.getPeso()));
            selecionarAcademiaNoCombo(atleta.getAcademiaId());
        }
    }

    private void selecionarAcademiaNoCombo(int academiaId) {
        for (int i = 0; i < comboAcademia.getItemCount(); i++) {
            if (comboAcademia.getItemAt(i).getId() == academiaId) {
                comboAcademia.setSelectedIndex(i);
                return;
            }
        }
    }

    private boolean camposPreenchidos() {
        return !campoNome.getText().trim().isEmpty()
                && !campoDataNasc.getText().trim().isEmpty()
                && !campoFaixa.getText().trim().isEmpty()
                && !campoPeso.getText().trim().isEmpty()
                && comboAcademia.getSelectedItem() != null;
    }

    private void salvar() {
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos e escolha uma academia antes de salvar.");
            return;
        }
        try {
            Atleta atleta = montarAtletaDoFormulario();
            dao.inserir(atleta);
            carregarTabela();
            limparFormulario();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato AAAA-MM-DD (ex: 2009-04-15).");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso deve ser um número (ex: 70.5).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atleta na tabela primeiro.");
            return;
        }
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos e escolha uma academia antes de atualizar.");
            return;
        }
        try {
            Atleta atleta = montarAtletaDoFormulario();
            atleta.setId(idSelecionado);
            dao.atualizar(atleta);
            carregarTabela();
            limparFormulario();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato AAAA-MM-DD (ex: 2009-04-15).");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Peso deve ser um número (ex: 70.5).");
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private Atleta montarAtletaDoFormulario() {
        Atleta atleta = new Atleta();
        atleta.setNome(campoNome.getText());
        atleta.setDataNasc(LocalDate.parse(campoDataNasc.getText().trim()));
        atleta.setFaixa(campoFaixa.getText());
        atleta.setPeso(Double.parseDouble(campoPeso.getText().trim()));
        Academia academiaEscolhida = (Academia) comboAcademia.getSelectedItem();
        atleta.setAcademiaId(academiaEscolhida.getId());
        return atleta;
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um atleta na tabela primeiro.");
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
        campoDataNasc.setText("");
        campoFaixa.setText("");
        campoPeso.setText("");
        if (comboAcademia.getItemCount() > 0) {
            comboAcademia.setSelectedIndex(0);
        }
        tabela.clearSelection();
    }

    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
