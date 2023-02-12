package Common;

public class Client extends Person{
    private String address;
    private boolean client;

    public Client(String name, String password, String address){
        super(name, password);
        this.address=address;
        client=true;

    }
    public Client(String name){
        super(name);
    }
    public boolean getBoolean(){
        return client;
    }
    public String getAddress(){
        return address;
    }
    public String toString(){
        return ("Name: "+name+"\n"+"ID number: "+idNumber+"\n"+"Adresse: "+address+"\n");
    }
}