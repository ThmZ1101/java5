package Controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Model.Carros;
import Model.Vendas;

public class CarrosControl {

    private List<Carros> carros;
    private DefaultTableModel tableModel;
    private JTable table;

    public CarrosControl(List<Carros> carros, DefaultTableModel tableModel, JTable table) {
        this.carros = carros;
        this.tableModel = tableModel;
        this.table = table;
    }

    public CarrosControl() {
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        carros = new CarrosUS().listarTodos();
        for (Carros carro : carros) {
            tableModel.addRow(new Object[] { carro.getMarca(), carro.getModelo(), carro.getAno(),
                    carro.getPlaca(), carro.getValor() });
        }
    }

    public void cadastrar(String marca, String modelo, String ano, String placa, String valor) {
        new CarrosUS().cadastrar(marca, modelo, ano, placa, valor);
        atualizarTabela();
    }

    public void atualizar(String marca, String modelo, String ano, String placa, String valor) {
        new CarrosUS().atualizar(marca, modelo, ano, placa, valor);
        atualizarTabela();
    }

    public void apagar(String placa) {
        new CarrosUS().apagar(placa);
        atualizarTabela();
    }

    public List<Vendas> listarTodos() {
        // Método não implementado ainda
        return null;
    }
}
