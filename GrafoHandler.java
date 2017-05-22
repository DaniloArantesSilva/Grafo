package grafo;

import grafo.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.thrift.TException;
/**
 *
 * @author danilo
 */
public class GrafoHandler implements Grafo.Iface{
    private ArrayList<Vertice> vertice = new ArrayList<>();
    private ArrayList<Aresta> aresta = new ArrayList<>();
    
    @Override
     public synchronized boolean verificaExistenciaVertice(int nome){
        for(Vertice v: vertice){
           if(v.nome == nome)
               return true;
        }         
        return false;
    }   
 

    @Override
    public synchronized boolean verificaExistenciaAresta(int vnome1, int vnome2){
       for(Aresta a: aresta){
          if(a.vnome1 == vnome1 && a.vnome2 == vnome2)
             return true;
       } 
       return false;
    } 


    @Override
    public synchronized boolean insereVertice(int nome, int cor,  String descricao, double peso){  
        if(verificaExistenciaVertice(nome))
          return false;
        else{   
          Vertice v = new Vertice();
          v.nome = nome;
          v.cor = cor;
          v.descricao = descricao;
          v.peso = peso;
          vertice.add(v);
          return true;
        } 
    }   
 
 
    @Override
    public synchronized boolean insereAresta(int vnome1, int vnome2, double peso, boolean flg_bidirecional, String descricao) throws TException{
        if (verificaExistenciaVertice(vnome1) && verificaExistenciaVertice(vnome2)){
          Aresta a = new Aresta();
          a.vnome1 = vnome1;
          a.vnome2 = vnome2;
          a.peso = peso;
          a.flg_bidirecional = flg_bidirecional;
          a.descricao = descricao;  
          aresta.add(a);
          return true;
        }
        else 
          throw new VerticeNaoEncontrado("Algum dos Vértices não foram encontrados !");
    }   
 

    @Override
    public synchronized String buscaVertice(int nome) throws TException{
          for(Vertice v: vertice){
            if (v.nome == nome){
                return "+=========================+\n"+
                       "Nome: "+v.nome+"\n"+
                       "Cor: "+v.cor+"\n"+
                       "Descrição: "+v.descricao+"\n"+
                       "Peso: "+v.peso+"\n"+
                       "+=========================+";
            }
          } 
        throw new VerticeNaoEncontrado("Vértice não encontrado !");
    }
    

    @Override 
    public synchronized String buscaAresta(int vnome1, int vnome2) throws TException{
          String bidirecional="";
          for(Aresta a: aresta){
             if(a.flg_bidirecional)
                bidirecional = "Sim";
             else
                bidirecional = "Não";
            
             if (a.vnome1 == vnome1 && a.vnome2 == vnome2){  
                return  "+=========================+\n"+
                        "Nome Vértice 1: "+a.vnome1+"\n"+
                        "Nome Vértice 2: "+a.vnome2+"\n"+
                        "Peso: "+a.peso+"\n"+
                        "Direcionado ? "+bidirecional+"\n"+
                        "Descrição: "+a.descricao+"\n"+                       
                        "+=========================+";  
             }
              
          }         
        throw new ArestaNaoEncontrada("Aresta não encontrada !");
    }    


    @Override
    public synchronized boolean alteraVertice(int nome, int cor,  String descricao, double peso) throws TException{
        for(Vertice v: vertice){
            if (v.nome == nome){
                v.cor = cor;
                v.descricao = descricao;
                v.peso = peso;
                return true;
            }
         }        
        throw new VerticeNaoEncontrado("Vértice não encontrado !");        
    }


    @Override
    public synchronized boolean alteraAresta(int vnome1, int vnome2,  double peso, boolean flg_bidirecional, String descricao) throws TException{
        for(Aresta a: aresta){
           if (a.vnome1 == vnome1 && a.vnome2 == vnome2){
               a.peso = peso;
               a.flg_bidirecional = flg_bidirecional;
               a.descricao = descricao;
               return true;
           }          
        }        
        throw new ArestaNaoEncontrada("Aresta não encontrada !");        
    }  
 

   
    @Override
    public synchronized boolean removeVertice(int nome) throws TException{
      Iterator<Vertice> itVertice = vertice.iterator(); 
      Iterator<Aresta> itAresta = aresta.iterator(); 

      while(itVertice.hasNext()){
        Vertice v = itVertice.next();
        if(v.nome == nome){
           while(itAresta.hasNext()){
              Aresta a = itAresta.next();
              if(a.vnome1 == v.nome || a.vnome2 == v.nome)
                 itAresta.remove();
           }   

           itVertice.remove();
           return true;                        
        }    
      }
      throw new VerticeNaoEncontrado("Vértice não encontrado !");     
    } 


   
    @Override
    public synchronized boolean removeAresta(int vnome1, int vnome2) throws TException{
      Iterator<Aresta> it = aresta.iterator();
      while(it.hasNext()){
        Aresta a = it.next();
        if(a.vnome1 == vnome1 && a.vnome2 == vnome2){
          it.remove();
          return true;
        }
      }      
      throw new ArestaNaoEncontrada("Aresta não encontrada !");      
    } 


    @Override
    public synchronized String listaVerticeAresta(int vnome1, int vnome2) throws TException{
       String sVertices = "";
       if(verificaExistenciaAresta(vnome1, vnome2)){ 
          for(Vertice v: vertice){
             if(v.nome == vnome1)
               sVertices += "\nVértice 1\n"+buscaVertice(v.nome)+"\n";
             else
             if(v.nome == vnome2)   
               sVertices += "\nVértice 2\n"+buscaVertice(v.nome)+"\n";
          }

          if(!sVertices.equals("")) 
            return sVertices;
          else
            throw new VerticeNaoEncontrado("Vértices não encontrados !"); 
       }
       else
         throw new ArestaNaoEncontrada("Aresta não encontrada !"); 
    }     


    @Override
    public synchronized String listaArestaVertice(int nome){
       String sArestas = "";
       for(Aresta a: aresta){
          if(a.vnome1 == nome || a.vnome2 == nome)
            sArestas += "Aresta: V1: "+a.vnome1+", V2: "+a.vnome2+"\n";
       }

       if(!sArestas.equals("")) 
         return sArestas;
       else 
         return "Não possui Aresta neste Vértice !";
    }  


    @Override
    public synchronized String listaVerticeVizinho(int nome){
       String sVertices = "";
       ArrayList<Integer> verticeVizinho = new ArrayList<>();
       for(Aresta a: aresta){
          if(a.vnome1 == nome){
            if(!verticeVizinho.contains(a.vnome2))
              verticeVizinho.add(a.vnome2);
          }
          else{
            if (a.vnome2 == nome){
              if(!verticeVizinho.contains(a.vnome1))
                verticeVizinho.add(a.vnome1);
            }
          }
       }
       
       for(Integer vizinho: verticeVizinho){
         if(sVertices.equals(""))
           sVertices = "Vizinho(s): "+vizinho;
         else 
           sVertices += ", "+vizinho;
       }
       

       if(!sVertices.equals("")) 
         return sVertices;
       else 
         return "Este Vértice não possui vizinhos !";
    }      
}
