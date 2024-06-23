package funcoes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import menus.Instancias;
import classes.*;

/**
 *
 * @author Gustavo
 */
public class FunPersistenciaDados {
    private Instancias instancias;
    // Método para salvar os dados do estacionamento em um arquivo
    public void salvarDados(String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(instancias.getClienteIns().clientes);
            outputStream.writeObject(instancias.getVagasIns().vagas);
            outputStream.writeObject(instancias.getTicketsIns().tickets);
            outputStream.writeObject(instancias.getTarifasIns().tarifasHoristas);
            outputStream.writeObject(instancias.getTarifasIns().tarifasMensalistas);
            System.out.println("Dados do estacionamento salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados do estacionamento: " + e.getMessage());
        }
    }

    // Método para carregar os dados do estacionamento de um arquivo
    public void carregarDados(String nomeArquivo) {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
        instancias.getClienteIns().clientes = (List<Cliente>) inputStream.readObject();
        instancias.getVagasIns().vagas = (List<Vagas>) inputStream.readObject();
        instancias.getTicketsIns().tickets = (List<Ticket>) inputStream.readObject();
        instancias.getTarifasIns().tarifasHoristas = (List<TarifaHorista>) inputStream.readObject();
        instancias.getTarifasIns().tarifasMensalistas = (List<TarifaMensalista>) inputStream.readObject();
        System.out.println("Dados do estacionamento carregados com sucesso!");
    } catch (IOException | ClassNotFoundException e) {
        System.err.println("Erro ao carregar os dados do estacionamento: " + e.getMessage());
    }
}
}
