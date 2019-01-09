package com.arny.java.presenters.base;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class BaseMvpJFrame<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends JFrame implements BaseMvpView {
    protected P mPresenter;


    protected abstract P initPresenter();

    protected void onCreate() {
       // System.out.println("onCreate");
    }

    protected void onResume() {
       // System.out.println("onResume");
    }

    protected void onPause() {
        //System.out.println("onPause");
    }

    protected void onDestroy() {
        //System.out.println("onDestroy");
    }

    @Override
    public void toast(@Nullable String title, @Nullable String error, boolean success) {
        int messType = success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(null, error, title, messType);
    }

    protected BaseMvpJFrame() throws HeadlessException {
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        mPresenter.attachView((V) this);
        onCreate();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mPresenter.detachView();
                onDestroy();
            }

            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                onResume();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                onPause();
            }
        });
    }
}
