package View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.CarrosControl;
import Controller.CarrosDAO;
import Controller.ClientesControl;
import Controller.ClientesDAO;
import Controller.VendasDAO;
import Model.Carros;
import Model.Clientes;
import Model.Vendas;

public class VendasPainel extends JPanel {
    // Atributos(componentes)
    private JButton registrarVenda;
    private JTextField dataVendaField;
    private JComboBox<String> carrosComboBox;
    private JComboBox<String> clientesComboBox;
    private List<Vendas> vendas;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Carros> carros;
    private List<Clientes> clientes;

    

    // Construtor(GUI-JPanel)
    public VendasPainel() {
        super();

        // Entrada de dados
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Registro de Vendas"));
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Data da Venda"));
        dataVendaField = new JTextField(20);
        inputPanel.add(dataVendaField);

        // Carregar carros e clientes do banco de dados
        carrosComboBox = new JComboBox<>();
        clientesComboBox = new JComboBox<>();
        this.attCombo();

        inputPanel.add(new JLabel("Carro"));
        inputPanel.add(carrosComboBox);
        inputPanel.add(new JLabel("Cliente"));
        inputPanel.add(clientesComboBox);
        add(inputPanel);

        // Botão para registrar a venda
        JPanel botoes = new JPanel();
        botoes.add(registrarVenda = new JButton("Registrar Venda"));
        add(botoes);

        // Tabela de vendas
        JScrollPane jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Data da Venda", "Carro", "Cliente" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        // Incluindo elementos do banco na criação do painel
        atualizarTabela();

        // Tratamento de eventos
        registrarVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter os valores dos campos de entrada
                Date dataVenda = new Date(); // Você pode modificar para obter a data de forma apropriada
                Carros carroSelecionado = (Carros) carrosComboBox.getSelectedItem();
                Clientes clienteSelecionado = (Clientes) clientesComboBox.getSelectedItem();

                // Chamar o método de registro de venda no controlador
                new VendasDAO().registrarVenda(dataVenda, carroSelecionado, clienteSelecionado);

                // Limpar os campos de entrada após a operação de registro de venda
                dataVendaField.setText("");
                carrosComboBox.setSelectedIndex(0);
                clientesComboBox.setSelectedIndex(0);
            }
        });
    }

    // Atualizar tabela de vendas com o banco de dados
    private void atualizarTabela() {
        // Atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodas();
        for (Vendas venda : vendas) {
            tableModel.addRow(new Object[] { venda.getDataVenda(), venda.getCpf(), venda.getClass() });
        }
    }
    public void attCombo(){
        carrosComboBox.removeAllItems();
        clientesComboBox.removeAllItems();
        carros = new CarrosDAO().listarTodos();
        clientes = new ClientesDAO().listarTodos();
        for (Carros carro : carros) {
            carrosComboBox.addItem(carro
            + " " + carro.getAno()
            + " " + carro.getMarca()
            + " " + carro.getAno()
            + " " + carro.getValor());
        
            
        }
        for (Clientes cliente : clientes) {
            clientesComboBox.addItem(cliente
            + " " + cliente.getCpf()
            + " " + cliente.getEmail()
            + " " + cliente.getTelefone()
            + " " + cliente.getNome());

            
        }

    }
}
