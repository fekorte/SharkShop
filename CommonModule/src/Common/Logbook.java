package Common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Logbook{
    private String date;
    private String hour;
    private Item item;
    private String userName;
    private String entry;
    private int quantity;

    private String getDateFromSystem(){ return String.valueOf(LocalDate.now());}
    private String getTimeFromSystem(){
        LocalTime time=LocalTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMAN);
        return formatter.format(time);
    }
    public Logbook(String entry, Item item, int quantity, String userName) {
        this.entry=entry;
        this.item=item;
        this.quantity = quantity;
        this.userName = userName;
        this.date=getDateFromSystem();
        this.hour=getTimeFromSystem();
    }
    public Logbook(String entry, Item item, int quantity, String userName, String date, String hour) {
        this.entry=entry;
        this.item=item;
        this.quantity = quantity;
        this.userName = userName;
        this.date=date;
        this.hour=hour;
    }
    public String toString(){
        return (entry+"\n"+"Item code: "+item.getItemCode()+"\n"+"Item name: "+item.getItemName()+
                "\n"+"Quantity: "+ quantity +"\n"+"Name: "+ userName +"\n"+"Date and time: "+date+", "+hour+"\n");
    }
    public String getEntry(){
        return entry;
    }
    public String getUserName(){
        return userName;
    }
    public Item getItem(){
        return item;
    }
    public String getDate(){
        return date;
    }
    public int getQuantity(){ return quantity;}
    public String getTime(){
        return hour;
    }
}
