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

public class ClientMenu extends JFrame implements SearchingBarPanel.SearchListener,
        ClientMenuBar.MenuListener, ClientMenuBar.LogoutListener{
    private final IFrankie manager;
    private Person user;
    private static ItemTable itemModel;

    public void onSearched(java.util.List<Item> result){//used for the class SearchBarPanel to display the searched item
        result.sort(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
        itemModel.setItems(result);
    }
    public void logout(){
        this.user=null;
        dispose();
        LoginFrame login=new LoginFrame(manager);
    }
    @Override
    public void seeList(List<Item> list){
        itemModel.setItems(list);
    }
    public ClientMenu(IFrankie manager, Person user){
        super("Frankie: The E-Shop");
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);

        this.manager=manager;
        this.user=user;

        this.setSize(new Dimension(1435, 800));
        this.setLayout(new BorderLayout());

        JMenuBar mb=new ClientMenuBar(manager, this.user, this, this);
        setJMenuBar(mb);

        searchingBar();

        List<Item> itemList=new ArrayList<Item>(manager.getItemsInStock().values());
        showItemList(itemList);

        addWindowListener(new WindowCloser());
        setVisible(true);
    }
    private void searchingBar(){
        JPanel northPanel=new SearchingBarPanel(manager, this);
        add(northPanel, BorderLayout.NORTH);
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

        DefaultTableCellRenderer alinear = new DefaultTableCellRenderer();
        alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        itemTable.getColumnModel().getColumn(0).setCellRenderer(alinear);
        itemTable.getColumnModel().getColumn(1).setCellRenderer(alinear);
        itemTable.getColumnModel().getColumn(3).setCellRenderer(alinear);
        itemTable.getColumnModel().getColumn(4).setCellRenderer(alinear);
        itemTable.getColumnModel().getColumn(5).setCellRenderer(alinear);

        JScrollPane scrollPane=new JScrollPane(itemTable);
        scrollPane.setSize(1902,1080);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }
}
