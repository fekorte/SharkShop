package Common.exceptions;

public class ExceptionItemMismatchingPackingSize extends Exception{
    public ExceptionItemMismatchingPackingSize(int packingSize){
        super("This item is only available in packages of: "+packingSize);
    }
}