package Server.domain;

import Common.Item;
import Common.Receipt;

import java.util.HashMap;
import java.util.Map;

public class ShopCart{
    public Map<Item, Integer> itemsInCart;
    public ShopCart(){
        itemsInCart=new HashMap<>();
    }

    public void putItemsInCart(Item item, int quantity){
        Item itemThatIsInCart = new Item(item.getItemName(), item.getItemCode(), item.getPrice(), item.getNumberInStock(),
        item.getPic(), quantity );
        itemsInCart.putIfAbsent(itemThatIsInCart, quantity);
    }

    public void emptyCart(){
        itemsInCart.clear();
    }

    public void removeItemsFromCart(Item item){
        itemsInCart.remove(item);
    }

    public void increaseItemStock(int itemCode, int stockIncrease) {

        for (Item item : itemsInCart.keySet()) {
            if (item.getItemCode() == itemCode) {
                int number = itemsInCart.get(item);
                itemsInCart.replace(item, number + stockIncrease);
            }
        }
    }

    //deletes a certain quantity of objects
    public void decreaseItemStock(int itemCode, int stockDecrease){
        for(Item item: itemsInCart.keySet()){
            if(item.getItemCode() == itemCode){
                int number = itemsInCart.get(item);
                itemsInCart.replace(item, number-stockDecrease);
            }
        }
    }

    public Map<Item, Integer> getItemsInCart(){
        return itemsInCart;
    }

    public String getItemInfo(){
        String s="";
        for(Map.Entry<Item, Integer> entry: itemsInCart.entrySet()){
            Item k=entry.getKey();
            Integer v=entry.getValue();
            s+=("Item name: "+k.getItemName()+"\n"+" / Item code: "+k.getItemCode()+"\n"+"  / Item code: "+k.getPic()+"\n"+" / Price: "+k.getPrice()+"\n"+"  / Quantity: "+v+"\n");

        }
        return s;
    }

    public float endPrice(){
        float price=0;
        for(Map.Entry<Item, Integer> entry: itemsInCart.entrySet()){
            Item k=entry.getKey();
            Integer vQuantity=entry.getValue();
            price+=(k.getPrice() * vQuantity);
        }
        return price;
    }

    public Receipt informationForReceipt(String userName){
        return new Receipt(userName);
    }
}
