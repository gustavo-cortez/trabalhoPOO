package menus;

import enums.EnumMenuFuncoesGerais;
import interfaces.*;

public class MenuFuncoesGerais implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface,Instancias instancias) {

        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Clientes:\n");
            for (EnumMenuFuncoesGerais option : EnumMenuFuncoesGerais.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            opcao = Interface.solicitarInt(menu.toString());

            switch (opcao) {
                case 1:
                    instancias.getTicketsIns().listarTickets();
                    break;
                case 2:
                    Interface.exibirMensagem("Voltando ao menu principal...");
                    break;
                default:
                    Interface.exibirErro("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 2);
    }
}
 