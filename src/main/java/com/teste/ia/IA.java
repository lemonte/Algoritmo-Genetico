/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.teste.ia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import models.Individuo;


/**
 *
 * @author geandersonlemonte
 */
public class IA {

    public static void main(String[] args) {
        LinkedList<Individuo> populacao = Metodos.gerarPopulacao(5); // GERA A POPULACAO DE PAIS
        int geracao = 0; // GERACAO ATUAL
        double ultimoFitness = 0;  // ULTIMO FITNESS ENCONTRADO
        int contador = 0; // CONTADOR DE QUANTAS GERACOES NAO HOUVE EVOLUCAO
        int chanceMutacao = 100; // PORCENTAGEM DE CHANCE DE MUTACAO
        int chanceCrossOver = 30; // PORCENTAGEM DE CHANCE DE CROSSOVER
        int quantidadeMutacao = 2; // QUANTIDADE DE MUTACOES
        Grafico grafico = new Grafico();
        LinkedList<Double> dados = new LinkedList();

        while (populacao.getFirst().getFitness() < 10000) { // ENQUANTO ELE NAO ACHAR O VALOR MAXIMO ELE VAI EXECUTAR O WHILE
            LinkedList<Individuo> filhos = new LinkedList(); // CRIA LISTA DE FILHOS
            for (int i = 0; i < Metodos.TAMANHOPOPULACAO / 2; i++) {
                final LinkedList<Individuo> itensSelecionados = Metodos.selecaoPorRoleta(2, populacao); // RODA A ROLETA E SELECIONA DOIS INDIVIDUOS
                final LinkedList<Individuo> itensAposCrossOver = Metodos.crossOverUniforme(itensSelecionados.getFirst(), itensSelecionados.getLast(), chanceCrossOver); // REALIZA CROOSSOVER UNIFORME EM DOIS INDIVIDUOS 
                final Individuo itemMutado1 = Metodos.realizarMutacao(itensAposCrossOver.getFirst(), chanceMutacao, quantidadeMutacao); // IDIVIDUO 1 APOS MUTACAO
                final Individuo itemMutado2 = Metodos.realizarMutacao(itensAposCrossOver.getLast(), chanceMutacao, quantidadeMutacao); // INDIVIDUO 2 APOS MUTACAO
                filhos.add(itemMutado1); // ADICIONA O INDIVIDUO 1 A LISTA DE FILHOS
                filhos.add(itemMutado2); // ADICIONA O INDIVIDUO 2 A LISTA DE FILHOS
            }
            populacao.addAll(filhos); // ADICIONA TODOS OS FILHOS A LISTA DE PAIS
            Collections.sort(populacao); // ORNDENA A POPULACAO TOTAL
            populacao = new LinkedList(populacao.subList(0, Metodos.TAMANHOPOPULACAO)); // REMOVE A PIOR METADE DA POPULACAO
            final double melhorFitness = populacao.getFirst().getFitness(); // PEGA O MELHOR FITNESS

            System.out.println(melhorFitness); // MOSTRAR O ELEMENTO DE MELHOR FITNESS DESSA GERACAO
            quantidadeMutacao = 2; // QUANTIDADE DE MUTACAO RECEBE 2
            if (melhorFitness == ultimoFitness) { 
                // CASO NAO TENHA TIDO PROGRESSO NA GERACAO ELE AUMENTA O CONTADOR 
                contador++;
            } else {
                // CASO TENHA TIDO PROGRESSO NA GERACAO
                ultimoFitness = melhorFitness; // SALVA O ULTIMO MELHOR FITNESS
                contador = 0; // RESETA O CONTADOR   
                if (geracao % 10 == 0) {
                    // CASO ELE ESTEJA EVOLUINDO NORMALMENTE EM GERACOES MULTIPLAS DE 10 ELE AUMENTA A QUANTIDADE DE MUTACAO
                    // PARA QUE POSSA ESPALHAR UM POUCO A SOLUÇÃO PARA SABER TER NOVAS POSSIBILIDADES
                    quantidadeMutacao = 5; // AUMENTA A QUANTIDADE DE MUTACAO
                }
            }
            if (contador > 3) {
                // CASO ELE JA TENHA TIDO 3 GERACOES SEM MELHORIAS
                // ELE REDUZ A QUANTIDADE DE MUTACAO PARA TENTAR DEIXAR MAIS PRECISO AS MUDANCAS
                // PARA ENCONTRAR AQUELE ESPECÍFICO QUE PODERIA SER MELHORADO NO CROMOSSOMO
                quantidadeMutacao = 1; // AUMENTA A QUANTIDADE DE MUTACAO
            }
            System.out.println(geracao); // MOSTRA A GERACAO ATUAL
            geracao++; // AUMENTA A GERACAO
            dados.add(ultimoFitness);
        }
        // ATINGIU A CONDICAO DE PARADA
        System.out.println(populacao.getFirst()); // MOSTROU O MELHOR VALOR ENCONTRADO
        grafico.criarGrafico(dados);
    }
}
