package menus;

import enums.EnumMenuFuncoesGerais;
import interfaces.*;
import java.util.List;

public class MenuFuncoesGerais implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface,Instancias instancias) {
        instancias.getTicketsIns().verificarTicketsMensalistas();
        int opcao;
        do {
            /*Imprimir o menu dependendo da interface escolhida*/
            List<EnumMenuFuncoesGerais> opcoesMenuFuncoesGerais = List.of(EnumMenuFuncoesGerais.values());
            
            opcao = Interface.exibirMenus("Submenu - Funções Gerais", opcoesMenuFuncoesGerais);

            switch (opcao) {
                /*Caso 1 - Listar todos os tickets gerados pelo sistema, tanto Horista quanto Mensalista*/
                case 1:
                    instancias.getTicketsIns().listarTickets();
                    break;
                /*Caso 2 - Exibir o submenu tarifas*/
                case 2:
                    instancias.getMenuTarifas().exibir(Interface, instancias);
                    break;
                /*Caso 3 - Voltar*/
                case 3:
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;
                default:
                    if(opcao == 0){
                        opcao = 3;
                        break;
                    }
                    Interface.exibirErro("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 3);
    }
}
 