package Server.persistence;

import Common.Item;
import Server.domain.EShopManager;
import Server.domain.EShopManager;
import Common.Logbook;
import Common.Person;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IPersistence{
    public void openForReading(String data) throws IOException;

    public void openForWriting(String data) throws IOException;

    public boolean close();

    Map<Integer, Item> fetchItem(String file) throws IOException;

    boolean saveItem(Map<Integer, Item> stock, String file) throws IOException;

    List<Person> fetchUsers(String file) throws IOException;

    boolean saveUsers(List<Person> users, String file) throws IOException;

    List<Logbook> fetchLogbookEntries(String file, EShopManager manager) throws IOException;

    boolean saveLogbookEntries(List<Logbook> logbookEntries, String file) throws IOException;
}









