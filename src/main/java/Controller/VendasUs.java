package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Connection.ConnectionFactory;
import Model.Carros;
import Model.Clientes;
import Model.Vendas;

public class VendasUs {

    private Connection connection;

    public VendasUs() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS vendas_lojacarros (DATA_VENDA DATE, CARRO_PLACA VARCHAR(255), CLIENTE_CPF VARCHAR(255), PRIMARY KEY (DATA_VENDA, CARRO_PLACA, CLIENTE_CPF))";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela de vendas criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela de vendas: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public List<Vendas> listarTodas() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Vendas> vendas = new ArrayList<>();

        try {
            stmt = connection.prepareStatement("SELECT * FROM vendas_lojacarros");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Date dataVenda = rs.getDate("data_venda");
                String carroPlaca = rs.getString("carro_placa");
                String clienteCpf = rs.getString("cliente_cpf");

                Vendas venda = new Vendas(dataVenda, carroPlaca, clienteCpf);
                vendas.add(venda);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
        return vendas;
    }

    public void registrarVenda(Date dataVenda, String carroSelecionado, String clienteSelecionado) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO vendas_lojacarros (data_venda, carro_placa, cliente_cpf) VALUES (?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setDate(1, new java.sql.Date(dataVenda.getTime()));
            stmt.setString(2, carroSelecionado);
            stmt.setString(3, clienteSelecionado);
            stmt.executeUpdate();
            System.out.println("Venda registrada com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    public void registrarVenda(Date dataVenda, Carros carroSelecionado, Clientes clienteSelecionado) {
    }

}
