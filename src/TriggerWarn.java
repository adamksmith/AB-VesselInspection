import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TriggerWarn {
    final JFrame warning;
    JPanel warn;
    JLabel notice;
    JLabel reason;
    JButton cancel1;

    public TriggerWarn(String s) {
        warning = new JFrame("Warning!");
        warn = new JPanel();
        notice = new JLabel("Please Correct The Following:");
        reason = new JLabel(s);
        cancel1 = new JButton("Cancel");
        warn.add(notice);
        warn.add(reason);
        warn.add(cancel1);
        warn.updateUI();
        warning.setContentPane(warn);
        warning.setResizable(false);
        warning.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        warning.setLocationRelativeTo(null);
        warning.setVisible(true);
        warning.setSize(50, 20);
        warning.pack();
        cancel1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                warning.dispose();
            }
        });
    }
}
