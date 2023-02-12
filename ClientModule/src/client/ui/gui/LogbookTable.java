package client.ui.gui;

import Common.Logbook;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LogbookTable extends AbstractTableModel{
    private List<Logbook> logbookList;
    private String[] columnNames={"Entry", "Item code", "Item name", "Quantity", "User", "Date and time"};
    public LogbookTable(List<Logbook> currentLogbookList){
        logbookList=new ArrayList<>();
        logbookList.addAll(currentLogbookList);
    }
    public void setLogbookList(List<Logbook> currentLogbookList){
        logbookList.addAll(currentLogbookList);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount(){
        return logbookList.size();
    }
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Logbook selectedLogbookEntries=logbookList.get(rowIndex);
        switch(columnIndex){
            case 0:
                return selectedLogbookEntries.getEntry();
            case 1:
                return selectedLogbookEntries.getItem().getItemCode();
            case 2:
                return selectedLogbookEntries.getItem().getItemName();
            case 3:
                return selectedLogbookEntries.getQuantity();
            case 4:
                return selectedLogbookEntries.getUserName();
            case 5:
                return selectedLogbookEntries.getDate()+" "+selectedLogbookEntries.getTime();
            default:
                return null;
        }
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}
