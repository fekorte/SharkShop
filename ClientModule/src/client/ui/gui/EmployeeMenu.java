package client.ui.gui;

import Common.IFrankie;
import Common.Item;
import Common.Person;
import Server.domain.EShopManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeMenu  extends JFrame implements SearchingBarPanel.SearchListener, ClientMenuBar.MenuListener, EmployeeMenuBar.MenuListener, ClientMenuBar.LogoutListener, EmployeeMenuBar.LogoutListener, EmployeeMenuBar.LogBookListener {
    //private EShopManager manager;
    private final IFrankie manager;
    private Person user;
    private static ItemTable itemModel;

    public void onSearched(java.util.List<Item> result){//used for the class SearchBarPanel to display the searched item
        result.sort(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
        itemModel.setItems(result);

    }

    public void logout(){
        this.user = null;
        dispose();
        LoginFrame login = new LoginFrame(manager);
    }
    @Override

    public void seeList(List<Item> list){
        itemModel.setItems(list);
    }

    public void addToLayout(JScrollPane pane){
        add(pane, BorderLayout.EAST);
    }

    public EmployeeMenu(IFrankie manager, Person user) {
        super("Frankie: The E-Shop");
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);
        this.manager = manager;
        this.user = user;

        this.setLayout(new BorderLayout());
        this.setSize(800,900);
        JMenuBar mb=new EmployeeMenuBar(manager, this.user,this, this);

        pack();
        setJMenuBar(mb);
        searchingBar();

        List<Item> itemList=new ArrayList<>(manager.getItemsInStock().values());
        showItemList(itemList);

        addWindowListener(new WindowCloser());
        setVisible(true);
    }

    private void searchingBar(){
        JPanel northPanel=new SearchingBarPanel(manager, this);
        pack();
        add(northPanel, BorderLayout.NORTH); //finally add it to the main layout
    }

    private void showItemList(java.util.List<Item> itemList){
        itemModel=new ItemTable(itemList);
        JTable itemTable = new JTable(itemModel) {
            public Class getColumnClass(int column) {
                return (column == 2) ? Icon.class : Object.class;
            }
        };
        itemTable.setRowHeight(120);
        itemTable.setFont(new Font("Serif", Font.BOLD, 15));
        itemTable.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();

        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        itemTable.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        itemTable.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        itemTable.getColumnModel().getColumn(3).setCellRenderer(Alinear);
        itemTable.getColumnModel().getColumn(4).setCellRenderer(Alinear);
        itemTable.getColumnModel().getColumn(5).setCellRenderer(Alinear);

        JScrollPane scrollPane=new JScrollPane(itemTable);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
        pack();
    }
}
