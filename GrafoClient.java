package grafo;

import grafo.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TBinaryProtocol;
/**
 *
 * @author danilo
 */
public class GrafoClient {
    public static void main (String [] args){
      try{
        TTransport transport = new TSocket("localhost", 9090);
        transport.open();
	TProtocol protocol = new TBinaryProtocol(transport);
        Grafo.Client client = new Grafo.Client(protocol);
	menu(client);   
        transport.close();			
      } catch (TException x){
         x.printStackTrace();
      }        
    }
    
    private static void menu(Grafo.Client client) throws TException{
      int opcao;
      Scanner sc = new Scanner(System.in); 
        
      do{
          System.out.println("Digite a Opção desejada:\n"+
                             "   1: Inserir Vértice;\n"+
                             "   2: Inserir Aresta;\n"+ 
                             "   3: Alterar Vértice;\n"+
                             "   4: Alterar Aresta;\n"+
                             "   5: Remover Vértice;\n"+
                             "   6: Remover Aresta;\n"+
                             "   7: Buscar Vértice;\n"+
                             "   8: Buscar Aresta;\n"+
                             "   9: Listar Vértices de uma Aresta;\n"+ 
                             "  10: Listar Aresta de um Vértice;\n"+   
                             "  11: Listar Vértices vizinhos de um Vértice;\n"+    
                             "   0: Sair;\n");
          opcao = sc.nextInt();
          sc.nextLine();
      
          switch(opcao){
              case 1: int nome, cor;
                      String descricao;
                      double peso;
                      
                      try{
                       	 System.out.println("Digite o nome: ");
                     	 nome = sc.nextInt();
                     	 sc.nextLine();
                     	 System.out.println("Digite a cor: ");
                     	 cor = sc.nextInt();
                     	 sc.nextLine();
                     	 System.out.println("Digite a descrição: ");
                     	 descricao = sc.nextLine();
                         System.out.println("Digite o peso: ");
                     	 peso = sc.nextDouble();
                       
                         if(client.insereVertice(nome, cor, descricao, peso))
                           System.out.println("Vértice inserido com Sucesso !\n\n");
                         else
                           System.out.println("Vertice já inserido !\n\n");

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      }
                      break;
                      
                      
              case 2: int vnome1, vnome2;
                      String flg_bidirecional;
                      
                      try{
                         System.out.println("Digite o nome do primeiro Vértice: ");
                         vnome1 = sc.nextInt();
            	         sc.nextLine();

	                 if(!client.verificaExistenciaVertice(vnome1)){
                           System.out.println("Vértice não encontrado !\n\n"); 
                           break;
                         }

             	         System.out.println("Digite o nome do segundo Vértice: ");
             	         vnome2 = sc.nextInt();
             	         sc.nextLine();

                         if(!client.verificaExistenciaVertice(vnome2)){
                            System.out.println("Vértice não encontrado !\n\n"); 
                            break;
                         }

                         System.out.println("Digite o peso: ");
                         peso = sc.nextDouble();
                         sc.nextLine();
                         System.out.println("Esta Aresta é bi-direcional ? (Sim ou Não): ");
                         flg_bidirecional = sc.nextLine();
                         System.out.println("Digite a descrição: ");
                         descricao = sc.nextLine();
                        
                         if(client.insereAresta(vnome1, vnome2, peso, flg_bidirecional.equals("Sim"), descricao))
                           System.out.println("Aresta inserida com Sucesso !\n\n");

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (VerticeNaoEncontrado vne){
                         System.out.println(vne.erro+"\n");
                      }
                      break;

                        
              case 3: try{
                     	 System.out.println("Digite o nome do Vértice que deseja alterar: ");
                    	 nome = sc.nextInt();
                     	 sc.nextLine();
               	         System.out.println(client.buscaVertice(nome)+"\n");
                	 System.out.println("Cor: ");
                  	 cor = sc.nextInt();
                   	 sc.nextLine();
                     	 System.out.println("Descrição: ");
                     	 descricao = sc.nextLine();
                   	 System.out.println("Peso: ");
                  	 peso = sc.nextDouble();
        
       		         if(client.alteraVertice(nome, cor, descricao, peso))
                   	   System.out.println("Vértice alterado com Sucesso !\n\n");

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (VerticeNaoEncontrado vne){
                         System.out.println(vne.erro+"\n");
                      }
                      break;
        
        
              case 4: try{
                         System.out.println("Digite o nome do Vértice 1 e do Vértice 2 da Aresta que deseja alterar: ");
                         vnome1 = sc.nextInt();
                         sc.nextLine();

                         if(!client.verificaExistenciaVertice(vnome1)){
                             System.out.println("Vértice não encontrado !\n\n"); 
                             break;
                         }
                               
                         vnome2 = sc.nextInt();
                         sc.nextLine();

                         if(!client.verificaExistenciaVertice(vnome2)){
                             System.out.println("Vértice não encontrado !\n\n"); 
                             break;
                         }
                         
                         System.out.println(client.buscaAresta(vnome1, vnome2)+"\n");
                         System.out.println("Peso: ");
                         peso = sc.nextDouble();
                         sc.nextLine();
                         System.out.println("É bi-direcional ? ");
                         flg_bidirecional = sc.nextLine();
                         System.out.println("Descrição: ");
                         descricao = sc.nextLine();
                         
                         if(client.alteraAresta(vnome1, vnome2, peso, flg_bidirecional.equalsIgnoreCase("sim"), descricao))
                             System.out.println("Aresta alterada com Sucesso !\n\n");

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (ArestaNaoEncontrada ane){
                         System.out.println(ane.erro+"\n");
                      }
                      break;

        
              case 5: try{
                         System.out.println("Digite o nome(número inteiro) do Vértice que deseja remover: ");
                         nome = sc.nextInt();
                         sc.nextLine();
                        
                         if(client.removeVertice(nome) == true)
                           System.out.println("Vértice removido com Sucesso !\n\n");  
 
                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (VerticeNaoEncontrado vne){
                         System.out.println(vne.erro+"\n");
                      }
                      break;
        

              case 6: try{
                         System.out.println("Digite o nome do Vértice 1 e do Vértice 2 da Aresta que deseja remover: ");
                         vnome1 = sc.nextInt();
                         sc.nextLine();
                         vnome2 = sc.nextInt();
                         sc.nextLine(); 
                      
                         if(client.removeAresta(vnome1, vnome2) == true)
                           System.out.println("Aresta removida com Sucesso !\n\n");

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (ArestaNaoEncontrada ane){
                         System.out.println(ane.erro+"\n");
                      } 
                      break;  


              case 7: try{ 
                         System.out.println("Digite o nome do Vértice que deseja Buscar: ");
                         nome = sc.nextInt();
                         sc.nextLine(); 
                         System.out.println(client.buscaVertice(nome)+"\n\n"); 

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (VerticeNaoEncontrado vne){
                         System.out.println(vne.erro+"\n");
                      }
                      break;
  

              case 8: try{
                         System.out.println("Digite o nome do Vértice 1 e do Vértice 2 da Aresta que deseja Buscar: ");
                         vnome1 = sc.nextInt();
                         sc.nextLine();
                         vnome2 = sc.nextInt();
                         sc.nextLine();  
                         System.out.println(client.buscaAresta(vnome1, vnome2)+"\n\n");  

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (ArestaNaoEncontrada ane){
                         System.out.println(ane.erro+"\n");
                      }                  
                      break;

              
              case 9: try{
                         System.out.println("Digite o Vértice 1 e o Vértice 2 da Aresta: ");
                         vnome1 = sc.nextInt();
                         sc.nextLine();
                        
                         if(!client.verificaExistenciaVertice(vnome1)){
                           System.out.println("Vértice não encontrado !\n\n"); 
                           break;
                         }

                         vnome2 = sc.nextInt();
                         sc.nextLine();

                         if(!client.verificaExistenciaVertice(vnome2)){
                           System.out.println("Vértice não encontrado !\n\n"); 
                           break;
                         }

                         System.out.println(client.listaVerticeAresta(vnome1, vnome2)+"\n\n");                    

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } catch (VerticeNaoEncontrado vne){
                         System.out.println(vne.erro+"\n");
                      } catch (ArestaNaoEncontrada ane){
                         System.out.println(ane.erro+"\n");
                      } 
                      break;


             case 10: try{
                         System.out.println("Digite o nome do Vértice: ");
                         nome = sc.nextInt();
                         sc.nextLine();
                         System.out.println(client.listaArestaVertice(nome)+"\n\n");                    

                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      } 
                      break;


             case 11: try{
                         System.out.println("Digite o nome do Vértice: ");
                         nome = sc.nextInt();
                         sc.nextLine();
                         System.out.println(client.listaVerticeVizinho(nome)+"\n\n");                    
 
                      } catch (InputMismatchException ime){
                         System.out.println("Valor incorreto !\n");
                      }   
                      break;
          }
      }while(opcao != 0);
    }
}

