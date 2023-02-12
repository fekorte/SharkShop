package client.ui.gui;

import Common.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTable extends AbstractTableModel{
    private List<Item> items;

    private String[] columnNames={"Item code", "Item name","Item Image", "Price", "Number in stock", "Packing size"};

    public ItemTable(List<Item> currentItems){
        items=new ArrayList<>();
        items.addAll(currentItems);
    }

    public void setItems(List<Item> currentItems){
        items.clear();
        items.addAll(currentItems);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount(){
        return items.size();
    }

    @Override
    public int getColumnCount(){
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Item selectedItem=items.get(rowIndex);
        switch(columnIndex){
            case 0:
                return selectedItem.getItemCode();
            case 1:
                return selectedItem.getItemName();
            case 2:
                return  new ImageIcon( new ImageIcon(selectedItem.getPic()).getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
            case 3:
                return selectedItem.getPrice();
            case 4:
                return selectedItem.getNumberInStock();
            case 5:
                return selectedItem.getPackingSize();
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}

