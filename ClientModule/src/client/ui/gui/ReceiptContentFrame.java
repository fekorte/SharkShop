package client.ui.gui;
/*
import Common.IFrankie;
import Common.Item;
import Common.Logbook;
import Common.Person;
import Server.domain.EShopManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.List;

public class ReceiptContentFrame extends JFrame implements CartContentMenuBar.MenuListener,  CartContentMenuBar.LogoutListener{
    //private EShopManager manager;
    private IFrankie manager;
    private Person user;
    private static JTable  receiptTable;
    private static ReceiptTable receiptModel;
    private LocalDate date;


    public void seeList(List<Item> receiptReport){
        receiptModel.setCartItems(receiptReport);
    }
    public void logout(){
        dispose();
           }

    public ReceiptContentFrame(IFrankie manager, Person user){
        super("Receipt");//here super shows the name of the window
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);

        this.manager=manager;
        this.user=user;
        this.setLayout(new BorderLayout()); //creates the layout
        this.setSize(new Dimension(400, 800));
        //mirar si si funciona
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Map<Item, Integer> itemsInCartMap=this.manager.getItemsInCart(user.getName()); //This is a new line
        List<Item> receiptReport=new ArrayList<Item>();//this is the list where we store the items in the cart
        for(Map.Entry<Item, Integer> entry: itemsInCartMap.entrySet()){
            Item key=entry.getKey();
            receiptReport.add(key);
        }
        //Panel info user
        showReceiptInfo();
        //Panel info table
        showReceiptTable(receiptReport);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        pack();

        manager.emptyCart(user.getName());
    }
    private void showReceiptTable(List<Item> receiptReport){
        receiptModel=new ReceiptTable (receiptReport);
        receiptTable=new JTable(receiptModel);
        receiptTable.setFont(new Font("Serif", Font.BOLD, 15));
        receiptTable.setRowHeight(25);
        receiptTable.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);//.LEFT .RIGHT .CENTER
        receiptTable.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        receiptTable.getColumnModel().getColumn(2).setCellRenderer(Alinear);
        JScrollPane scrollPane=new JScrollPane(receiptTable);
        add(scrollPane, BorderLayout.CENTER); //adds the table to the center of the layout
        setVisible(true);
    }

    private void showReceiptInfo (){
        JPanel receiptPanel=new JPanel();
        receiptPanel.setBorder(new EmptyBorder(10, 10, 10, 40));
        receiptPanel.setLayout(new GridLayout(4,2));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        // LocalDate date=LocalDate.now();
        //LocalTime time= LocalTime.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMAN);
        // String hour= formatter.format(time);
        //date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));

        Font font = new Font("arial", Font.BOLD, 20);
        Font font2 = new Font("arial", Font.PLAIN, 15);

        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setFont(font2);
        receiptPanel.add(dateLabel);

        JLabel date = new JLabel(dateFormat.format(cal.getTime()));
        date.setFont(font2);
        receiptPanel.add(date);

        JLabel userNameLabel = new JLabel("Name: ");
        userNameLabel.setFont(font2);
        receiptPanel.add(userNameLabel);

        JLabel userName = new JLabel(user.getName());
        userName.setFont(font2);
        receiptPanel.add(userName);

        JLabel userAddressLabel = new JLabel("Address: ");
        userAddressLabel.setFont(font2);
        receiptPanel.add(userAddressLabel);

        JLabel userAddress = new JLabel(user.getAddress());
        userAddress.setFont(font2);
        receiptPanel.add(userAddress);

        JLabel totalPriceLabel=new JLabel("Total price: ");
        totalPriceLabel.setFont(font);
        receiptPanel.add(totalPriceLabel);

        String price=String.valueOf(this.manager.endPrice(user.getName()));
        JLabel totalPriceNumber=new JLabel(price); //method to calculate the total price
        totalPriceNumber.setFont(font);
        receiptPanel.add(totalPriceNumber);

        this.add(receiptPanel, BorderLayout.PAGE_START);
    }
}
*/