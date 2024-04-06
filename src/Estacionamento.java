/**
 * Trabalho POO Estacionamento
 * @author Gustavo e Gabriel
 */
import java.io.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import classes.*;


// Classe Estacionamento para gerenciar o sistema
public class Estacionamento {
    private List<Cliente> clientes;
    private List<Vagas> vagas;
    private List<Ticket> tickets;
    private List<Tarifa> tarifas;

    public Estacionamento() {
        this.clientes = new ArrayList<>();
        this.vagas = new ArrayList<>();
        this.tickets = new ArrayList<>();
        this.tarifas = new ArrayList<>();
    }

    // Métodos para gerenciar clientes, vagas, estacionamento, etc.

    // Método para cadastrar uma nova vaga
    public void cadastrarVaga(int numero, String rua, TipoVeiculo tipoVeiculo) {
        Vagas vaga = new Vagas(numero, rua, VagaStatus.DISPONIVEL, tipoVeiculo);  
        vagas.add(vaga);
    }
    
    // Método para buscar uma vaga pelo número
    private Vagas buscarVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                return vaga;
            }
        }
        return null; // Vaga não encontrada
    }
    
    // Método para excluir uma vaga pelo número
    public boolean excluirVaga(int numeroVaga) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vagas.remove(vaga);
                return true; // Vaga removida com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para editar uma vaga pelo número
    public boolean editarVaga(int numeroVaga, String novaRua, TipoVeiculo novoTipoVeiculo) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setRua(novaRua);
                vaga.setTipoVeiculo(novoTipoVeiculo);
                return true; // Vaga editada com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para alterar a disponibilidade de uma vaga pelo número
    public boolean alterarDisponibilidadeVaga(int numeroVaga, VagaStatus novoStatus) {
        for (Vagas vaga : vagas) {
            if (vaga.getNumero() == numeroVaga) {
                vaga.setStatus(novoStatus);
                return true; // Disponibilidade da vaga alterada com sucesso
            }
        }
        return false; // Vaga não encontrada
    }

    // Método para listar todas as vagas disponíveis
    public List<Vagas> listarVagasDisponiveis() {
        List<Vagas> vagasDisponiveis = new ArrayList<>();
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == VagaStatus.DISPONIVEL) {
                vagasDisponiveis.add(vaga);
            }
        }
        return vagasDisponiveis;
    }

    // Métodos para gerenciar tarifas
    public void cadastrarTarifa(Tarifa tarifa) {
        tarifas.add(tarifa);
    }
    
    // Método para listar todas as tarifas cadastradas
    public List<Tarifa> listarTarifas() {
        return tarifas;
    }
    
    // Método para estacionar um veículo
    public Ticket estacionarVeiculo(Veiculo veiculo, int numeroVaga) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.DISPONIVEL) {
            vaga.setStatus(VagaStatus.OCUPADA);
            Ticket ticket = new Ticket(new Date(), new Date(), veiculo, 0.0, vaga);
            tickets.add(ticket);
            return ticket;
        } else {
            return null; // Vaga não disponível ou inexistente
        }
    }
    
    public double retirarVeiculo(int numeroVaga) {
        Vagas vaga = buscarVaga(numeroVaga);
        if (vaga != null && vaga.getStatus() == VagaStatus.OCUPADA) {
            vaga.setStatus(VagaStatus.DISPONIVEL);
            Ticket ticket = buscarTicketPorVaga(numeroVaga);
            ticket.setFim(new Date());

            double valorTotal = calcularValorTicket(ticket);

            tickets.remove(ticket); // Remover ticket da lista de tickets

            return valorTotal;
        }
        return -1; // Vaga não ocupada ou inexistente
    }

    

    // Método auxiliar para buscar um ticket pelo número da vaga
    private Ticket buscarTicketPorVaga(int numeroVaga) {
        for (Ticket ticket : tickets) {
            if (ticket.getVaga().getNumero() == numeroVaga) {
                return ticket;
            }
        }
        return null; // Ticket não encontrado
    }

    // Método para calcular o valor total do ticket
    public double calcularValorTicket(Ticket ticket) {
        Date inicio = ticket.getInicio();
        Date fim = ticket.getFim();
        long diffInMillies = Math.abs(fim.getTime() - inicio.getTime());
        long diffInMinutes = diffInMillies / (60 * 1000);

        // Encontrar a tarifa correspondente ao dia da semana
        Tarifa tarifa = null;
        for (Tarifa t : tarifas) {
            
            tarifa = t;
            break;
            
        }

        if (tarifa == null) {
            return -1; // Tarifa não encontrada para o dia da semana
        }

        double valorTotal = tarifa.getValorPrimeiraHora();
        long horasSubsequentes = (long) Math.ceil((double) (diffInMinutes - 60) / 60);
        valorTotal += horasSubsequentes * tarifa.getValorHoraSubsequente();

        return valorTotal;
    }
    
     // Método para cadastrar um novo cliente
    public void cadastrarCliente(String nome, String documento) {
        Cliente cliente = new Cliente(nome, documento);
        clientes.add(cliente);
    }

    // Método para consultar um cliente pelo documento
    public Cliente consultarCliente(String documento) {
        for (Cliente cliente : clientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null; // Cliente não encontrado
    }

    // Método para excluir um cliente
    public boolean excluirCliente(String documento) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            clientes.remove(cliente);
            return true; // Cliente removido com sucesso
        } else {
            return false; // Cliente não encontrado
        }
    }

    // Método para editar os dados de um cliente
    public boolean editarCliente(String documento, String novoNome) {
        Cliente cliente = consultarCliente(documento);
        if (cliente != null) {
            cliente.setNome(novoNome);
            return true; // Cliente editado com sucesso
        } else {
            return false; // Cliente não encontrado
        }
    }
    
    // Método para listar todos os clientes cadastrados
    public void listarClientes() {
        System.out.println("\nLista de todos os clientes cadastrados:");
        for (Cliente cliente : clientes) {
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Documento: " + cliente.getDocumento());
            System.out.println("Veículos:");
            List<Veiculo> veiculos = cliente.getVeiculos();
            if (veiculos.isEmpty()) {
                System.out.println("Nenhum veículo cadastrado para este cliente.");
            } else {
                for (Veiculo veiculo : veiculos) {
                    System.out.println("- Placa: " + veiculo.getPlaca() + ", Tipo: " + veiculo.getTipo());
                }
            }
            System.out.println();
        }
    }
    
    // Método para adicionar um veículo a um cliente
    public void adicionarVeiculoCliente(String placa, String tipo, String documentoCliente) {
        String nomeCliente;
        TipoVeiculo tipoVeiculo = TipoVeiculo.valueOf(tipo.toUpperCase());
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            Veiculo veiculo = new Veiculo(placa, consultarCliente(documentoCliente) ,tipoVeiculo);
            cliente.adicionarVeiculo(veiculo);
            nomeCliente = cliente.getNome();
            System.out.println("Veículo adicionado com sucesso ao cliente " + nomeCliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    // Método para consultar os veículos de um cliente por documento
    public List<Veiculo> consultarVeiculo(String documentoCliente) {
        Cliente cliente = consultarCliente(documentoCliente);
        if (cliente != null) {
            List<Veiculo> veiculosCliente = cliente.getVeiculos();
            if (veiculosCliente.isEmpty()) {
                System.out.println("O cliente não possui veículos cadastrados.");
            } else {
                System.out.println("Veículos do cliente:");
                for (Veiculo veiculo : veiculosCliente) {
                    System.out.println("Placa: " + veiculo.getPlaca() + ", Tipo: " + veiculo.getTipo());
                }
            }
            return veiculosCliente;
        } else {
            System.out.println("Cliente não encontrado.");
            return null;
        }
    }
    
    // Método para consultar veículo por placa
    public Veiculo consultarPlaca(String placaCarro) {
        for (Cliente cliente : clientes) {
            for (Veiculo veiculo : cliente.getVeiculos()) {
                if (veiculo.getPlaca().equals(placaCarro)) {
                    return veiculo;
                }
            }
        }
        return null; // Retorna null se nenhum veículo com a placa especificada for encontrado
    }
    
    // Método para gerar um ticket de estacionamento
    public Ticket gerarTicket(Veiculo veiculo, Cliente cliente) {
        // Encontrar uma vaga disponível para o veículo
        Vagas vagaDisponivel = null;
        for (Vagas vaga : vagas) {
            if (vaga.getStatus() == VagaStatus.DISPONIVEL && vaga.getTipoVeiculo() == veiculo.getTipo()) {
                vagaDisponivel = vaga;
                break;
            }
        }

        // Verificar se há vagas disponíveis
        if (vagaDisponivel == null) {
            System.out.println("Não há vagas disponíveis para estacionar o veículo.");
            return null;
        }

        // Criar um novo ticket
        Ticket novoTicket = new Ticket(new Date(), new Date(), veiculo, 0.0, vagaDisponivel);

        // Atualizar o status da vaga para ocupada
        vagaDisponivel.setStatus(VagaStatus.OCUPADA);

        // Adicionar o ticket à lista de tickets
        tickets.add(novoTicket);

        System.out.println("Ticket gerado com sucesso!");
        return novoTicket;
    }


    // Métodos para gerenciar tarifas de acordo com períodos específicos
    public void cadastrarTarifaPeriodo(Tarifa tarifa) {
        tarifas.add(tarifa);
    }

    public Tarifa buscarTarifaPorPeriodo(Date inicioPeriodo, Date fimPeriodo) {
        for (Tarifa tarifa : tarifas) {
            if (tarifa.getInicio().equals(inicioPeriodo) && tarifa.getFim().equals(fimPeriodo)) {
                return tarifa;
            }
        }
        return null; // Tarifa não encontrada para o período especificado
    }

    // Método para consultar o faturamento em um período específico
    public double consultarFaturamentoPeriodo(Date inicioPeriodo, Date fimPeriodo) {
        double faturamento = 0;
        for (Ticket ticket : tickets) {
            Date inicioTicket = ticket.getInicio();
            Date fimTicket = ticket.getFim();
            if (inicioTicket.after(inicioPeriodo) && fimTicket.before(fimPeriodo)) {
                double valorTicket = calcularValorTicket(ticket);
                faturamento += valorTicket;
            }
        }
        return faturamento;
    }

    // Método para salvar os dados do estacionamento em um arquivo
    public void salvarDados(String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(clientes);
            outputStream.writeObject(vagas);
            outputStream.writeObject(tickets);
            outputStream.writeObject(tarifas);
            System.out.println("Dados do estacionamento salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados do estacionamento: " + e.getMessage());
        }
    }

    // Método para carregar os dados do estacionamento de um arquivo
    public void carregarDados(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            clientes = (List<Cliente>) inputStream.readObject();
            vagas = (List<Vagas>) inputStream.readObject();
            tickets = (List<Ticket>) inputStream.readObject();
            tarifas = (List<Tarifa>) inputStream.readObject();
            System.out.println("Dados do estacionamento carregados com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados do estacionamento: " + e.getMessage());
        }
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        Estacionamento estacionamento = new Estacionamento();
        Scanner scanner = new Scanner(System.in);
        estacionamento.carregarDados("DadosEstacionamento.json");
        int opcao;
        do {
            System.out.println("Menu Principal:");
            System.out.println("1 - Gerenciar clientes");
            System.out.println("2 - Gerenciar vagas");
            System.out.println("3 - Gerenciar estacionamento");
            System.out.println("4 - Cadastros gerais");
            System.out.println("5 - Consultar total faturado em um período");
            System.out.println("6 - Sair do programa");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    // Submenu para gerenciar clientes
                    int opcaoClientes;
                    String nomeCli;
                    String documentoCli;
                    do {
                        System.out.println("\nSubmenu - Gerenciar clientes:");
                        System.out.println("1 - Cadastrar cliente");
                        System.out.println("2 - Consultar cliente por documento");
                        System.out.println("3 - Excluir cliente");
                        System.out.println("4 - Editar cliente");
                        System.out.println("5 - Gerenciar veículos");
                        System.out.println("6 - Listar todos os cadastros");
                        System.out.println("7 - Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoClientes = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoClientes) {
                            case 1:
                                
                                System.out.println("Informe o nome:");
                                nomeCli = scanner.nextLine();
                                System.out.println("Informe o documento:");
                                documentoCli = scanner.nextLine();
                                estacionamento.cadastrarCliente(nomeCli, documentoCli);
                                break;
                            case 2:
                                // Implementar consulta de cliente por documento
                                System.out.println("\nInforme o documento:");
                                documentoCli = scanner.nextLine();
                                Cliente clienteConsulta = estacionamento.consultarCliente(documentoCli);
                                if(clienteConsulta != null){
                                    System.out.println("Nome: " + clienteConsulta.getNome());
                                    System.out.println("Documento: " + clienteConsulta.getDocumento());
                                }else{
                                    System.out.println("Cliente não encontrado.");
                                }
                                break;
                            case 3:
                                // Implementar exclusão de cliente
                                System.out.println("\nInforme o documento:");
                                documentoCli = scanner.nextLine();
                                estacionamento.excluirCliente(documentoCli);
                                break;
                            case 4:
                                // Área de edição do cliente, informando seu nome e documento
                                System.out.println("\nInforme o nome:");
                                nomeCli = scanner.nextLine();
                                System.out.println("Informe o documento:");
                                documentoCli = scanner.nextLine();
                                estacionamento.editarCliente(documentoCli, nomeCli);
                                break;
                            case 5:
                                int opcaoVeiculo;
                                System.out.println("\nSubmenu - Gerenciar Veículos:");
                                System.out.println("1 - Cadastrar veículos");
                                System.out.println("2 - Consultar veículos por documento");
                                System.out.println("3 - Voltar");
                                opcaoVeiculo = scanner.nextInt();
                                scanner.nextLine();
                                switch (opcaoVeiculo){
                                    
                                    case 1:
                                        String tipoVeiculo;
                                        String veiculoPlaca;
                                        System.out.println("Informe o documento:");
                                        documentoCli = scanner.nextLine();
                                        System.out.println("\nInforme o nome:");
                                        veiculoPlaca = scanner.nextLine();
                                        System.out.println("Informe o documento:");
                                        tipoVeiculo = scanner.nextLine();
                                        estacionamento.adicionarVeiculoCliente(veiculoPlaca, tipoVeiculo , documentoCli);
                                        break;
                                    case 2:
                                        System.out.println("Informe o documento:");
                                        documentoCli = scanner.nextLine();
                                        estacionamento.consultarVeiculo(documentoCli);
                                        break;
                                    case 3:
                                        break;
                                    
                                    default:
                                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                                } 
                                // Implementar gerenciamento de veículos
                                break;
                            case 6:
                                // Implementar listagem de todos os clientes cadastrados
                                estacionamento.listarClientes();
                                break;
                            case 7:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        }
                    } while (opcaoClientes != 7);
                    break;
                case 2:
                    // Submenu para gerenciar vagas
                    int opcaoVagas;
                    do {
                        System.out.println("\nSubmenu - Gerenciar vagas:");
                        System.out.println("1 - Cadastrar vaga");
                        System.out.println("2 - Consultar vaga por número");
                        System.out.println("3 - Excluir vaga");
                        System.out.println("4 - Editar vaga");
                        System.out.println("5 - Alterar disponibilidade");
                        System.out.println("6 - Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoVagas = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoVagas) {
                            case 1:
                                // Implementar cadastro de vaga
                                int numeroVaga;
                                String ruaVaga;
                                String Vaga;
                                System.out.println("Digite o número da vaga");
                                numeroVaga = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Digite a rua da vaga");
                                ruaVaga = scanner.nextLine();
                                System.out.println("Digite o tipo da vaga");
                                Vaga = scanner.nextLine();
                                if(Vaga.toUpperCase().equals("CARRO")){
                                    
                                    estacionamento.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.CARRO);
                                    
                                }else{
                                    if(Vaga.equals("MOTO")){

                                        estacionamento.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.MOTO);

                                    }else{
                                        if(Vaga.equals("CAMINHAO")){

                                            estacionamento.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.CAMINHAO);

                                        }else{
                                            
                                            System.out.println("OPÇÃO INVÁLIDA!");
                                            break;
                                            
                                        }
                                    }
                                }
                                break;
                            case 2:
                                // Implementar consulta de vaga por número
                                int numVaga;
                                numVaga = scanner.nextInt();
                                Vagas vagaConsulta = estacionamento.buscarVaga(numVaga);;
                                if(vagaConsulta != null){
                                    System.out.println("Numero: " + vagaConsulta.getNumero());
                                    System.out.println("Rua: " + vagaConsulta.getRua());
                                    System.out.println("Status: " + vagaConsulta.getStatus());
                                    System.out.println("Tipo: " + vagaConsulta.getTipoVeiculo());
                                }else{
                                    System.out.println("Cliente não encontrado.");
                                }
                                break;
                            case 3:
                                // Implementar exclusão de vaga
                                System.out.print("Informe o número da vaga a ser excluída: ");
                                int numeroVagaExcluir = scanner.nextInt();
                                estacionamento.excluirVaga(numeroVagaExcluir);
                                break;
                            case 4:
                                // Implementar edição de vaga
                                int numeroVagaEditar;
                                String ruaVagaEditar;
                                String VagaEditar;
                                System.out.println("Digite o número da vaga que dejesa editar");
                                numeroVagaEditar = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Digite a nova rua da vaga");
                                ruaVagaEditar = scanner.nextLine();
                                System.out.println("Digite o novo tipo da vaga");
                                VagaEditar = scanner.nextLine();
                                if(VagaEditar.toUpperCase().equals("CARRO")){
                                    
                                    estacionamento.cadastrarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.CARRO);
                                    
                                }else{
                                    if(VagaEditar.equals("MOTO")){

                                        estacionamento.cadastrarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.MOTO);

                                    }else{
                                        if(VagaEditar.equals("CAMINHAO")){

                                            estacionamento.cadastrarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.CAMINHAO);

                                        }else{
                                            
                                            System.out.println("OPÇÃO INVÁLIDA!");
                                            break;
                                            
                                        }
                                    }
                                }
                                break;
                            case 5:
                                // Implementar alteração de disponibilidade de vaga
                                int numeroVagaDispo;
                                String VagaDispo;
                                System.out.println("Digite o número da vaga que dejesa editar o status");
                                numeroVagaDispo = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Digite o número da vaga que dejesa editar o status");
                                VagaDispo = scanner.nextLine();
                                if(VagaDispo.toUpperCase().equals("DISPONIVEL")){
                                    
                                    estacionamento.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.DISPONIVEL);
                                    
                                }else{
                                    if(VagaDispo.equals("OCUPADA")){

                                        estacionamento.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.OCUPADA);

                                    }else{
                                        if(VagaDispo.equals("INDISPONIVEL")){

                                            estacionamento.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.INDISPONIVEL);

                                        }else{
                                            
                                            System.out.println("OPÇÃO INVÁLIDA!");
                                            break;
                                            
                                        }
                                    }
                                }
                                break;
                            case 6:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        }
                    } while (opcaoVagas != 6);
                    break;
                case 3:
                    // Submenu para gerenciar estacionamento
                    int opcaoEstacionamento;
                    do {
                        System.out.println("\nSubmenu - Gerenciar estacionamento:");
                        System.out.println("1 - Estacionar");
                        System.out.println("2 - Retirar");
                        System.out.println("3 - Listar todas as vagas disponíveis");
                        System.out.println("4 - Gerenciar tarifas");
                        System.out.println("5 - Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoEstacionamento = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoEstacionamento) {
                            case 1:
                                // Implementar função para estacionar veículo
                                String PlacaEstacionar;
                                int numVaga;
                                System.out.println("Digite a placa do veículo a ser estacionado");
                                PlacaEstacionar = scanner.nextLine();
                                System.out.println("Digite o número da vaga para estacionar o veículo");
                                numVaga = scanner.nextInt();
                                scanner.nextLine();
                                estacionamento.estacionarVeiculo(estacionamento.consultarPlaca(PlacaEstacionar), numVaga);
                                break;
                            case 2:
                                // Implementar função para retirar veículo
                                int numVagaRetirar;
                                double valor;
                                System.out.println("Digite o número da vaga para retirar o veículo");
                                numVagaRetirar = scanner.nextInt();
                                scanner.nextLine();
                                valor = estacionamento.retirarVeiculo(numVagaRetirar);
                                if (valor != -1) {
                                    
                                    System.out.println("Veículo retirado com sucesso. VALOR: R$ " + valor);
                                   
                                    
                                }else{
                                    
                                    System.out.println("Vaga não ocupada.");
                                    
                                }
                                break;
                            case 3:
                                // Implementar função para listar todas as vagas disponíveis
                                List<Vagas> vagasDisponiveis = estacionamento.listarVagasDisponiveis();
                                System.out.println("Vagas disponíveis:");
                                for (Vagas vaga : vagasDisponiveis) {
                                    System.out.println("Número: " + vaga.getNumero() + ", Rua: " + vaga.getRua() + ", Tipo: " + vaga.getTipoVeiculo());
                                }
                                break;
                            case 4:
                                // Implementar função para gerenciar tarifas
                                break;
                            case 5:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        }
                    } while (opcaoEstacionamento != 5);
                    break;
                case 4:
                    // Submenu para cadastros gerais
                    int opcaoCadastros;
                    do {
                        System.out.println("\nSubmenu - Cadastros gerais:");
                        System.out.println("1 - Cadastrar tarifa");
                        System.out.println("2 - Cadastrar cliente");
                        System.out.println("3 - Voltar");
                        System.out.print("Escolha uma opção: ");
                        opcaoCadastros = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoCadastros) {
                            case 1:
                                // Implementar função para cadastrar tarifa
                                break;
                            case 2:
                                // Implementar função para cadastrar cliente
                                break;
                            case 3:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        }
                    } while (opcaoCadastros != 3);
                    break;

                case 5:
                    // Implementar consulta de faturamento em um período
                    System.out.print("Digite a data de início (formato dd/mm/yyyy): ");
                    String dataInicioStr = scanner.next();
                    System.out.print("Digite a data de fim (formato dd/mm/yyyy): ");
                    String dataFimStr = scanner.next();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date inicioPeriodo = formatter.parse(dataInicioStr);
                        Date fimPeriodo = formatter.parse(dataFimStr);

                        double faturamentoPeriodo = estacionamento.consultarFaturamentoPeriodo(inicioPeriodo, fimPeriodo);
                        System.out.println("Total faturado no período: R$ " + faturamentoPeriodo);
                    } catch (ParseException e) {
                        System.out.println("Formato de data inválido. Use o formato dd/mm/yyyy.");
                    }
                    break;
                case 6:
                    System.out.println("Saindo do programa...");
                    estacionamento.salvarDados("DadosEstacionamento.json");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 6);
    }
}
