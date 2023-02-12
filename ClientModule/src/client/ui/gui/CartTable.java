package client.ui.gui;
/*
import Common.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;

public class CartTable extends AbstractTableModel{

    private List<Item>  toPrintTable;
    private String[] columnNames={"Item Image", "Name", "Quantity", "Price"};

    public CartTable(List<Item>  toPrintTable){
        this.toPrintTable=new ArrayList<>();
        this.toPrintTable.addAll(toPrintTable);
    }

    public void setCartItems(List<Item>  toPrintTable){
        toPrintTable.clear();
        toPrintTable.addAll(toPrintTable);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount(){
        return toPrintTable.size();
    }
    public int getColumnCount(){
        return columnNames.length;
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Item selectedItem=toPrintTable.get(rowIndex);
        float partialPrice= selectedItem.getPrice() * (float)selectedItem.getQuantity();

        switch(columnIndex){
            case 0:
                return  new ImageIcon( new ImageIcon(selectedItem.getPic()).getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
            case 1:
                return selectedItem.getItemName();
            case 2:
                return selectedItem.getQuantity();
            case 3:
                return partialPrice;
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}
*/