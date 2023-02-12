package Server.net;


import Common.IFrankie;
import Common.Item;
import Common.Logbook;
import Common.Person;
import Common.exceptions.*;
import Server.domain.EShopManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class EShopServer {

    public static void main(String[] args) throws IOException {
        IFrankie manager = new EShopManager();

        ServerSocket ss = new ServerSocket(1399);
        System.out.println("Server is running and waits for connection.");

        while (true) {
            Socket s = ss.accept();

            ClientRequestProcessor c = new ClientRequestProcessor(s, manager);

            // Start parallel processing of client
            Thread t = new Thread(c);
            t.start();

            System.err.println("Client connected.");
        }
    }
}