package client.ui.gui;

import Common.IFrankie;
import Common.Person;
import Common.exceptions.ExceptionItemAlreadyExists;
import Common.exceptions.ExceptionItemDoesntExist;
import Common.exceptions.ExceptionUserAlreadyExists;
import Common.Item;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class EmployeeMenuBar extends JMenuBar {

    public interface MenuListener{void seeList(java.util.List<Item> toSee);}
    public interface LogoutListener{void logout();}

    public interface LogBookListener{void addToLayout(JScrollPane scrollPane);}

    private final IFrankie manager;
    private final Person user;
    private final MenuListener listener;
    private final LogoutListener listener2;
    private JFileChooser fc;

    public EmployeeMenuBar(IFrankie manager, Person user, MenuListener listener, LogoutListener listener2){
        this.manager = manager;
        this.user = user;
        this.listener = listener;
        this.listener2 = listener2;

        setLayout(new GridLayout(1, 8));

        Icon sortAtoZ = new ImageIcon("images/icons/SotAtoZ.png");
        JButton menu = new JButton(sortAtoZ);
        menu.setText("Show items (a-z)");
        menu.addActionListener(new EmployeeMenuListener());
        add(menu);

        Icon sortCode= new ImageIcon("images/icons/SortCode.png");
        JButton menu2 = new JButton(sortCode);
        menu2.setText("Show items (item code)");
        menu2.addActionListener(new EmployeeMenuListener());
        add(menu2);

        Icon addItemB= new ImageIcon("images/icons/addItem.png");
        JButton menu3 = new JButton(addItemB);
        menu3.setText("Add new item");
        menu3.addActionListener(new EmployeeMenuListener());
        add(menu3);

        Icon Increase = new ImageIcon("images/icons/add.png");
        JButton menu4 = new JButton(Increase);
        menu4.setText("Increase stock");
        menu4.addActionListener(new EmployeeMenuListener());
        add(menu4);

        Icon decrease = new ImageIcon("images/icons/minus.png");
        JButton menu5 = new JButton(decrease);
        menu5.setText("Decrease stock");
        menu5.addActionListener(new EmployeeMenuListener());
        add(menu5);

        Icon registerEmploy = new ImageIcon("images/icons/addUser.png");
        JButton menu6 = new JButton(registerEmploy);
        menu6.setText("Register new employee");
        menu6.addActionListener(new EmployeeMenuListener());
        add(menu6);

        Icon showStock = new ImageIcon("images/icons/stock.png");
        JButton menu7 = new JButton(showStock);
        menu7.setText("Show stock logbook");
        menu7.addActionListener(new EmployeeMenuListener());
        add(menu7);

        Icon logout = new ImageIcon("images/icons/Logout.png");
        JButton menu8=new JButton(logout);
        menu8.setText("Logout");
        menu8.addActionListener(new EmployeeMenuListener());
        add(menu8);

        setVisible(true);
    }

    class EmployeeMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "Show items (a-z)" -> {
                    Map<Integer, Item> itemsI = manager.getItemsInStock();
                    List<Item> list = new ArrayList<Item>(itemsI.values());
                    list.sort(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
                    listener.seeList(list);
                }
                case "Show items (item code)" -> {
                    Map<Integer, Item> items = manager.getItemsInStock();
                    List<Item> list2 = new ArrayList<>(items.values());
                    list2.sort(Comparator.comparingInt(Item::getItemCode));
                    listener.seeList(list2);
                }
                case "Add new item" -> {
                    JFrame frame = new JFrame();
                    fc = new JFileChooser();
                    Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    frame.setIconImage(icon);
                    frame.setTitle("Add new item");
                    frame.setSize(600, 400);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel addItemPanel = new JPanel();
                    addItemPanel.setLayout(new GridLayout(0,2));

                    JLabel itemName = new JLabel("  Item name:");
                    addItemPanel.add(itemName);

                    JTextField itemText = new JTextField(10);
                    addItemPanel.add(itemText);
                    itemText.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!(c < '0' || c > '9')) {
                                e.consume();
                            }
                        }
                    });

                    JLabel priceLabel = new JLabel("  Price:");
                    addItemPanel.add(priceLabel);

                    JTextField priceText = new JTextField(10);
                    addItemPanel.add(priceText);

                    JLabel numberLabel = new JLabel("  Number of items:");
                    addItemPanel.add(numberLabel);

                    JTextField numberText = new JTextField(10);
                    addItemPanel.add(numberText);

                    JLabel packingLabel = new JLabel("  Packing size");
                    addItemPanel.add(packingLabel);

                    JTextField packingText = new JTextField(10);
                    addItemPanel.add(packingText);

                    JTextField fileRouteText = new JTextField(10);
                    fileRouteText.setEnabled(false);


                    JButton openButton = new JButton("Open a File...", new ImageIcon("images/Open16.gif"));
                    addItemPanel.add(openButton);
                    openButton.addActionListener(a -> {
                        int returnVal = fc.showOpenDialog(EmployeeMenuBar.this);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                File file = fc.getSelectedFile();
                                fileRouteText.setText("images/"+file.getName());

                            } else {
                                fileRouteText.setText("Open command cancelled by user.");
                            }
                    });

                    addItemPanel.add(fileRouteText);

                    JLabel success2 = new JLabel("");
                    addItemPanel.add(success2);
                    JLabel success23 = new JLabel("");
                    addItemPanel.add(success23);

                    JButton enter = new JButton("Enter");

                    addItemPanel.add(enter);

                    JButton back = new JButton("Back");
                    addItemPanel.add(back);

                    JLabel success = new JLabel("");
                    addItemPanel.add(success);
                    frame.add(addItemPanel);
                    frame.setVisible(true);


                    int answer = JOptionPane.showConfirmDialog(frame,"Is this a wholesale item?\n" + ".\n"
                                    + "if it is the case please introduce the size of the pack","HOLA",
                                    JOptionPane.YES_NO_OPTION);

                    if (answer == JOptionPane.YES_OPTION) {
                        packingLabel.setVisible(true);
                        packingText.setVisible(true);
                        frame.setVisible(true);
                    } else if (answer == JOptionPane.NO_OPTION) {
                        packingLabel.setVisible(false);
                        packingText.setText("1");
                        packingText.setVisible(false);
                        frame.setVisible(true);
                    }
                    enter.addActionListener(a -> {
                        String nameItem = itemText.getText();
                        float price = Float.parseFloat(priceText.getText());
                        int numberInStock = Integer.parseInt(numberText.getText());
                        String picName = fileRouteText.getText();

                        int packingSize = Integer.parseInt(packingText.getText());
                        System.out.println(packingSize);

                        try {
                            manager.createNewItem(nameItem, price, numberInStock, packingSize, user.getName(), picName);
                            success.setText("Item added to stock");
                            frame.dispose();

                            List<Item> itemsX = new ArrayList<>(manager.getItemsInStock().values()); //to update the stock list on screen
                            listener.seeList(itemsX);

                        } catch (ExceptionItemAlreadyExists | IOException c) {
                            success.setText("Failed to add to stock. Item already exists.");
                            c.printStackTrace();
                        }
                    });
                    back.addActionListener(i -> frame.dispose());
                }
                case "Increase stock" -> {
                    JFrame increaseFrame = new JFrame();
                    Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    increaseFrame.setIconImage(icon);
                    increaseFrame.setTitle("Increase stock");
                    increaseFrame.setSize(300, 200);
                    increaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel increasePanel = new JPanel();
                    increasePanel.setLayout(new GridLayout(0,2));

                    JLabel space1 = new JLabel("");
                    increasePanel.add(space1);
                    JLabel space12 = new JLabel("");
                    increasePanel.add(space12);

                    JLabel itemCodeLabel = new JLabel("Item code: ");
                    increasePanel.add(itemCodeLabel);

                    JTextField itemCodeText = new JTextField(10);
                    increasePanel.add(itemCodeText);
                    itemCodeText.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                                e.consume();  // ignore event
                            }
                        }
                    });

                    JLabel quantityLabel = new JLabel("Quantity to add: ");
                    increasePanel.add(quantityLabel);

                    JTextField quantityText = new JTextField(10);
                    increasePanel.add(quantityText);

                    JLabel success2 = new JLabel("");
                    increasePanel.add(success2);
                    JLabel success23 = new JLabel("");
                    increasePanel.add(success23);

                    JButton enter2 = new JButton("Enter");
                    increasePanel.add(enter2);

                    JButton back2 = new JButton("Back");
                    increasePanel.add(back2);

                    increaseFrame.add(increasePanel);
                    increaseFrame.setVisible(true);

                    enter2.addActionListener(a -> {
                        int itemCode = Integer.parseInt(itemCodeText.getText());
                        int quantity = Integer.parseInt(quantityText.getText());
                        try {
                            manager.increaseStock(itemCode, quantity, user.getName());
                            success2.setText("Stock increased.");//todo this is not working
                            increaseFrame.dispose();
                            List<Item> itemsG = new ArrayList<>(manager.getItemsInStock().values());
                            listener.seeList(itemsG);
                        } catch (ExceptionItemDoesntExist | IOException b) {
                            success2.setText("Item doesn't exist.");
                             b.printStackTrace();
                        }
                    });
                    back2.addActionListener(i -> increaseFrame.dispose());
                }
                case "Decrease stock" -> {
                    JFrame decreaseFrame = new JFrame();
                    Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    decreaseFrame.setIconImage(icon);
                    decreaseFrame.setTitle("Decrease stock");
                    decreaseFrame.setSize(300, 200);
                    decreaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel decreasePanel = new JPanel();
                    decreasePanel.setLayout(new GridLayout(0,2));

                    JLabel space3 = new JLabel("");
                    decreasePanel.add(space3);
                    JLabel space32 = new JLabel("");
                    decreasePanel.add(space32);

                    JLabel itemCodeLabel2 = new JLabel("Item code: ");
                    decreasePanel.add(itemCodeLabel2);
                    JTextField itemCodeText2 = new JTextField(10);
                    decreasePanel.add(itemCodeText2);
                    itemCodeText2.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                                e.consume();  // ignore event
                            }
                        }
                    });

                    JLabel quantityLabel2 = new JLabel("Quantity to remove: ");
                    decreasePanel.add(quantityLabel2);
                    JTextField quantityText2 = new JTextField(10);
                    decreasePanel.add(quantityText2);
                    quantityText2.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                                e.consume();  // ignore event
                            }
                        }
                    });

                    JLabel success3 = new JLabel("");
                    decreasePanel.add(success3);
                    JLabel success32 = new JLabel("");
                    decreasePanel.add(success32);

                    JButton enter3 = new JButton("Enter");
                    decreasePanel.add(enter3);

                    JButton back3 = new JButton("Back");
                    decreasePanel.add(back3);

                    decreaseFrame.add(decreasePanel);
                    decreaseFrame.setVisible(true);

                    enter3.addActionListener(a -> {
                        int itemCode = Integer.parseInt(itemCodeText2.getText());
                        int quantity = Integer.parseInt(quantityText2.getText());
                        try {
                            String userName=user.getName();
                            manager.decreaseStock(itemCode, quantity, userName );
                            success3.setText("Stock increased.");
                            decreaseFrame.dispose();
                            List<Item> itemsZ = new ArrayList<>(manager.getItemsInStock().values());
                            listener.seeList(itemsZ);
                        } catch (ExceptionItemDoesntExist | IOException b) {
                            success3.setText("Item doesn't exist.");
                            b.printStackTrace();
                        }
                    });
                    back3.addActionListener(i -> decreaseFrame.dispose());
                }
                case "Register new employee" -> {
                    JFrame frame3 = new JFrame();
                    Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    frame3.setIconImage(icon);
                    JPanel registerPanel = new JPanel(new FlowLayout());

                    frame3.setTitle("Registration");
                    frame3.setSize(300, 200);
                    frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JLabel userLabel = new JLabel("Username:");
                    registerPanel.add(userLabel);

                    JTextField userText = new JTextField(10);
                    registerPanel.add(userText);

                    JLabel passwordLabel = new JLabel("Password:");
                    registerPanel.add(passwordLabel);

                    JTextField passwordText = new JPasswordField(10);
                    registerPanel.add(passwordText);

                    JButton register = new JButton("Enter");
                    registerPanel.add(register);

                    JLabel success = new JLabel("");
                    registerPanel.add(success);

                    JButton back4 = new JButton("Back");
                    registerPanel.add(back4);

                    frame3.add(registerPanel);
                    frame3.setVisible(true);

                    register.addActionListener(s -> {
                        String userName = userText.getText();
                        String password = passwordText.getText();
                        try {
                            manager.registerEmployee(userName, password);
                            frame3.dispose();
                        } catch (ExceptionUserAlreadyExists | IOException g) {
                            success.setText("Registration failed. Please select a different user name");
                            g.printStackTrace();
                        }
                    });
                    back4.addActionListener(i -> frame3.dispose());
                }
                case "Show stock logbook" -> {
                    JFrame frameLogbook = new JFrame();
                    Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    frameLogbook.setIconImage(icon);
                    frameLogbook.setTitle("Stock Logbook");
                    frameLogbook.setSize(600, 300);
                    frameLogbook.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    LogbookTable modelTable = new LogbookTable(manager.getEntries());
                    JTable logbookTable = new JTable(modelTable);
                    logbookTable.getTableHeader().setReorderingAllowed(false);

                    JScrollPane scrollPaneTable = new JScrollPane(logbookTable);

                    JPanel logbookPanel = new JPanel(new BorderLayout());//distribute components
                    logbookPanel.add(scrollPaneTable, BorderLayout.CENTER);

                    frameLogbook.add(logbookPanel);
                    frameLogbook.setVisible(true);
                }
                case "Logout" -> {
                    manager.logout(user.getName());
                    listener2.logout();
                }
                default -> throw new IllegalArgumentException("Unknown menu item!");
            }

        }
    }
}
