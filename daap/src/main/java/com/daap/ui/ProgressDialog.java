package com.daap.ui;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 * Created by ALI on 8/20/2017.
 */

public class ProgressDialog extends JDialog {

    private static  JDialog dialog;
    private static ProgressDialog progressDialog;


    // mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {
    // @Override
    // public void propertyChange(PropertyChangeEvent evt) {
    // if (evt.getPropertyName().equals("state")) {
    // if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
    // dialog.dispose();
    // }
    // }
    // }
    // });


    public static ProgressDialog getInstance() {


        if (progressDialog == null){
            progressDialog = new ProgressDialog();
        }

        return progressDialog;
    }

    public void showDialog(Window win, String message) {

        if (dialog == null) {
            dialog = new JDialog(win, message, ModalityType.APPLICATION_MODAL);
            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(progressBar, BorderLayout.CENTER);
            panel.add(new JLabel("Please wait......."), BorderLayout.PAGE_START);
            dialog.add(panel);
            dialog.pack();
            dialog.dispose();
            dialog.setLocationRelativeTo(win);
            dialog.setVisible(true);

//            JOptionPane.showMessageDialog(new JFrame(),
//                    "Patterns Detected. Check for results at the .txt file created in the export folder.", "JOB'S DONE!",
//                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            dialog.setVisible(true);
        }

//        if (dialog == null) {
//            dialog =   getInstance(win);
//        }

//        try {
//            if (!message.isEmpty()) {
//                dialog.setTitle(message);
//            } else {
//                dialog.setTitle("Loading data...");
//            }
//
//            dialog.setTitle(null);
////            dialog.setIndeterminate(true);
//            dialog.setVisible(true);
////            dialog.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // nothing
//        }
    }

    public void cancel() {
        if (dialog != null) {
            try {
                dialog.setVisible(false);
                dialog = null;
            } catch (Exception e) {
                // nothing
                e.printStackTrace();
            }
        }
    }

}
