package enums;

/**
 *
 * @author Gustavo
 */
/*Enumeração para os dias da semana*/
public enum EnumDiaSemana {
    
    SEGUNDA(1, "SEGUNDA-FEIRA"),
    TERCA(2, "TERÇA-FEIRA"),
    QUARTA(3, "QUARTA-FEIRA"),
    QUINTA(4, "QUINTA-FEIRA"),
    SEXTA(5, "SEXTA-FEIRA"),
    SABADO(6, "SABADO"),
    DOMINGO(7, "DOMINGO");
    
    
    private final int opcaodia;
    private final String descricao;
    
    EnumDiaSemana(int opcaodia, String descricao){
    
        this.opcaodia = opcaodia;
        this.descricao = descricao;
    
    }

    public int getOpcaodia() {
        return opcaodia;
    }

    public String getDescricao() {
        return descricao;
    }
    
    /*Método para obter o enum correspondente ao número do dia*/
    public static EnumDiaSemana getByOpcao(int opcao) {
        if (opcao >= 1 && opcao <= 7) {
            for (EnumDiaSemana dia : EnumDiaSemana.values()) {
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
