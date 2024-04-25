/**
 * Trabalho POO Estacionamento
 * @author Gustavo e Gabriel
 */
import java.util.List;
import java.util.Scanner;
import classes.*;
import funcoes.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// Classe Estacionamento para gerenciar o sistema
public class Estacionamento {

    // Método principal para executar o programa
    public static void main(String[] args) {
        
        FunTickets ticketsIns = new FunTickets();
        FunTarifas tarifasIns = new FunTarifas();
        FunCliente clienteIns = new FunCliente();
        FunVagas vagasIns = new FunVagas();
        
        Scanner scanner = new Scanner(System.in);
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
                                clienteIns.cadastrarCliente(nomeCli, documentoCli);
                                break;
                            case 2:
                                System.out.println("\nInforme o documento:");
                                documentoCli = scanner.nextLine();
                                Cliente clienteConsulta = clienteIns.consultarCliente(documentoCli);
                                if(clienteConsulta != null){
                                    System.out.println("Nome: " + clienteConsulta.getNome());
                                    System.out.println("Documento: " + clienteConsulta.getDocumento());
                                }else{
                                    System.out.println("Cliente não encontrado.");
                                }
                                break;
                            case 3:
                                System.out.println("\nInforme o documento:");
                                documentoCli = scanner.nextLine();
                                clienteIns.excluirCliente(documentoCli);
                                break;
                            case 4:
                                // Área de edição do cliente, informando seu nome e documento
                                System.out.println("\nInforme o nome:");
                                nomeCli = scanner.nextLine();
                                System.out.println("Informe o documento:");
                                documentoCli = scanner.nextLine();
                                clienteIns.editarCliente(documentoCli, nomeCli);
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
                                        String corVeiculo;
                                        String modeloVeiculo;
                                        System.out.println("Informe o documento:");
                                        documentoCli = scanner.nextLine();
                                        System.out.println("\nInforme a placa do veiculo:");
                                        veiculoPlaca = scanner.nextLine();
                                        System.out.println("Informe o modelo do veiculo:");
                                        modeloVeiculo = scanner.nextLine();
                                        System.out.println("\nInforme a cor do veiculo:");
                                        corVeiculo = scanner.nextLine();
                                        System.out.println("Informe o tipo do veiculo(CARRO, MOTO ou CAMINHAO):");
                                        tipoVeiculo = scanner.nextLine();
                                        clienteIns.adicionarVeiculoCliente(veiculoPlaca, tipoVeiculo , documentoCli, corVeiculo, modeloVeiculo);
                                        break;
                                    case 2:
                                        System.out.println("Informe o documento:");
                                        documentoCli = scanner.nextLine();
                                        clienteIns.consultarVeiculo(documentoCli);
                                        break;
                                    case 3:
                                        break;
                                    
                                    default:
                                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                                } 
                                break;
                            case 6:
                                clienteIns.listarClientes();
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
                                    
