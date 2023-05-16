package models;

import java.util.LinkedList;

public class Conjunto {

    private LinkedList<Individuo> lstIndividuos = new LinkedList();

    public int quantidadeIndividuos() {
        return this.lstIndividuos.size();
    }

    public static Conjunto adicionarIndividuoLista(Conjunto group, Individuo individuo) {
        group.lstIndividuos.add(individuo);
        return group;
    }

    public Individuo obterMaioral() {
        return this.lstIndividuos.getFirst();
    }

    public void removerIndividuoLista(Individuo individuo) {
        this.lstIndividuos.remove(individuo);
    }

    public static boolean calcularDistancia(Conjunto grupo, Individuo cromossomoComparado, int maxDistancia) {
        int distancia = 0;

        for (int i = 0; i < grupo.quantidadeIndividuos(); i++) {
            if (!grupo.obterMaioral().getCromossomo().get(i).equals(cromossomoComparado.getCromossomo().get(i))) {
                distancia++;
            }
        }
        return distancia < maxDistancia;
    }
}
