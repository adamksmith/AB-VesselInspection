import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VesselInspection {
    //Main window variables
    private static JPanel rootPanel;
    private static JComboBox comboBox1;
    private static JComboBox comboBox2;
    private static JComboBox comboBox3;
    private static JTextArea textArea1;
//    private static JList list1;

    private static JComboBox comboBox4;
    private static JButton button1;

    private static String[] Location = {"Select Side", "Hot Side", "Cold Side"};
    private static String[] VesselType = {"Please Select a Location"};
    private static String[] VesselID = {"Please Select a Vessel Type"};
    private static String[] VesselCheck = {"Please Complete the Vessel Selection Process"};
    //Main window Variables
    //Dialouge Variables
    private static JFrame dialoge;
    private static JPanel diagroot;
    private static JLabel name;
    private static JLabel comments;
    private static JTextField textField1;
    private static JRadioButton passRadioButton;
    private static JRadioButton failRadioButton;
    private static JTextField editorPane1;
    private static JButton submit;
    private static JButton cancel;
    private static JButton SOP;


    private static void setupDiag() {
        diagroot = new JPanel();
        textField1 = new JTextField(12);
        passRadioButton = new JRadioButton("Pass");
        failRadioButton = new JRadioButton("Fail");
        editorPane1 = new JTextField(32);
        name = new JLabel("Name:");
        comments = new JLabel("Comments:");
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        Dimension size = new Dimension(20, 30);
        editorPane1.setMinimumSize(size);
        SOP = new JButton("Load SOP");

        diagroot.add(SOP);
        diagroot.add(name);
        diagroot.add(textField1);
        diagroot.add(passRadioButton);
        diagroot.add(failRadioButton);
        diagroot.add(comments);
        diagroot.add(editorPane1);
        diagroot.add(submit);
        diagroot.add(cancel);
        diagroot.updateUI();
    }

    private static void setupUI() {
        rootPanel = new JPanel(new BorderLayout(5, 5));
        rootPanel.setLayout(new GridLayoutManager(9, 5, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = getFont(null, -1, 16, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Location:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = getFont(null, -1, 16, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Position:");
        rootPanel.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = getFont(null, -1, 16, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("Vessel Type:");
        rootPanel.add(label3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(8, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        rootPanel.add(spacer2, new GridConstraints(5, 0, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        rootPanel.add(spacer3, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Due for Inspection");
        rootPanel.add(label4, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        list1 = new JList();
//        rootPanel.add(list1, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
//        list1.add("Tank", "Tank");
        comboBox1 = new JComboBox();
        comboBox1.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel(Location);

        comboBox1.setModel(defaultComboBoxModel1);
        rootPanel.add(comboBox1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        comboBox2.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel(VesselType);

        comboBox2.setModel(defaultComboBoxModel2);
        rootPanel.add(comboBox2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox3 = new JComboBox();
        comboBox3.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel(VesselID);

        comboBox3.setModel(defaultComboBoxModel3);
        rootPanel.add(comboBox3, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        loadSelectionButton = new JButton();
//        loadSelectionButton.setText("Load Selection");
//        rootPanel.add(loadSelectionButton, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        Font label5Font = getFont(null, -1, 16, label3.getFont());
        if (label5Font != null) label5.setFont(label5Font);
        label5.setText("Vessel Number:");
        rootPanel.add(label5, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox4 = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel(VesselCheck);

        comboBox4.setModel(defaultComboBoxModel4);
        rootPanel.add(comboBox4, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        button1 = new JButton();
        button1.setText("Complete Form");
        rootPanel.add(button1, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        loadNextMenuButton = new JButton();
//        loadNextMenuButton.setText("Load Next Menu");
//        rootPanel.add(loadNextMenuButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
//        loadNextMenuButton1 = new JButton();
//        loadNextMenuButton1.setText("Load Next Menu");
//        rootPanel.add(loadNextMenuButton1, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));


        textArea1 = new JTextArea();

        textArea1.setColumns(27);
        textArea1.setRows(3);
        textArea1.setMaximumSize(new Dimension(27, 3));
        textArea1.setEditable(false);

        textArea1.setText("This will populate once a vessel type has been selected  ");

        rootPanel.add(textArea1, new GridConstraints(6, 2, 2, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }

    /**
     * @noinspection ALL
     */
    private static Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

    public static void main(String args[]) {

        JFrame frame = new JFrame("Main");
        setupUI();
        frame.setContentPane(rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        comboBox2.disable();
        comboBox3.disable();
        comboBox4.disable();


        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Loc = comboBox1.getSelectedIndex();
                comboBox2.enable();
                String[] pos;
                switch (Loc) {
                    case 1:
                        pos = intializePos("_Pos/HotSide_Pos.cfg");
                        comboBox2.setModel(new DefaultComboBoxModel(pos));

                        break;
                    case 2:
                        pos = intializePos("_Pos/ColdSide_Pos.cfg");
                        comboBox2.setModel(new DefaultComboBoxModel(pos));
                    default:
                        break;
                }


            }

        });
        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int Loc = comboBox1.getSelectedIndex();
                comboBox3.enable();
                textArea1.setText(loadDue((String) comboBox2.getSelectedItem()));
//
                String[] ves;
                switch (Loc) {
                    case 1:
                        ves = intializeVes("_Pos/HotSide_Pos.cfg", comboBox2.getSelectedIndex());
                        comboBox3.setModel(new DefaultComboBoxModel(ves));

                        break;
                    case 2:
                        ves = intializeVes("_Pos/ColdSide_Pos.cfg", comboBox2.getSelectedIndex());
                        comboBox3.setModel(new DefaultComboBoxModel(ves));
                    default:
                        break;
                }


            }
        });
        comboBox3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox4.enable();
                comboBox4.setModel(new DefaultComboBoxModel(readcfg("_VesselDB/" + (String) comboBox3.getSelectedItem() + ".cfg").toArray()));
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Inspection trigger;
                TriggerWarn warn;
                ArrayList<String> selections;
                if (comboBox4.isEnabled()) {
                    selections = new ArrayList<String>();
                    selections.add((String) comboBox1.getSelectedItem());
                    selections.add((String) comboBox2.getSelectedItem());
                    selections.add((String) comboBox3.getSelectedItem());
                    selections.add((String) comboBox4.getSelectedItem());
                    trigger = new Inspection((String) comboBox3.getSelectedItem() + " " + comboBox4.getSelectedItem(), "_Inspec/" + comboBox3.getSelectedItem() + "_Inspec.cfg", selections);
                } else
                    warn = new TriggerWarn("Please complete all fields");
            }
        });


    }


    public static ArrayList<String> readcfg(String filename) {
        File file =
                new File(filename);
        Scanner sc = null;
        ArrayList<String> returns = new ArrayList<String>();
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (sc.hasNextLine())
            returns.add(sc.nextLine());


        return returns;
    }

    public static ArrayList<String> getTankList(String filename) {
        String[] tanksRaw = readcfg(filename).toArray(new String[0]);
        ArrayList<String> returns = new ArrayList<String>();

        for (String s : tanksRaw) {
            String[] splited = s.split(" ");
            returns.add(splited[0]);
        }
        return returns;
    }

    public static ArrayList<Integer> getTankNumber(int index, String filename) {
        System.out.println(index);
        String[] tanksRaw = readcfg(filename).toArray(new String[0]);
        ArrayList<String> returns = new ArrayList<String>();

        for (String s : tanksRaw) {
            String[] splited = s.split(" ");
            returns.add(splited[1]);
        }
        int number = Integer.parseInt(returns.get(index));
        System.out.println(number);
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for (int i = 1; i <= number; i++) {
            returnList.add(i);
        }
        System.out.println(returnList.toString());
        return returnList;

    }

    public static String loadDue(String searchterm) {


        return "Vessels Due for: " + searchterm + "\n" +
                "This will populate once I finish it";
    }

    public static String[] intializePos(String file) {
        ArrayList<String> readIn = readcfg(file);
        ArrayList<String> readOutPos = new ArrayList<String>();


        for (String s : readIn) {
            String[] split = s.split("~");
            readOutPos.add(split[0]);

        }

        return readOutPos.toArray(new String[0]);


    }

    private static String[] intializeVes(String s, int selectedIndex) {
        ArrayList<String> readIn = readcfg(s);
        ArrayList<String[]> readOut = new ArrayList<String[]>();

        for (String d : readIn) {
            String[] split1 = d.split("~");
            readOut.add(split1[1].split(","));

        }
        return readOut.get(selectedIndex);
    }
}


