package client.ui.cui;

import Common.*;
import Common.exceptions.*;
import Server.domain.EShopManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

class UI{
    private final BufferedReader in;
    boolean loggedIn;
    boolean itsAClient;
    private final IFrankie manager;
    private Person user;

    public UI() throws IOException {
        manager = new EShopManager();
        in=new BufferedReader(new InputStreamReader(System.in));
    }

    private void showMenu(){
        System.out.print("Commands: \n  Login:  'l'");
        System.out.print("         \n  Register:  'r'");
        System.out.print("         \n  ---------------------");
        System.out.println("         \n  Exit:        'q'");
        System.out.print("> ");
        System.out.flush();
    }

    private void showClientMenu(){
        System.out.print("Commands:\n ---------------------");
        System.out.print("         \n Show items (a-z): 'i' ");
        System.out.print("         \n Show items (item number) : 'n' ");
        System.out.print("         \n Show cart content: 'm' ");
        System.out.print("         \n Select items: 's' ");
        System.out.print("         \n Remove items: 'v' ");
        System.out.print("         \n Empty shopping cart: 'e' ");
        System.out.print("         \n Buy selected items: 'b' ");
        System.out.print("         \n ---------------------");
        System.out.println("         \n  Exit:        'w'");
        System.out.print("> ");
        System.out.flush();
    }

    private void showEmployeeMenu(){
        System.out.print("Commands:\n ---------------------");
        System.out.print("         \n Show items (a-z item name): 'i' ");
        System.out.print("         \n Show items (item code) : 'n' ");
        System.out.print("         \n Add new item: 'a' ");
        System.out.print("         \n Increase stock: 'k' ");
        System.out.print("         \n Decrease stock: 'j' ");
        System.out.print("         \n Register new employee: 'y' ");
        System.out.print("         \n Show stock logbook: 'x' ");
        System.out.print("         \n ---------------------");
        System.out.println("       \n  Exit:        'w'");
        System.out.print("> ");
        System.out.flush();
    }

    private String readInput() throws IOException{
        return in.readLine();
    }

