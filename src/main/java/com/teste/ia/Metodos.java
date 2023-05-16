/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.teste.ia;

import external.ILeituraArquivo;
import external.LeituraArquivo;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import models.Conjunto;
import models.Individuo;
import models.ItemMochila;

/**
 *
 * @author geandersonlemonte
 */
public class Metodos {
    // classe com os metodos e funcoes do algoritmo

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
        // busca os itens no arquivo
        final ILeituraArquivo leituraArquivo = new LeituraArquivo();
        return leituraArquivo.lerLista1();
    }

    public static LinkedList<Individuo> crossOverUniforme(Individuo pai1, Individuo pai2, int chanceCrossOver) {
        // Realiza o crossOverUniforme entre dois pais e retorna dois filhos
        final Random gerador = new Random();
        final LinkedList<Integer> cromossomo1 = new LinkedList(pai1.getCromossomo());
        final LinkedList<Integer> cromossomo2 = new LinkedList(pai2.getCromossomo());
        for (int i = 0; i < TAMANHOCROMOSSOMO; i++) {
            final double chanceGerada = gerador.nextInt(100);
            if (chanceGerada < chanceCrossOver) {
                cromossomo1.set(i, pai2.getCromossomo().get(i));
                cromossomo2.set(i, pai1.getCromossomo().get(i));
            }
        }

        final LinkedList<Individuo> resultadoCrossOverUniforme = new LinkedList();
        Individuo individuo1 = retornaIndividuoComFitnessEPeso(cromossomo1);
        Individuo individuo2 = retornaIndividuoComFitnessEPeso(cromossomo2);
        resultadoCrossOverUniforme.add(individuo1);
        resultadoCrossOverUniforme.add(individuo2);
        return resultadoCrossOverUniforme;
    }

    public static LinkedList<Individuo> crossOverUmPonto(Individuo pai1, Individuo pai2, int chanceCrossOver) {
        final Random gerador = new Random();
        final LinkedList<Integer> cromossomo1 = new LinkedList(pai1.getCromossomo());
        final LinkedList<Integer> cromossomo2 = new LinkedList(pai2.getCromossomo());
        final double chanceGerada = gerador.nextInt(100);
        if (chanceGerada < chanceCrossOver) {
            final int pontoCorte = gerador.nextInt(TAMANHOCROMOSSOMO);
            for (int i = pontoCorte; i < TAMANHOCROMOSSOMO; i++) {
                cromossomo1.set(i, pai2.getCromossomo().get(i));
                cromossomo2.set(i, pai1.getCromossomo().get(i));
            }

        }
        final LinkedList<Individuo> resultadoCrossOverUmPonto = new LinkedList();
        Individuo individuo1 = retornaIndividuoComFitnessEPeso(cromossomo1);
        Individuo individuo2 = retornaIndividuoComFitnessEPeso(cromossomo2);
        resultadoCrossOverUmPonto.add(individuo1);
        resultadoCrossOverUmPonto.add(individuo2);
        return resultadoCrossOverUmPonto;
    }

    public static LinkedList<Individuo> gerarPopulacao(int chancePossuirItem) {
// Gerar pupulacao de pais        
        final LinkedList<Individuo> populacao = new LinkedList();
        for (int i = 0; i < TAMANHOPOPULACAO; i++) {
            populacao.add(geraIndividuo(chancePossuirItem));
        }
        return populacao;
    }

    public static void ordenarProFitness(LinkedList<Individuo> populacao) {
//        Ordenar populacao por fitness
        Collections.sort(populacao);
    }

    public static Individuo geraIndividuo(int chancePossuirItem) {
//        Gerar individuo
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
//        Calcula o peso e o firness a partir de do cromossomo e retorna o individuo
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
//        Recebe a populacao e a quantidade de itens que deseja da roleta e retorna uma lista com a quantidade desejade de individuos
        final LinkedList<Individuo> individuosSelecionados = new LinkedList();
        final Random gerador = new Random();
        double fitnessTotal = 0;
        for (Individuo individuo : populacao) {
            fitnessTotal = fitnessTotal + individuo.getFitness();
        }
        for (int i = 0; i < quantidadeDeItensSelecionados; i++) {
            final double localDaRoletaEscolhido = gerador.nextDouble() * fitnessTotal;
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

    public static LinkedList<Individuo> selecaoPorTorneio(
            int tamanhoTorneio,
            LinkedList<Individuo> populacao
    ) {

        int posicao1 = Math.round(populacao.size() / tamanhoTorneio);
        int posicao2 = posicao1 * 2;
        int posicao3 = populacao.size();

        LinkedList<Individuo> primeiraParte = new LinkedList(populacao.subList(0, posicao1));
        LinkedList<Individuo> segundaParte = new LinkedList(populacao.subList(posicao1, posicao2));
        LinkedList<Individuo> terceiraParte = new LinkedList(populacao.subList(posicao2, posicao3));
        Collections.sort(primeiraParte);
        Collections.sort(segundaParte);
        Collections.sort(terceiraParte);
        LinkedList<Individuo> resultado = new LinkedList();
        resultado.add(primeiraParte.getFirst());
        resultado.add(segundaParte.getFirst());
        resultado.add(terceiraParte.getFirst());

        return resultado;

    }

    public static LinkedList<Individuo> miLambda(LinkedList<Individuo> populacao, LinkedList<Individuo> filhos) {
        LinkedList<Individuo> populacaoFinal = new LinkedList(populacao);
        populacaoFinal.addAll(filhos); // ADICIONA TODOS OS FILHOS A LISTA DE PAIS
        Collections.sort(populacaoFinal); // ORNDENA A POPULACAO TOTAL
        populacaoFinal = new LinkedList(populacaoFinal.subList(0, Metodos.TAMANHOPOPULACAO)); // REMOVE A PIOR METADE DA POPULACAO
        return populacaoFinal;
    }

    public static LinkedList<Individuo> elitismo(LinkedList<Individuo> populacao, LinkedList<Individuo> filhos) {
        final double porcentagemElite = 0.1;
        Collections.sort(populacao);
        LinkedList<Individuo> populacaoFinal = new LinkedList(populacao.subList(0, (int) (TAMANHOPOPULACAO * porcentagemElite)));
        LinkedList<Individuo> paisNaoSelecionados = new LinkedList(populacao.subList((int) (TAMANHOPOPULACAO * porcentagemElite), (TAMANHOPOPULACAO)));
        paisNaoSelecionados.addAll(filhos);
        Collections.sort(paisNaoSelecionados);
        populacaoFinal.addAll(paisNaoSelecionados.subList(0, (int) (TAMANHOPOPULACAO * (1 - porcentagemElite))));
        Collections.sort(populacaoFinal);
        return populacaoFinal;
    }

    public static Individuo realizarMutacao(Individuo cromossomo, int chanceMutacao, int quantidadeMutacao) {
//        Realiza a mutacao de um individuo
        final Random gerador = new Random();
        final LinkedList<Integer> novoCromossomo = new LinkedList(cromossomo.getCromossomo());
        final double chanceDeMudar = gerador.nextDouble() * 100;
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

    public static void mutacaoDirigida(LinkedList<Integer> esquema, LinkedList<Individuo> populacao, double taxaMutacao) {
        ordenarProFitness(populacao);
        for (int i = ((int) (0.1 * populacao.size())); i < populacao.size(); i++) {
            LinkedList<Integer> novoCromossomo = populacao.get(i).getCromossomo();

            for (int j = 0; j < novoCromossomo.size(); j++) {
                Random gerador = new Random();

                if (-1 == esquema.get(j)) {
                    if (gerador.nextDouble() < taxaMutacao) {
                        novoCromossomo.set(j, novoCromossomo.get(j) == 1 ? 0 : 1);
                    }
                } else {

                    if (Objects.equals(populacao.get(i).getCromossomo().get(j), esquema.get(j))) {
                        if (gerador.nextDouble() < taxaMutacao) {
                            novoCromossomo.set(j, novoCromossomo.get(j) == 1 ? 0 : 1);
                        }
                    } else {
                        if (gerador.nextDouble() < 0.04) {
                            novoCromossomo.set(j, novoCromossomo.get(j) == 1 ? 0 : 1);
                        }
                    }
                }
            }
            Individuo individuo = retornaIndividuoComFitnessEPeso(novoCromossomo);
            populacao.set(i, individuo);
        }
    }

    public static boolean temConvergencia(LinkedList<Individuo> populacao) {
        System.out.println("Testando conversão genética");
        int k = 10; // max conjunto
        int y = 20; // max distancia entre os individuos
        int m = 3000; // max individuos em um conjunto

        List<Conjunto> groups = new LinkedList<>();

        groups.add(Conjunto.adicionarIndividuoLista(new Conjunto(), populacao.get(0)));
        for(int i =0; i < populacao.size(); i ++){
            Individuo individuo = populacao.get(i);
            boolean added = false;
            for(int j = 0; j < groups.size(); j ++){
                 Conjunto group = groups.get(j);
                 if (Conjunto.calcularDistancia(group, individuo, y)) {
                    Conjunto.adicionarIndividuoLista(group, individuo);
                    added = true;
                    if (group.quantidadeIndividuos() > m) {
                        System.out.println("Conversão genética detectada por super grupo!");
                        return true;
                    }
                }
            }
            if (!added) {
                System.out.println("Criando novo conjunto");
                groups.add(Conjunto.adicionarIndividuoLista(new Conjunto(), individuo));
            }
        }
        if (groups.size() < k) {
            System.out.println("Conversão genética detectada por número de conjuntos!");
            return true;
        }
        return false;

    }

    public static LinkedList<Integer> criarEsquema(LinkedList<Individuo> populacao) {
        ordenarProFitness(populacao);

        List<Individuo> subLista = populacao.subList(0, (int) (populacao.size() * 0.1));

        LinkedList<Integer> esquema = new LinkedList();

        for (int i = 0; i < TAMANHOCROMOSSOMO; i++) {
            int soma = 0;
            for (int j = 0; j < subLista.size(); j++) {
                // Soma todos os valores da posição
                soma = soma + subLista.get(j).getCromossomo().get(i);
            }
            if (soma > subLista.size() * 0.8) {
                esquema.add(1);
            } else {
                if (soma < subLista.size() * 0.2) {
                    esquema.add(0);
                } else {
                    esquema.add(-1);
                }
            }
        }

        return esquema;
    }
}
