package client.ui.gui;
/*
import Common.Item;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReceiptTable extends AbstractTableModel{
    private List<Item> receiptReport ;
    private String[] columnNames={"Items", "Quantity", "Price", "Partial price"};
    public ReceiptTable(List<Item> receiptReport){
        this.receiptReport=new ArrayList<>();
        this.receiptReport.addAll(receiptReport);
    }
    public void setCartItems(List<Item>receiptReport){
        receiptReport.clear();
        receiptReport.addAll(receiptReport);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount(){
        return receiptReport.size();
    }
    public int getColumnCount(){
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Item selectedItem=receiptReport.get(rowIndex);

        switch(columnIndex){
            case 0:
                return  selectedItem.getItemName();
            case 1:
                return selectedItem.getQuantity();
            case 2:
                return selectedItem.getPrice();
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
