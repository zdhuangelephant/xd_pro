package nettyDemo.chat.manager;

import nettyDemo.chat.server.recieve.RecieveServer;
import nettyDemo.chat.server.send.SendServer;

public class ServerManager {
  
  private static RecieveServer workerServer = new RecieveServer("workerServer", 8999);
  private static SendServer producerServer = new SendServer("producerServer", 8899);
  
  public static RecieveServer getWorkerServer() {
    return workerServer;
  }

  public static void setWorkerServer(RecieveServer workerServer) {
    ServerManager.workerServer = workerServer;
  }

  public static SendServer getProducerServer() {
    return producerServer;
  }

  public static void setProducerServer(SendServer producerServer) {
    ServerManager.producerServer = producerServer;
  }

  public static void main(String[] args) {
    workerServer.startup();
    producerServer.startup();
    new Thread(new MessageManager()).start();
    new Thread(new CountManager()).start();
    
  }
}
