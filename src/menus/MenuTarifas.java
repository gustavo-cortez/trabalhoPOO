package menus;

import enums.EnumMenuTarifas;
import interfaces.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MenuTarifas implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
       
        int opcao;
        do {
            /*Imprimir o menu principal dependendo da interface escolhida*/
            List<EnumMenuTarifas> opcoesMenuTarifas = List.of(EnumMenuTarifas.values());
            
            opcao = Interface.exibirMenus("Submenu - Tarifas", opcoesMenuTarifas);

            switch (opcao) {
                /*Caso 1 - Cadastra uma tarifa horista, com data, valor tarifa base e valor das horas subsequentes*/
                case 1:
                    boolean continua = false;
                    String dataStr = "";
                    DateTimeFormatter formatter;
                    LocalDate data = null;
                    do{
                        try{
                            dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                            if(dataStr == null){
                                break;
                            }
                            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            data = LocalDate.parse(dataStr, formatter);
                            continua = true;
                        }
                        catch(DateTimeParseException e){
                            Interface.exibirErro("FORMATO DE ENTRADA INCORRETO");
                        }
                    }while(continua == false);
                    if(dataStr == null){
                        break;
                    }
                    double tarifaBase = Interface.solicitarDouble("Informe a tarifa base:");
                    double taxaAdicional = Interface.solicitarDouble("Informe o valor das horas subsequentes:");
                    instancias.getTarifasIns().cadastrarTarifa(data, tarifaBase, taxaAdicional);
                    break;
                /*Caso 2 - Cadastra uma tarifa mensalista, com data e valor mensal*/
                case 2:
                    continua = false;
                    data = null;
                    do{
                        try{
                            dataStr = Interface.solicitarEntrada("Informe a data de início (formato dd/MM/yyyy):");
                            if(dataStr == null){
                                break;
                            }
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
                /*Caso 3 - Lista todas as tarifas, tanto mensal quanto horista*/
                case 3:
                    instancias.getTarifasIns().listarTarifas();
                    break;
                /*Caso 4 - Voltar para o menu anterior*/
                case 4:
                    Interface.exibirMensagem("Voltando...");
                    break;
                default:
                    if(opcao == 0){
                        opcao = 4;
                        break;
                    }
                    Interface.exibirMensagem("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 4);
    }
}
