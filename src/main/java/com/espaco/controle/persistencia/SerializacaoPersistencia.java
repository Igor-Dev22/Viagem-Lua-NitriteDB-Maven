package com.espaco.controle.persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializacaoPersistencia<T extends Serializable> {
    
    private final String nomeArquivo;

    public SerializacaoPersistencia(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public void salvar(List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
            System.out.println("Dados salvos por Serializacao em: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> carregar() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            @SuppressWarnings("unchecked") 
            List<T> lista = (List<T>) ois.readObject();
            System.out.println("Dados carregados por Serializacao de: " + nomeArquivo);
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}