import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import  java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.*;
import java.util.Scanner;


import static sun.rmi.transport.TransportConstants.Return;


public class TankInspection {
    public static String[] choices;
    public static ArrayList<Tank> tanks;
    public static void main(final String args[]) {
        choices = readtankcfg(args[0]);
        JFrame frame = new JFrame("Inspection Tool");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocation(430, 100);

        final JPanel panel = new JPanel();

        frame.add(panel);

        JLabel lbl = new JLabel("Select one of the possible choices and click OK");
        lbl.setVisible(true);

        panel.add(BorderLayout.NORTH, lbl);

        final JTextArea textArea = new JTextArea(10, 40);
        panel.add(BorderLayout.CENTER, textArea);


        String[] passfail = {"PASS", "FAIL"};

        final JTextField search = new JTextField(10);
        final JButton searchbtn = new JButton("Search");
        final JComboBox cb1 = new JComboBox(passfail);
        final JComboBox cb;
        cb = new JComboBox(choices);
        final JTextField initials = new JTextField("Initials", 5);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        cb.setVisible(true);
        panel.add(searchbtn);
        panel.add(search);
        panel.add(cb);
        cb1.setVisible(true);
        panel.add(cb1);
        panel.add(initials);


        JButton btn = new JButton("Submit");
        panel.add(btn);
        searchbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchterm = search.getText();

                cb.setSelectedItem(searchterm);
            }
        });
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(initials.getText().isEmpty() || initials.getText().contains("it")){
                    textArea.append("Please enter your initials!! \n");

                }
                else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = new Date();
                    String dates = dateFormat.format(date);
                    String state = (String) cb1.getSelectedItem();
                    String tank = (String) cb.getSelectedItem();
                    String initial = initials.getText();
                    textArea.append("Tank: " + tank + " was marked as: " + state + " on: " + dates + " by: " + initial + "\n");
                    textArea.append(write(tank, state, dates, initial, args[1]) + "\n");
                }

            }
        });
        //Tanks reporting list
        JButton refresh = new JButton("Refresh");

        JLabel needinspec = new JLabel("The following tanks need inspection (Past Due 30 Days)");
        panel.add(needinspec);
        panel.add(refresh);
        tanks = convertTank(readCSV(args[1]));
        ArrayList<String> past = pastDue(choices);
        String TanksID = "";
        int count = 0;
        for(String s : past){

            if(count > 8){
                TanksID += "\n";
                count = 0;
            }
            TanksID += " " + s;
            count++;
        }
        final JTextArea report = new JTextArea(TanksID, 40, 40);
        panel.add(report);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                report.setText(null);
                tanks = convertTank(readCSV(args[1]));
                ArrayList<String> refreshed = pastDue(choices);
                String TanksID = "";
                int count = 0;
                for(String s : refreshed){

                    if(count > 8){
                        TanksID += "\n";
                        count = 0;
                    }
                    TanksID += " " + s;
                    count++;
                }
                System.out.println(TanksID);
                report.setText(TanksID);
                panel.updateUI();
            }
        });




        panel.updateUI();


    }

    public static String write(String tankID, String status, String date, String intial, String filename) {
        String returns = "";

        try{ PrintWriter writer = new PrintWriter(new FileWriter(filename,true));


            StringBuilder sb = new StringBuilder();
//            sb.append("Vessel ID");
//            sb.append(',');
//            sb.append("Name");
//            sb.append(',');
//            sb.append("Date");
//            sb.append('\n');

            sb.append(tankID);
            sb.append(',');
            sb.append(status);
            sb.append(',');
            sb.append(date);
            sb.append(',');
            sb.append(intial);
            //sb.append('\n');

            writer.println(sb.toString());
            writer.close();

            returns = "done!";


        } catch (FileNotFoundException e) {
            returns = e.getMessage();
        } catch (IOException e) {
            returns = e.getMessage();
        }
        return returns;

    }
    public static String[] readtankcfg(String filename){
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


        return returns.toArray(new String[0]);
    }
    public static String searchTankArray(String search){
        String searchUC = search.toUpperCase();
        for(String s : choices){
            if(s.contains(searchUC))
                return s;
        }
        return "";
    }
    public static ArrayList<String> readCSV(String filename){
        //get size of array
        ArrayList<String> returns = new ArrayList<String>();
        try{ Scanner sc = new Scanner(new File(filename));
            while(sc.hasNextLine())
                returns.add(sc.nextLine());

        }catch (Exception e){
            e.printStackTrace();
        }
        return returns;
    }
    public static ArrayList<Tank> convertTank(ArrayList<String> tanks){
        ArrayList<Tank> returns = new ArrayList<Tank>();
        for(String s: tanks){
            String[] values = s.split(",");
            Date date;
            try{ date = new SimpleDateFormat("yyyy/MM/dd").parse(values[2]);
                returns.add(new Tank(values[0], values[1], date, values[3]));
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return returns;
    }
    public static Tank findTank(String tankID){

        for(Tank t : tanks){
            if(tankID.equals(t.getTankID())){
                return t;
            }
        }
        return null;
    }

    public static ArrayList<String> pastDue(String[] tanks){
        ArrayList<String> returns = new ArrayList<String>();
        for(String s: tanks){
            if(findTank(s) == null || findTank(s).isPast() == true){
                returns.add(s);
            }
        }
        return returns;
    }
}





