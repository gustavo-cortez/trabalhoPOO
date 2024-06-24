package menus;

import classes.*;
import enums.EnumMenuClientes;
import interfaces.UserInterface;
import java.util.List;

public class MenuClientes implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        do {
            List<EnumMenuClientes> opcoesMenuCliente = List.of(EnumMenuClientes.values());
            
            opcao = Interface.exibirMenus("Submenu - Cliente", opcoesMenuCliente);

            try{
                switch (opcao) {
                case 1:
                    // Cadastrar cliente
                    String nomeCli = Interface.solicitarEntrada("Digite o nome do cliente:");
                    String documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente:");
                    instancias.getClienteIns().cadastrarCliente(nomeCli, documentoCli);
                    break;

                case 2:
                    // Consultar cliente por documento
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser consultado:");
                    Cliente clienteConsulta = instancias.getClienteIns().consultarCliente(documentoCli);
                    if (clienteConsulta != null) {
                        Interface.exibirMensagem("Nome: " + clienteConsulta.getNome() + "\nDocumento: " + clienteConsulta.getDocumento());
                    } else {
                        Interface.exibirMensagem("Cliente não encontrado.");
                    }
                    break;

                case 3:
                    // Excluir cliente
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser excluído:");
                    if(instancias.getClienteIns().excluirCliente(documentoCli) == false){
                        Interface.exibirErro("Erro ao excluir o cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Cliente excluido com sucesso.");
                    }
                    break;

                case 4:
                    // Editar cliente
                    nomeCli = Interface.solicitarEntrada("Digite o novo nome do cliente:");
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser alterado:");
                    instancias.getClienteIns().editarCliente(documentoCli, nomeCli);
                    break;

                case 5:
                    // Gerenciar veículos
                    instancias.getMenuVeiculos().exibir(Interface, instancias);
                    break;

                case 6:
                    // Listar todos os cadastros
                    instancias.getClienteIns().listarClientes();
                    break;

                case 7:
                    // Voltar
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;

                default:
                    if(opcao == 0){
                        opcao = 7;
                        break;
                    }
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != 7);
    }
}
