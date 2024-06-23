package enums;

/**
 *
 * @author Gustavo
 */
// Enumeração para o tipo de veículo
public enum EnumTipoVeiculo {
    CARRO(1.0),
    MOTO(0.5),
    ÔNIBUS(1.5);
    
    private final double Multiplicador;

    private EnumTipoVeiculo(double Multiplicador) {
        this.Multiplicador = Multiplicador;
    }

    public double getMultiplicador() {
        return Multiplicador;
    }
    
}
