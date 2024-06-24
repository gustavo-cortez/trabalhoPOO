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
    CONSULTAR_FATURAMENTO_VEICULO(6, "Consultar Faturamento por Veículo"),
    SAIR(7, "Sair");

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
}
