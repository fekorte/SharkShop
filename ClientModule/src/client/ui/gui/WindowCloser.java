package client.ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowCloser extends WindowAdapter{
    @Override
    public void windowClosing(WindowEvent e){
        Window window=e.getWindow();
        int result=JOptionPane.showConfirmDialog(window, "Are you sure you want to exit?", "Attention!", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION){
            window.setVisible(false);
            window.dispose();
            System.exit(0);
        }
    }
}
