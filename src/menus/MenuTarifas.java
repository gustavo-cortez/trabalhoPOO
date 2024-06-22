package menus;

import enums.EnumMenuTarifas;
import interfaces.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MenuTarifas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
       
        int opcao;
        do {
            StringBuilder menu = new StringBuilder("Menu Principal:\n");
            for (EnumMenuTarifas option : EnumMenuTarifas.values()) {
                menu.append(option).append("\n");
            }
            menu.append("Escolha uma opção:");

            String opcaoStr = Interface.solicitarEntrada(menu.toString());
            opcao = Integer.parseInt(opcaoStr);

            switch (opcao) {
                case 0:
                    String dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(dataStr, formatter);

                    double tarifaBase = Interface.solicitarDouble("Informe a tarifa base:");
                    double taxaAdicional = Interface.solicitarDouble("Informe o valor das horas subsequentes:");
                    instancias.getTarifasIns().cadastrarTarifa(data, tarifaBase, taxaAdicional);
                    break;
                case 1:
                    dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(dataStr, formatter);
                    double taxaMensal = Interface.solicitarDouble("Informe a taxa mensal:");
                    instancias.getTarifasIns().cadastrarTarifaMensal(data, taxaMensal);
                    break;
                case 2:
                    instancias.getTarifasIns().listarTarifas();
                    break;
                case 3:
                    Interface.exibirMensagem("Voltando...");
                    break;
                default:
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 3);
    }
}
