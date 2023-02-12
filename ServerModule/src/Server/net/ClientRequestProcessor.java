package Server.net;

import Common.*;
import Common.exceptions.*;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientRequestProcessor implements Runnable {
    private BufferedReader socketIn;
    private PrintStream socketOut;
    final String separator = ";";
    IFrankie frankie;

    public ClientRequestProcessor(Socket s, IFrankie frankie) throws IOException {
        this.frankie = frankie;

        OutputStream outputStream = s.getOutputStream();
        socketOut = new PrintStream(outputStream);

        InputStream inputStream = s.getInputStream();
        socketIn = new BufferedReader(new InputStreamReader(inputStream));
    }


    @Override
    public void run() {
        while (true) {
            try {
                String receivedData = socketIn.readLine(); // BufferedReader bietet readLine()
                handleCommandRequest(receivedData);
            } catch (SocketException e) {
                System.err.println("Client disconnected");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleCommandRequest(String receivedData){
        System.err.println("Data received from client: " + receivedData);
        String[] parts = receivedData.split(separator);

        switch (Commands.valueOf(parts[0])) {

            case CMD_GET_ITEMS -> handleGetItem();
            case CMD_GET_ENTRIES -> handleGetEntries();
            case CMD_GET_USERS -> handleGetUser();
            case CMD_LOGIN -> handleGetLogin(parts);
            case CMD_REGISTER_CLIENT -> handleGetRegisterClient(parts);
            case CMD_REGISTER_EMPLOYEE -> handleGetRegisterEmployee(parts);
            case CMD_CREATE_NEW_ITEM1 -> handleGetCreatNewItem1(parts);
            case CMD_CREATE_NEW_ITEM2 -> handleGetCreatNewItem2(parts);
            case CMD_BUY_ITEMS -> handleGetBuyItem(parts);
            case CMD_EMPTY_CART -> handleGetEmptyCart(parts);
            case CMD_DECREASE_STOCK -> handleGetDecreaseStock(parts);
            case CMD_INCREASE_STOCK -> handleGetIncreaseStock(parts);
            case CMD_SELECT_ITEM -> handleGetSelectItem(parts);
            case CMD_REMOVE_ITEM -> handleGetRemoveItem(parts);
            case CMD_GET_ITEM_INFO -> handleGetItemInfo(parts);
            case CMD_LOGOUT -> handleGetLogout(parts);
            case CMD_END_PRICE -> handleEndPrice(parts);
            case CMD_GET_ITEMS_IN_CART -> handleGetItemsInCart(parts);
            default -> System.err.println("Invalid request received!");
        }
    }



    private void handleGetItem() {
        Map<Integer, Item> items = frankie.getItemsInStock();

        String cmd = Commands.CMD_GET_ITEMS_RESP.name();

        for (Item b : items.values()) {
            cmd += separator + b.getItemName();
            cmd += separator + b.getItemCode();
            cmd += separator + b.getPrice();
            cmd += separator + b.getNumberInStock();
            cmd += separator + b.getPackingSize();
            cmd += separator + b.getPic();
        }

        socketOut.println(cmd);
    }


    private void handleGetEntries() {

        List<Logbook> entries = frankie.getEntries();

        String cmd = Commands.CMD_GET_ENTRIES_RESP.name();

       for  (Logbook b : entries) {
            cmd += separator + b.getEntry();
            cmd += separator + b.getItem().getItemName();
            cmd += separator + b.getItem().getItemCode();
            cmd += separator + b.getItem().getPrice();
            cmd += separator + b.getItem().getNumberInStock();
            cmd += separator + b.getItem().getPackingSize();
            cmd += separator + b.getUserName();
            cmd += separator + b.getQuantity();
        }
        socketOut.println(cmd);
    }


    private void handleGetUser() {
        List<Person> users = frankie.getUsers();

        String cmd = Commands.CMD_GET_USERS_RESP.name();

        for(Person b: users) {
            cmd += separator + b.getName();
            cmd += separator + b.getPassword();
            cmd += separator + b.getBoolean();
            if (b.getBoolean()) {
                cmd += separator + b.getAddress();
            } else {
                cmd += separator + b.getBoolean();
            }

        }
        socketOut.println(cmd);
    }

    private void handleGetLogin(String[] data) {
        String username = data[1];
        String password = data[2];
        Person p = null;

        try {
            p = frankie.login(username, password);
        } catch (ExceptionLoginFailed e) {
            throw new RuntimeException(e);
        }

        String cmd = Commands.CMD_LOGIN_RESP.name();
        cmd += separator + p.getName();
        cmd += separator + p.getPassword();
        cmd += separator + p.getBoolean();
        if(p.getBoolean()){
            cmd += separator + p.getAddress();
        }
        socketOut.println(cmd);
    }

    private void handleGetRegisterClient(String[] data) {
        String username = data[1];
        String password = data[2];
        String address = data[3];
        try {
            frankie.registerClient(username,password,address);
        } catch (ExceptionUserAlreadyExists | IOException e) {
            e.printStackTrace();
        }
    }


    private void handleGetRegisterEmployee(String[] data) {
        String username = data[1];
        String password = data[2];

        try {
            frankie.registerEmployee(username,password);
        } catch (ExceptionUserAlreadyExists | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetCreatNewItem1(String[] data) {

        String itemName = data[1];
        float price = Float.parseFloat(data[2]);
        int numberInStock = Integer.parseInt(data[3]);
        String username = data[4];
        String filePicName = data[5];

        try {
            frankie.createNewItem(itemName,price, numberInStock, username, filePicName);
        } catch (ExceptionItemAlreadyExists | IOException e) {
            e.printStackTrace();
        }

    }

    private void handleGetCreatNewItem2(String[] data) {
        String itemName = data[1];
        float price = Float.parseFloat(data[2]);
        int numberInStock = Integer.parseInt(data[3]);
        int packingSize = Integer.parseInt(data[4]);
        String username = data[5];
        String filePicName = data[6];

        try {
            frankie.createNewItem(itemName,price,numberInStock,packingSize,username, filePicName);
        } catch (ExceptionItemAlreadyExists | IOException e) {
            e.printStackTrace();
        }
    }


    private void handleGetBuyItem(String[] data) {
        String username = data[1];
        String cmd = Commands.CMD_BUY_ITEMS_RESP.name();
        String s = "";
        try {
            s = frankie.buyItems(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cmd += separator + s;
        socketOut.println(cmd);
    }

    private void handleGetEmptyCart(String[] data) {
        String username = data[1];
        frankie.emptyCart(username);
    }

    private void handleGetDecreaseStock(String[] data){
        int itemcode = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);
        String username = data[3];

        try {
            frankie.decreaseStock(itemcode,quantity,username);
        } catch (ExceptionItemDoesntExist | IOException e) {
            e.printStackTrace();
        }

    }

    private void handleGetIncreaseStock(String[] data) {
        int itemCode = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);
        String username = data[3];


        try {
            frankie.increaseStock(itemCode, quantity, username);
        } catch (ExceptionItemDoesntExist | IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetSelectItem(String[] data){
        int itemCode = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);
        String username = data[3];

        try {
            frankie.selectItem(itemCode,quantity,username);
        } catch (ExceptionItemDoesntExist | ExceptionItemMismatchingPackingSize | ExceptionInsufficientStock e) {
            e.printStackTrace();
        }
    }

    private void handleGetRemoveItem(String[] data){
        int itemCode = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);
        String username = data[3];

        try {
            frankie.removeItems(itemCode,quantity,username);
        } catch (ExceptionItemMismatchingPackingSize | ExceptionItemDoesntExist | ExceptionInsufficientStock e) {
            e.printStackTrace();
        }

    }

    private void handleGetItemInfo(String[] data){
        String username = data[1];
        String cmd = Commands.CMD_GET_ITEM_INFO_RESP.name();
        String s = frankie.getItemInfo(username);
        cmd += separator + s;
        socketOut.println(cmd);
    }

    private void handleGetLogout(String[] data){
        String username = data[1];
            frankie.logout(username);
    }

    private void handleEndPrice(String[] data){
        String username = data[1];
        String cmd = Commands.CMD_END_PRICE_RESP.name();
        float price = frankie.endPrice(username);
        cmd += separator + price;
        socketOut.println(cmd);
    }

    private void handleGetItemsInCart(String[] data){
        String username = data[1];
        String cmd = Commands.CMD_GET_ITEMS_IN_CART_RESP.name();

        for (Item b : frankie.getItemsInCart(username).keySet()) {
            cmd += separator + b.getItemName();
            cmd += separator + b.getItemCode();
            cmd += separator + b.getPrice();
            cmd += separator + b.getNumberInStock();
            cmd += separator + b.getPackingSize();
            cmd += separator + b.getPic();
            int quantity =  frankie.getItemsInCart(username).get(b);
            cmd += separator + quantity;
        }
        socketOut.println(cmd);
    }
}


