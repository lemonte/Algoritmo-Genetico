/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teste.ia;

import external.ILeituraArquivo;
import external.LeituraArquivo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import models.Individuo;
import models.ItemMochila;

/**
 *
 * @author geandersonlemonte
 */
public class Metodos {

    public static final int TAMANHOPOPULACAO = 500;
    public static final int TAMANHOCROMOSSOMO = 500;
    private static final int PESOTOTALMOCHILA = 30;
    private static Metodos instancia;
    private final LinkedList<ItemMochila> dadosDoArquivo;


    private Metodos() {
        dadosDoArquivo = buscarPossiveisItens();
    }


    public static Metodos pegarInstancia() {
        if (instancia == null) {
            instancia = new Metodos();
        }
        return instancia;
    }

    private LinkedList<ItemMochila> buscarPossiveisItens() {
        final ILeituraArquivo leituraArquivo = new LeituraArquivo();
        return leituraArquivo.lerLista1();
    }

    public static LinkedList<Individuo> crossOverUniforme(Individuo pai1, Individuo pai2, int chanceCrossOver) {
        final Random gerador = new Random();
        final LinkedList<Integer> cromossomo1 = new LinkedList(pai1.getCromossomo());
        final LinkedList<Integer> cromossomo2 = new LinkedList(pai2.getCromossomo());
        for (int i = 0; i < TAMANHOCROMOSSOMO; i++) {
            final double chanceGerada = gerador.nextInt(100);
            if (chanceGerada < chanceCrossOver) {
                cromossomo1.set(i, pai2.getCromossomo().get(i));
                cromossomo2.set(i,pai1.getCromossomo().get(i));
            }
        }

        final LinkedList<Individuo> resultadoCrossOverUniforme = new LinkedList();
        Individuo individuo1 = retornaIndividuoComFitnessEPeso(cromossomo1);
        Individuo individuo2 = retornaIndividuoComFitnessEPeso(cromossomo2);
        resultadoCrossOverUniforme.add(individuo1);
        resultadoCrossOverUniforme.add(individuo2);
        return resultadoCrossOverUniforme;
    }

    public static LinkedList<Individuo> gerarPopulacao(int chancePossuirItem) {
        final LinkedList<Individuo> populacao = new LinkedList();
        for (int i = 0; i < TAMANHOPOPULACAO; i++) {
            populacao.add(geraIndividuo(chancePossuirItem));
        }
        return populacao;
    }

    public static void ordenarProFitness(LinkedList<Individuo> populacao) {
        Collections.sort(populacao);
    }

    public static Individuo geraIndividuo(int chancePossuirItem) {
        final Random gerador = new Random();
        final LinkedList<Integer> cromossomo = new LinkedList();
        for (int j = 0; j < TAMANHOCROMOSSOMO; j++) {
            final double chanceGerada = gerador.nextInt(100);
            if (chanceGerada < chancePossuirItem) {
                cromossomo.add(1);
            } else {
                cromossomo.add(0);
            }
        }
        return retornaIndividuoComFitnessEPeso(cromossomo);
    }

    private static Individuo retornaIndividuoComFitnessEPeso(LinkedList<Integer> cromossomo) {
        LinkedList<ItemMochila> itens = Metodos.pegarInstancia().dadosDoArquivo;
        double somaPesos = 0;
        int somaUtilidades = 0;
        double fitness = 0;
        for (int i = 0; i < TAMANHOCROMOSSOMO; i++) {
            if (cromossomo.get(i) == 1) {
                somaPesos = somaPesos + itens.get(i).getPeso();
                somaUtilidades = somaUtilidades + itens.get(i).getUtilidade();
            }
        }
        if (somaPesos > PESOTOTALMOCHILA) {
            fitness = (somaUtilidades / (1 + (somaPesos - PESOTOTALMOCHILA)));
        } else {
            fitness = Double.parseDouble(String.valueOf(somaUtilidades));
        }
        return new Individuo(cromossomo, fitness, somaPesos);
    }

    public static LinkedList<Individuo> selecaoPorRoleta(
            int quantidadeDeItensSelecionados,
            LinkedList<Individuo> populacao
    ) {
        final LinkedList<Individuo> individuosSelecionados = new LinkedList();
        final Random gerador = new Random();
        double fitnessTotal = 0;
        for (Individuo individuo : populacao) {
            fitnessTotal = fitnessTotal + individuo.getFitness();
        }
        for (int i = 0; i < quantidadeDeItensSelecionados; i++) {
            final double localDaRoletaEscolhido = gerador.nextDouble(fitnessTotal);
            double somaFitness = 0;
            int j = 0;
            while (somaFitness < localDaRoletaEscolhido) {
                somaFitness = populacao.get(j).getFitness() + somaFitness;
                j++;
            }
            individuosSelecionados.add(populacao.get(j - 1));
        }
        return individuosSelecionados;
    }

    public static Individuo realizarMutacao(Individuo cromossomo, int chanceMutacao, int quantidadeMutacao) {
        final Random gerador = new Random();
        final LinkedList<Integer> novoCromossomo = new LinkedList(cromossomo.getCromossomo());
        final double chanceDeMudar = gerador.nextDouble(100);
        if (chanceDeMudar < chanceMutacao) {
            for (int i = 0; i < quantidadeMutacao; i++) {
                final int gene = gerador.nextInt(TAMANHOCROMOSSOMO);
                if (novoCromossomo.get(gene) == 1) {
                    novoCromossomo.set(gene, 0);
                } else {
                    novoCromossomo.set(gene, 1);
                }
            }
        }
        return retornaIndividuoComFitnessEPeso(novoCromossomo);
    }

}
