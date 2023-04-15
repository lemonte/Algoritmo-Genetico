/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.teste.ia;

import java.util.Collections;
import java.util.LinkedList;
import models.Individuo;

/**
 *
 * @author geandersonlemonte
 */
public class IA {

    public static void main(String[] args) {
        LinkedList<Individuo> populacao = Metodos.gerarPopulacao(5);
        int geracao = 0;
        double ultimoFitness = 0;
        int contador = 0;
        int chanceMutacao = 100; // 5
        int chanceCrossOver = 30; // 20
        int quantidadeMutacao = 2; // 2
        while (populacao.getFirst().getFitness() < 21312) {
            LinkedList<Individuo> filhos = new LinkedList();
            for (int i = 0; i < Metodos.TAMANHOPOPULACAO / 2; i++) {
                final LinkedList<Individuo> itensSelecionados = Metodos.selecaoPorRoleta(2, populacao);
                final LinkedList<Individuo> itensAposCrossOver = Metodos.crossOverUniforme(itensSelecionados.getFirst(), itensSelecionados.getLast(), chanceCrossOver);
                final Individuo itemMutado1 = Metodos.realizarMutacao(itensAposCrossOver.getFirst(), chanceMutacao, quantidadeMutacao);
                final Individuo itemMutado2 = Metodos.realizarMutacao(itensAposCrossOver.getLast(), chanceMutacao, quantidadeMutacao);
                filhos.add(itemMutado1);
                filhos.add(itemMutado2);
            }
            populacao.addAll(filhos);
            Collections.sort(populacao);
            populacao = new LinkedList(populacao.subList(0, Metodos.TAMANHOPOPULACAO));
            final double melhorFitness = populacao.getFirst().getFitness();
            
            System.out.println(melhorFitness);
            quantidadeMutacao = 2;
            if (melhorFitness == ultimoFitness) {
                contador++;
            } else {
//                chanceMutacao = 0;
                ultimoFitness = melhorFitness;
                contador = 0;
                if (geracao % 10 == 0) {
                    quantidadeMutacao = 5;
                }
            }
            if (contador > 3) {
//                chanceMutacao = 100;
                quantidadeMutacao = 1;
            }
            System.out.println(geracao);
            geracao++;
        }
            System.out.println(populacao.getFirst());
    }
}
