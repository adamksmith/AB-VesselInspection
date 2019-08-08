import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DropInspec extends InspecElement {
    public String[] dropdown;
    public boolean interact = false;
    public JComboBox combo;

    public DropInspec(String[] dropdown) {
        this.dropdown = dropdown;
        root.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        combo = new JComboBox(dropdown);
        root.add(combo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interact = true;
            }
        });
    }

    @Override
    public String getStatus() {
        return (String) combo.getSelectedItem();

    }

    @Override
    public boolean checkInput() {
        if (interact == true)
            return true;
        return false;
    }
}
