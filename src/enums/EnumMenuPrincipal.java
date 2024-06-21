package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuPrincipal {
    CLIENTES(1, "Clientes"),
    VAGAS(2, "Vagas"),
    ESTACIONAMENTO(3, "Estacionamento"),
    FUNCOES_GERAIS(4, "Funções Gerais"),
    CONSULTAR_FATURAMENTO(5, "Consultar Faturamento por Período"),
    SAIR(6, "Sair");

    private final int num;
    private final String opDesc;

    EnumMenuPrincipal(int numero, String opDesc) {
        this.num = numero;
        this.opDesc = opDesc;
    }

    public int getNum() {
        return num;
    }

    public String getOpcao() {
        return opDesc;
    }

    @Override
    public String toString() {
        return num + " - " + opDesc;
    }

    public static EnumMenuPrincipal porNumero(int numero) {
        for (EnumMenuPrincipal opcao : EnumMenuPrincipal.values()) {
            if (opcao.getNum() == numero) {
                return opcao;
            }
        }
        throw new IllegalArgumentException("Opção inválida: " + numero);
    }
}
