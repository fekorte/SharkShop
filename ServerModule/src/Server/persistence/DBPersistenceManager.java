package Server.persistence;

import Server.domain.EShopManager;
import Server.domain.EShopManager;
import Common.Item;
import Common.Logbook;
import Common.Person;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DBPersistenceManager implements IPersistence {
    @Override
    public void openForReading(String data) throws IOException{
    }

    @Override
    public void openForWriting(String data) throws IOException{
    }

    @Override
    public boolean close(){
        return false;
    }

    @Override
    public Map<Integer, Item> fetchItem(String file) throws IOException{
        return null;
    }

    @Override
    public boolean saveItem(Map<Integer, Item> stock, String file) throws IOException{
        return false;
    }

    @Override
    public List<Person> fetchUsers(String file) throws IOException{
        return null;
    }

    @Override
    public boolean saveUsers(List<Person> users, String file) throws IOException{
        return false;
    }

    @Override
    public List<Logbook> fetchLogbookEntries(String file, EShopManager maanger) throws IOException{
        return null;
    }

    @Override
    public boolean saveLogbookEntries(List<Logbook> logbookEntries, String file) throws IOException{
        return false;
    }
}
