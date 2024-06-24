package funcoes;
import enums.EnumDiaSemana;
import classes.TarifaMensalista;
import classes.TarifaHorista;
import classes.Veiculo;
import classes.Ticket;
import enums.EnumTipoVeiculo;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.Duration;
import java.time.LocalTime;
import menus.Instancias;
/**
 *
 * @author Gustavo
 */
public class FunTarifas {
    
    public List<TarifaHorista> tarifasHoristas;
    public List<TarifaMensalista> tarifasMensalistas;
    public List<Ticket> ticketsMensalistas;
    private Instancias instancias;
    public FunTarifas(Instancias instancias){
        this.tarifasHoristas = new ArrayList<>();
        this.tarifasMensalistas = new ArrayList<>();
        this.ticketsMensalistas = new ArrayList<>();
        this.instancias = instancias;
    }
    /*Método para cadastrar uma nova tarifa, é nescessário para cadastrar a tarifa informar os dias da semana 
    atráves de número do ENUM: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC..., numero abaixo de 1 e maior que 7 para a leitura dos dias*/;
    public void cadastrarTarifa(LocalDate inicio, double valorPrimeiraHora, double valorHoraSubsequente) {
        try {
            Scanner scanner = new Scanner(System.in);
            List<EnumDiaSemana> diasSemana = new ArrayList<>();
            /*Solicitar os dias da semana ao usuário atráves de números do ENUM*/
            int dia;
            instancias.getInterface().exibirMensagem("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC...):");
            dia = instancias.getInterface().solicitarInt("");
            while(dia <= 7 && dia > 0){
                EnumDiaSemana diaSemana = EnumDiaSemana.getByOpcao(dia); /*Obtém o enum correspondente ao dia informado*/
                diasSemana.add(diaSemana); /*Adiciona o dia à lista*/
                dia = instancias.getInterface().solicitarInt("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-DOMINGO 2-SEGUNDA-FEIRA, ETC...):");
            }
            TarifaHorista tarifa = new TarifaHorista(inicio, valorPrimeiraHora, valorHoraSubsequente, diasSemana.toArray(EnumDiaSemana[]::new));
            tarifasHoristas.add(tarifa);
            instancias.getInterface().exibirSucesso("Tarifa cadastrada com sucesso!");
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao cadastrar tarifa: " + e.getMessage());
        }
    }
    
    public void cadastrarTarifaMensal(LocalDate inicio, double valorMensal) {
        try {
            TarifaMensalista tarifa = new TarifaMensalista(inicio, valorMensal);
            tarifasMensalistas.add(tarifa);
            instancias.getInterface().exibirSucesso("Tarifa cadastrada com sucesso!");
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao cadastrar tarifa mensal: " + e.getMessage());
        }
    }
    
    /*Método para listar todas as tarifas*/
    public void listarTarifas() {
        StringBuilder mensagem = new StringBuilder();
        for (TarifaMensalista tarifa : tarifasMensalistas) {
            mensagem.append("Inicio: ").append(tarifa.getInicio());
                    
            mensagem.append(", Valor Mensal: ")
                    .append(tarifa.getValorMensal())
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
        instancias.getInterface().exibirMensagem(mensagem.toString());
    }
    public double calcularValorTarifaHorista(Ticket ticket, LocalDateTime horaSaida) {
        double valorTotal;
        try {
            // Obtém a tarifa horista aplicável para o dia da semana da entrada do veículo
            TarifaHorista tarifa = encontrarTarifaHorista(ticket.getInicio().getDayOfWeek());

            if (tarifa == null) {
                throw new Exception("Tarifa não encontrada para o dia da semana.");
            }

            // Calcula a diferença de tempo entre a entrada e a saída do veículo
            Duration duracaoEstacionamento = Duration.between(ticket.getInicio(), horaSaida);
            long minutosEstacionados = duracaoEstacionamento.toMinutes();

            // Calcula o valor total da tarifa com base no tempo estacionado
            valorTotal = tarifa.getValorPrimeiraHora(); // Valor inicial é o da primeira hora

            if (minutosEstacionados > 60) {
                // Se o veículo permaneceu estacionado por mais de uma hora
                long horasExtras = (long) Math.ceil((minutosEstacionados - 60) / 60.0); // Calcula horas extras
                valorTotal += horasExtras * tarifa.getValorHoraSubsequente(); // Adiciona o valor das horas extras
            }
        }
        catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao calcular valor da tarifa horista: " + e.getMessage());
            return -1; // Indica que ocorreu um erro
        }

        return valorTotal;
    }

