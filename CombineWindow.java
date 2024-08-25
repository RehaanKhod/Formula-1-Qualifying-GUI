import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class CombineWindow extends JFrame implements ActionListener {
    JComboBox DriverComboBox;
    JComboBox TrackComboBox;

    JButton CombineButton;
    JList CombinedList;

    DefaultListModel CombinedListModel;

    private static final int space = 20;
    private static final int space2 = 10;

    public CombineWindow() {
        this.setTitle("Formula 1 Qualifying JAVA Combination Frame");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        ImageIcon logo = new ImageIcon("Images/f1.png");
        this.setIconImage(logo.getImage());
        this.setPreferredSize(new Dimension(1650, 470));

        JPanel TopPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        TopPanel.add(new JLabel("  Driver Side Info"){{
            setForeground(new Color(0xFFFFFF)); 
        }});
        TopPanel.add(new JLabel("  Track Side Info"){{
            setForeground(new Color(0xFFFFFF)); 
        }});
        TopPanel.add(new JLabel(" ID       First Name          Last Name           Team                 Track"){{
            setForeground(new Color(0xFFFFFF)); 
        }});
        TopPanel.add(new JLabel("  ID       Pos                  Q1                        Q2                        Q3         Average Time      TyreCompound     Eliminated"){{
            setForeground(new Color(0xFFFFFF));   
        }});

        DriverComboBox = new JComboBox();
        TrackComboBox = new JComboBox();
        LoadData();

        DriverComboBox.addActionListener(this);
        TrackComboBox.addActionListener(this);

        TopPanel.add(DriverComboBox);
        TopPanel.add(TrackComboBox);

        this.add(TopPanel, BorderLayout.NORTH);

        /* */
        CombineButton = new JButton("Combine");

        CombineButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                CombineButton.setBackground(new Color(0xF3FEB0)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                CombineButton.setBackground(UIManager.getColor("control")); 
            }
        });
        
        CombineButton.addActionListener(this);
        this.add(CombineButton, BorderLayout.SOUTH);

        TopPanel.setBackground(new Color(0x616673));
        CombinedListModel = new DefaultListModel<>();
        CombinedList = new JList<>(CombinedListModel);
        CombinedList.setBackground(Color.lightGray);
        CombinedList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        CombinedListModel.addElement(String.format(
                "%-" + space2 + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space2 +
                        "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s",
                "ID", "First Name", "Last Name", "Team", "Track", "ID", "Position", "Q1", "Q2", "Q3",  "Average Time", "Tyre Compound", "Eliminated"));

        this.add(new JScrollPane(CombinedList), BorderLayout.CENTER);

        this.pack();
        this.setVisible(true);
    }

    private void LoadData() {
        try (BufferedReader DriverReader = new BufferedReader(new FileReader("DriverInfo.txt"));
             BufferedReader TrackReader = new BufferedReader(new FileReader("TrackInfo.txt"))) {

            String line;

            DriverReader.readLine();
            while ((line = DriverReader.readLine()) != null) {
                DriverComboBox.addItem(line);
            }

            TrackReader.readLine();
            while ((line = TrackReader.readLine()) != null) {
                TrackComboBox.addItem(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void ListDetails() {
        CombinedListModel.clear();

        CombinedListModel.addElement(String.format(
                "%-" + space2 + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space2 +
                        "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s",
                "ID", "First Name", "Last Name", "Team", "Track", "ID", "Position", "Q1", "Q2", "Q3",  "Average Time", "Tyre Compound", "Eliminated"));

        String DriverData = (String) DriverComboBox.getSelectedItem();
        String TrackData = (String) TrackComboBox.getSelectedItem();

        if (DriverData != null && TrackData != null) {
            CombinedListModel.addElement(DriverData + " " + TrackData);
        }
    }

    private void CombineData() {
        File CombinedFile = new File("CombinedInfo.txt");
        boolean FileExists = CombinedFile.exists();
    
        try (BufferedWriter CombinedWriter = new BufferedWriter(new FileWriter(CombinedFile, true))) {
            if (!FileExists) {
                CombinedWriter.write(String.format(
                        "%-" + space2 + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space2 +
                        "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s%-" + space + "s",
                "ID", "First Name", "Last Name", "Team", "Track", "ID", "Position", "Q1", "Q2", "Q3",  "Average Time", "Tyre Compound", "Eliminated"));
            }
    
            String DriverData = (String) DriverComboBox.getSelectedItem();
            String TrackData = (String) TrackComboBox.getSelectedItem();
    
            if (DriverData != null && TrackData != null) {
                CombinedWriter.write(DriverData + " " + TrackData + "\n");
                JOptionPane.showMessageDialog(this, "Files Combined Successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Please select both driver and track data.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error combining data: " + e.getMessage());
        }
    }
    



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CombineButton) {
            CombineData();
        } else if (e.getSource() == DriverComboBox || e.getSource() == TrackComboBox) {
            ListDetails();
        }
    }
}



