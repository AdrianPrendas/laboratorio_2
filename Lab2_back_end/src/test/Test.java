package test;

import eif203.util.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import org.json.*;

/**
 *
 * @author _Adrián_Prendas_
 */
public class Test {

    public static void test1() throws Exception {
        Runtime.getRuntime().exec("python .\\..\\lib\\soupGenerator.py");

        List<String> list = IOServices.readTextFileAsList("", "./data/soup.json");

        ArrayList nl = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            JSONObject obj = new JSONObject(list.get(i));
            nl = new ArrayList(obj.getJSONArray("soup").toList());
        }
        for (int i = 0; i < nl.size(); i++) {
            System.out.println(nl.get(i));
            //System.out.println(nl.get(i).getClass().getName());//java.util.ArrayList
        }
    }

    public static void showIpAddress() {
        try {
            InetAddress iAddress = InetAddress.getLocalHost();
            String server_IP = iAddress.getHostAddress();
            System.out.println("Server IP address : " + server_IP);
        } catch (UnknownHostException e) {
        }
    }

    public static void convertDomainToIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName("0.tcp.ngrok.io");
        } catch (UnknownHostException e) {
            System.exit(2);
        }
        System.out.println(address.getHostName() + "="
                + address.getHostAddress());
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        convertDomainToIp();
    }

}
