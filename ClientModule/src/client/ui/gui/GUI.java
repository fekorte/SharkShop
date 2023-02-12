package client.ui.gui;

import Common.IFrankie;
import Server.domain.EShopManager;


import javax.swing.*;
import java.io.IOException;


public class GUI{
    public GUI(){
        try{
            IFrankie manager = new EShopManager();
            JFrame frameLogin=new LoginFrame(manager);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        GUI gui=new GUI();
    }
}

