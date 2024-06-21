package menus;

import enums.EnumUsoEstacionamento;
import classes.*;
import enums.EnumMenuClientes;
import funcoesVisual.FunClienteVisual;
import funcoesVisual.FunTicketsVisual;
import interfaces.UserInterface;
import javax.swing.JOptionPane;

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
                    String nomeCli = JOptionPane.showInputDialog("Digite o nome do cliente:");
                    String documentoCli = JOptionPane.showInputDialog("Digite o CPF do cliente:");
                    instancias.getClienteIns().cadastrarCliente(nomeCli, documentoCli);
                    break;

                case CONSULTAR_CLIENTE:
                    // Consultar cliente por documento
                    documentoCli = JOptionPane.showInputDialog("Digite o CPF do cliente a ser consultado:");
                    Cliente clienteConsulta = instancias.getClienteIns().consultarCliente(documentoCli);
                    if (clienteConsulta != null) {
                        JOptionPane.showMessageDialog(null, "Nome: " + clienteConsulta.getNome() + "\nDocumento: " + clienteConsulta.getDocumento());
                    } else {
                        JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
                    }
                    break;

                case EXCLUIR_CLIENTE:
                    // Excluir cliente
                    documentoCli = JOptionPane.showInputDialog("Digite o CPF do cliente a ser excluído:");
                    if(instancias.getClienteIns().excluirCliente(documentoCli, instancias.getTicketsIns()) == false){
                        JOptionPane.showMessageDialog(null, "Erro ao excluir o cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case EDITAR_CLIENTE:
                    // Editar cliente
                    nomeCli = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
                    documentoCli = JOptionPane.showInputDialog("Digite o CPF do cliente a ser alterado:");
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
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
            }
            catch (IllegalArgumentException e) {
                Interface.exibirMensagem(e.getMessage());
            }
        } while (opcao != EnumMenuClientes.VOLTAR.getNum());
    }
}
