package com.espaco.controle.persistencia;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class NitritePersistencia {

    private final Nitrite db;

    public NitritePersistencia(String caminhoArquivo) {
        this.db = Nitrite.builder()
                .filePath(caminhoArquivo)
                .openOrCreate();
    }

    // Método Genérico para Salvar qualquer coisa (Missão, Astronauta, etc.)
    public <T> void salvar(T objeto) {
        ObjectRepository<T> repository = (ObjectRepository<T>) db.getRepository(objeto.getClass());
        repository.update(objeto, true); // Update com 'upsert' (insere se não existir)
    }

    // Método Genérico para Listar qualquer coisa
    public <T> List<T> listarTodos(Class<T> tipoClasse) {
        return db.getRepository(tipoClasse).find(ObjectFilters.ALL).toList();
    }

    public void fecharDB() {
        if (db != null && !db.isClosed()) {
            db.close();
        }
    }
}