                                    vagasIns.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.CARRO);
                                    
                                }else{
                                    if(Vaga.toUpperCase().equals("MOTO")){

                                        vagasIns.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.MOTO);

                                    }else{
                                        if(Vaga.toUpperCase().equals("CAMINHAO")){

                                            vagasIns.cadastrarVaga(numeroVaga, ruaVaga, TipoVeiculo.CAMINHAO);

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
                                Vagas vagaConsulta = vagasIns.buscarVaga(numVaga);;
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
                                vagasIns.excluirVaga(numeroVagaExcluir);
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
                                    
                                    vagasIns.editarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.CARRO);
                                    
                                }else{
                                    if(VagaEditar.equals("MOTO")){

                                        vagasIns.editarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.MOTO);

                                    }else{
                                        if(VagaEditar.equals("CAMINHAO")){

                                            vagasIns.editarVaga(numeroVagaEditar, ruaVagaEditar, TipoVeiculo.CAMINHAO);

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
                                System.out.println("Digite o status que a vaga estara");
                                VagaDispo = scanner.nextLine();
                                if(VagaDispo.toUpperCase().equals("DISPONIVEL")){
                                    
                                    vagasIns.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.DISPONIVEL);
                                    
                                }else{
                                    if(VagaDispo.equals("OCUPADA")){

                                        vagasIns.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.OCUPADA);

                                    }else{
                                        if(VagaDispo.equals("INDISPONIVEL")){

                                            vagasIns.alterarDisponibilidadeVaga(numeroVagaDispo, VagaStatus.INDISPONIVEL);

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
                                vagasIns.estacionarVeiculo(ticketsIns, clienteIns.consultarPlaca(PlacaEstacionar), numVaga);
                                break;
                            case 2:
                                // Implementar função para retirar veículo
                                int numVagaRetirar;
                                double valor;
                                System.out.println("Digite o número da vaga para retirar o veículo");
                                numVagaRetirar = scanner.nextInt();
                                scanner.nextLine();
                                valor = vagasIns.retirarVeiculo(ticketsIns, numVagaRetirar, tarifasIns);
                                System.out.println("Veículo retirado com sucesso. VALOR: R$ " + valor);
                              
                                break;
                            case 3:
                                // Implementar função para listar todas as vagas disponíveis
                                List<Vagas> vagasDisponiveis = vagasIns.listarVagasDisponiveis();
                                System.out.println("Vagas disponíveis:");
                                for (Vagas vaga : vagasDisponiveis) {
                                    System.out.println("Número: " + vaga.getNumero() + ", Rua: " + vaga.getRua() + ", Tipo: " + vaga.getTipoVeiculo());
                                }
                                break;
                            case 4:
                                int opcaoVeiculo;
                                System.out.println("\nSubmenu - Gerenciar Veículos:");
                                System.out.println("1 - Cadastrar tarifa");
                                System.out.println("2 - Consultar tarifas cadastradas");
                                System.out.println("3 - Voltar");
                                opcaoVeiculo = scanner.nextInt();
                                scanner.nextLine();
                                switch (opcaoVeiculo){
                                    
                                    case 1:
                                        // Implementar função para gerenciar tarifas
                                        System.out.println("Informe a data de início (formato dd/MM/yyyy):");
                                        String dataStr = scanner.nextLine();
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        LocalDate data = LocalDate.parse(dataStr, formatter);

                                        System.out.println("Informe a tarifa base:");
                                        double tarifaBase = scanner.nextDouble();

                                        System.out.println("Informe o valor das horas subsequentes:");
                                        double taxaAdicional = scanner.nextDouble();
                                       
                                        // Cadastrar a tarifa com os dados fornecidos pelo usuário
                                        tarifasIns.cadastrarTarifa(data, tarifaBase, taxaAdicional);
                                        break;
                                    case 2:
                                        tarifasIns.listarTarifas();
                                        break;
                                        
                                    case 3:
                                        System.out.println("Voltando...");
                                        break;
                                }
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
                                ticketsIns.listarTickets();
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
                    System.out.print("Digite a data de início (formato dd/mm/yyyy-HH:mm): ");
                    String dataInicioStr = scanner.next();
                    System.out.print("Digite a data de fim (formato dd/mm/yyyy-HH:mm): ");
                    String dataFimStr = scanner.next();

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy-HH:mm");
                    LocalDateTime inicioPeriodo = LocalDateTime.parse(dataInicioStr, formatter);
                    LocalDateTime fimPeriodo = LocalDateTime.parse(dataFimStr, formatter);

                    double faturamentoPeriodo = tarifasIns.consultarFaturamentoPeriodo(inicioPeriodo, fimPeriodo, tarifasIns);
                    System.out.println("Total faturado no período: R$ " + faturamentoPeriodo);

                    break;
                case 6:
                    System.out.println("Saindo do programa...");
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 6);
    }
}
