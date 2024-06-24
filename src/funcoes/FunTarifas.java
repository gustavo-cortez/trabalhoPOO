package funcoes;
import enums.EnumDiaSemana;
import classes.TarifaMensalista;
import classes.TarifaHorista;
import classes.Veiculo;
import classes.Ticket;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
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
            List<EnumDiaSemana> diasSemana = new ArrayList<>();
            /*Solicitar os dias da semana ao usuário atráves de números do ENUM*/
            int dia;
            dia = instancias.getInterface().solicitarInt("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-SEGUNDA-FEIRA, 2-TERÇA-FEIRA, ETC...):");
            while(dia <= 7 && dia > 0){
                EnumDiaSemana diaSemana = EnumDiaSemana.getByOpcao(dia); /*Obtém o enum correspondente ao dia informado*/
                diasSemana.add(diaSemana); /*Adiciona o dia à lista*/
                dia = instancias.getInterface().solicitarInt("Informe os dias da semana em que a tarifa será aplicada(Com número como: 1-SEGUNDA-FEIRA, 2-TERÇA-FEIRA, ETC...):");
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

    public TarifaHorista encontrarTarifaHorista(DayOfWeek diaDaSemana) {
        TarifaHorista tarifaEncontrada = null;
        
        for (TarifaHorista tarifa : tarifasHoristas.reversed()) {
            for (EnumDiaSemana dia : tarifa.getDiasSemana()) {
                if (dia.getOpcaodia() == diaDaSemana.getValue() && tarifa.getInicio().isBefore(LocalDate.now().plusDays(1))) {
                    if (tarifaEncontrada == null || (tarifa.getInicio().isAfter(tarifaEncontrada.getInicio()) && tarifaEncontrada.getInicio().isBefore(LocalDate.now().plusDays(1)))) {
                        tarifaEncontrada = tarifa; // Atualiza para a tarifa mais recente encontrada
                    }
                }
            }
        }
        
        if(tarifaEncontrada == null){
            instancias.getInterface().exibirErro("Tarifa não encontrada para o dia da semana!");
        }
        
        return tarifaEncontrada; // Retorna a tarifa mais recente encontrada ou null se nenhuma tarifa for encontrada
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
        if(tarifaMensalistaEncontrada == null){
            instancias.getInterface().exibirErro("Tarifa mensalista não encontrada!");
        }
        return tarifaMensalistaEncontrada; // Retorna a tarifa mensalista mais recente encontrada ou null se nenhuma tarifa mensalista for encontrada
    }
    
}
