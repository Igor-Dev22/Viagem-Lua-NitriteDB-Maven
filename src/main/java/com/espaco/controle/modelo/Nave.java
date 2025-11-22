package com.espaco.controle.modelo;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dizitart.no2.objects.Id;

@Data
@NoArgsConstructor
public class Nave implements Serializable {
    @Id
    private Long id;
    private String nome;
    private String modelo; // Ex: Saturn V, Falcon 9
    
    public Nave(String nome, String modelo) {
        this.id = System.nanoTime();
        this.nome = nome;
        this.modelo = modelo;
    }
}