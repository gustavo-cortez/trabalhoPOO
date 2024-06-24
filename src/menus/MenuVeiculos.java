package menus;

import enums.EnumTipoVeiculo;
import interfaces.*;
import enums.EnumMenuVeiculos;
import java.util.List;

public class MenuVeiculos implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        int opcao;
        do {
            /*Imprimir o menu principal dependendo da interface escolhida*/
            List<EnumMenuVeiculos> opcoesMenuVeiculos = List.of(EnumMenuVeiculos.values());
            
            opcao = Interface.exibirMenus("Submenu - Veículos", opcoesMenuVeiculos);

            switch (opcao) {
                case 1:
                    // Caso 1 - Cadastra veículo com base no documento do cliente, placa do veiculo, modelo, cor e tipo do veiculo
                    String documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    String veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo:");
                    String modeloVeiculo = Interface.solicitarEntrada("Informe o modelo do veículo:");
                    String corVeiculo = Interface.solicitarEntrada("Informe a cor do veículo:");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if(documentoCli == null || veiculoPlaca == null || modeloVeiculo == null || corVeiculo == null){
                        break;
                    }
                    if (tipoVeiculoStr != null) {
                        instancias.getClienteIns().adicionarVeiculoCliente(veiculoPlaca, EnumTipoVeiculo.valueOf(tipoVeiculoStr), documentoCli, corVeiculo, modeloVeiculo, null);
                    }
                    
                    break;

                case 2:
                    // Caso 2 - Consultar veículo por placa
                    String placa = Interface.solicitarEntrada("Informe a placa do veiculo:");
                    if(placa == null){
                        break;
                    }
                    instancias.getClienteIns().consultarVeiculo(placa);
                    break;

                case 3:
                    // Caso 3 - Excluir veículo do cliente
                    documentoCli = Interface.solicitarEntrada("Informe o documento do cliente:");
                    veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo que deseja excluir:"); 
                    if(documentoCli == null || veiculoPlaca == null){
                        break;
                    }
                    if(instancias.getClienteIns().excluirVeiculo(documentoCli, veiculoPlaca) == false){
                        Interface.exibirErro("Erro ao excluir o veículo do cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Veículo excluido com sucesso.");
                    }
                    break;

                case 4:
                    // Caso 4 - Voltar
                    Interface.exibirMensagem("Voltando ao menu de clientes...");
                    break;

                default:
                    if(opcao == 0){
                        opcao = 4;
                        break;
                    }
                    Interface.exibirErro("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 4);
    }
}
