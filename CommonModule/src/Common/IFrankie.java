package Common;
import Common.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IFrankie {
    Map<Integer, Item> getItemsInStock();

    List<Logbook> getEntries();

    List<Person> getUsers();

    Person login(String userName, String password) throws ExceptionLoginFailed;
    void registerClient(String userName, String password, String address) throws ExceptionUserAlreadyExists, IOException;

    void registerEmployee(String userName, String password) throws ExceptionUserAlreadyExists, IOException;

    void createNewItem(String itemName, float price, int numberInStock, String userName, String filePicName) throws ExceptionItemAlreadyExists, IOException;

    void createNewItem(String itemName, float price, int numberInStock, int packingSize, String userName, String filePicName) throws ExceptionItemAlreadyExists, IOException;

    String buyItems(String userName) throws IOException;

    void emptyCart(String userName);

    String getItemInfo(String userName);

    void decreaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException;

    void increaseStock(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, IOException;

    void selectItem(int itemCode, int quantity, String userName) throws ExceptionItemDoesntExist, ExceptionItemMismatchingPackingSize, ExceptionInsufficientStock;

    void removeItems(int itemCode, int quantity, String userName) throws ExceptionItemMismatchingPackingSize, ExceptionItemDoesntExist, ExceptionInsufficientStock;
    void logout(String userName);

    float endPrice(String userName);

    Map<Item, Integer>getItemsInCart(String userName);
}




