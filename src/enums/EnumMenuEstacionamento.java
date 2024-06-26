package enums;
/**
 *
 * @author Gustavo
 */
public enum EnumMenuEstacionamento {
    ESTACIONAR(1, "Estacionar veiculo"),
    RETIRAR(2, "Retirar Veiculo"),
    LISTAR_VAGAS_DISPONIVEIS(3, "Listar vagas disponiveis"),
    lISTAR_VAGAS_ALUGADAS(4, "Listar vagas alugadas"),
    VOLTAR(5, "Voltar");

    private final int num;
    private final String opDesc;

    EnumMenuEstacionamento(int numero, String opDesc) {
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
