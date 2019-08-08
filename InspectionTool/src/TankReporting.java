import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import  java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class TankReporting extends JFrame {
    public static JFrame gui;
    public static ArrayList<Tank> tanks;
    public static ArrayList<Tank> sortedTanks;
    public static JPanel panel = new JPanel();
    public static String[] choices;
    private static JPanel middlePanel;
    private static BufferedImage surface;
    private static Color middleColor = Color.BLACK;
    final static JButton search = new JButton("Search");
    final static JTextField searchterm = new JTextField(10);
    private static JComboBox tank;
    final static JButton load = new JButton("Load");
    final static JButton clear = new JButton("Clear");
    private static Graphics2D gc;
    private static int gWidth = 600; // Surface width
    private static int gHeight = 400; // Surface height
    public static void main(String args[]){
        choices = readtankcfg(args[0]);
        tank = new JComboBox(choices);
        gui = intilaizeGui("Tank Reporting");

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tanks = convertTank(readCSV(args[1]));
        initializeTop();
        intializeMiddle();
        initializeGraphics();


        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tank.setSelectedItem(searchterm.getText());

            }
        });
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortedTanks = findTank((String)tank.getSelectedItem());
                int count = 0;
                for(Tank t : sortedTanks){
                   drawer(t.getTankID(),t.getState(), t.getDate(), t.getInitials(), count, t.isPast());
                   count++;
                }
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });

    }
    public static JFrame intilaizeGui(String windowTitle){
        JFrame frame = new JFrame(windowTitle);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocation(430, 100);
        return frame;
    }
    public static void initializeTop(){
        gui.add(panel, BorderLayout.NORTH);
        panel.setPreferredSize(new Dimension(40, 40));// hardCoded sizing
        panel.setMaximumSize(new Dimension(50, 50));  // hardCoded sizing
        panel.setMinimumSize(new Dimension(50, 50));  // hardCoded sizing
        panel.add(search);
        panel.add(searchterm);
        panel.add(tank);
        panel.add(load);
        panel.add(clear);
        panel.updateUI();
    }
    public static void intializeMiddle(){

        middlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(surface, 0, 0, null);
            }
        };
        middlePanel.setPreferredSize(new Dimension(500,350 ));// hardCoded sizing
        middlePanel.setMaximumSize(new Dimension(500, 350));  // hardCoded sizing
        middlePanel.setMinimumSize(new Dimension(500, 350));  // hardCoded sizing
        middlePanel.setBackground(middleColor);
        gui.add(middlePanel, BorderLayout.CENTER);
        middlePanel.updateUI();

    }
    public static void initializeGraphics() {
        //gWidth = middlePanel.getWidth();
        //gHeight = middlePanel.getHeight();
        surface = new BufferedImage(gWidth, gHeight, BufferedImage.TYPE_INT_RGB);
        gc = (Graphics2D) surface.getGraphics();
        gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        gc.setBackground(middleColor);
        gc.clearRect(0, 0, gWidth, gHeight);

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
    public static ArrayList<Tank> findTank(String tankID){
        ArrayList<Tank> returns = new ArrayList<Tank>();
        for(Tank t : tanks){
            if(tankID.equals(t.getTankID())){
                returns.add(t);
            }
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
    public static void drawer(String tankID, String state, Date date, String initials, int count, boolean past){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if(state.equals("PASS"))
            gc.setColor(Color.GREEN);
        if(state.equals("FAIL"))
            gc.setColor(Color.RED);
        gc.drawRect(calculateRow(count), calculateCol(count) + 5, 100, 100);
        System.out.println(calculateRow(count));
        gc.drawString(simpleDateFormat.format(date).toString(), calculateRow(count) + 5, calculateCol(count) + 40);
        gc.drawString(initials, calculateRow(count) + 5, calculateCol(count) + 55);
        gc.drawString(tankID, calculateRow(count) + 5, calculateCol(count) + 70);
        gc.drawString(state, calculateRow(count) + 5, calculateCol(count) + 85);
        gc.drawString(Boolean.toString(past), calculateRow(count) + 5, calculateCol(count) + 100);
        middlePanel.updateUI();
    }
    public static void clear() {
        gc.setBackground(middleColor);
        gc.clearRect(0, 0, gWidth, gHeight);
        middlePanel.updateUI();
    }
    public static int calculateCol(int count){
        int mult = count/5;
        //System.out.println(count);
        //System.out.println(mult);
        if(mult < 1){
            return 10;
        }
        if(mult >= 1){
            return 10 + 100 * mult;
        }
        return 0;
    }
    public static int calculateRow(int count){
        int row = count % 5;
        return row * 105;

    }
}
