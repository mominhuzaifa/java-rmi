import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements Service{
    private ArrayList<String> messages = new ArrayList<>();

    public Server() throws RemoteException{
        UnicastRemoteObject.exportObject(this, 0);
    }

    public void recieveMessage(String message) throws RemoteException{
        System.out.println("Recieved Message: " + message);
        messages.add(message);
    }

    public static void main(String[] args) {
        try{
            Server server = new Server();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/Service", server);
            System.out.println("server ready");

        }catch (Exception e){
            System.out.println("Server Exception: "+e.toString());
            e.printStackTrace();
        }
    }

}

// interface service
interface Service extends Remote{
    void recieveMessage(String message) throws RemoteException;
}
