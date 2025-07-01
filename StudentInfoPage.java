import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class StudentInfoPage {
    JFrame frame;
    JTextField idField;
    JTextArea infoArea;
    JButton searchBtn, backBtn;

    public StudentInfoPage() {
        frame = new JFrame("Student Info");

        JLabel idLabel = new JLabel("Enter Student ID:");
        idLabel.setBounds(30, 20, 150, 30);

        idField = new JTextField();
        idField.setBounds(150, 20, 150, 30);

        searchBtn = new JButton("Search");
        searchBtn.setBounds(30, 60, 100, 30);

        backBtn = new JButton("Back");
        backBtn.setBounds(200, 60, 100, 30);

        infoArea = new JTextArea();
        infoArea.setBounds(30, 100, 270, 150);
        infoArea.setEditable(false);

        frame.add(idLabel);
        frame.add(idField);
        frame.add(searchBtn);
        frame.add(backBtn);
        frame.add(infoArea);

        frame.setSize(350, 320);
        frame.setLayout(null);
        frame.setVisible(true);

        searchBtn.addActionListener(e -> {
            String studentID = idField.getText();
            try {
                File file = new File("studentdata.txt");  // Replace with actual data file
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                boolean found = false;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(studentID)) {
                        infoArea.setText("Student Info:\n" + line);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    infoArea.setText("Student ID not found.");
                }
                br.close();
            } catch (IOException ex) {
                infoArea.setText("Error reading file.");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new StudentMenuPage();
        });
    }
}

