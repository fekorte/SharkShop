package Common;

public abstract class Person{
    protected String name;
    protected String password;
    protected int idNumber;
    public static int count=1;

    public Person(String name, String password){
        this.name=name;
        this.password=password;
        this.idNumber=count++;
    }
    public Person(String name){
        this.name=name;
        this.idNumber=count++;
    }

    public String getName(){return name;}
    public String getPassword(){return password;}
    public int getIdNumber(){return idNumber;}

    public abstract boolean getBoolean();
    public abstract String toString();
    public abstract String getAddress();
}