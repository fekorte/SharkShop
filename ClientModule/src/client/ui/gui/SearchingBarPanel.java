package client.ui.gui;

import Common.IFrankie;
import Common.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SearchingBarPanel extends JPanel{
    public interface SearchListener{
        void onSearched(java.util.List<Item> result);
    }
    private IFrankie manager;
    //private EShopManager manager;
    private SearchListener listener;

    public SearchingBarPanel(IFrankie manager, SearchListener listener){
        this.manager = manager;
        this.listener = listener;
        setLayout(new FlowLayout()); //create layout

        add(new JLabel("Search item name:")); //add following components to layout

        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));
        searchTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(c < '0' || c > '9')) {
                    e.consume();  // ignore event
                }
            }
        });
        add(searchTextField);

        JButton searchButton = new JButton("Search");
        add(searchButton);

        add(new JLabel("Search item code:"));

        JTextField searchTextField2 = new JTextField();
        searchTextField2.setPreferredSize(new Dimension(200, 30));
        Icon sortAtoZ = new ImageIcon("images/icons/SotAtoZ.png");
        searchTextField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();  // ignore event
                }
            }
        });

        add(searchTextField2);

        JButton searchButton2 = new JButton("Search");
        add(searchButton2);

        //ActionListener decides what happens when a button gets clicked
        searchButton.addActionListener(e -> { //if the first search button gets clicked
            String titel = searchTextField.getText(); //read content of the first text field

            //now we are searching the item in the stock
            java.util.List<Item> result = new ArrayList<>();
            for (Item element : manager.getItemsInStock().values()) { //checks if item name is to be found in stock
                if (element.getItemName().equals(titel)) {
                    result.add(element);
                }
            }
            listener.onSearched(result); //calls method showItemList() and displays the result of the search on screen

            java.util.List<Item> stock = new ArrayList<>(manager.getItemsInStock().values()); //if item is not found show stock list
            if (result.isEmpty()) {
                listener.onSearched(stock);
            }
        });

        searchButton2.addActionListener(e -> { //same as before with difference that the second text field is being read, we search for the item code
            int itemCode = Integer.parseInt(searchTextField2.getText());
            java.util.List<Item> result = new ArrayList<>();
            for (Item element : manager.getItemsInStock().values()) {
                if (element.getItemCode() == itemCode) {
                    result.add(element);
                }
            }
            listener.onSearched(result);

            java.util.List<Item> stock = new ArrayList<>(manager.getItemsInStock().values());
            if (result.isEmpty()) {
                listener.onSearched(stock);
            }
        });
    }
}
