package Server.domain;

import Server.persistence.FilePersistenceManager;
import Server.persistence.IPersistence;
import Common.Item;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Stock{
    private IPersistence pm=new FilePersistenceManager();
    private String file="";

    public Map<Integer, Item> itemStock;

    public Stock() throws IOException{
        itemStock=new HashMap(pm.fetchItem(file+"Stocklist.txt"));

    }

    public void setStock(Map<Integer, Item> itemStock){
        this.itemStock=itemStock;
    }

    public Map<Integer, Item> getStock(){
        return itemStock;
    }

    public void increaseStock(int itemCode, int stockIncrease){
        for(Item item: itemStock.values()){
            if(item.getItemCode() == itemCode){
                item.addItemInStock(stockIncrease);
            }
        }
    }

    public void decreaseStock(int itemCode, int stockIncrease){
        for(Item item: itemStock.values()){
            if(item.getItemCode() == itemCode){
                item.decreaseItemStock(stockIncrease);
            }
        }
    }
}
