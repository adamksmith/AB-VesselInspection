import javax.swing.*;

public class TriggerSuccess {
    public TriggerSuccess() {
        final JFrame warning;
        JPanel warn;
        JLabel notice;

        warning = new JFrame("Success!");
        warn = new JPanel();
        notice = new JLabel("Writing to the database!");

        warn.add(notice);

        warn.updateUI();
        warning.setContentPane(warn);
        warning.setResizable(false);
        warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        warning.setLocationRelativeTo(null);
        warning.setVisible(true);
        warning.setSize(50, 20);
        warning.pack();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        warning.dispose();

    }
}
