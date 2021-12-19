import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private String username;

    public Client(Socket socket , String username)
    {
        try {
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.username=username;
        } catch (Exception e) {
            closeEveryThing(socket , dataInputStream,dataOutputStream);
        }
    }
    public void sendMessage(){
        try {
            dataOutputStream.writeUTF(username);
            Scanner src = new Scanner(System.in);
            while (socket.isConnected()) {
                String msgToSend = src.nextLine();
                dataOutputStream.writeUTF(username + "-" + msgToSend);
                dataOutputStream.flush();
            }
            src.close();
        } catch (Exception e) {
            closeEveryThing(socket , dataInputStream,dataOutputStream);
        }
    }

    public void listenForMessage()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
               String msgFromChat;
               while (socket.isConnected()) {
                   try {
                       msgFromChat = dataInputStream.readUTF();
                       System.out.println(msgFromChat);
                   } catch (Exception e) {
                    closeEveryThing(socket , dataInputStream ,dataOutputStream);
                   }
               }
                
            }
            
            
        }).start();
    }
    public void closeEveryThing(Socket socket , DataInputStream bufferedReader ,DataOutputStream bufferedWriter)
    {
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
    public static void main(String[] args) throws Exception {
        Scanner src = new Scanner (System.in);
        System.out.println("Enter Your Name ");
        String username = src.nextLine();
        Socket socket = new Socket("localhost" , 7777);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
        src.close();
    }
}
