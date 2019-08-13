import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
//new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false))

public class Inspection {
    public JButton submit = new JButton("Submit");
    public JButton cancel = new JButton("Cancel");
    public JButton SOP = new JButton("SOP");
    public JTextField name = new JTextField();
    public JFrame main;
    Map<String, InspecElement> buttons = new HashMap<String, InspecElement>();
    private ArrayList<String> selections;

    public Inspection(String inspection, String file, final ArrayList<String> selections) {
        this.selections = selections;
        //Button functions here:
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.dispose();
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String check = checkEntries();
                if (!check.equals("true")) {
                    TriggerWarn trigger = new TriggerWarn(check);

                } else {
                    System.out.println(Arrays.toString(selections.toArray()) + Arrays.toString(readEntries()));
                    TriggerSuccess trigger = new TriggerSuccess();
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    main.dispose();
                }
            }
        });
        //load inspections and URL for SOP into an arraylist
        ArrayList<String> questions = readcfg(file);
        final String SOPURL = questions.get(0);
        questions.remove(0);
        ArrayList<String> question = new ArrayList<String>();
        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String[]> dropdowns = new ArrayList<String[]>();
        for (String s : questions) {
            String[] splited = s.split("~");
            question.add(splited[0]);
            fields.add(splited[1]);
            if (splited[1].equals("2")) {
                dropdowns.add(splited[2].split(","));
            } else {
                dropdowns.add(null);
            }

        }
        SOP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupSOP(SOPURL);
            }
        });
        main = new JFrame(inspection);
        main.setResizable(false);
        main.setSize(500, 500);
        JPanel testing = new JPanel();
        main.setVisible(true);
        main.setContentPane(testing);
        testing.setLayout(new GridLayoutManager(15, 5, new Insets(0, 0, 0, 0), 1, -1));
        //Determines the type of object to create
        for (int i = 0; i < questions.size(); i++) {

            switch (Integer.parseInt(fields.get(i))) {
                case 1:
                    //Loads into the hash table
                    buttons.put(question.get(i), new RadioInspec());
                    break;
                case 2:
                    buttons.put(question.get(i), new DropInspec(dropdowns.get(i)));
                    break;
                case 3:
                    buttons.put(question.get(i), new TextInspec());
                    break;
                default:
                    //buttons.put(question.get(i), new JLabel("Error in the following question: " + question.get(i)));

            }

        }
        testing.add(new JLabel("Name: "), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testing.add(name, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testing.add(SOP, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        int i = 0;
        for (String s : question) {
            i = question.indexOf(s);
            System.out.println(i);
            testing.add(new JLabel(s), new GridConstraints(1 + i, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
            testing.add(buttons.get(s).getRoot(), new GridConstraints(1 + i, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        }
        testing.add(submit, new GridConstraints(2 + question.size(), 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testing.add(cancel, new GridConstraints(2 + question.size(), 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        testing.updateUI();
    }

    public ArrayList<String> readcfg(String filename) {

        ArrayList<String> returns = new ArrayList<String>();
        try {
            File file = new File(filename);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())


                returns.add(sc.nextLine());

        } catch (FileNotFoundException e) {
            TriggerWarn warn = new TriggerWarn("Vessel Database File Not Found, Contact Support");
            e.printStackTrace();
        }


        return returns;
    }

    public String checkEntries() {
        if (name.getText().equals(""))
            return "Name";

        for (Map.Entry<String, InspecElement> entry : buttons.entrySet()) {
            if (entry.getValue().checkInput() == false)
                return entry.getKey();
        }
        return "true";
    }

    public String[] readEntries() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        ArrayList<String> returns = new ArrayList<String>();
        returns.add(name.getText());
        SQLInterface sql = new SQLInterface("I:\\OPERATOR\\Brewing\\Vessel Inspection Tool\\InspectionTool\\DB\\vesselInspection.accdb");
        for (Map.Entry<String, InspecElement> entry : buttons.entrySet()) {
            sql.formatedInput(selections.get(2), selections.get(3), selections.get(0), name.getText(), entry.getKey(), entry.getValue().getStatus(), uuid.toString());
            returns.add(entry.getValue().getStatus());
        }
        return returns.toArray(new String[0]);
    }

    private static void setupSOP(String url) {

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

}
