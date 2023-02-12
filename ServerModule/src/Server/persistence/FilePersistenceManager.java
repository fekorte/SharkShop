package Server.persistence;

import Common.*;
import Server.domain.EShopManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePersistenceManager implements IPersistence {
    private BufferedReader reader=null;
    private PrintWriter writer=null;

    @Override
    public Map<Integer, Item> fetchItem(String file) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(file));
        Map<Integer, Item> stock=new HashMap<>();
        while(br.read() != -1) {
            String firstSign=br.readLine(); //first symbol of document vanishes (don't know why) so that's why we have this

            String itemName=br.readLine();
            int itemCode=Integer.parseInt(br.readLine());
            float price=Float.parseFloat(br.readLine());
            int numberInStock=Integer.parseInt(br.readLine());
            int packingSize=Integer.parseInt(br.readLine());
            String pic=br.readLine();

            Item item=new Item(itemName, itemCode, price, numberInStock, packingSize, pic);
            stock.put(item.getItemCode(),item);

        }
        return stock;

    }

    @Override
    public void saveItem(Map<Integer, Item> stock, String file) throws IOException{ //used to write in the txt
        try (PrintWriter fw2=new PrintWriter(file)){
            for(Item item: stock.values()){ //each time we save the list we save every single item the list contains, not only the new ones
                fw2.println("-");
                fw2.println(item.getItemName());
                fw2.println(item.getItemCode());
                fw2.println(item.getPrice());
                fw2.println(item.getNumberInStock());
                fw2.println(item.getPackingSize());
                fw2.println(item.getPic());
            }
        }
    }

    @Override
    public List<Person> fetchUsers(String file) throws IOException{

        try (BufferedReader br=new BufferedReader(new FileReader(file))){
            List<Person> users=new ArrayList<>();

            while(br.read() != -1) {

                String firstLine=br.readLine();

                String userName=br.readLine();
                String password=br.readLine();
                int idNumber=Integer.parseInt(br.readLine());
                String personType=br.readLine();

                if(personType.equals("Client")){ //checks if it is a client or employee
                    String address=br.readLine(); //if it is a client we have an extra line
                    Person client=new Client(userName, password, address);
                    users.add(client);
                } else {
                    Person employee=new Employee(userName, password);
                    users.add(employee);
                }
            }
            return users;
        }
    }

    @Override
    public void saveUsers(List<Person> users, String file) throws IOException{
        try (PrintWriter fw=new PrintWriter(file)){
            for(Person user: users){
                fw.println("-");
                fw.println(user.getName());
                fw.println(user.getPassword());
                fw.println(user.getIdNumber());
                if(user.getBoolean()){
                    fw.println("Client");
                    fw.println(user.getAddress());
                } else {
                    fw.println("Employee");
                }
            }
        }
    }

    @Override
    public List<Logbook> fetchLogbookEntries(String file, EShopManager manager) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            List<Logbook> entries = new ArrayList<>();

            while (br.read() != -1) {

                String firstLine = br.readLine();

                String entry = br.readLine();
                int itemCode = Integer.parseInt(br.readLine());
                String itemName = br.readLine();
                int numberInStock = Integer.parseInt(br.readLine());
                String userName = br.readLine();
                int quantity = Integer.parseInt(br.readLine());
                String date = br.readLine();
                String hour = br.readLine();

                for (Item element : manager.getItemsInStock().values()) {
                    if (element.getItemCode() == itemCode) {
                        Item addTo = new Item(itemName, itemCode, element.getPrice(), numberInStock, element.getPackingSize());
                        Logbook logbook = new Logbook(entry, addTo, quantity, userName, date, hour);
                        entries.add(logbook);
                    }
                }
            }
            return entries;
        }
    }


    @Override
    public void saveLogbookEntries(List<Logbook> logbookEntries, String file) throws IOException{
        try (PrintWriter fw2=new PrintWriter(file)){
            for(Logbook entry: logbookEntries){
                fw2.println("-");
                fw2.println(entry.getEntry());
                fw2.println(entry.getItem().getItemCode());
                fw2.println(entry.getItem().getItemName());
                fw2.println(entry.getItem().getNumberInStock());
                fw2.println(entry.getUserName());
                fw2.println(entry.getQuantity());
                fw2.println(entry.getDate());
                fw2.println(entry.getTime());
            }
        }
    }
}