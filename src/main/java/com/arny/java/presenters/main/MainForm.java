/*
 * Created by JFormDesigner on Sat Jan 06 19:58:52 MSK 2018
 */

package com.arny.java.presenters.main;

import javax.swing.table.*;

import com.arny.java.data.models.CleanFolder;
import com.arny.java.presenters.base.BaseMvpJFrame;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainForm extends BaseMvpJFrame<MainContract.View, MainPresener> implements MainContract.View {
    @Override
    protected MainPresener initPresenter() {
        return new MainPresener();
    }

    private FoldersTableModel tableModel;

    public MainForm() {
        super();
        initComponents();
        initComponentsListener();
        setVisible(true);
    }

    @Override
    public void showInfo(@NotNull String msg) {
        label_info.setText(msg);
    }

    private void initComponentsListener() {
        table_folders.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table_folders.convertRowIndexToModel(table_folders.getSelectedRow());
                CleanFolder folder = tableModel.getFolders().get(row);
                mPresenter.initCurrent(folder);
            }
        });
        btn_update.addActionListener(e -> mPresenter.loadFolders());
        btn_clean.addActionListener(e -> mPresenter.removeSelectedFolder());
        btn_remove.addActionListener(e -> mPresenter.deleteSelectedFolder());
    }

    @Override
    public void clearSelection() {
        table_folders.clearSelection();
    }

    @Override
    public void updateTable(@NotNull ArrayList<CleanFolder> folders) {
        tableModel = new FoldersTableModel(folders);
        table_folders.setModel(tableModel);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mPresenter.initDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.loadFolders();
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
        table_folders = new JTable();
        label_info = new JLabel();
        btn_remove = new JButton();
        btn_update = new JButton();
        btn_clean = new JButton();

        //======== this ========
        setTitle("\u041e\u0447\u0438\u0441\u0442\u043a\u0430 \u0434\u0438\u0440\u0435\u043a\u0442\u043e\u0440\u0438\u0439");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();

        //---- btn_add ----
        btn_add.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c");
        btn_add.addActionListener(e -> btn_addActionPerformed(e));

        //======== panel4 ========
        {

            //======== scrollPane1 ========
            {

                //---- table_folders ----
                table_folders.setModel(new DefaultTableModel(
                        new Object[][]{
                                {null, null},
                                {null, null},
                        },
                        new String[]{
                                "Path", "Size"
                        }
                ));
                {
                    TableColumnModel cm = table_folders.getColumnModel();
                    cm.getColumn(0).setResizable(false);
                    cm.getColumn(1).setResizable(false);
                }
                table_folders.setAutoCreateRowSorter(true);
                table_folders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scrollPane1.setViewportView(table_folders);
            }

            //---- label_info ----
            label_info.setText("text");

            GroupLayout panel4Layout = new GroupLayout(panel4);
            panel4.setLayout(panel4Layout);
            panel4Layout.setHorizontalGroup(
                    panel4Layout.createParallelGroup()
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
                            .addComponent(label_info, GroupLayout.DEFAULT_SIZE, 563, Short.MAX_VALUE)
            );
            panel4Layout.setVerticalGroup(
                    panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label_info, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
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
                                .addComponent(panel4, GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JButton btn_add;
    private JPanel panel4;
    private JScrollPane scrollPane1;
    private JTable table_folders;
    private JLabel label_info;
    private JButton btn_remove;
    private JButton btn_update;
    private JButton btn_clean;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
