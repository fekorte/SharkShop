package client.ui.gui;

import Common.Logbook;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LogbookTable extends AbstractTableModel{
    private final List<Logbook> logbookList;
    private final String[] columnNames={"Entry", "Item code", "Item name", "Quantity", "User", "Date and time"};
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
        return switch (columnIndex) {
            case 0 -> selectedLogbookEntries.getEntry();
            case 1 -> selectedLogbookEntries.getItem().getItemCode();
            case 2 -> selectedLogbookEntries.getItem().getItemName();
            case 3 -> selectedLogbookEntries.getQuantity();
            case 4 -> selectedLogbookEntries.getUserName();
            case 5 -> selectedLogbookEntries.getDate() + " " + selectedLogbookEntries.getTime();
            default -> null;
        };
    }
    @Override
    public String getColumnName(int column){
        return columnNames[column];
    }
}
