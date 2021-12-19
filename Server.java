import java.net.*;
// import java.io.*;
// import java.util.*;
public class Server {
    private ServerSocket serverSocket;
    // private DataInputStream dataInputStream;
    // private DataOutputStream dataOutputStream;

    public Server(ServerSocket serverSocket )
    {
        try {
            this.serverSocket = serverSocket;
        } catch (Exception e) {
           System.out.println(e);
        }
    }
    public void startServer()
    {
            try {
                while (!serverSocket.isClosed()) {
                    Socket socket = serverSocket.accept();
                    System.out.println("New Client Connected ");
                    ClientHandler1 cHandler1 = new ClientHandler1(socket);
                    Thread thread = new Thread(cHandler1);
                    thread.start();
                }
            } catch (Exception e) {
               closeServerSocket();
            }
    }
    public void closeServerSocket(){
        try {
            if (serverSocket!=null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // public void reading(){

    //     new Thread(new Runnable() {

    //         @Override
    //         public void run() {
    //            String msgFromChat;
    //            while (!serverSocket.isClosed()) {
    //                try {
    //                    msgFromChat = dataInputStream.readUTF();
    //                    System.out.println(msgFromChat);
    //                } catch (Exception e) {
    //                 closeServerSocket();
    //                }
    //            }
                
    //         }
            
            
    //     }).start();
        
    // }
    // public void writting()
    // {
    //     try {
    //         Scanner src = new Scanner(System.in);
    //         while (!serverSocket.isClosed()) {
    //             String msgToSend = src.nextLine();
    //             dataOutputStream.writeUTF("Server" + "-" + msgToSend);
    //             dataOutputStream.flush();
    //         }
    //         src.close();
    //     } catch (Exception e) {
    //         closeServerSocket();
    //     }
    // }
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7777);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
