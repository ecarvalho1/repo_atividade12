package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.AtletaDAO;
import com.mycompany.atividade12.dao.CampeonatoDAO;
import com.mycompany.atividade12.dao.CategoriaDAO;
import com.mycompany.atividade12.dao.LutaDAO;
import com.mycompany.atividade12.model.Atleta;
import com.mycompany.atividade12.model.Campeonato;
import com.mycompany.atividade12.model.Categoria;
import com.mycompany.atividade12.model.Luta;

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
import java.util.List;

/**
 * tela de CRUD completo para Luta
 * registra o confronto entre dois atletas dentro de um campeonato e categoria
 */
public class LutaFrame extends JFrame {

    private final LutaDAO dao = new LutaDAO();
    private final CampeonatoDAO campeonatoDao = new CampeonatoDAO();
    private final CategoriaDAO categoriaDao = new CategoriaDAO();
    private final AtletaDAO atletaDao = new AtletaDAO();

    private JComboBox<Campeonato> comboCampeonato;
    private JComboBox<Categoria> comboCategoria;
    private JComboBox<Atleta> comboAtleta1;
    private JComboBox<Atleta> comboAtleta2;
    private JTextField campoResultado;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private List<Luta> listaAtual;
    private int idSelecionado = -1;

    public LutaFrame() {
        setTitle("Cadastro de Lutas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 450);
        setLocationRelativeTo(null);
        montarTela();
        carregarCombos();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.add(new JLabel("Campeonato:"));
        comboCampeonato = new JComboBox<>();
        painelForm.add(comboCampeonato);
        painelForm.add(new JLabel("Categoria:"));
        comboCategoria = new JComboBox<>();
        painelForm.add(comboCategoria);
        painelForm.add(new JLabel("Atleta 1:"));
        comboAtleta1 = new JComboBox<>();
        painelForm.add(comboAtleta1);
        painelForm.add(new JLabel("Atleta 2:"));
        comboAtleta2 = new JComboBox<>();
        painelForm.add(comboAtleta2);
        painelForm.add(new JLabel("Resultado:"));
        campoResultado = new JTextField();
        painelForm.add(campoResultado);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Campeonato", "Categoria", "Atleta 1", "Atleta 2", "Resultado"}, 0) {
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

    private void carregarCombos() {
        try {
            comboCampeonato.setModel(new DefaultComboBoxModel<>(
                    campeonatoDao.listarTodos().toArray(new Campeonato[0])));
            comboCategoria.setModel(new DefaultComboBoxModel<>(
                    categoriaDao.listarTodos().toArray(new Categoria[0])));
            Atleta[] atletas = atletaDao.listarTodos().toArray(new Atleta[0]);
            comboAtleta1.setModel(new DefaultComboBoxModel<>(atletas));
            comboAtleta2.setModel(new DefaultComboBoxModel<>(atletas));
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            listaAtual = dao.listarTodos();
            for (Luta luta : listaAtual) {
                modeloTabela.addRow(new Object[]{
                        luta.getId(), luta.getCampeonatoNome(), luta.getCategoriaNome(),
                        luta.getAtletaNome(), luta.getAtleta2Nome(), luta.getResultado()
                });
            }
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0 && listaAtual != null) {
            Luta luta = listaAtual.get(linha);
            idSelecionado = luta.getId();
            selecionarCampeonatoNoCombo(luta.getCampeonatoId());
            selecionarCategoriaNoCombo(luta.getCategoriaId());
            selecionarAtletaNoCombo(comboAtleta1, luta.getAtletaId());
            selecionarAtletaNoCombo(comboAtleta2, luta.getAtleta2Id());
            campoResultado.setText(luta.getResultado());
        }
    }

    private void selecionarCampeonatoNoCombo(int id) {
        for (int i = 0; i < comboCampeonato.getItemCount(); i++) {
            if (comboCampeonato.getItemAt(i).getId() == id) {
                comboCampeonato.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarCategoriaNoCombo(int id) {
        for (int i = 0; i < comboCategoria.getItemCount(); i++) {
            if (comboCategoria.getItemAt(i).getId() == id) {
                comboCategoria.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selecionarAtletaNoCombo(JComboBox<Atleta> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getId() == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private boolean camposPreenchidos() {
        return comboCampeonato.getSelectedItem() != null
                && comboCategoria.getSelectedItem() != null
                && comboAtleta1.getSelectedItem() != null
                && comboAtleta2.getSelectedItem() != null
                && !campoResultado.getText().trim().isEmpty();
    }

    private boolean atletasSaoDiferentes() {
        Atleta a1 = (Atleta) comboAtleta1.getSelectedItem();
        Atleta a2 = (Atleta) comboAtleta2.getSelectedItem();
        return a1 != null && a2 != null && a1.getId() != a2.getId();
    }

    private void salvar() {
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar.");
            return;
        }
        if (!atletasSaoDiferentes()) {
            JOptionPane.showMessageDialog(this, "Atleta 1 e Atleta 2 precisam ser diferentes.");
            return;
        }
        try {
            dao.inserir(montarLutaDoFormulario());
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma luta na tabela primeiro.");
            return;
        }
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de atualizar.");
            return;
        }
        if (!atletasSaoDiferentes()) {
            JOptionPane.showMessageDialog(this, "Atleta 1 e Atleta 2 precisam ser diferentes.");
            return;
        }
        try {
            Luta luta = montarLutaDoFormulario();
            luta.setId(idSelecionado);
            dao.atualizar(luta);
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private Luta montarLutaDoFormulario() {
        Luta luta = new Luta();
        luta.setCampeonatoId(((Campeonato) comboCampeonato.getSelectedItem()).getId());
        luta.setCategoriaId(((Categoria) comboCategoria.getSelectedItem()).getId());
        luta.setAtletaId(((Atleta) comboAtleta1.getSelectedItem()).getId());
        luta.setAtleta2Id(((Atleta) comboAtleta2.getSelectedItem()).getId());
        luta.setResultado(campoResultado.getText());
        return luta;
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma luta na tabela primeiro.");
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
        if (comboCampeonato.getItemCount() > 0) comboCampeonato.setSelectedIndex(0);
        if (comboCategoria.getItemCount() > 0) comboCategoria.setSelectedIndex(0);
        if (comboAtleta1.getItemCount() > 0) comboAtleta1.setSelectedIndex(0);
        if (comboAtleta2.getItemCount() > 0) comboAtleta2.setSelectedIndex(0);
        campoResultado.setText("");
        tabela.clearSelection();
    }

    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
