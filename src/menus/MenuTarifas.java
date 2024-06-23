package menus;

import enums.EnumMenuTarifas;
import interfaces.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

            opcao = Interface.solicitarInt(menu.toString());

            switch (opcao) {
                case 1:
                    boolean continua = false;
                    String dataStr;
                    DateTimeFormatter formatter;
                    LocalDate data = null;
                    do{
                        try{
                            dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            data = LocalDate.parse(dataStr, formatter);
                            continua = true;
                        }
                        catch(DateTimeParseException e){
                            Interface.exibirErro("FORMATO DE ENTRADA INCORRETO");
                        }
                    }while(continua == false);
                    double tarifaBase = Interface.solicitarDouble("Informe a tarifa base:");
                    double taxaAdicional = Interface.solicitarDouble("Informe o valor das horas subsequentes:");
                    instancias.getTarifasIns().cadastrarTarifa(data, tarifaBase, taxaAdicional);
                    break;
                case 2:
                    continua = false;
                    data = null;
                    do{
                        try{
                            dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            data = LocalDate.parse(dataStr, formatter);
                            continua = true;
                        }
                        catch(DateTimeParseException e){
                            Interface.exibirErro("FORMATO DE ENTRADA INCORRETO");
                        }  
                        double taxaMensal = Interface.solicitarDouble("Informe a taxa mensal:");
                        instancias.getTarifasIns().cadastrarTarifaMensal(data, taxaMensal);
                    }while(continua == false);
                    break;
                case 3:
                    instancias.getTarifasIns().listarTarifas();
                    break;
                case 4:
                    Interface.exibirMensagem("Voltando...");
                    break;
                default:
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 4);
    }
}
