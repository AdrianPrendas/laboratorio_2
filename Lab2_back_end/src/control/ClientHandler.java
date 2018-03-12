
package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author _Adrián_Prendas_
 */
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
