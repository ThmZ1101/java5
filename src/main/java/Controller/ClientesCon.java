package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Clientes;
import Model.Vendas;

public class ClientesCon {

    private List<Clientes> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    public ClientesCon(List<Clientes> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }

    public ClientesCon() {
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        clientes = new ClientesUs().listarTodos();
        for (Clientes cliente : clientes) {
            tableModel.addRow(new Object[] { cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEmail() });
        }
    }

    public void cadastrar(String nome, String cpf, String telefone, String email) {
        new ClientesUs().cadastrar(nome, cpf, telefone, email);
        atualizarTabela();
    }

    public void atualizar(String nome, String cpf, String telefone, String email) {
        new ClientesUs().atualizar(nome, cpf, telefone, email);
        atualizarTabela();
    }

    public void apagar(String cpf) {
        new ClientesUs().apagar(cpf);
        atualizarTabela();
    }

    public List<Vendas> listarTodos() {
        // Método não implementado ainda
        return null;
    }
}
