package client.net;

import Common.*;
import Common.exceptions.*;
import Server.domain.ShopCart;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrankieClient implements Common.IFrankie {
    private Socket socket;
    private BufferedReader socketIn;
    private PrintStream socketOut;

    final String separator = ";";

    //to read the responses the server send
    private String[] readResponse() {
        String[] parts = null;
        try {
            String receivedData = socketIn.readLine();
            parts = receivedData.split(separator);

            System.err.println("Received answer: " + receivedData);
        } catch (SocketTimeoutException e) {
            System.out.println("Server didn't answer.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }


    public FrankieClient() throws IOException {
        socket = new Socket("127.0.0.1", 1399);
        socket.setSoTimeout(1000);

        InputStream inputStream = socket.getInputStream();
        socketIn = new BufferedReader(new InputStreamReader(inputStream));
        socketOut = new PrintStream(socket.getOutputStream());
    }


   
    @Override
    public Map<Integer, Item> getItemsInStock() {
        String cmd = Commands.CMD_GET_ITEMS.name();
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_GET_ITEMS_RESP) {
            throw new RuntimeException("Invalid response to request.");
        }
        return createItemMapFromData(data);
    }


    private Map<Integer, Item> createItemMapFromData(String[] data) {
        Map<Integer, Item> items = new HashMap<>();

        for (int i = 1; i < data.length; i += 6) {
            String itemName = data[i];
            int itemCode = Integer.parseInt(data[i + 1]);
            float price = Float.parseFloat(data[i + 2]);
            int numberInStock = Integer.parseInt(data[i + 3]);
            int packingSize = Integer.parseInt(data[i + 4]);
            String pic = data[i+5];
            Item newItem = new Item(itemName, itemCode, price, numberInStock, packingSize, pic);
            items.put(newItem.getItemCode(), newItem);
        }
        return items;
    }

    @Override
    public List<Logbook> getEntries() {
        String cmd = Commands.CMD_GET_ENTRIES.name();
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_GET_ENTRIES_RESP) {
            throw new RuntimeException("Invalid response to request.");
        }
        return createLogBookListFromData(data);
    }

    public List<Logbook> createLogBookListFromData(String[] data) {
        List<Logbook> entries = new ArrayList<>();

        for (int i = 1; i < data.length; i += 8) {
            String entry = data[i];
            String itemName = data[i + 1];
            int itemCode = Integer.parseInt(data[i + 2]);
            float price = Float.parseFloat(data[i + 3]);
            int numberInStock = Integer.parseInt(data[i + 4]);
            int packingSize = Integer.parseInt(data[i + 5]);
            String userName = data[i + 6];
            int quantity = Integer.parseInt(data[i + 7]);
            Item newItem = new Item(itemName, itemCode, price, numberInStock, packingSize);
            Logbook book = new Logbook(entry, newItem, quantity, userName);
            entries.add(book);
        }
        return entries;
    }

    @Override
    public List<Person> getUsers() {
        String cmd = Commands.CMD_GET_USERS.name();
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_GET_USERS_RESP) {
            throw new RuntimeException("Invalid response to request.");
        }
        return createUserListFromData(data);
    }

    public List<Person> createUserListFromData(String[] data) {
        List<Person> users = new ArrayList<>();

        for (int i = 1; i < data.length; i += 4) {
            String username = data[i];
            String password = data[i + 1];
            boolean itsAClient = Boolean.parseBoolean(data[i + 2]);
            if (itsAClient) {
                String address = data[i + 3];

                Person client = new Client(username, password, address);
                users.add(client);
            } else {
                Boolean itsAnEmployee = Boolean.valueOf(data[i + 3]);
                Person employee = new Employee(username, password);
                users.add(employee);
            }
        }
        return users;
    }

    @Override
    public Person login(String userName, String password){
        String cmd = Commands.CMD_LOGIN.name() + separator + userName + separator + password;
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_LOGIN_RESP) {
            throw new RuntimeException("Invalid Answer to request!");
        }
        String name = data[1];
        String password2 = data[2];
        boolean itsAClient = Boolean.parseBoolean(data[3]);
        if (itsAClient) {
            String address = data[4];
            return new Client(name, password2, address);
        } else {
            return new Employee(name, password2);
        }
    }
    @Override
    public void registerClient(String userName, String password, String address){
        String cmd = Commands.CMD_REGISTER_CLIENT.name() + separator + userName + separator + password + separator + address;
        socketOut.println(cmd);

    }

    @Override
    public void registerEmployee(String userName, String password) {
        String cmd = Commands.CMD_REGISTER_EMPLOYEE.name() + separator + userName + separator + password;
        socketOut.println(cmd);
    }


    @Override
    public void createNewItem(String itemName, float price, int numberInStock, String userName, String filePicName) {
        String cmd = Commands.CMD_CREATE_NEW_ITEM1.name() + separator + itemName + separator + price + separator + numberInStock + separator + userName + separator + filePicName;
        socketOut.println(cmd);
    }

    @Override
    public void createNewItem(String itemName, float price, int numberInStock, int packingSize, String userName, String filePicName) throws ExceptionItemAlreadyExists, IOException {
        String cmd = Commands.CMD_CREATE_NEW_ITEM2.name() + separator + itemName + separator + price + separator + numberInStock + separator + packingSize + separator + userName + separator + filePicName;
        socketOut.println(cmd);
    }


    @Override
    public String buyItems(String userName) throws IOException {
        String cmd = Commands.CMD_BUY_ITEMS.name() + separator + userName;
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_BUY_ITEMS_RESP) {
            throw new RuntimeException("Invalid Answer to request!");
        }
        return data[1];
    }


    @Override
    public void emptyCart(String userName) {
        String cmd = Commands.CMD_EMPTY_CART.name() + separator + userName;
        socketOut.println(cmd);
    }


    @Override
    public void decreaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException {
        String cmd = Commands.CMD_DECREASE_STOCK.name() + separator + itemCode + separator + quantity + separator + userName;
        socketOut.println(cmd);
    }


    public void increaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException {
        String cmd = Commands.CMD_INCREASE_STOCK.name() + separator + itemCode + separator + quantity + separator + userName;
        socketOut.println(cmd);
    }


    public void selectItem(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, ExceptionItemMismatchingPackingSize, ExceptionInsufficientStock {
        String cmd = Commands.CMD_SELECT_ITEM.name() + separator + itemCode + separator + quantity + separator + userName;
        socketOut.println(cmd);
    }


    public void removeItems(int itemCode, int quantity, String userName) throws ExceptionItemMismatchingPackingSize, ExceptionItemDoesntExist {
        String cmd = Commands.CMD_REMOVE_ITEM.name() + separator + itemCode + separator + quantity + separator + userName;
        socketOut.println(cmd);
    }


    @Override
    public String getItemInfo(String userName) {
        String cmd = Commands.CMD_GET_ITEM_INFO.name() + separator + userName;
        socketOut.println(cmd);

        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_GET_ITEM_INFO_RESP) {
            throw new RuntimeException("Invalid Answer to request!");
        }
        return data[1];
    }


    public void logout(String userName) {
        String cmd = Commands.CMD_LOGOUT.name() + separator + userName;
        socketOut.println(cmd);
    }

    @Override
    public float endPrice(String userName) {
        String cmd = Commands.CMD_END_PRICE.name() + separator + userName;
        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_END_PRICE_RESP) {
            throw new RuntimeException("Invalid Answer to request!");
        }
        return Float.parseFloat(data[1]);
    }


    @Override
    public Map<Item, Integer> getItemsInCart(String userName) {
        String cmd = Commands.CMD_GET_ITEMS_IN_CART.name() + separator + userName;
        String[] data = readResponse();

        if (Commands.valueOf(data[0]) != Commands.CMD_GET_ITEMS_IN_CART_RESP) {
            throw new RuntimeException("Invalid Answer to request!");
        }
        return createShopCartMapFromData(data);
    }


    private Map<Item, Integer> createShopCartMapFromData(String[] data) {
        Map<Item, Integer> cart = new HashMap<>();

        for (int i = 1; i < data.length; i += 7) {
            String itemName = data[i];
            int itemCode = Integer.parseInt(data[i + 1]);
            float price = Float.parseFloat(data[i + 2]);
            int numberInStock = Integer.parseInt(data[i + 3]);
            int packingSize = Integer.parseInt(data[i + 4]);
            String pic = data[i + 5];
            int quantity = Integer.parseInt(data[i + 6]);
            Item newItem = new Item(itemName, itemCode, price, numberInStock, packingSize);
            cart.put(newItem,quantity);
        }
        return cart;
    }
}


