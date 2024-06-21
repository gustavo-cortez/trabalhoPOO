package funcoesVisual;

import funcoes.*;
import enums.EnumDiaSemana;
import classes.Tarifa;
import classes.TarifaMensalista;
import classes.TicketMensalista;
import classes.TarifaHorista;
import classes.Veiculo;
import classes.Ticket;
import enums.EnumTipoVeiculo;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.LocalTime;
/**
 * 
 * @author Gustavo
 */
public class FunTarifasVisual {

    public List<TarifaHorista> tarifasHoristas;
    public List<TarifaMensalista> tarifasMensalistas;
    public List<Ticket> ticketsMensalistas;

    public FunTarifasVisual() {
        this.tarifasHoristas = new ArrayList<>();
        this.tarifasMensalistas = new ArrayList<>();
        this.ticketsMensalistas = new ArrayList<>();
    }

    /* Método para cadastrar uma nova tarifa, é necessário para cadastrar a tarifa informar os dias da semana 
    através de número do ENUM: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC..., número abaixo de 1 e maior que 7 para encerrar a leitura dos dias */
    public void cadastrarTarifa(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente) {
        List<EnumDiaSemana> diasSemana = new ArrayList<>();
        int dia;
        do {
            String diaString = JOptionPane.showInputDialog("Informe os dias da semana em que a tarifa será aplicada (Com número como: 1-DOMINGO, 2-SEGUNDA-FEIRA, ETC...)\nDigite um número fora deste intervalo para encerrar:");
            dia = Integer.parseInt(diaString);
            if (dia >= 1 && dia <= 7) {
                EnumDiaSemana diaSemana = EnumDiaSemana.getByOpcao(dia); // Obtém o enum correspondente ao dia informado
                diasSemana.add(diaSemana); // Adiciona o dia à lista
            }
        } while (dia >= 1 && dia <= 7);

        TarifaHorista tarifa = new TarifaHorista(inicio, valorPrimeiraHora, valorHoraSubsequente, diasSemana.toArray(new EnumDiaSemana[0]));
        tarifasHoristas.add(tarifa);
        JOptionPane.showMessageDialog(null, "Tarifa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void cadastrarTarifaMensal(LocalDate inicio, double valorMensal) {
        TarifaMensalista tarifa = new TarifaMensalista(inicio, valorMensal);
        tarifasMensalistas.add(tarifa);
        JOptionPane.showMessageDialog(null, "Tarifa cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    /* Método para listar todas as tarifas */
    public void listarTarifas() {
        StringBuilder mensagem = new StringBuilder();
        for (TarifaMensalista tarifa : tarifasMensalistas) {
            mensagem.append("Inicio: ").append(tarifa.getInicio());
                    
            mensagem.append(", Valor Mensal: ")
                    .append(tarifa.getValorMensal(EnumTipoVeiculo.CARRO))
                    .append(", Tipo da tarifa : Mensalista");
            mensagem.append("\n");
        }
        for (TarifaHorista tarifa : tarifasHoristas) {
            mensagem.append("Inicio: ").append(tarifa.getInicio());
            mensagem.append(", Valor primeira hora: ")
                        .append(tarifa.getValorPrimeiraHora())
                        .append(", Valor hora subsequente: ")
                        .append(tarifa.getValorHoraSubsequente())
                        .append(", Dias da semana: ");
                for (EnumDiaSemana dia : tarifa.getDiasSemana()) {
                    mensagem.append(dia.getDescricao()).append(" ");
                }
                mensagem.append(", Tipo da tarifa : Horista");
            mensagem.append("\n");
        }
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Lista de Tarifas", JOptionPane.INFORMATION_MESSAGE);
    }

    public double calcularValorTarifaHorista(Ticket ticket, LocalDateTime horaSaida) {
        TarifaHorista tarifa = encontrarTarifaHorista(ticket.getInicio().getDayOfWeek());

        if (tarifa == null) {
            JOptionPane.showMessageDialog(null, "Tarifa não encontrada para o dia da semana.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1; // Indica que não foi encontrada uma tarifa válida
        }

        Duration duracaoEstacionamento = Duration.between(ticket.getInicio(), horaSaida);
        long minutosEstacionados = duracaoEstacionamento.toMinutes();
        double valorTotal = tarifa.getValorPrimeiraHora(); // Valor inicial é o da primeira hora

        if (minutosEstacionados > 60) {
            long horasExtras = (long) Math.ceil((minutosEstacionados - 60) / 60.0); // Calcula horas extras
            valorTotal += horasExtras * tarifa.getValorHoraSubsequente(); // Adiciona o valor das horas extras
        }

        return valorTotal;
    }

    public TarifaHorista encontrarTarifaHorista(DayOfWeek diaDaSemana) {
        TarifaHorista tarifaEncontrada = null;

        for (TarifaHorista tarifa : tarifasHoristas) {
            for (EnumDiaSemana dia : tarifa.getDiasSemana()) {
                if (dia.getOpcaodia() == diaDaSemana.getValue() && tarifa.getInicio().isBefore(LocalDate.now().plusDays(1))) {
                    if (tarifaEncontrada == null || (tarifa.getInicio().isAfter(tarifaEncontrada.getInicio()) && tarifaEncontrada.getInicio().isBefore(LocalDate.now().plusDays(1)))) {
                        tarifaEncontrada = (TarifaHorista) tarifa; // Atualiza para a tarifa mais recente encontrada
                    }
                }
            }
        }

        return tarifaEncontrada; // Retorna a tarifa mais recente encontrada ou null se nenhuma tarifa for encontrada
    }


    public double calcularValorTarifaMensalista(Ticket ticket, LocalDate dataAtual) {
        if (existeTicketMensalista(ticket.getVeiculo())) {
            JOptionPane.showMessageDialog(null, "Já existe um ticket mensalista para este veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1; // Indica que já existe um ticket mensalista para o veículo
        }

        TarifaMensalista tarifaMensalista = encontrarTarifaMensalista();

        if (tarifaMensalista == null) {
            JOptionPane.showMessageDialog(null, "Tarifa mensalista não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1; // Indica que não foi encontrada uma tarifa mensalista válida
        }

        LocalDate dataInicio = ticket.getInicio().toLocalDate();
        LocalDate dataFim = dataInicio.plusDays(30); // Adiciona 30 dias ao início para obter o fim do período
        LocalDateTime horaAtual = LocalDateTime.of(dataAtual, LocalTime.now());

        if (horaAtual.isAfter(LocalDateTime.of(dataFim, LocalTime.MAX))) {
            JOptionPane.showMessageDialog(null, "Período mensal já expirou.", "Erro", JOptionPane.ERROR_MESSAGE);
            return -1; // Indica que o período mensal já expirou
        }

        double valorTotal = tarifaMensalista.getValorMensal(EnumTipoVeiculo.MOTO); // Valor fixo mensal
        return valorTotal;
    }

    public TarifaMensalista encontrarTarifaMensalista() {
        TarifaMensalista tarifaMensalistaEncontrada = null;

        for (Tarifa tarifa : tarifasMensalistas) {
            if (tarifa instanceof TarifaMensalista && tarifa.getInicio().isBefore(LocalDate.now().plusDays(1))) {
                if (tarifaMensalistaEncontrada == null || tarifa.getInicio().isAfter(tarifaMensalistaEncontrada.getInicio())) {
                    tarifaMensalistaEncontrada = (TarifaMensalista) tarifa; // Atualiza para a tarifa mensalista mais recente encontrada
                }
            }
        }

        return tarifaMensalistaEncontrada; // Retorna a tarifa mensalista mais recente encontrada ou null se nenhuma tarifa mensalista for encontrada
    }



    private boolean existeTicketMensalista(Veiculo veiculo) {
        for (Ticket ticket : ticketsMensalistas) {
            if (ticket.getVeiculo().equals(veiculo)) {
                return true; // Retorna true se existir um ticket mensalista para o veículo
            }
        }
        return false; // Retorna false se não existir nenhum ticket mensalista para o veículo
    }
}