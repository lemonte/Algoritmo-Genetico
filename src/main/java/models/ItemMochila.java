/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


/**
 *
 * @author geandersonlemonte
 */
public class ItemMochila {

    @Override
    public String toString() {
        return "ItemMochila{" + "posicao=" + posicao + ", peso=" + peso + ", utilidade=" + utilidade + ", preco=" + preco + '}';
    }

    private final int posicao;
    private final double peso;
    private final int utilidade;
    private final double preco;

    public ItemMochila(int posicao, double peso, int utilidade, double preco) {
        this.posicao = posicao;
        this.peso = peso;
        this.utilidade = utilidade;
        this.preco = preco;
    }

    public int getPosicao() {
        return posicao;
    }

    public double getPeso() {
        return peso;
    }

    public int getUtilidade() {
        return utilidade;
    }

    public double getPreco() {
        return preco;
    }

   public static  ItemMochila fromString(
    String linha) {
        String[] lista = linha.split(";");
        final String posicao = lista[0];
        final String peso = lista[1].replaceFirst(",", ".");
        final String utilidade = lista[2];
        final String preco = lista[3].replaceFirst(",", ".");;
        return new ItemMochila(
                Integer.parseInt(posicao),
                Double.parseDouble(peso),
                Integer.parseInt(utilidade),
                Double.parseDouble(preco)
        );
    }

}
