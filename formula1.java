import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class formula1 extends JFrame implements ActionListener {
    JButton SaveButton;
    JButton CombineWindowButton;
    
    JComboBox TeamComboBox;
    JComboBox TrackComboBox;

    JPanel DriverInfoPanel, TrackInfoPanel;

    JTextField FirstNames;
    JTextField LastNames;
    
    JTextField Position;
    JTextField Q1;
    JTextField Q2;
    JTextField Q3;

    JCheckBox Eliminated;

    JRadioButton SoftTyreButton;
    JRadioButton MediumTyreButton;
    JRadioButton HardTyreButton;
    JRadioButton IntermediateTyreButton;
    JRadioButton FullWetTyreButton;

    JList DriverTrackInfolist;
    DefaultListModel listModel;

    private int currentID = 1; 
    private static final int space = 20;
    private static final int space2 = 10;
    private static final int space3= 15;


    public formula1() {
        this.setTitle("Formula 1 Qualifying JAVA GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        ImageIcon logo = new ImageIcon("Images/f1.png");
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0x616673));
        this.setPreferredSize(new Dimension(810, 305));

        DriverInfoPanel = new JPanel(new GridLayout(4, 2));
        DriverInfoPanel.setBackground(new Color(0x616673));

        DriverInfoPanel.add(new JLabel("First Name") {{
            setForeground(new Color(0xFFFFFF)); 
        }});
        FirstNames = new JTextField(10);
        DriverInfoPanel.add(FirstNames);

        DriverInfoPanel.add(new JLabel("Last Name"){{
            setForeground(new Color(0xFFFFFF));
        }});
        LastNames = new JTextField(10);
        DriverInfoPanel.add(LastNames);

        DriverInfoPanel.add(new JLabel("Team"){{
            setForeground(new Color(0xFFFFFF));
        }});
        String[] teams =
         {"RedBull Racing", "Ferrari", "McLaren", "Mercedes", 
        "Aston Martin", "RB", "Haas", "Alpine", "Williams", "Kick Sauber"};
        TeamComboBox = new JComboBox<>(teams);
        DriverInfoPanel.add(TeamComboBox);

        DriverInfoPanel.add(new JLabel("Track"){{
            setForeground(new Color(0xFFFFFF));
        }});
        String[] TrackArray = 
        {"Bahrain", "Jeddah", "Australia", "Suzuka", "Shanghai", "Miami", "Imola",
         "Monaco", "Barcelona", "Austria", "Silverstone", "Hunagry", "Spa-Francorchamps", "Zandvoort", 
         "Monza", "Baku",  "Singapore", "COTA", "Mexico", "Brazil", "Las Vegas", "Qatar", "Yas Marina"};

        TrackComboBox = new JComboBox(TrackArray);
        DriverInfoPanel.add(TrackComboBox);

        this.add(DriverInfoPanel, BorderLayout.CENTER);

        
        TrackInfoPanel = new JPanel(new GridLayout(4, 2));
        TrackInfoPanel.setBackground(new Color(0x616673));

        TrackInfoPanel.add(new JLabel("Position"));
        Position = new JTextField(10);
        TrackInfoPanel.add(Position);

        TrackInfoPanel.add(new JLabel("Q1"));
        Q1 = new JTextField(10);
        TrackInfoPanel.add(Q1);

        TrackInfoPanel.add(new JLabel("Q2"));
        Q2 = new JTextField(10);
        TrackInfoPanel.add(Q2);

        TrackInfoPanel.add(new JLabel("Q3"));
        Q3 = new JTextField(10);
        TrackInfoPanel.add(Q3);

        this.add(TrackInfoPanel, BorderLayout.CENTER);

        Eliminated = new JCheckBox("Eliminated");
        this.add(Eliminated);

        SoftTyreButton = JRadioButtonWithImage("Soft", "Images/softs.png");
        MediumTyreButton = JRadioButtonWithImage("Medium", "Images/mediums.png");
        HardTyreButton = JRadioButtonWithImage("Hard", "Images/hards.png");
        IntermediateTyreButton = JRadioButtonWithImage("Intermediate", "Images/inters.png");
        FullWetTyreButton = JRadioButtonWithImage("Full Wet", "Images/wets.png");

        ButtonGroup tyreGroup = new ButtonGroup();
        tyreGroup.add(SoftTyreButton);
        tyreGroup.add(MediumTyreButton);
        tyreGroup.add(HardTyreButton);
        tyreGroup.add(IntermediateTyreButton);
        tyreGroup.add(FullWetTyreButton);

        this.add(SoftTyreButton);
        this.add(MediumTyreButton);
        this.add(HardTyreButton);
        this.add(IntermediateTyreButton);
        this.add(FullWetTyreButton);

        SaveButton = new JButton("Save");
        SaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SaveButton.setBackground(new Color(0xF3FEB0)); // Change to desired hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SaveButton.setBackground(UIManager.getColor("control")); // Reset to default color
            }
        });
        SaveButton.addActionListener(this);
        this.add(SaveButton);

        CombineWindowButton = new JButton("Open Combination Window");
        CombineWindowButton.setFocusable(false);

        CombineWindowButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                CombineWindowButton.setBackground(new Color(0xF3FEB0)); // Change to desired hover color
            }

            @Override
            public void mouseExited(MouseEvent e) {
                CombineWindowButton.setBackground(UIManager.getColor("control")); // Reset to default color
            }
        });
        CombineWindowButton.addActionListener(this);
        this.add(CombineWindowButton);  

        this.pack();
        this.setVisible(true);

        CountData("DriverInfo.txt");
        CountData("TrackInfo.txt");
        CountData("CombinedInfo.txt");
    }

    private JRadioButton JRadioButtonWithImage(String label, String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JRadioButton button = new JRadioButton(label, icon);
        button.addActionListener(this);
        return button;
    }

    private String GetSelectedTyre() {
        if (SoftTyreButton.isSelected()) return "Soft";
        if (MediumTyreButton.isSelected()) return "Medium";
        if (HardTyreButton.isSelected()) return "Hard";
        if (IntermediateTyreButton.isSelected()) return "Intermediate";
        if (FullWetTyreButton.isSelected()) return "Full Wet";
        return "None";
    }

    private double ConvertTimeStringToSeconds(String Time) {
        String[] TimeParts = Time.split(":");
        double Minutes = Double.parseDouble(TimeParts[0]);
        double Seconds = Double.parseDouble(TimeParts[1]);
        return Minutes * 60 + Seconds;
    }

    private String ConvertSecondsToTimeString(double TotalSeconds) {
        int minutes = (int) (TotalSeconds / 60);
        double seconds = TotalSeconds % 60;
        return String.format("%d:%06.3f", minutes, seconds);
    }

    private String GetCalculateAverageTime(String q1, String q2, String q3) {
        double Q1Seconds = ConvertTimeStringToSeconds(q1);
        double Q2Seconds = ConvertTimeStringToSeconds(q2);
        double Q3Seconds = ConvertTimeStringToSeconds(q3);
    
        double AverageSeconds = (Q1Seconds + Q2Seconds + Q3Seconds) / 3;
    
        return ConvertSecondsToTimeString(AverageSeconds);
    }
    
    private void saveData() {
        String firstName = FirstNames.getText();
        String lastName = LastNames.getText();
        String selectedTeam = (String) TeamComboBox.getSelectedItem();
        String track = (String)TrackComboBox.getSelectedItem();
        String position = Position.getText();
        boolean eliminated = Eliminated.isSelected();
        String q1 = Q1.getText();
        String q2 = Q2.getText();
        String q3 = Q3.getText();
        String AverageTime = GetCalculateAverageTime(q1, q2, q3);
        String TyreCompound = GetSelectedTyre();

        File driverInfoFile = new File("DriverInfo.txt");
        File trackInfoFile = new File("TrackInfo.txt");

        try (BufferedWriter driverInfoWriter = new BufferedWriter(new FileWriter(driverInfoFile, true));
            BufferedWriter trackInfoWriter = new BufferedWriter(new FileWriter(trackInfoFile, true))) {

            if (driverInfoFile.length() == 0) {
                driverInfoWriter.write(String.format(
                    "%-" + space2 + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s\n", 
                    "ID", "First Name", "Last Name", "Team", "Track"));
            }
            driverInfoWriter.write(String.format(
                "%-" + space2 + "d%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s\n", currentID, firstName, lastName, selectedTeam, track));

            if (trackInfoFile.length() == 0) {
                trackInfoWriter.write(String.format(
                    "%-" + space2 + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space3 + "s%-" + space + "s%-" + space + "s%-" + space + "s\n", 

                    "ID", "Position", "Q1", "Q2", "Q3", "Average Lap Time", "Tyre Compound", "Eliminated"));
            }
            trackInfoWriter.write(String.format(
                "%-" + space2 + "d%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s\n", 
                currentID, position, q1, q2, q3,AverageTime, TyreCompound, eliminated ? "Yes" : "No"));

            JOptionPane.showMessageDialog(this, "Data Saved Successfully");
            currentID++; 
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }    

    private void CountData (String FileName){

        int RowCount = 0;
        try(BufferedReader Driverreader = new BufferedReader(new FileReader(FileName))) {

            Driverreader.readLine();


            while (Driverreader.readLine() != null){
                RowCount++;
            }

            JOptionPane.showMessageDialog(this, "Number of data in the " + FileName + " is: " + RowCount);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error counting rows: " + e.getMessage());
        }
    }
    


   @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SaveButton) {
            saveData();
            FirstNames.setText("");
            LastNames.setText("");
            Position.setText("");
            Q1.setText("");
            Q2.setText("");
            Q3.setText("");
            

        } else if (e.getSource() == CombineWindowButton) { 
           CombineWindow CombineWindow = new CombineWindow();
        } else {
           
            String tyreType = ((JRadioButton) e.getSource()).getText();
            listModel.addElement(tyreType + " Tyre");
            String SelectedTeam = (String) TeamComboBox.getSelectedItem();
        }
    }
}
