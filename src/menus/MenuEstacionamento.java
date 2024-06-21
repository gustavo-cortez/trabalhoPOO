package menus;

import enums.EnumUsoEstacionamento;
import classes.*;
import funcoesVisual.*;
import interfaces.*;
import java.util.List;
import javax.swing.JOptionPane;

public class MenuEstacionamento implements MenuInterface {
    private Instancias instancias;

    @Override
    public void exibir(UserInterface Interface, Instancias instancias) {
        String[] opcoesSubMenuEstacionamento = {
            "Estacionar",
            "Retirar",
            "Listar todas as vagas disponíveis",
            "Listar todas as vagas alugadas",
            "Gerenciar tarifas",
            "Voltar"
        };
        instancias.getTicketsIns().verificarTicketsMensalistas(instancias.getVagasIns());
        int opcaoEstacionamento;
        do {
            opcaoEstacionamento = JOptionPane.showOptionDialog(null, "Submenu - Gerenciar estacionamento:", "Gerenciar estacionamento",
                    JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesSubMenuEstacionamento, opcoesSubMenuEstacionamento[0]);

            switch (opcaoEstacionamento) {
                case 0:
                    String placaEstacionar = JOptionPane.showInputDialog("Digite a placa do veículo a ser estacionado"); 
                    int numVaga = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da vaga para estacionar o veículo"));
                    if(instancias.getClienteIns().consultarPlaca(placaEstacionar) != null){
                        if(instancias.getClienteIns().consultarPlaca(placaEstacionar).getTipoUso().equals(EnumUsoEstacionamento.HORISTA)){
                            instancias.getVagasIns().estacionarVeiculo(instancias.getTicketsIns(), instancias.getClienteIns().consultarPlaca(placaEstacionar), numVaga, instancias.getTarifasIns());
                        }
                        else{
                            instancias.getVagasIns().estacionarVeiculoMensalista(instancias.getTicketsIns(), instancias.getClienteIns().consultarPlaca(placaEstacionar), numVaga, instancias.getTarifasIns());
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Veículo não encontrado ou cliente inexistente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 1:
                    numVaga = Integer.parseInt(JOptionPane.showInputDialog("Digite o número da vaga para retirar o veículo", JOptionPane.OK_OPTION));
                    if(instancias.getTicketsIns().buscarTicketPorVaga(numVaga) != null){
                        
                        if(instancias.getTicketsIns().buscarTicketPorVaga(numVaga).getTipo().equals(EnumUsoEstacionamento.HORISTA)){
                            instancias.getVagasIns().retirarVeiculo(instancias.getTicketsIns(), numVaga, instancias.getTarifasIns());
                        }
                        else{
                            instancias.getVagasIns().retirarVeiculoMensalista(instancias.getTicketsIns(), numVaga, instancias.getTarifasIns()); 
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Vaga não ocupada ou inexistente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    break;
                case 2:
                    List<Vagas> vagasDisponiveis = instancias.getVagasIns().listarVagasDisponiveis();
                    if (!vagasDisponiveis.isEmpty()) {
                        StringBuilder mensagem = new StringBuilder("Vagas disponíveis:");
                        for (Vagas vaga : vagasDisponiveis) {
                            mensagem.append("\nNúmero: ").append(vaga.getNumero()).append(", Rua: ").append(vaga.getRua()).append(", Tipo: ").append(vaga.getTipoVeiculo());
                        }
                        JOptionPane.showMessageDialog(null, mensagem.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Sem vagas disponíveis!");
                    }
                    break;
                case 3:
                    List<Vagas> vagasAlugadas = instancias.getVagasIns().listarVagasAlugadas();
                    if (!vagasAlugadas.isEmpty()) {
                        StringBuilder mensagem = new StringBuilder("Vagas alugadas:");
                        for (Vagas vaga : vagasAlugadas) {
                            mensagem.append("\nNúmero: ").append(vaga.getNumero()).append(", Rua: ").append(vaga.getRua()).append(", Tipo: ").append(vaga.getTipoVeiculo());
                        }
                        JOptionPane.showMessageDialog(null, mensagem.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "Sem vagas alugadas!");
                    }
                    break;
                case 4:
                    exibir(Interface, instancias);
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Voltando ao menu principal...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcaoEstacionamento != 5);
    }
}
