package com.company;

import javax.swing.*;
import java.awt.event.*;

public class ToolWindow extends JDialog {
    private JPanel contentPane;
    private JButton plusButton;
    private JButton minusButton;
    private DrawPanel drawPanel1;

    public ToolWindow() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(plusButton);
        setResizable(false);
        plusButton.addActionListener(e -> onOK1());

        minusButton.addActionListener(e -> onOK2());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK1() {
        drawPanel1.press2();
    }
    private void onOK2() {

        drawPanel1.press1();
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ToolWindow dialog = new ToolWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }



}
