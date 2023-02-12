package client.ui.gui;

import Common.IFrankie;
import Common.exceptions.ExceptionInsufficientStock;
import Common.exceptions.ExceptionItemDoesntExist;
import Common.exceptions.ExceptionItemMismatchingPackingSize;
import Common.Item;
import Common.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ClientMenuBar extends JMenuBar{

    public interface MenuListener{void seeList(java.util.List<Item> toSee);}
    public interface LogoutListener{void logout();}
    private IFrankie manager;
    private Person user;
    private MenuListener listener;
    private LogoutListener listener2;

    public ClientMenuBar(IFrankie manager, Person user, MenuListener listener, LogoutListener listener2){
        this.manager=manager;
        this.user = user;
        this.listener=listener;
        this.listener2=listener2;

        setLayout(new GridLayout(1, 8));

        Icon sortAtoZ = new ImageIcon("images/icons/SotAtoZ.png");
        JButton menu = new JButton(sortAtoZ);
        menu.setText("Show items (a-z)");
        menu.addActionListener(new ClientMenuListener());
        add(menu);

        Icon sortCode = new ImageIcon("images/icons/SortCode.png");
        JButton menu2=new JButton(sortCode); //same procedure
        menu2.setText("Show items (item code)");
        menu2.addActionListener(new ClientMenuListener());
        add(menu2);

        Icon cartIcon = new ImageIcon("images/icons/Cart.png");
        JButton menu3=new JButton(cartIcon);
        menu3.setText("Show cart content");
        menu3.addActionListener(new ClientMenuListener());
        add(menu3);

        Icon selectItem = new ImageIcon("images/icons/SelectItem.png");
        JButton menu4=new JButton(selectItem);
        menu4.setText("Add items");
        menu4.addActionListener(new ClientMenuListener());
        add(menu4);

        JButton menu5=new JButton("Remove items");
        menu5.addActionListener(new ClientMenuListener());
        add(menu5);

        JButton menu6=new JButton("Empty cart");
        menu6.addActionListener(new ClientMenuListener());
        add(menu6);

        JButton menu7=new JButton("Buy");
        menu7.addActionListener(new ClientMenuListener());
        add(menu7);

        Icon logout = new ImageIcon("images/icons/Logout.png");
        JButton menu8=new JButton(logout);
        menu8.setText("Logout");
        menu8.addActionListener(new ClientMenuListener());
        add(menu8);

        setVisible(true);
    }

    class ClientMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String command=e.getActionCommand();

            switch(command){
                case "Show items (a-z)" -> {
                    Map<Integer, Item> itemsI=manager.getItemsInStock();
                    List<Item> list=new ArrayList<>(itemsI.values());
                    Collections.sort(list, Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
                    listener.seeList(list); //give list to showItemList() method
                }
                case "Show items (item code)" -> {
                    Map<Integer, Item> items=manager.getItemsInStock();
                    List<Item> list2=new ArrayList<>(items.values());
                    Collections.sort(list2, Comparator.comparingInt(Item::getItemCode));
                    listener.seeList(list2);
                }
                case "Show cart content" -> {
                    JFrame toCartFrame=new JFrame();
                    toCartFrame.setTitle("Cart content");
                    toCartFrame.setSize(300, 200);
                    toCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JPanel cartPanel=new JPanel(new FlowLayout());
                    String cartContent=manager.getItemInfo(user.getName()); //write cart content in a string to be displayed in JTextArea
                    JTextArea area=new JTextArea(cartContent, 20, 20);
                    cartPanel.add(area);
                    JScrollPane scrollPane2=new JScrollPane(area);
                    toCartFrame.add(cartPanel);
                    toCartFrame.add(scrollPane2);
                    toCartFrame.setVisible(true);
                }
                case "Add items" -> {
                    JFrame toCartFrame2=new JFrame();
                    Image icon=Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    toCartFrame2.setIconImage(icon);
                    ImageIcon sharkIcon=new ImageIcon(new ImageIcon("images/shark.png").getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

                    toCartFrame2.setTitle("Select item");
                    toCartFrame2.setSize(300, 200);
                    toCartFrame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel toCartPanel=new JPanel();//based on: https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridLayoutDemoProject/src/layout/GridLayoutDemo.java
                    toCartPanel.setLayout(new GridLayout(0, 2));

                    JLabel space1=new JLabel("");
                    toCartPanel.add(space1);
                    JLabel space2=new JLabel("");
                    toCartPanel.add(space2);

                    JLabel itemCodeLabel=new JLabel("   Item code: ");
                    toCartPanel.add(itemCodeLabel);

                    JTextField itemCodeText=new JTextField(10);
                    toCartPanel.add(itemCodeText);

                    JLabel quantityLabel=new JLabel("   Quantity: ");
                    toCartPanel.add(quantityLabel);

                    JTextField quantityText=new JTextField(10);
                    toCartPanel.add(quantityText);

                    JLabel success2=new JLabel("");
                    toCartPanel.add(success2);
                    JLabel success23=new JLabel("");
                    toCartPanel.add(success23);

                    JButton enter2=new JButton("Enter");
                    toCartPanel.add(enter2);

                    JButton back2=new JButton("Back");
                    toCartPanel.add(back2);

                    toCartFrame2.add(toCartPanel);
                    toCartFrame2.setVisible(true);

                    enter2.addActionListener(a -> {
                        int itemCode=Integer.parseInt(itemCodeText.getText());
                        int quantity=Integer.parseInt(quantityText.getText());

                        try{
                            manager.selectItem(itemCode, quantity, user.getName());
                            success2.setText("Added to cart");
                            toCartFrame2.dispose();
                            JOptionPane.showMessageDialog(toCartFrame2,
                                    "Item added to the cart.",
                                    "Operation success",
                                    JOptionPane.INFORMATION_MESSAGE,
                                    sharkIcon);

                        } catch(ExceptionItemDoesntExist s){
                            success2.setText("Item doesn't exist.");
                            JOptionPane.showMessageDialog(toCartFrame2,
                                    "Item doesn't exist.",
                                    "Operation not possible",
                                    JOptionPane.WARNING_MESSAGE);
                        } catch(ExceptionItemMismatchingPackingSize l){
                            success2.setText("the item is only available by packs");
                            l.printStackTrace();
                            JOptionPane.showMessageDialog(toCartFrame2,
                                    l.getMessage(),
                                    "Operation not possible",
                                    JOptionPane.WARNING_MESSAGE);
                        } catch(ExceptionInsufficientStock ex){
                            success2.setText(ex.getMessage());

                            JOptionPane.showMessageDialog(toCartFrame2,
                                    ex.getMessage(),
                                    "Operation not possible",
                                    JOptionPane.WARNING_MESSAGE);

                        }
                    });
                    back2.addActionListener(i -> {
                        toCartFrame2.dispose();
                    });
                }
                case "Remove items" -> {
                    JFrame removeFrame=new JFrame();
                    JPanel removePanel=new JPanel(new FlowLayout());
                    removeFrame.setTitle("Remove items");
                    removeFrame.setSize(300, 200);
                    removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    JLabel itemCodeLabel2=new JLabel("Item code: ");
                    removePanel.add(itemCodeLabel2);
                    JTextField itemCodeText2=new JTextField(10);
                    removePanel.add(itemCodeText2);
                    JLabel quantityLabel2=new JLabel("Quantity: ");
                    removePanel.add(quantityLabel2);
                    JTextField quantityText2=new JTextField(10);
                    removePanel.add(quantityText2);
                    JButton enter=new JButton("Enter");
                    removePanel.add(enter);
                    JLabel success=new JLabel("");
                    removePanel.add(success);
                    JButton back=new JButton("Back");
                    removePanel.add(back);
                    removeFrame.add(removePanel);
                    removeFrame.setVisible(true);

                    enter.addActionListener(a -> {
                        int itemCode=Integer.parseInt(itemCodeText2.getText());
                        int quantity=Integer.parseInt(quantityText2.getText());
                        try{
                            manager.removeItems(itemCode, quantity, user.getName());
                            success.setText("Removed from cart.");
                            removeFrame.dispose();
                        } catch(ExceptionItemDoesntExist l){
                            success.setText("Item doesn't exist.");
                            l.printStackTrace();
                        } catch(ExceptionItemMismatchingPackingSize l){
                            success.setText(l.getMessage());
                            l.printStackTrace();
                        } catch (ExceptionInsufficientStock ex) {
                            success.setText(ex.getMessage());
                        }
                    });
                    back.addActionListener(i -> removeFrame.dispose());
                }
                case "Empty cart" -> manager.emptyCart(user.getName());
                case "Buy" -> {
                    String receipt;
                    try{
                        receipt=manager.buyItems(user.getName());

                        JFrame receiptFrame=new JFrame();
                        receiptFrame.setTitle("Receipt");
                        receiptFrame.setSize(300, 200);
                        receiptFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        String content=String.valueOf(receipt);
                        JTextArea area2=new JTextArea(content, 30, 50);
                        JScrollPane scrollPane3=new JScrollPane(area2);
                        receiptFrame.add(scrollPane3);
                        receiptFrame.setVisible(true);

                        manager.emptyCart(user.getName());

                    } catch(IOException ex){
                        throw new RuntimeException(ex);
                    }
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


