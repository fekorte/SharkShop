package Common.exceptions;

import Common.Item;

public class ExceptionItemDoesntExist extends Exception{
    public ExceptionItemDoesntExist(int itemCode) {
        super("Item with item code " + itemCode + " doesn't exist. ");
    }
}
