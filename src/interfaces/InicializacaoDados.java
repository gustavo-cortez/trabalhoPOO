package interfaces;
import enums.EnumTipoVeiculo;
import enums.EnumUsoEstacionamento;
import java.time.LocalDate;
import funcoes.*;
/**
 *
 * @author Gusta
 */
public class InicializacaoDados {
  
    public static void inicializarVagas(FunVagas vagasIns) {
        // Adicionando vagas
        vagasIns.cadastrarVaga(1, "Rua A", EnumTipoVeiculo.CARRO);
        vagasIns.cadastrarVaga(2, "Rua B", EnumTipoVeiculo.MOTO);
        vagasIns.cadastrarVaga(3, "Rua C", EnumTipoVeiculo.CARRO);
        vagasIns.cadastrarVaga(4, "Rua D", EnumTipoVeiculo.MOTO);
    }
    
    public static void inicializarClientes(FunCliente clientesIns) {
        // Adicionando clientes
        clientesIns.cadastrarCliente("Cliente A", "11111111111");
        clientesIns.cadastrarCliente("Cliente B", "22222222222");
        clientesIns.cadastrarCliente("Cliente C", "33333333333");
        clientesIns.cadastrarCliente("Cliente D", "44444444444");
    }
    
    public static void inicializarVeiculos(FunCliente clienteIns) {
        // Adicionando veículos
        clienteIns.adicionarVeiculoCliente("ABC1234",EnumTipoVeiculo.CARRO, "11111111111" , "Vermelho", "Sedan", EnumUsoEstacionamento.HORISTA);
        clienteIns.adicionarVeiculoCliente("DEF5678", EnumTipoVeiculo.MOTO, "22222222222" ,"Azul", "Esportiva",  EnumUsoEstacionamento.HORISTA);
        clienteIns.adicionarVeiculoCliente("GHI9012", EnumTipoVeiculo.CARRO, "33333333333" , "Preto", "SUV",EnumUsoEstacionamento.MENSALISTA);
        clienteIns.adicionarVeiculoCliente("JKL3456", EnumTipoVeiculo.MOTO, "44444444444", "Verde", "Custom",EnumUsoEstacionamento.MENSALISTA);
    }

}
