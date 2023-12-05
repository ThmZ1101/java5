package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Connection.ConnectionFactory;
import Model.Clientes;

public class ClientesUs {

    private Connection connection;

    public ClientesUs() {
        this.connection = ConnectionFactory.getConnection();
    }

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes_lojaclientes (NOME VARCHAR(255),CPF VARCHAR(14) PRIMARY KEY, TELEFONE VARCHAR(20), EMAIL VARCHAR(255))";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.execute();
            System.out.println("Tabela de clientes criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela de clientes: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public List<Clientes> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Clientes> clientes = new ArrayList<>();

        try {
            stmt = connection.prepareStatement("SELECT * FROM clientes_lojaclientes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Clientes cliente = new Clientes(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("email")
                );
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
        return clientes;
    }

    public void cadastrar(String nome, String cpf, String telefone, String email) {
        String sql = "INSERT INTO clientes_lojaclientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, telefone);
            stmt.setString(4, email);
            stmt.executeUpdate();
            System.out.println("Dados do cliente inseridos com sucesso");
            new Registro().registrarOperacao("Cliente CPF: " + cpf + " Cadastrado com Sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados do cliente no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection);
        }
    }

    public void atualizar(String nome, String cpf, String telefone, String email) {
        String sql = "UPDATE clientes_lojaclientes SET nome = ?, telefone = ?, email = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, email);
            stmt.setString(4, cpf);
            stmt.executeUpdate();
            System.out.println("Dados do cliente atualizados com sucesso");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados do cliente no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection);
        }
    }

    public void apagar(String cpf) {
        String sql = "DELETE FROM clientes_lojaclientes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Dados do cliente apagados com sucesso");
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados do cliente no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection);
        }
    }
}
