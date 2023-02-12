package client.ui.gui;

import Common.IFrankie;
import Common.Item;
import Common.Person;

import Common.exceptions.ExceptionInsufficientStock;
import Common.exceptions.ExceptionItemDoesntExist;
import Common.exceptions.ExceptionItemMismatchingPackingSize;
import Server.domain.EShopManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
/*
public class CartContentMenuBar extends JMenuBar{


    public interface MenuListener{
        void seeList(List<Item> toPrintTable);
       //void logout();
    }

    public interface LogoutListener{
        //void seeList(List<Item> toPrintTable);
        void logout();
    }


    private IFrankie manager;
    //private EShopManager manager;
    private Person user;
    private MenuListener listener;
    private LogoutListener listener2;
    private static JTable  receiptTable;
    private static ReceiptTable receiptModel;


    public CartContentMenuBar(IFrankie manager, Person user, MenuListener listener,LogoutListener listener2 ){
        this.manager=manager;
        this.user=user;
        this.listener=listener;
        this.listener2=listener2;

        setLayout(new GridLayout(1, 3));

        Icon removeItem=new ImageIcon("images/icons/RemoveItem.png");
        JButton menu1=new JButton(removeItem);
        menu1.setText("Remove items");
        menu1.addActionListener(new CartContentListener());
        add(menu1);

        Icon emptyCart=new ImageIcon("images/icons/EmptyCart.png");
        //JButton nextButton = new JButton("Next");
      //  nextButton.addActionListener(new ActionListener()JButton nextButton = new JButton("Next");
       // nextButton.addActionListener(new ActionListener();

        JButton menu2=new JButton(emptyCart);
        menu2.setText("Empty cart");
        menu2.addActionListener(new CartContentListener());
        //TODO cerrar ventana del carrito al vaciar el carrito
        //this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
         add(menu2);

        Icon buyItem=new ImageIcon("images/icons/buy.png");
        JButton menu3=new JButton(buyItem);
        menu3.setText("Buy");
        menu3.addActionListener(new CartContentListener());
        add(menu3);

        this.setSize(new Dimension(1435, 800));


        setVisible(true);
        //pack(); TODO: Why is this commented here??

    }

    class CartContentListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String command=e.getActionCommand();

            switch(command){ //Depending on the clicked button
                case "Remove items" -> {
                    JFrame removeFrame=new JFrame();
                    Image icon=Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    removeFrame.setIconImage(icon);
                    removeFrame.setTitle("Remove items");
                    removeFrame.setSize(400, 200);
                    removeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    JPanel removePanel=new JPanel();//based on: https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridLayoutDemoProject/src/layout/GridLayoutDemo.java
                    removePanel.setLayout(new GridLayout(0, 2));

                    JLabel space3=new JLabel("");
                    removePanel.add(space3);
                    JLabel space4=new JLabel("");
                    removePanel.add(space4);

                    JLabel itemCodeLabel2=new JLabel("Item code: ");
                    removePanel.add(itemCodeLabel2);

                    JTextField itemCodeText2=new JTextField(10);
                    removePanel.add(itemCodeText2);

                    JLabel quantityLabel2=new JLabel("Quantity: ");
                    removePanel.add(quantityLabel2);

                    JTextField quantityText2=new JTextField(10);
                    removePanel.add(quantityText2);

                    JLabel success=new JLabel("");
                    removePanel.add(success);
                    JLabel success1=new JLabel("");
                    removePanel.add(success1);

                    JButton enter=new JButton("Enter");
                    removePanel.add(enter);


                    JButton back=new JButton("Back");
                    removePanel.add(back);

                    //removePanel.getRootPane().setDefaultButton(enter);
                    removeFrame.add(removePanel);
                    removeFrame.setVisible(true);

                    enter.addActionListener(a -> {
                        int itemCode=Integer.parseInt(itemCodeText2.getText());
                        int quantity=Integer.parseInt(quantityText2.getText());
                        //int packingSize= manager.getItems().get(itemCode).getPackingSize(); --> from the EShopManager
                        //but it isn't working

                        try{
                            manager.removeItems(itemCode, quantity, user.getName());
                            success.setText("Removed from cart.");
                            removeFrame.dispose();
                        } catch(ExceptionItemDoesntExist l){
                            success.setText("Item doesn't exist."); //TODO Natha to get consisten error messages we can call the error like s in cui
                            l.printStackTrace();//why this is the only message that goes printing????
                        } catch(ExceptionItemMismatchingPackingSize l){
                            success.setText("the item is only available  by packs");
                            l.printStackTrace();
                        } catch(ExceptionInsufficientStock ex){
                            success.setText(ex.getMessage());
                        }
                    });
                    back.addActionListener(i -> removeFrame.dispose());
                }
                case "Empty cart" -> {
                    manager.emptyCart(user.getName());
                   JFrame toCartFrame=new JFrame();
                    Image icon=Toolkit.getDefaultToolkit().getImage("images/shark.png");
                    toCartFrame.setIconImage(icon);
                    toCartFrame.setTitle("Cart content");
                   toCartFrame.setSize(300, 200);
                   toCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                   listener2.logout();

                    ImageIcon sadFish=new ImageIcon(new ImageIcon("images/Icons/sadFish.png").getImage().getScaledInstance(100, 50, Image.SCALE_DEFAULT));

                    toCartFrame.dispose();
                    JOptionPane.showMessageDialog(toCartFrame,
                            "The cart is now empty",
                            "Operation success",
                            JOptionPane.INFORMATION_MESSAGE,
                            sadFish);

                }
                case "Buy" -> {
                    String receipt;
                    try{
                        manager.buyItems(user.getName());

                        JFrame receiptContentFrame=new ReceiptContentFrame(manager,user);
                        manager.emptyCart(user.getName());

                    } catch(IOException ex){
                        throw new RuntimeException(ex);
                    }
                }
                default -> throw new IllegalArgumentException("Unknown menu item!");
            }
        }
    }
}

 */
