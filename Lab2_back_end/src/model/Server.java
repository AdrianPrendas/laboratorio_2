package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTextArea;

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
    
    public Server(JTextArea table,JTextArea actions,JTextArea players, JTextArea chat){
        this.table = table;
        this.actions = actions;
        this.players = players;
        this.chat = chat;
    }

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        //ta_chat.append("Before " + name + " added. \n");
        users.add(name);
        //ta_chat.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                this.chat.append("Sending: " + message + "\n");
                writer.flush();
                this.chat.setCaretPosition(this.chat.getDocument().getLength());

            } catch (Exception ex) {
                this.chat.append("Error telling everyone. \n");
            }
        }
    }
    
    public void cleanScreen(){
        //ta_chat.setText("");
    }
    
    public void stopServer() {
        try {
            Thread.sleep(5000);                 //5000 milliseconds is five second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        this.actions.append("Server stopping... \n");

        //ta_chat.setText("");
    }
    
    public static Server startServer(JTextArea table,JTextArea actions,JTextArea players, JTextArea chat){
        Server server = new Server(table,actions,players,chat);
        Thread starter = new Thread(server);
        starter.start();
        server.actions.append("Server started...\n");
        return server;
    }
    
    public void onlineUsers(){
        //ta_chat.append("\n Online users : \n");
        for (String current_user : users) {
            //ta_chat.append(current_user);
            //ta_chat.append("\n");
        }
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
    
    private JTextArea table;
    private JTextArea actions;
    private JTextArea players;
    private JTextArea chat;

    private ServerSocket serverSock;
    private int port = 7777;
    private String address = "127.0.0.1";
    private ArrayList<String> users = new ArrayList();
    private ArrayList clientOutputStreams = new ArrayList();
}
