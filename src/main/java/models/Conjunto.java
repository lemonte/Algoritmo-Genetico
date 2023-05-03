package models;

import java.util.LinkedList;

public class Conjunto {
    private LinkedList<Individuo> lstIndividuos;
    
   
    
    public int quantidadeIndividuos(){
      return this.lstIndividuos.size();
    }
    
    public void adicionarIndividuoLista(Individuo individuo){
       this.lstIndividuos.add(individuo);
   }
    
    public Individuo obterMaioral(){
        return this.lstIndividuos.getFirst();
    }
    
    public void removerIndividuoLista(Individuo individuo){
        this.lstIndividuos.remove(individuo);
    }
}
