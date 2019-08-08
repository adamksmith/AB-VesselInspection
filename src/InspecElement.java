import javax.swing.*;

public abstract class InspecElement {
    public JPanel root = new JPanel();

    public InspecElement() {
        //root.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
    }

    public JPanel getRoot() {

        return root;
    }

    public abstract String getStatus();

    public abstract boolean checkInput();


}
