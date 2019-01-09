package com.arny.java;

import com.arny.java.presenters.main.MainForm;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {
        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainForm();
    }


}
