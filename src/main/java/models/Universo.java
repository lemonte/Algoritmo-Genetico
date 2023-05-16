package models;

import java.util.LinkedList;


public class Universo {
    private static Universo instancia;
    private LinkedList<Conjunto> conjuntos = new LinkedList();
    private Universo(){}
    
    public static Universo getInstancia(){
        if (instancia == null){
            instancia = new Universo();
        }
        return instancia;
    }
    
    public LinkedList<Conjunto> getConjuntos() {
        return conjuntos;
    }
   
    public int quantidadeDeConjuntos(){
       return this.conjuntos.size();
    }
   
    public void adicionarConjuntoLista(Conjunto conjunto){
       this.conjuntos.add(conjunto);
    }
}
