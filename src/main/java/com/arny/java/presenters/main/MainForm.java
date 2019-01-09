/*
 * Created by JFormDesigner on Sat Jan 06 19:58:52 MSK 2018
 */

package com.arny.java.presenters.main;

import com.arny.java.presenters.base.BaseMvpJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Arny
 */
public class MainForm extends BaseMvpJFrame<MainContract.View, MainPresener> implements MainContract.View {
    @Override
    protected MainPresener initPresenter() {
        return new MainPresener();
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mPresenter.initDB();
        System.out.println("MainForm onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("MainForm onResume");
        mPresenter.loadFolders();
    }

    public MainForm() {
        super();
        initComponents();
        setVisible(true);
    }

    private void btn_addActionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getAbsolutePath();
            System.out.println("file path:" + path);
            mPresenter.addFolder(path);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        btn_add = new JButton();
        panel4 = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        btn_remove = new JButton();
        btn_update = new JButton();
        btn_clean = new JButton();

        //======== this ========
        setTitle("\u041e\u0447\u0438\u0441\u0442\u043a\u0430 \u0434\u0438\u0440\u0435\u043a\u0442\u043e\u0440\u0438\u0439");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();

        //---- btn_add ----
        btn_add.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c");
        btn_add.addActionListener(e -> btn_addActionPerformed(e));

        //======== panel4 ========
        {

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(table1);
            }

            GroupLayout panel4Layout = new GroupLayout(panel4);
            panel4.setLayout(panel4Layout);
            panel4Layout.setHorizontalGroup(
                panel4Layout.createParallelGroup()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
            );
            panel4Layout.setVerticalGroup(
                panel4Layout.createParallelGroup()
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
            );
        }

        //---- btn_remove ----
        btn_remove.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0438\u0437 \u0441\u043f\u0438\u0441\u043a\u0430");

        //---- btn_update ----
        btn_update.setText("\u041e\u0431\u043d\u043e\u0432\u0438\u0442\u044c");

        //---- btn_clean ----
        btn_clean.setText("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btn_add)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btn_update)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btn_clean)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(btn_remove)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_clean)
                        .addComponent(btn_remove)
                        .addComponent(btn_add)
                        .addComponent(btn_update))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton btn_add;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton btn_remove;
    private JButton btn_update;
    private JButton btn_clean;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
