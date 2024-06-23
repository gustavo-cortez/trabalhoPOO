package funcoes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import menus.Instancias;
import classes.*;

public class FunPersistenciaDados {
    private Instancias instancias;
    
    public FunPersistenciaDados(Instancias instancias) {
        this.instancias = instancias;
    }
    
    public void salvarDados(String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(instancias.getClienteIns().clientes);
            outputStream.writeObject(instancias.getVagasIns().vagas);
            outputStream.writeObject(instancias.getTicketsIns().tickets);
            outputStream.writeObject(instancias.getTarifasIns().tarifasHoristas);
            outputStream.writeObject(instancias.getTarifasIns().tarifasMensalistas);
            instancias.getInterface().exibirSucesso("Dados do estacionamento salvos com sucesso!");
        } catch (IOException e) {
            instancias.getInterface().exibirErro("Erro ao salvar os dados do estacionamento: " + e.getMessage());
        }
    }

    public void carregarDados(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            instancias.getClienteIns().clientes = (List<Cliente>) inputStream.readObject();
            instancias.getVagasIns().vagas = (List<Vagas>) inputStream.readObject();
            instancias.getTicketsIns().tickets = (List<Ticket>) inputStream.readObject();
            instancias.getTarifasIns().tarifasHoristas = (List<TarifaHorista>) inputStream.readObject();
            instancias.getTarifasIns().tarifasMensalistas = (List<TarifaMensalista>) inputStream.readObject();
            instancias.getInterface().exibirSucesso("Dados do estacionamento carregados com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            instancias.getInterface().exibirErro("Erro ao carregar os dados do estacionamento: " + e.getMessage());
        }
    }
}