    private void processInput(String line) throws IOException{
        String userName;
        String password;
        String address;
        int quantity;

        switch(line){
            case "a":{ //add new item
                System.out.print("Item Name > ");
                String itemName=readInput();
                System.out.print("Price > ");
                float price=Float.parseFloat(readInput());
                System.out.print("Number of items > ");
                int numberInStock=parseInt(readInput());
                System.out.print("Write the route of the image-- images/file name > ");
                String pic=readInput();
                System.out.print("Is this a wholesale Item? > ");
                System.out.print("Introduce 1 for yes and 2 for no > ");
                int wholesale=parseInt(readInput());

                if(wholesale==2){
                    try{
                        manager.createNewItem(itemName, price, numberInStock, this.user.getName(), pic);
                        System.out.println("Added to stock.");
                    } catch(ExceptionItemAlreadyExists e){
                        System.out.println("Item already exists. ");
                        e.printStackTrace();
                    }
                }

                  if(wholesale ==1){
                    System.out.print("Introduce package size:> ");
                    int packingSize=parseInt(readInput());{
                    try{
                        manager.createNewItem(itemName, price, numberInStock, packingSize, this.user.getName(), pic);
                        System.out.println("Added to stock.");
                    }
                    catch(ExceptionItemAlreadyExists e){
                        System.out.println("Item already exists. ");
                        e.printStackTrace();
                    }
                }}
                for(Item element: manager.getItemsInStock().values()){
                    System.out.println(element);
                }
            }
            break;

            case "b": { //buy selected items
                String receipt = manager.buyItems(this.user.getName());
                System.out.println(receipt);
                manager.emptyCart(this.user.getName());
                if (receipt.isEmpty()) {
                    System.out.println("Cart is empty. Please select items.");
                }
            }
                break;
            case "e": { //empty shopping cart
                manager.emptyCart(this.user.getName());
                System.out.println("Cart has been emptied.");
            }
                break;
            case "i": { //show items (a-z)
                Map<Integer, Item> itemsI = manager.getItemsInStock();
                List<Item> list = new ArrayList<>(itemsI.values());
                list.sort(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
                for (Item element : list) {
                    System.out.println(element);
                }
            }
            break;
            case "j":{ //decrease stock
                System.out.print("Item code > ");
                int itemCode=parseInt(readInput());
                System.out.print("Quantity > ");
                quantity=parseInt(readInput());
                try{
                    manager.decreaseStock(itemCode, quantity, this.user.getName());
                    System.out.println("Stock decreased. ");
                } catch(ExceptionItemDoesntExist e){
                    System.out.println("Item doesn't exist. ");
                    e.printStackTrace();
                }
            }
            break;
            case "k":{ //increase stock

                System.out.print("Item code > ");
                int itemCode=Integer.parseInt(readInput());
                System.out.print("Quantity > ");
                quantity=parseInt(readInput());


                try{
                    manager.increaseStock(itemCode, quantity, this.user.getName());
                    System.out.println("Stock increased. ");
                } catch(ExceptionItemDoesntExist e){
                    System.out.println("Item doesn't exist. ");
                    e.printStackTrace();
                }
            }
            break;
            case "l": { //login
                System.out.print("Username > ");
                userName = readInput();
                System.out.print("Password > ");
                password = readInput();
                try {
                    this.user = manager.login(userName, password);
                    System.out.println("Login successful.");
                    if (user != null) {
                        loggedIn = true;
                        itsAClient = user.getBoolean();
                    } else {
                        loggedIn = false;
                    }
                } catch (ExceptionLoginFailed e) {
                    System.out.println("Login failed. Please try again.");
                    loggedIn = false;
                    e.printStackTrace();
                }
            }
            break;
            case "m": //show cart content
                System.out.println(manager.getItemInfo(this.user.getName()));
            break;
            case "n": //Show items (item number) : 'n' "
            {
                Map<Integer, Item> items=manager.getItemsInStock();
                List<Item> list=new ArrayList<>(items.values());
                list.sort(Comparator.comparingInt(Item::getItemCode));
                for(Item element: list){
                    System.out.println(element);
                }

            }
            break;
            case "r": //register
                System.out.print("Username > ");
                userName=readInput();
                System.out.print("Password > ");
                password=readInput();
                System.out.print("Address > ");
                address=readInput();
                try{
                    manager.registerClient(userName, password, address);
                } catch(ExceptionUserAlreadyExists e){
                    System.out.println("Registration failed. Please select a different user name.");
                    e.printStackTrace();
                }
                break;
            case "s": //select item
                System.out.print("Item code > ");
                int itemCode=Integer.parseInt(readInput());
                System.out.print("Quantity >");
                quantity=parseInt(readInput());
                try{
                    manager.selectItem(itemCode, quantity, this.user.getName());
                    System.out.println("Added to cart.");
                } catch(ExceptionItemDoesntExist | ExceptionItemMismatchingPackingSize | ExceptionInsufficientStock e){
                    System.out.println(e.getMessage());
                }
                break;
            case "v": //remove item
                System.out.print("Item code > ");
                itemCode=Integer.parseInt(readInput());
                System.out.print("Quantity >");
                quantity=parseInt(readInput());
                try{
                    manager.removeItems(itemCode, quantity, this.user.getName());
                    System.out.println("Removed from cart");
                } catch(ExceptionItemDoesntExist e){
                    System.out.println("Item doesn't exist. ");
                    e.printStackTrace();
                } catch(ExceptionItemMismatchingPackingSize e){
                    System.out.println(e.getMessage());
                } catch (ExceptionInsufficientStock e) {
                    System.out.println("Selected quantity is too high.");
                    e.printStackTrace();
                }
                break;
            case "w": //log out
                loggedIn=false;
                manager.logout(this.user.getName());
                System.out.println("Logged out.");
                break;
            case "x": //show stock logbook
                manager.getEntries().sort(Comparator.comparing(Logbook::getDate, String.CASE_INSENSITIVE_ORDER));
                for(Logbook element: manager.getEntries()) {
                    System.out.println("------------------------");
                    System.out.println(element);
                }
                break;
            case "y": //register employee
                System.out.print("Username > ");
                userName=readInput();
                System.out.print("Password > ");
                password=readInput();
                try{
                    manager.registerEmployee(userName, password);
                } catch(ExceptionUserAlreadyExists e){
                    System.out.println("Registration failed. Please select a different user name.");
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }

    }

    public void run(){
        String input="";
        do{
            if(loggedIn){
                if(itsAClient){
                    showClientMenu();
                } else {
                    showEmployeeMenu();
                }
            } else {
                showMenu();
            }

            try{
                input=readInput();
                processInput(input);
            } catch(IOException e){
                e.printStackTrace();
            }
        } while(!input.equals("q"));
    }

    public static void main(String[] args) throws IOException {
        UI cui=new UI();
        cui.run();
    }

}