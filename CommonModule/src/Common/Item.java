package Common;

public class Item {
    protected String itemName;
    protected float price;
    protected int itemCode;
    protected int numberInStock;
    private int packingSize=1;
    private String pic;
    public static int count=1;

    public Item(String itemName, float price, int numberInStock, int packingSize, String pic){
        this.itemName=itemName;
        this.price=price;
        this.numberInStock=numberInStock;
        this.packingSize=packingSize;
        this.pic= pic;
        this.itemCode=count++;
    }
    public Item(String itemName, float price, int numberInStock, int packingSize){
        this.itemName=itemName;
        this.price=price;
        this.itemCode=count++;
        this.numberInStock=numberInStock;
        this.packingSize=packingSize;

    }
    public Item(String itemName, float price, int numberInStock, String pic){
        this.itemName=itemName;
        this.price=price;
        this.itemCode=count++;
        this.numberInStock=numberInStock;
        this.pic= pic;
    }

    public Item(String itemName, int itemCode, float price, int numberInStock, String pic){
        this.itemName=itemName;
        this.price=price;
        this.itemCode = itemCode;
        this.numberInStock=numberInStock;
        this.pic= pic;
    }

    public Item(String itemName,  int itemCode, float price, int numberInStock, int packingSize, String pic){
        this.itemName=itemName;
        this.price=price;
        this.numberInStock=numberInStock;
        this.packingSize=packingSize;
        this.pic= pic;
        this.itemCode = itemCode;
    }

    public Item(String itemName,  int itemCode, float price, int numberInStock, int packingSize){
        this.itemName=itemName;
        this.price=price;
        this.itemCode = itemCode;
        this.numberInStock=numberInStock;
        this.packingSize=packingSize;

    }


    public String toString(){
        String availability=(numberInStock > 0)?"available":"sold out"; //this is to know if we have this item in stock
        String packing=(packingSize > 1)?" only in packs of "+packingSize:" ";
        return ("Item Name " + itemName + " / price: " + price + "/ Item code: "+ itemCode +" / Availability: "+availability+packing+" /Number of items: "+numberInStock);
    }
    //methods to access the information of the items
    public String getItemName(){
        return itemName;
    }
    public float getPrice(){
        return price;
    }
    public int getItemCode(){return itemCode;}
    public int getNumberInStock(){
        return numberInStock;
    }
    public int getPackingSize(){
        return packingSize;
    }
    public String getPic(){return pic;}

    public void addItemInStock(int stockIncrease){this.numberInStock=numberInStock+stockIncrease;}
    public void decreaseItemStock(int stockDecrease){this.numberInStock=numberInStock-stockDecrease;}
}



