package menus;

import classes.*;
import funcoesVisual.*;
import interfaces.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class MenuTarifas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        String[] opcoesSubMenuTarifas = {
            "Cadastrar tarifa",
            "Cadastrar tarifa mensalista",
            "Listar tarifas cadastradas",
            "Voltar"
        };

        int opcaoTarifa;
        do {
            opcaoTarifa = JOptionPane.showOptionDialog(null, "Submenu - Gerenciar Tarifas:", "Gerenciar Tarifas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuTarifas, opcoesSubMenuTarifas[0]);

            switch (opcaoTarifa) {
                case 0:
                    String dataStr = JOptionPane.showInputDialog("Informe a data de início (formato dd/MM/yyyy):", JOptionPane.OK_OPTION);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate data = LocalDate.parse(dataStr, formatter);

                    double tarifaBase = Double.parseDouble(JOptionPane.showInputDialog("Informe a tarifa base:", JOptionPane.OK_OPTION));
                    double taxaAdicional = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor das horas subsequentes:", JOptionPane.OK_OPTION));
                    instancias.getTarifasIns().cadastrarTarifa(data, tarifaBase, taxaAdicional);
                    break;
                case 1:
                    dataStr = JOptionPane.showInputDialog("Informe a data de início (formato dd/MM/yyyy):", JOptionPane.OK_OPTION);
                    formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    data = LocalDate.parse(dataStr, formatter);
                    double taxaMensal = Double.parseDouble(JOptionPane.showInputDialog("Informe a taxa mensal:", JOptionPane.OK_OPTION));
                    instancias.getTarifasIns().cadastrarTarifaMensal(data, taxaMensal);
                    break;
                case 2:
                    instancias.getTarifasIns().listarTarifas();
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Voltando...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcaoTarifa != 3);
    }
}
