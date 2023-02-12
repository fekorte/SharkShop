package client.ui.gui;

import Common.IFrankie;
import Common.Item;
import Common.Person;
import Server.domain.EShopManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
public class CartContentFrame extends JFrame implements CartContentMenuBar.MenuListener,  CartContentMenuBar.LogoutListener{


    //private EShopManager manager;
    private IFrankie manager;
    private Person user;
    private static JTable cartTable, receiptTable;
    private static CartTable cartModel;

    private static ReceiptTable receiptModel;


    @Override
    public void seeList(List<Item> toPrintTable){
        cartModel.setCartItems(toPrintTable);
    }

    public void logout(){
        dispose();
       // ClientMenu=new ClientMenu=(manager,this.user);
    }

    public CartContentFrame(IFrankie manager, Person user){
        super("Cart content");//here super shows the name of the window
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);
        this.setSize(new Dimension(800,500));
        this.setLayout(new BorderLayout());
        this.manager=manager;
        this.user=user;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar=new CartContentMenuBar(this.manager, this.user, this,this);// mb means menu bar
        setJMenuBar(menuBar);

        Map<Item, Integer> itemsInCartMap=this.manager.getItemsInCart(user.getName()); //This is a new line

        List<Item> toPrintTable=new ArrayList<Item>();//this is the list where we store the items in the cart

        for(Map.Entry<Item, Integer> entry: itemsInCartMap.entrySet()){
            Item key=entry.getKey();
            toPrintTable.add(key);
             }
        //TODO hide the table in case the cart is enpty
        // toPrintTable.isEmpty()? (empty.setText("Stock increased.")): showTable(toPrintTable);
        showTable(toPrintTable);


        JPanel totalPanel=new JPanel();
        totalPanel.setLayout(new GridLayout(2,1));
        totalPanel.setSize(100,400);
        totalPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel totalPriceLabel=new JLabel("Total price");
        totalPriceLabel.setFont(new Font("arial", Font.BOLD, 20));

        totalPanel.add(totalPriceLabel);


        String price=String.valueOf(manager.endPrice(user.getName()));
        JLabel totalPriceNumber=new JLabel("€ "+price); //method to calculate the total price
        totalPriceNumber.setFont(new Font("arial", Font.BOLD, 20));
        totalPriceNumber.setHorizontalAlignment(JLabel.CENTER);
        totalPanel.add(totalPriceNumber);

        this.add(totalPanel,BorderLayout.EAST);
        setVisible(true);
    }
    private void showTable(List<Item> toPrintTable){
        cartModel=new CartTable (toPrintTable);
        cartTable=new JTable(cartModel){
            public Class getColumnClass(int column){return (column == 0)?Icon.class:Object.class;}
        };
        cartTable.setRowHeight(100);
        cartTable.setFont(new Font("Serif", Font.BOLD, 10));
        cartTable.getTableHeader().setReorderingAllowed(false);
        cartTable.setSize(500,400);

        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        cartTable.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        cartTable.getColumnModel().getColumn(2).setCellRenderer(Alinear);
        cartTable.getColumnModel().getColumn(3).setCellRenderer(Alinear);

        JScrollPane scrollPane=new JScrollPane(cartTable);
        add(scrollPane, BorderLayout.CENTER); //adds the table to the center of the layout
    }
}
*/