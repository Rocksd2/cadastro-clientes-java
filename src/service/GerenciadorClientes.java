package service;

import model.Cliente;
import model.Compra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela lógica de negócios e operações de CRUD dos clientes.
 */
public class GerenciadorClientes {
    private final List<Cliente> clientes;
    private int proximoIdCliente;
    private int proximoIdCompra;

    public GerenciadorClientes() {
        this.clientes = new ArrayList<>();
        this.proximoIdCliente = 1;
        this.proximoIdCompra = 1;
    }

    /**
     * Cadastra um novo cliente no sistema.
     */
    public Cliente cadastrarCliente(String nome, String email, String telefone) {
        Cliente novoCliente = new Cliente(proximoIdCliente++, nome.trim(), email.trim(), telefone.trim());
        clientes.add(novoCliente);
        return novoCliente;
    }

    /**
     * Retorna a lista completa de clientes cadastrados.
     */
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    /**
     * Busca um cliente pelo identificador único (ID).
     */
    public Optional<Cliente> buscarPorId(int id) {
        return clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    /**
     * Busca um cliente pelo endereço de e-mail (case-insensitive).
     */
    public Optional<Cliente> buscarPorEmail(String email) {
        return clientes.stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }

    /**
     * Busca clientes cujo nome contenha o termo pesquisado.
     */
    public List<Cliente> buscarPorNome(String termo) {
        String termoLower = termo.toLowerCase().trim();
        return clientes.stream()
                .filter(c -> c.getNome().toLowerCase().contains(termoLower))
                .collect(Collectors.toList());
    }

    /**
     * Atualiza os dados cadastrais de um cliente existente.
     */
    public boolean atualizarCliente(int id, String novoNome, String novoEmail, String novoTelefone) {
        Optional<Cliente> clienteOpt = buscarPorId(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (novoNome != null && !novoNome.isBlank()) cliente.setNome(novoNome.trim());
            if (novoEmail != null && !novoEmail.isBlank()) cliente.setEmail(novoEmail.trim());
            if (novoTelefone != null && !novoTelefone.isBlank()) cliente.setTelefone(novoTelefone.trim());
            return true;
        }
        return false;
    }

    /**
     * Remove um cliente pelo ID.
     */
    public boolean removerCliente(int id) {
        return clientes.removeIf(c -> c.getId() == id);
    }

    /**
     * Registra uma nova compra no histórico de um cliente.
     */
    public boolean registrarCompra(int idCliente, String descricao, double valor) {
        Optional<Cliente> clienteOpt = buscarPorId(idCliente);
        if (clienteOpt.isPresent()) {
            Compra compra = new Compra(proximoIdCompra++, descricao.trim(), valor);
            clienteOpt.get().adicionarCompra(compra);
            return true;
        }
        return false;
    }

    /**
     * Popula a lista com alguns dados fictícios para facilitar demonstrações e testes.
     */
    public void carregarDadosExemplo() {
        Cliente c1 = cadastrarCliente("Ana Silva", "ana.silva@email.com", "(11) 98765-4321");
        registrarCompra(c1.getId(), "Monitor Ultrawide 29''", 1199.90);
        registrarCompra(c1.getId(), "Teclado Mecânico RGB", 249.00);

        Cliente c2 = cadastrarCliente("Carlos Souza", "carlos.souza@email.com", "(21) 99123-8877");
        registrarCompra(c2.getId(), "Notebook Dell Inspiron", 3899.00);
        registrarCompra(c2.getId(), "Mouse Sem Fio Logitech", 89.90);
        registrarCompra(c2.getId(), "Suporte Ergonômico", 59.90);

        cadastrarCliente("Mariana Oliveira", "mariana.oli@email.com", "(31) 97654-1122");
    }
}
