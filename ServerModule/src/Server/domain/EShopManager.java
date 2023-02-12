package Server.domain;

import Common.*;
import Common.exceptions.*;
import Server.persistence.FilePersistenceManager;
import Server.persistence.IPersistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EShopManager implements Common.IFrankie {

    private String file="";
    private IPersistence pm;
    private Stock stock;

    List<Person> users;
    List<Logbook> entries;
    Map<String, ShopCart> ourClients;

    public EShopManager() throws IOException{
        pm =new FilePersistenceManager();
        stock =new Stock();
        ourClients = new HashMap<>();
        users=new ArrayList<>(pm.fetchUsers(file+"Userlist.txt"));
        entries=new ArrayList<>(pm.fetchLogbookEntries(file+"Logbooklist.txt", this)); //same
    }
    public Map<Integer, Item> getItemsInStock(){
        return stock.getStock();
    }
    public List<Logbook> getEntries(){
        return entries;
    }
    public List<Person> getUsers(){
        return users;
    }
    public Person login(String userName, String password) throws ExceptionLoginFailed {
        for(Person person: users){
            if(person.getName().equals(userName) && person.getPassword().equals(password)){
                ourClients.putIfAbsent(person.getName(), new ShopCart());
                return person;
            }
        }
        throw new ExceptionLoginFailed(userName);
    }
    public void registerClient(String userName, String password, String address) throws ExceptionUserAlreadyExists, IOException{
        for(Person user: users){
            if(user.getName().equals(userName)){
                throw new ExceptionUserAlreadyExists(userName);
            }
        }
        Client client=new Client(userName, password, address);
        users.add(client);
        pm.saveUsers(users, file+"Userlist.txt");
    }

    public void registerEmployee(String userName, String password) throws ExceptionUserAlreadyExists, IOException{
        for(Person user: users){
            if(user.getName().equals(userName)){
                throw new ExceptionUserAlreadyExists(userName);
            }
        }
        Employee employee=new Employee(userName, password);
        users.add(employee);
        pm.saveUsers(users, file+"Userlist.txt");
    }

    public void createNewItem(String itemName, float price, int numberInStock, String userName, String filePicName) throws ExceptionItemAlreadyExists, IOException{
        Map<Integer, Item> stockItems=getItemsInStock();
        for(Item element: stockItems.values()){
            if(element.getItemName().equals(itemName)){
                int itemCode=element.getItemCode();
                throw new ExceptionItemAlreadyExists(itemCode);
            }
        }

        Item itemNew=new Item(itemName, price, numberInStock, filePicName);
        int itemCode=itemNew.getItemCode();
        stockItems.put(itemCode, itemNew);
        pm.saveItem(stockItems, file+"Stocklist.txt");
        String entry="New item added. ";
        Logbook logbook=new Logbook(entry, itemNew, numberInStock, userName);
        entries.add(logbook);
        pm.saveLogbookEntries(entries, file+"Logbooklist.txt");
    }

    public void createNewItem(String itemName, float price, int numberInStock, int packingSize, String userName, String filePicName)
            throws ExceptionItemAlreadyExists, IOException{

        Map<Integer, Item> stockItems=getItemsInStock();
        for(Item element: stockItems.values()){
            if(element.getItemName().equals(itemName)){
                int itemCode = element.getItemCode();
                throw new ExceptionItemAlreadyExists(itemCode);
            }
        }

        Item itemNew=new Item(itemName, price, numberInStock, packingSize, filePicName);
        int itemCode=itemNew.getItemCode();
        stockItems.put(itemCode, itemNew);
        pm.saveItem(stockItems, file+"Stocklist.txt");
        String entry="New item added. ";
        Logbook logbook=new Logbook(entry, itemNew, numberInStock, userName);
        entries.add(logbook);
        pm.saveLogbookEntries(entries, file+"Logbooklist.txt");
    }

    public String buyItems(String userName) throws IOException{
        ShopCart itemCodeMap = ourClients.get(userName);
        Map<Integer, Item> stockItems=getItemsInStock();
        for(Item element: stockItems.values()){
            for(Map.Entry<Item, Integer> entry:
                    itemCodeMap.itemsInCart.entrySet()){
                Item k=entry.getKey();
                Integer v=entry.getValue();
                if(element.getItemName().equals(k.getItemName())){
                    element.decreaseItemStock(v);
                    pm.saveItem(stockItems, file+"Stocklist.txt");
                    String stockEntry="Items bought. ";
                    Logbook logbook=new Logbook(stockEntry, element, element.getNumberInStock(), userName);
                    entries.add(logbook);
                    pm.saveLogbookEntries(entries, file+"Logbooklist.txt");
                }
            }
        }

        Receipt r = itemCodeMap.informationForReceipt(userName);
        return r.toString()+ "\n" + "Bought Items: " + "\n" + itemCodeMap.getItemInfo() + "\n" + "End price: " +
                itemCodeMap.endPrice() + "\n" + "Thank you for shopping with us.";
    }

    public float endPrice(String userName){
        ShopCart itemCodeMap = ourClients.get(userName);
        return itemCodeMap.endPrice();
    }
    public void emptyCart(String userName){
        ShopCart cart = ourClients.get(userName);
        cart.emptyCart();
    }
    public String getItemInfo(String userName){
        ShopCart cart = ourClients.get(userName);
        return cart.getItemInfo();
    }
    public void decreaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException{

        if(!getItemsInStock().containsKey(itemCode)){
            throw new ExceptionItemDoesntExist(itemCode);
        }

        for(Item item: getItemsInStock().values()){
            if(item.getItemCode() == itemCode){
                stock.decreaseStock(itemCode, quantity);
                if(stock.itemStock.get(itemCode).getNumberInStock() == 0){
                    stock.itemStock.remove(itemCode);
                }
                pm.saveItem(stock.itemStock, file+"Stocklist.txt");
                String entry="Stock decreased.";
                Logbook logbook=new Logbook(entry, item, quantity, userName);
                entries.add(logbook);
                pm.saveLogbookEntries(entries, file+"Logbooklist.txt");
            }
        }

    }
    public void increaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException{

        if(!getItemsInStock().containsKey(itemCode)){
            throw new ExceptionItemDoesntExist(itemCode);
        }

        for(Item item: getItemsInStock().values()){
            if(item.getItemCode() == itemCode){
                stock.increaseStock(itemCode, quantity);
                pm.saveItem(stock.itemStock, file+"Stocklist.txt");
                String entry="Stock increased.";
                Logbook logbook=new Logbook(entry, item, quantity, userName);
                entries.add(logbook);
                pm.saveLogbookEntries(entries, file+"Logbooklist.txt");
            }
        }
    }
    public void selectItem(int itemCode, int quantity, String userName)
            throws ExceptionItemDoesntExist, ExceptionItemMismatchingPackingSize, ExceptionInsufficientStock {

        if(!getItemsInStock().containsKey(itemCode)) throw new ExceptionItemDoesntExist(itemCode);

        ShopCart cart = ourClients.get(userName);
        int numberInStock=getItemsInStock().get(itemCode).getNumberInStock();
        int packingSize=getItemsInStock().get(itemCode).getPackingSize();

        if((numberInStock < quantity)) throw new ExceptionInsufficientStock();
        if(!(packingSize == 1 || quantity % packingSize == 0))
            throw new ExceptionItemMismatchingPackingSize(packingSize);

        if(cart.itemsInCart.containsKey(getItemsInStock().get(itemCode))){
            cart.increaseItemStock(itemCode, quantity);
        } else {
            cart.putItemsInCart(getItemsInStock().get(itemCode), quantity);
        }
    }
    public Map<Item, Integer>getItemsInCart(String userName){
        ShopCart cart = ourClients.get(userName);
        return cart.getItemsInCart();
    }
    public void removeItems(int itemCode, int quantity, String userName) throws ExceptionItemMismatchingPackingSize,
            ExceptionItemDoesntExist {


        if(!stock.itemStock.containsKey(itemCode)) throw new ExceptionItemDoesntExist(itemCode);

        ShopCart cart = ourClients.get(userName);

        int packingSize = getItemsInStock().get(itemCode).getPackingSize();

        if(!(packingSize == 1 || quantity % packingSize == 0))
            throw new ExceptionItemMismatchingPackingSize(packingSize);

        cart.decreaseItemStock(itemCode, quantity);
    }
    public void logout(String userName){
        emptyCart(userName);
        ourClients.remove(userName);
    }
}


