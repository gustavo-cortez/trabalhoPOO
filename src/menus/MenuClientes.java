package menus;

import classes.*;
import enums.EnumMenuClientes;
import interfaces.UserInterface;

public class MenuClientes implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Clientes:\n");
            for (EnumMenuClientes option : EnumMenuClientes.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            String opcaoStr = Interface.solicitarEntrada(menu.toString());
            opcao = Integer.parseInt(opcaoStr);

            try{
                EnumMenuClientes opcaoEscolhida = EnumMenuClientes.porNumero(opcao);

                switch (opcaoEscolhida) {
                case CADASTRAR_CLIENTE:
                    // Cadastrar cliente
                    String nomeCli = Interface.solicitarEntrada("Digite o nome do cliente:");
                    String documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente:");
                    instancias.getClienteIns().cadastrarCliente(nomeCli, documentoCli);
                    break;

                case CONSULTAR_CLIENTE:
                    // Consultar cliente por documento
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser consultado:");
                    Cliente clienteConsulta = instancias.getClienteIns().consultarCliente(documentoCli);
                    if (clienteConsulta != null) {
                        Interface.exibirMensagem("Nome: " + clienteConsulta.getNome() + "\nDocumento: " + clienteConsulta.getDocumento());
                    } else {
                        Interface.exibirMensagem("Cliente não encontrado.");
                    }
                    break;

                case EXCLUIR_CLIENTE:
                    // Excluir cliente
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser excluído:");
                    if(instancias.getClienteIns().excluirCliente(documentoCli, instancias.getTicketsIns()) == false){
                        Interface.exibirErro("Erro ao excluir o cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Cliente excluido com sucesso.");
                    }
                    break;

                case EDITAR_CLIENTE:
                    // Editar cliente
                    nomeCli = Interface.solicitarEntrada("Digite o novo nome do cliente:");
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser alterado:");
                    instancias.getClienteIns().editarCliente(nomeCli, documentoCli);
                    break;

                case GERENCIAR_VEICULOS:
                    // Gerenciar veículos
                    
                    break;

                case LISTAR_CADASTROS:
                    // Listar todos os cadastros
                    instancias.getClienteIns().listarClientes();
                    break;

                case VOLTAR:
                    // Voltar
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;

                default:
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != EnumMenuClientes.VOLTAR.getNum());
    }
}
