package Model;

import java.util.Date;

public class Vendas {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Vendas(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public Vendas(Date dataVenda, String carroPlaca, String clienteCpf) {
    }

    public Vendas() {
    }

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getDataVenda() {
        return null;
    }

    public void registrarVenda(Date dataVenda, Carros carroSelecionado, Clientes clienteSelecionado) {
    }

    // Outros métodos conforme necessário
}
