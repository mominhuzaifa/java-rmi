import java.rmi.*;
import java.rmi.registry.LocateRegistry;

import java.util.Scanner;


public class Client implements Runnable {
    private Service service;
    public Client(Service service){
        this.service = service;
    }

    public void run(){
        try (Scanner scanner = new Scanner(System.in)) {
            while(true){
                System.out.print("Enter Message: ");
                String message = scanner.nextLine();
            
                try{
                    service.recieveMessage(message);
                }catch(RemoteException remoteException){
                    System.out.println("Client Exception: "+remoteException.toString());
                    remoteException.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        try{
            LocateRegistry.getRegistry(1099);
            Service service = (Service) Naming.lookup("rmi://localhost/Service");
            Client client = new Client(service);
            Thread thread = new Thread(client);
            thread.start();

        } catch(Exception e){
            System.out.println("Client Exception: "+e.toString());
            e.printStackTrace();
        }
    }
    
}
