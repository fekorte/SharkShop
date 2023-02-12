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

    public SearchingBarPanel(IFrankie manager, SearchListener listener){
        setLayout(new FlowLayout());

        add(new JLabel("Search item name:"));

        JTextField searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(200, 30));
        searchTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(c < '0' || c > '9')) {
                    e.consume();
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
                    e.consume();
                }
            }
        });

        add(searchTextField2);

        JButton searchButton2 = new JButton("Search");
        add(searchButton2);

        searchButton.addActionListener(e -> {
            String titel = searchTextField.getText();

            //now we are searching the item in the stock
            java.util.List<Item> result = new ArrayList<>();
            for (Item element : manager.getItemsInStock().values()) {
                if (element.getItemName().equals(titel)) {
                    result.add(element);
                }
            }
            listener.onSearched(result);

            java.util.List<Item> stock = new ArrayList<>(manager.getItemsInStock().values());
            if (result.isEmpty()) {
                listener.onSearched(stock);
            }
        });

        searchButton2.addActionListener(e -> {
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
