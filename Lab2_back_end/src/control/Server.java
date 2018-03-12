package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author _Adrián_Prendas_
 */
public class Server implements Runnable {
    
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                //ta_chat.append("Unexpected error... \n");
            }
        }

        @Override
        public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    //ta_chat.append("Received: " + message + "\n");
                    data = message.split(":");

                    for (String token : data) {
                        //ta_chat.append(token + "\n");
                    }

                    if (data[2].equals(connect)) {
                        //tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        //userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        //tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                        //userRemove(data[0]);
                        Thread.currentThread().stop();
                    } else if (data[2].equals(chat)) {
                        //tellEveryone(message);
                    } else {
                        //ta_chat.append("No Conditions were met. \n");
                    }
                }
            } catch (Exception ex) {
                //ta_chat.append("Lost a connection. \n");
                ex.printStackTrace();
                //clientOutputStreams.remove(client);//hacer statico
            }
        }
    }

    
    
    
    
    
    

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSock = new ServerSocket(port);

            while (true) {
                Socket clientSock = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);

                Thread listener = new Thread(new ClientHandler(clientSock, writer));
                listener.start();
                //ta_chat.append("Got a connection. \n");
            }
        } catch (Exception ex) {
            //ta_chat.append("Error making a connection. \n");
        }
    }
    
    private  ServerSocket serverSock;
    private int port = 7777;
    private String address = "127.0.0.1";
    private ArrayList<String> users = new ArrayList();
    private ArrayList clientOutputStreams = new ArrayList();
}
