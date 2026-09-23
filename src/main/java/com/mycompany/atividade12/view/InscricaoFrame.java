package com.mycompany.atividade12.view;

import com.mycompany.atividade12.dao.AtletaDAO;
import com.mycompany.atividade12.dao.CampeonatoDAO;
import com.mycompany.atividade12.dao.CategoriaDAO;
import com.mycompany.atividade12.dao.InscricaoDAO;
import com.mycompany.atividade12.model.Atleta;
import com.mycompany.atividade12.model.Campeonato;
import com.mycompany.atividade12.model.Categoria;
import com.mycompany.atividade12.model.Inscricao;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

/**
 * tela de CRUD completo para Inscricao
 */
public class InscricaoFrame extends JFrame {

    private final InscricaoDAO dao = new InscricaoDAO();
    private final AtletaDAO atletaDao = new AtletaDAO();
    private final CategoriaDAO categoriaDao = new CategoriaDAO();
    private final CampeonatoDAO campeonatoDao = new CampeonatoDAO();

    private JComboBox<Atleta> comboAtleta;
    private JComboBox<Categoria> comboCategoria;
    private JComboBox<Campeonato> comboCampeonato;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private List<Inscricao> listaAtual;
    private int idSelecionado = -1;

    public InscricaoFrame() {
        setTitle("Inscrições de Atletas em Categorias");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 420);
        setLocationRelativeTo(null);
        montarTela();
        carregarCombos();
        carregarTabela();
    }

    private void montarTela() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        painelForm.add(new JLabel("Atleta:"));
        comboAtleta = new JComboBox<>();
        painelForm.add(comboAtleta);
        painelForm.add(new JLabel("Categoria:"));
        comboCategoria = new JComboBox<>();
        painelForm.add(comboCategoria);
        painelForm.add(new JLabel("Campeonato:"));
        comboCampeonato = new JComboBox<>();
        painelForm.add(comboCampeonato);
        add(painelForm, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[]{"ID", "Atleta", "Categoria", "Campeonato"}, 0) {
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
            comboAtleta.setModel(new DefaultComboBoxModel<>(
                    atletaDao.listarTodos().toArray(new Atleta[0])));
            comboCategoria.setModel(new DefaultComboBoxModel<>(
                    categoriaDao.listarTodos().toArray(new Categoria[0])));
            comboCampeonato.setModel(new DefaultComboBoxModel<>(
                    campeonatoDao.listarTodos().toArray(new Campeonato[0])));
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        try {
            listaAtual = dao.listarTodos();
            for (Inscricao inscricao : listaAtual) {
                modeloTabela.addRow(new Object[]{
                        inscricao.getId(), inscricao.getAtletaNome(),
                        inscricao.getCategoriaNome(), inscricao.getCampeonatoNome()
                });
            }
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0 && listaAtual != null) {
            Inscricao inscricao = listaAtual.get(linha);
            idSelecionado = inscricao.getId();
            selecionarAtletaNoCombo(inscricao.getAtletaId());
            selecionarCategoriaNoCombo(inscricao.getCategoriaId());
            selecionarCampeonatoNoCombo(inscricao.getCampeonatoId());
        }
    }

    private void selecionarAtletaNoCombo(int id) {
        for (int i = 0; i < comboAtleta.getItemCount(); i++) {
            if (comboAtleta.getItemAt(i).getId() == id) {
                comboAtleta.setSelectedIndex(i);
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

    private void selecionarCampeonatoNoCombo(int id) {
        for (int i = 0; i < comboCampeonato.getItemCount(); i++) {
            if (comboCampeonato.getItemAt(i).getId() == id) {
                comboCampeonato.setSelectedIndex(i);
                return;
            }
        }
    }

    private boolean camposPreenchidos() {
        return comboAtleta.getSelectedItem() != null
                && comboCategoria.getSelectedItem() != null
                && comboCampeonato.getSelectedItem() != null;
    }

    private void salvar() {
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Escolha atleta, categoria e campeonato antes de salvar.");
            return;
        }
        try {
            dao.inserir(montarInscricaoDoFormulario());
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private void atualizar() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma inscrição na tabela primeiro.");
            return;
        }
        if (!camposPreenchidos()) {
            JOptionPane.showMessageDialog(this, "Escolha atleta, categoria e campeonato antes de atualizar.");
            return;
        }
        try {
            Inscricao inscricao = montarInscricaoDoFormulario();
            inscricao.setId(idSelecionado);
            dao.atualizar(inscricao);
            carregarTabela();
            limparFormulario();
        } catch (SQLException ex) {
            mostrarErro(ex);
        }
    }

    private Inscricao montarInscricaoDoFormulario() {
        Inscricao inscricao = new Inscricao();
        inscricao.setAtletaId(((Atleta) comboAtleta.getSelectedItem()).getId());
        inscricao.setCategoriaId(((Categoria) comboCategoria.getSelectedItem()).getId());
        inscricao.setCampeonatoId(((Campeonato) comboCampeonato.getSelectedItem()).getId());
        return inscricao;
    }

    private void excluir() {
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma inscrição na tabela primeiro.");
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
        if (comboAtleta.getItemCount() > 0) comboAtleta.setSelectedIndex(0);
        if (comboCategoria.getItemCount() > 0) comboCategoria.setSelectedIndex(0);
        if (comboCampeonato.getItemCount() > 0) comboCampeonato.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private void mostrarErro(Exception ex) {
        JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
