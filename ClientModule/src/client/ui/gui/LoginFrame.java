package client.ui.gui;

import Common.IFrankie;
import Common.exceptions.ExceptionLoginFailed;
import Common.Person;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private Person user;

    public LoginFrame(IFrankie manager)  {
        Image icon = Toolkit.getDefaultToolkit().getImage("images/shark.png");
        this.setIconImage(icon);

        JPanel loginPanel = new JPanel(new FlowLayout());
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel userLabel=new JLabel("Username:"); //add components to panel
        loginPanel.add(userLabel);

        JTextField userText=new JTextField(20);
        loginPanel.add(userText);

        JLabel passwordLabel=new JLabel("Password:");
        loginPanel.add(passwordLabel);

        JTextField passwordText=new JPasswordField(20);//this is were the ** magic pass
        loginPanel.add(passwordText);

        JButton buttonLogin=new JButton("Login");
        loginPanel.add(buttonLogin);

        JButton buttonRegister=new JButton("Register");
        loginPanel.add(buttonRegister);

        JLabel success=new JLabel("");
        loginPanel.add(success);

        add(loginPanel);
        setVisible(true);
        pack();

        loginPanel.getRootPane().setDefaultButton(buttonLogin);
        buttonLogin.addActionListener(e -> {

            String userName = userText.getText();
            String password = passwordText.getText();

            try {
                this.user = manager.login(userName, password);

                success.setText("Login successful.");
                dispose();
                if (user != null) {
                    if (user.getBoolean()) {
                        ClientMenu clientMenu = new ClientMenu(manager, this.user);
                    } else {
                        EmployeeMenu employeeMenu = new EmployeeMenu(manager, this.user);
                    }
                }
            } catch (ExceptionLoginFailed a) {
                success.setText("Login failed. Please try again.");
                a.printStackTrace();
            }
        });

        buttonRegister.addActionListener(a -> {
            dispose();
            RegisterFrame register = new RegisterFrame(manager);

        });
    }
}
