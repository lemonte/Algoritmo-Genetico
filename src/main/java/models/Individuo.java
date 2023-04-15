/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.LinkedList;

/**
 *
 * @author geandersonlemonte
 */
public class Individuo implements Comparable<Individuo> {

    @Override
    public String toString() {
        return "Individuo{" + "cromossomo=" + cromossomo + ", fitness=" + fitness + ", peso=" + peso + '}';
    }

    private LinkedList<Integer> cromossomo;
    private double fitness;
    private double peso;

    public Individuo(LinkedList<Integer> cromossomo, double fitness, double peso) {
        this.cromossomo = cromossomo;
        this.fitness = fitness;
        this.peso = peso;
    }

    public LinkedList<Integer> getCromossomo() {
        return cromossomo;
    }

    public void setCromossomo(LinkedList<Integer> cromossomo) {
        this.cromossomo = cromossomo;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(Individuo individuo) {
        if (this.fitness > individuo.getFitness()) {
            return -1;
        }
        if (this.fitness < individuo.getFitness()) {
            return 1;
        }
        return 0;
    }
}
