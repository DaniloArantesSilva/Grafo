package grafo;

import grafo.*;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.server.TThreadPoolServer;
/**
 *
 * @author danilo
 */
public class GrafoServer {
    public static GrafoHandler handler; 
    public static Grafo.Processor processor;	
	
    public static void main(String [] args){
      try{
        handler  = new GrafoHandler();
        processor = new Grafo.Processor(handler);
		
	Runnable simple = new Runnable() {
          public void run() {
            simple(processor);
          }
        }; 
		
        new Thread(simple).start();
      } catch (Exception x) {
         x.printStackTrace();
      }
     }
		
     public static void simple(Grafo.Processor processor) {
       try {
         TServerTransport serverTransporte = new TServerSocket(9090);
         TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransporte).processor(processor));
         System.out.println("Iniciado o servidor...");
         server.serve();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
}
