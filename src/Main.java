import model.Cliente;
import model.Compra;
import service.GerenciadorClientes;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Ponto de entrada da aplicação de Cadastro de Clientes.
 * Interface de linha de comando (CLI) interativa.
 */
public class Main {
    private static final GerenciadorClientes gerenciador = new GerenciadorClientes();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean executando = true;

        while (executando) {
            exibirMenu();
            int opcao = lerInteiro("Selecione uma opção: ");

            System.out.println();
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> buscarCliente();
                case 4 -> atualizarCliente();
                case 5 -> registrarCompraCliente();
                case 6 -> visualizarHistoricoCompras();
                case 7 -> removerCliente();
                case 8 -> popularExemplos();
                case 0 -> {
                    System.out.println("Encerrando o sistema. Até logo!");
                    executando = false;
                }
                default -> System.out.println("Opção inválida! Por favor, escolha um número válido.");
            }
            System.out.println();
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("==================================================");
        System.out.println("       SISTEMA DE CADASTRO DE CLIENTES            ");
        System.out.println("==================================================");
        System.out.println(" [1] Cadastrar novo cliente");
        System.out.println(" [2] Listar todos os clientes");
        System.out.println(" [3] Buscar cliente (por ID ou Nome)");
        System.out.println(" [4] Atualizar dados de um cliente");
        System.out.println(" [5] Registrar compra para um cliente");
        System.out.println(" [6] Visualizar histórico de compras");
        System.out.println(" [7] Remover cliente");
        System.out.println(" [8] Carregar dados de teste/demonstração");
        System.out.println(" [0] Sair");
        System.out.println("--------------------------------------------------");
    }

    private static void cadastrarCliente() {
        System.out.println("--- [ Novo Cliente ] ---");
        String nome = lerTexto("Nome completo: ");
        String email = lerTexto("E-mail: ");
        String telefone = lerTexto("Telefone/Celular: ");

        if (nome.isBlank() || email.isBlank()) {
            System.out.println("Erro: Nome e E-mail são obrigatórios!");
            return;
        }

        Cliente cliente = gerenciador.cadastrarCliente(nome, email, telefone);
        System.out.printf("Cliente cadastrado com sucesso! ID gerado: #%d\n", cliente.getId());
    }

    private static void listarClientes() {
        System.out.println("--- [ Lista de Clientes ] ---");
        List<Cliente> clientes = gerenciador.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no momento.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println(c);
        }
        System.out.printf("Total de clientes cadastrados: %d\n", clientes.size());
    }

    private static void buscarCliente() {
        System.out.println("--- [ Buscar Cliente ] ---");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Nome");
        int tipo = lerInteiro("Escolha o método de busca: ");

        if (tipo == 1) {
            int id = lerInteiro("Informe o ID do cliente: ");
            gerenciador.buscarPorId(id).ifPresentOrElse(
                c -> {
                    System.out.println("Cliente encontrado:");
                    System.out.println(c);
                },
                () -> System.out.println("Nenhum cliente encontrado com o ID " + id)
            );
        } else if (tipo == 2) {
            String termo = lerTexto("Informe parte do nome: ");
            List<Cliente> resultados = gerenciador.buscarPorNome(termo);
            if (resultados.isEmpty()) {
                System.out.println("Nenhum cliente encontrado com o nome contendo '" + termo + "'.");
            } else {
                System.out.printf("Encontrado(s) %d cliente(s):\n", resultados.size());
                resultados.forEach(System.out::println);
            }
        } else {
            System.out.println("Opção de busca inválida.");
        }
    }

    private static void atualizarCliente() {
        System.out.println("--- [ Atualizar Cliente ] ---");
        int id = lerInteiro("Informe o ID do cliente a atualizar: ");

        Optional<Cliente> opt = gerenciador.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("Cliente não encontrado com o ID " + id);
            return;
        }

        Cliente atual = opt.get();
        System.out.println("Cliente atual: " + atual.getNome() + " (" + atual.getEmail() + ")");
        System.out.println("(Pressione ENTER sem digitar para manter o valor atual)");

        String novoNome = lerTexto("Novo Nome [" + atual.getNome() + "]: ");
        String novoEmail = lerTexto("Novo E-mail [" + atual.getEmail() + "]: ");
        String novoTel = lerTexto("Novo Telefone [" + atual.getTelefone() + "]: ");

        gerenciador.atualizarCliente(
            id,
            novoNome.isBlank() ? atual.getNome() : novoNome,
            novoEmail.isBlank() ? atual.getEmail() : novoEmail,
            novoTel.isBlank() ? atual.getTelefone() : novoTel
        );

        System.out.println("Dados do cliente atualizados com sucesso!");
    }

    private static void registrarCompraCliente() {
        System.out.println("--- [ Registrar Nova Compra ] ---");
        int id = lerInteiro("ID do cliente comprador: ");

        Optional<Cliente> opt = gerenciador.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("Cliente não encontrado com o ID " + id);
            return;
        }

        String descricao = lerTexto("Descrição do produto/serviço: ");
        double valor = lerDouble("Valor da compra (R$): ");

        if (valor <= 0) {
            System.out.println("Erro: O valor deve ser maior que zero!");
            return;
        }

        boolean sucesso = gerenciador.registrarCompra(id, descricao, valor);
        if (sucesso) {
            System.out.printf("Compra de R$ %.2f registrada para %s!\n", valor, opt.get().getNome());
        }
    }

    private static void visualizarHistoricoCompras() {
        System.out.println("--- [ Histórico de Compras do Cliente ] ---");
        int id = lerInteiro("ID do cliente: ");

        Optional<Cliente> opt = gerenciador.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("Cliente não encontrado com o ID " + id);
            return;
        }

        Cliente cliente = opt.get();
        System.out.println("Histórico de Compras de: " + cliente.getNome());
        System.out.println("E-mail: " + cliente.getEmail() + " | Telefone: " + cliente.getTelefone());
        System.out.println("--------------------------------------------------");

        List<Compra> compras = cliente.getHistoricoCompras();
        if (compras.isEmpty()) {
            System.out.println("Este cliente ainda não realizou nenhuma compra.");
        } else {
            for (Compra c : compras) {
                System.out.println(" - " + c);
            }
            System.out.println("--------------------------------------------------");
            System.out.printf("Total acumulado de compras: R$ %.2f\n", cliente.getTotalGasto());
        }
    }

    private static void removerCliente() {
        System.out.println("--- [ Remover Cliente ] ---");
        int id = lerInteiro("ID do cliente a ser removido: ");

        Optional<Cliente> opt = gerenciador.buscarPorId(id);
        if (opt.isEmpty()) {
            System.out.println("Cliente não encontrado com o ID " + id);
            return;
        }

        String confirmacao = lerTexto("Tem certeza que deseja excluir o cliente '" + opt.get().getNome() + "'? (s/n): ");
        if (confirmacao.equalsIgnoreCase("s")) {
            gerenciador.removerCliente(id);
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private static void popularExemplos() {
        gerenciador.carregarDadosExemplo();
        System.out.println("3 clientes de exemplo e histórico de compras foram inseridos com sucesso!");
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor monetário válido (ex: 49.90).");
            }
        }
    }
}
