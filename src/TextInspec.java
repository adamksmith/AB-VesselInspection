import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class TextInspec extends InspecElement {
    private JTextField text = new JTextField(20);

    public TextInspec() {
        root.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));

        root.add(text, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    @Override
    public String getStatus() {
        return text.getText();
    }

    @Override
    public boolean checkInput() {
        if (text.getText().equals(""))
            return false;
        return true;
    }
}
