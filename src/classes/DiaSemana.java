package classes;

/**
 *
 * @author Gustavo
 */
// Enumeração para os dias da semana
public enum DiaSemana {
    DOMINGO(1, "DOMINGO"),
    SEGUNDA(2, "SEGUNDA-FEIRA"),
    TERCA(3, "TERÇA-FEIRA"),
    QUARTA(4, "QUARTA-FEIRA"),
    QUINTA(5, "QUINTA-FEIRA"),
    SEXTA(6, "SEXTA-FEIRA"),
    SABADO(7, "SABADO");
    
    
    private final int opcaodia;
    private final String descricao;
    
    DiaSemana(int opcaodia, String descricao){
    
        this.opcaodia = opcaodia;
        this.descricao = descricao;
    
    }

    public int getOpcaodia() {
        return opcaodia;
    }

    public String getDescricao() {
        return descricao;
    }
    
    // Método para obter o enum correspondente ao número do dia
    public static DiaSemana getByOpcao(int opcao) {
        if (opcao >= 1 && opcao <= 7) {
            for (DiaSemana dia : DiaSemana.values()) {
                if (dia.opcaodia == opcao) {
                    return dia;
                }
            }
        }
        else{
            System.out.println("Opção de dia inválida: " + opcao);
        }
        return null;
    }
    
    
}