    public TarifaHorista encontrarTarifaHorista(DayOfWeek diaDaSemana) {
        TarifaHorista tarifaEncontrada = null;

        for (TarifaHorista tarifa : tarifasHoristas) {
            for (EnumDiaSemana dia : tarifa.getDiasSemana()) {
                if (dia.getOpcaodia() == diaDaSemana.getValue() && tarifa.getInicio().isBefore(LocalDate.now().plusDays(1))) {
                    if (tarifaEncontrada == null || (tarifa.getInicio().isAfter(tarifaEncontrada.getInicio()) && tarifaEncontrada.getInicio().isBefore(LocalDate.now().plusDays(1)))) {
                        tarifaEncontrada = tarifa; // Atualiza para a tarifa mais recente encontrada
                    }
                }
            }
        }

        return tarifaEncontrada; // Retorna a tarifa mais recente encontrada ou null se nenhuma tarifa for encontrada
    }

    public double calcularValorTarifaMensalista(Ticket ticket, LocalDate dataAtual) {
        double valorTotal;
        try{
            // Verifica se já existe um ticket mensalista para o veículo
            if (existeTicketMensalista(ticket.getVeiculo())) {
                throw new Exception("Já existe um ticket mensalista para este veículo.");
            }

            // Obtém a tarifa mensalista aplicável
            TarifaMensalista tarifaMensalista = encontrarTarifaMensalista();

            if (tarifaMensalista == null) {
                throw new Exception("Tarifa mensalista não encontrada.");
            }

            // Calcula a duração do período mensal
            LocalDate dataInicio = ticket.getInicio().toLocalDate();
            LocalDate dataFim = dataInicio.plusDays(30); // Adiciona 30 dias ao início para obter o fim do período
            LocalDateTime horaAtual = LocalDateTime.of(dataAtual, LocalTime.now());

            // Verifica se o período mensal já terminou
            if (horaAtual.isAfter(LocalDateTime.of(dataFim, LocalTime.MAX))) {
                instancias.getInterface().exibirMensagem("Período mensal já expirou.");
                return -1; // Indica que o período mensal já expirou
            }

            // Calcula o valor da tarifa mensalista
            valorTotal = tarifaMensalista.getValorMensal(); // Valor fixo mensal
        } catch (Exception e) {
            instancias.getInterface().exibirErro("Erro ao calcular valor da tarifa mensalista: " + e.getMessage());
            return -1; // Indica que ocorreu um erro
        }
        return valorTotal;
    }

    public TarifaMensalista encontrarTarifaMensalista() {
        TarifaMensalista tarifaMensalistaEncontrada = null;

        for (TarifaMensalista tarifa : tarifasMensalistas) {
            if (tarifa.getInicio().isBefore(LocalDate.now().plusDays(1))) {
                if (tarifaMensalistaEncontrada == null || tarifa.getInicio().isAfter(tarifaMensalistaEncontrada.getInicio())) {
                    tarifaMensalistaEncontrada = (TarifaMensalista) tarifa; // Atualiza para a tarifa mensalista mais recente encontrada
                }
            }
        }

        return tarifaMensalistaEncontrada; // Retorna a tarifa mensalista mais recente encontrada ou null se nenhuma tarifa mensalista for encontrada
    }


    private boolean existeTicketMensalista(Veiculo veiculo) {
        // Verifica se existe algum ticket mensalista para o veículo fornecido
        for (Ticket ticket : ticketsMensalistas) {
            if (ticket.getVeiculo().equals(veiculo)) {
                return true; // Retorna true se existir um ticket mensalista para o veículo
            }
        }
        return false; // Retorna false se não existir nenhum ticket mensalista para o veículo
    }


    
}
