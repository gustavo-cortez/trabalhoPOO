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
            List<EnumMenuVeiculos> opcoesMenuVeiculos = List.of(EnumMenuVeiculos.values());
            
            opcao = Interface.exibirMenus("Submenu - Veículos", opcoesMenuVeiculos);

            switch (opcao) {
                case 1:
                    // Cadastrar veículo
                    String documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    String veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo:");
                    String modeloVeiculo = Interface.solicitarEntrada("Informe o modelo do veículo:");
                    String corVeiculo = Interface.solicitarEntrada("Informe a cor do veículo:");
                    String tipoVeiculoStr = (String) Interface.solicitarEntradaMaior("Selecione o tipo da vaga.", "Tipo de Vaga", new String[]{"CARRO", "MOTO", "ÔNIBUS"}, "CARRO");
                    if (tipoVeiculoStr != null) {
                        instancias.getClienteIns().adicionarVeiculoCliente(veiculoPlaca, EnumTipoVeiculo.valueOf(tipoVeiculoStr), documentoCli, corVeiculo, modeloVeiculo, null);
                    }
                    
                    break;

                case 2:
                    // Consultar veículo por documento
                    documentoCli = Interface.solicitarEntrada("Informe o documento:");
                    instancias.getClienteIns().consultarVeiculo(documentoCli);
                    break;

                case 3:
                    // Excluir veículo do cliente
                    documentoCli = Interface.solicitarEntrada("Informe o documento do cliente:");
                    veiculoPlaca = Interface.solicitarEntrada("Informe a placa do veículo que deseja excluir:"); 
                    if(instancias.getClienteIns().excluirVeiculo(documentoCli, veiculoPlaca) == false){
                        Interface.exibirErro("Erro ao excluir o veículo do cliente.");
                    }
                    else{
                        Interface.exibirSucesso("Veículo excluido com sucesso.");
                    }
                    break;

                case 4:
                    // Voltar
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
