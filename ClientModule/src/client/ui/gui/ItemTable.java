package client.ui.gui;

import Common.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTable extends AbstractTableModel{
    private final List<Item> items;

    private final String[] columnNames={"Item code", "Item name","Item Image", "Price", "Number in stock", "Packing size"};

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
        return switch (columnIndex) {
            case 0 -> selectedItem.getItemCode();
            case 1 -> selectedItem.getItemName();
            case 2 ->
                    new ImageIcon(new ImageIcon(selectedItem.getPic()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
            case 3 -> selectedItem.getPrice();
            case 4 -> selectedItem.getNumberInStock();
            case 5 -> selectedItem.getPackingSize();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}

