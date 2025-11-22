package com.espaco.controle;

import com.espaco.controle.modelo.*;
import com.espaco.controle.persistencia.NitritePersistencia;
import java.util.List;
import java.util.Scanner;

public class App {
    
    // Scanner e Banco de Dados acessíveis globalmente no App
    private static final Scanner scanner = new Scanner(System.in);
    private static NitritePersistencia db;

    public static void main(String[] args) {
        // Inicializa o Banco
        db = new NitritePersistencia("mission_control.db");

        System.out.println("🚀 --- SISTEMA DE CONTROLE ESPACIAL --- 🚀");
        
        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1" -> menuMissoes();
                case "2" -> menuAstronautas();
                case "3" -> menuNaves();
                case "4" -> menuResultados();
                case "0" -> {
                    System.out.println("Saindo do sistema... Até logo, comandante!");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
        
        // Fecha o banco e o scanner ao sair
        db.fecharDB();
        scanner.close();
    }

    // --- MENUS PRINCIPAIS ---

    private static void exibirMenuPrincipal() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Gerenciar Missões");
        System.out.println("2. Gerenciar Astronautas");
        System.out.println("3. Gerenciar Naves");
        System.out.println("4. Gerenciar Resultados Científicos");
        System.out.println("0. Sair");
        System.out.print("> ");
    }

    // --- 1. LÓGICA DE MISSÕES ---
    private static void menuMissoes() {
    System.out.println("\n--- GESTÃO DE MISSÕES ---");
    System.out.println("1. Cadastrar Nova Missão");
    System.out.println("2. Listar Todas as Missões");
    System.out.print("Opção > ");
    String op = scanner.nextLine();

    if (op.equals("1")) {
        System.out.print("Nome da Missão: ");
        String nome = scanner.nextLine();
        System.out.print("Data Lançamento (ex: 2025-12-01): ");
        String data = scanner.nextLine();
        
        Missao m = new Missao(nome, data);
        m.setIdMissao(System.nanoTime());
        
        db.salvar(m);
        System.out.println("✅ Missão cadastrada com sucesso!");
        
    } else if (op.equals("2")) {
        List<Missao> lista = db.listarTodos(Missao.class);
        
        if (lista.isEmpty()) {
            System.out.println("⚠️ Nenhuma missão registada.");
        } else {
            System.out.println("\n--------------------------------------------------------------------");
            System.out.printf("%-20s | %-20s | %-15s | %-10s%n", "ID (Parcial)", "NOME", "DESTINO", "DATA");
            System.out.println("--------------------------------------------------------------------");
            for (Missao m : lista) {
                // Mostramos apenas os últimos dígitos do ID para não poluir o ecrã
                String idCurto = String.valueOf(m.getIdMissao() % 10000); 
                System.out.printf("%-20s | %-20s | %-15s | %-10s%n", 
                    "..." + idCurto, m.getNome(), m.getDestino(), m.getDataLancamento());
            }
            System.out.println("--------------------------------------------------------------------");
        }
    }
}

    // --- 2. LÓGICA DE ASTRONAUTAS ---
  private static void menuAstronautas() {
    System.out.println("\n--- GESTÃO DE ASTRONAUTAS ---");
    System.out.println("1. Cadastrar Astronauta");
    System.out.println("2. Listar Todos os Astronautas");
    System.out.print("Opção > ");
    String op = scanner.nextLine();

    if (op.equals("1")) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Especialidade (ex: Piloto): ");
        String esp = scanner.nextLine();
        
        db.salvar(new Astronauta(nome, esp));
        System.out.println("✅ Astronauta cadastrado!");
        
    } else if (op.equals("2")) {
        List<Astronauta> lista = db.listarTodos(Astronauta.class);
        
        if (lista.isEmpty()) {
            System.out.println("⚠️ Nenhum astronauta registado.");
        } else {
            System.out.println("\n--------------------------------------------------");
            System.out.printf("%-25s | %-20s%n", "NOME DO ASTRONAUTA", "ESPECIALIDADE");
            System.out.println("--------------------------------------------------");
            for (Astronauta a : lista) {
                System.out.printf("%-25s | %-20s%n", a.getNome(), a.getEspecialidade());
            }
            System.out.println("--------------------------------------------------");
        }
    }
}

    // --- 3. LÓGICA DE NAVES ---
   private static void menuNaves() {
    System.out.println("\n--- GESTÃO DE NAVES ---");
    System.out.println("1. Cadastrar Nave");
    System.out.println("2. Listar Frota de Naves");
    System.out.print("Opção > ");
    String op = scanner.nextLine();

    if (op.equals("1")) {
        System.out.print("Nome da Nave: ");
        String nome = scanner.nextLine();
        System.out.print("Modelo (ex: Falcon 9): ");
        String modelo = scanner.nextLine();
        
        db.salvar(new Nave(nome, modelo));
        System.out.println("✅ Nave cadastrada!");
        
    } else if (op.equals("2")) {
        List<Nave> lista = db.listarTodos(Nave.class);
        
        if (lista.isEmpty()) {
            System.out.println("⚠️ Nenhuma nave registada na frota.");
        } else {
            System.out.println("\n--------------------------------------------------");
            System.out.printf("%-25s | %-20s%n", "NOME DA NAVE", "MODELO");
            System.out.println("--------------------------------------------------");
            for (Nave n : lista) {
                System.out.printf("%-25s | %-20s%n", n.getNome(), n.getModelo());
            }
            System.out.println("--------------------------------------------------");
        }
    }
}

    // --- 4. LÓGICA DE RESULTADOS ---
    private static void menuResultados() {
    System.out.println("\n--- RESULTADOS CIENTÍFICOS ---");
    System.out.println("1. Registrar Resultado");
    System.out.println("2. Consultar Resultados de Expedições");
    System.out.print("Opção > ");
    String op = scanner.nextLine();

    if (op.equals("1")) {
        System.out.print("Título da Descoberta: ");
        String titulo = scanner.nextLine();
        System.out.print("Descrição breve: ");
        String desc = scanner.nextLine();
        System.out.print("Nome da Missão Origem: ");
        String missao = scanner.nextLine();
        
        db.salvar(new Resultado(titulo, desc, missao));
        System.out.println("✅ Resultado registado!");
        
    } else if (op.equals("2")) {
        List<Resultado> lista = db.listarTodos(Resultado.class);
        
        if (lista.isEmpty()) {
            System.out.println("⚠️ Nenhum resultado científico arquivado.");
        } else {
            System.out.println("\n--------------------------------------------------------------------");
            System.out.printf("%-30s | %-30s%n", "TÍTULO DA DESCOBERTA", "MISSÃO VINCULADA");
            System.out.println("--------------------------------------------------------------------");
            for (Resultado r : lista) {
                System.out.printf("%-30s | %-30s%n", r.getTitulo(), r.getNomeMissaoVinculada());
                // Mostra a descrição numa linha abaixo para facilitar a leitura
                System.out.println("   ↳ Descrição: " + r.getDescricao());
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            }
        }
    }
}
}