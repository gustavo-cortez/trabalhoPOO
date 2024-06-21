package menus;

import classes.*;
import funcoesVisual.*;
import interfaces.*;
import javax.swing.JOptionPane;

public class MenuFuncoesGerais implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface,Instancias instancias) {
        String[] opcoesSubMenuFuncoesGerais = {
            "Listar Tickets",
            "Voltar"
        };

        int opcaoCadastros;
        do {
            opcaoCadastros = JOptionPane.showOptionDialog(null, "Submenu - Funções gerais:", "Funções gerais",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuFuncoesGerais, opcoesSubMenuFuncoesGerais[0]);

            switch (opcaoCadastros) {
                case 0:
                    instancias.getTicketsIns().listarTickets();
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcaoCadastros != 1);
    }
}
