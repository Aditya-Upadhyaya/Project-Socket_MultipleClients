import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler1 implements Runnable {

    public static ArrayList<ClientHandler1> clientHandlers = new ArrayList<>();
    private Socket socket;
    private DataInputStream dataInputStream1;
    private DataOutputStream dataOutputStream1;
    private String clientusername;
    
    public ClientHandler1(Socket socket )
    {
       try {
           this.socket= socket;
           this.dataInputStream1 = new DataInputStream(socket.getInputStream());
           this.dataOutputStream1 = new DataOutputStream(socket.getOutputStream());
           this.clientusername = dataInputStream1.readUTF();
           clientHandlers.add(this);
       } catch (Exception e) {
           closeEveryThing(socket , dataInputStream1, dataOutputStream1);
       }
    }
    @Override
    public void run() {
        String msgFromClient;
        while (socket.isConnected()) {
            try {
                msgFromClient = dataInputStream1.readUTF();
                broadCast(msgFromClient);
            } catch (Exception e) {
                closeEveryThing(socket , dataInputStream1, dataOutputStream1);
                break;
            }
        }
        
    }
    public void broadCast(String msg)
    {
    for (ClientHandler1 clientHandler1 : clientHandlers) {
        try {
            if (!clientHandler1.clientusername.equals(clientusername)) {
                clientHandler1.dataOutputStream1.writeUTF(msg);
                clientHandler1.dataOutputStream1.flush();
            }
        } catch (Exception e) {
            closeEveryThing(socket , dataInputStream1, dataOutputStream1);
        }
    }
    
    }
    public void removeClientHandler()
    {
        clientHandlers.remove(this);
    }
    public void closeEveryThing(Socket socket , DataInputStream bufferedReader , DataOutputStream bufferedWriter)
    {
          removeClientHandler();
          try {
              if (bufferedReader!=null) {
                  bufferedReader.close();
              }
              if (bufferedWriter!=null) {
                  bufferedWriter.close();
              }
              if (socket!=null) {
                  socket.close();
              }
          } catch (Exception e) {
             System.out.println(e);
          }
    }
}
