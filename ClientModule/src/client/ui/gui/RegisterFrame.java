package client.ui.gui;

import Common.IFrankie;
import Common.exceptions.ExceptionUserAlreadyExists;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterFrame extends JFrame{

    public RegisterFrame(IFrankie manager){
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);
        JPanel registerPanel = new JPanel(new FlowLayout());

        setTitle("Registration");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel = new JLabel("Username:");
        registerPanel.add(userLabel);

        JTextField userText = new JTextField(30);
        registerPanel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        registerPanel.add(passwordLabel);

        JTextField passwordText = new JPasswordField(30);
        registerPanel.add(passwordText);

        JLabel addressLabel = new JLabel("Address:");
        registerPanel.add(addressLabel);

        JTextField addressText = new JTextField(30);
        registerPanel.add(addressText);

        JButton buttonRegisterClient = new JButton("Submit");
        registerPanel.add(buttonRegisterClient);

        JButton goBack = new JButton("Go to Login");
        registerPanel.add(goBack);

        JLabel success = new JLabel("");
        registerPanel.add(success);

        add(registerPanel);
        setVisible(true);
        pack();

        buttonRegisterClient.addActionListener(e -> {
            String userName = userText.getText();
            String password = passwordText.getText();
            String address = addressText.getText();

            try {
                manager.registerClient(userName, password, address); //here
                dispose();
                LoginFrame login = new LoginFrame(manager);
            } catch (ExceptionUserAlreadyExists | IOException a) {
                success.setText("Registration failed. Please try again."); //here a new frame that just display the failure
                a.printStackTrace();
            }
        });
        goBack.addActionListener(a -> {
            dispose();
            LoginFrame login = new LoginFrame(manager);
        });
    }
}