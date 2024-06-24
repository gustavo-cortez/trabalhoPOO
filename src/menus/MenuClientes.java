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
            /*Imprimir o menu dependendo da interface escolhida*/
            List<EnumMenuClientes> opcoesMenuCliente = List.of(EnumMenuClientes.values());
            
            opcao = Interface.exibirMenus("Submenu - Cliente", opcoesMenuCliente);

            try{
                switch (opcao) {
                /*Caso 1 - Cadastrar cliente com nome e documento*/
                case 1:
                    String nomeCli = Interface.solicitarEntrada("Digite o nome do cliente:");
                    String documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente:");
                    if(nomeCli == null || documentoCli == null){
                        break;
                    }
                    instancias.getClienteIns().cadastrarCliente(nomeCli, documentoCli);
                    break;
                /*Caso 2 - Consultar cliente por documento*/
                case 2:
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser consultado:");
                    if(documentoCli == null){
                        break;
                    }
                    Cliente clienteConsulta = instancias.getClienteIns().consultarCliente(documentoCli);
                    if (clienteConsulta != null) {
                        instancias.getClienteIns().imprimirCliente(clienteConsulta);
                    } else {
                        Interface.exibirMensagem("Cliente não encontrado.");
                    }
                    break;
                /*Caso 3 - Excluir cliente a partir do documento informado*/
                case 3:
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser excluído:");
                    if(documentoCli == null){
                        break;
                    }
                    if(instancias.getClienteIns().excluirCliente(documentoCli) == false){
                        Interface.exibirErro("Erro ao excluir o cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Cliente excluido com sucesso.");
                    }
                    break;
                /*Caso 4 - Editar cliente a partir do documento informado, podendo assim alterar o nome da pessoa*/
                case 4:
                    nomeCli = Interface.solicitarEntrada("Digite o novo nome do cliente:");
                    documentoCli = Interface.solicitarEntrada("Digite o CPF do cliente a ser alterado:");
                    if(nomeCli == null || documentoCli == null){
                        break;
                    }
                    instancias.getClienteIns().editarCliente(documentoCli, nomeCli);
                    break;
                /*Caso 5 - Gerenciar os veículos dos cliente acessando um novo submenu do sistema*/
                case 5:
                    // Gerenciar veículos
                    instancias.getMenuVeiculos().exibir(Interface, instancias);
                    break;
                /*Caso 6 - Listar todos os cadastros de clientes do sistema um por um, com nome, documento e veículos relacionados à ele*/
                case 6:
                    instancias.getClienteIns().listarClientes();
                    break;
                /*Caso 7 - Voltar*/   
                case 7:
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
