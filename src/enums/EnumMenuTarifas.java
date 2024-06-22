package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuTarifas {
    CADASTRAR_TARIFA(1, "Cadastrar Tarifa Horista"),
    CADASTRAR_TARIFA_MENSALISTA(2, "Cadastrar Tarifa Mensalista"),
    lISTAR_TARIFAS_CADASTRADAS(3, "Listar Tarifas Cadastradas"),
    VOLTAR(4, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuTarifas(int numero, String opDesc) {
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

    public static EnumMenuTarifas porNumero(int numero) {
        for (EnumMenuTarifas opcao : EnumMenuTarifas.values()) {
            if (opcao.getNum() == numero) {
                return opcao;
            }
        }
        throw new IllegalArgumentException("Opção inválida: " + numero);
    }
}
