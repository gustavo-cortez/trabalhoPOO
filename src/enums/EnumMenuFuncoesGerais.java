package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuFuncoesGerais {
    LISTAR_TICEKT(1, "Listar tickets"),
    TARIFAS(2, "Gerenciar Tarifas"),
    VOLTAR(3, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuFuncoesGerais(int numero, String opDesc) {
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
