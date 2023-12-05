package View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.ClientesControl;
import Controller.ClientesDAO;
import Model.Clientes;

public class ClientesPainel extends JPanel {
    private JButton cadastrar, apagar, editar;
    private JTextField nomeField, cpfField, telefoneField, emailField;
    private List<Clientes> clientes;
    private JTable table;
    private DefaultTableModel tableModel;
    private int linhaSelecionada = -1;

    public ClientesPainel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Cadastro Clientes"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Nome"));
        nomeField = new JTextField(20);
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("CPF"));
        cpfField = new JTextField(20);
        inputPanel.add(cpfField);
        inputPanel.add(new JLabel("Telefone"));
        telefoneField = new JTextField(20);
        inputPanel.add(telefoneField);
        inputPanel.add(new JLabel("E-mail"));
        emailField = new JTextField(20);
        inputPanel.add(emailField);
        add(inputPanel);

        JPanel botoes = new JPanel();
        botoes.add(cadastrar = new JButton("Cadastrar"));
        botoes.add(editar = new JButton("Editar"));
        botoes.add(apagar = new JButton("Apagar"));
        add(botoes);

        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "CPF", "Telefone", "E-mail" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new ClientesDAO().criarTabela();
        atualizarTabela();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    nomeField.setText((String) table.getValueAt(linhaSelecionada, 0));
                    cpfField.setText((String) table.getValueAt(linhaSelecionada, 1));
                    telefoneField.setText((String) table.getValueAt(linhaSelecionada, 2));
                    emailField.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        ClientesControl operacoes = new ClientesControl(clientes, tableModel, table);

        cadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.cadastrar(nomeField.getText(), cpfField.getText(), telefoneField.getText(), emailField.getText());
                limparCampos();
            }
        });

        editar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.atualizar(nomeField.getText(), cpfField.getText(), telefoneField.getText(),
                        emailField.getText());
                limparCampos();
            }
        });

        apagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operacoes.apagar(cpfField.getText());
                limparCampos();
            }
        });
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesDAO().listarTodos();
        for (Clientes cliente : clientes) {
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(), cliente.getTelefone(),
                    cliente.getEmail() });
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        emailField.setText("");
    }
}
