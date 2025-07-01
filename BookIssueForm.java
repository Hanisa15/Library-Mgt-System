import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BookIssueForm extends JPanel implements ActionListener {
    private JTextField stdID, stdName, borrowDate, returnDate;
    private JTextField numberOfBooksField;
    private JButton enterBtn;
    private JButton bckBtn;
    private BookEntryPanel bookPanel;
    private JFrame parentFrame;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BookIssueForm(JFrame parent) {
        this.parentFrame = parent;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        Font fontLabel = new Font("Times New Roman", Font.BOLD, 16);
        Font fontField = new Font("Times New Roman", Font.PLAIN, 15);
        Font fontButton = new Font("Times New Roman",  Font.BOLD, 16);
        Color labelColor = Color.WHITE;

        stdID = new JTextField(); stdID.setFont(fontField);
        stdName = new JTextField(); stdName.setFont(fontField);
        borrowDate = new JTextField(LocalDate.now().format(formatter)); borrowDate.setFont(fontField);
        returnDate = new JTextField(); returnDate.setEditable(false); returnDate.setFont(fontField);

        add(createRow("Student ID:", stdID, fontLabel, fontField, labelColor));
        add(createRow("Student Name:", stdName, fontLabel, fontField, labelColor));
        add(createRow("Borrow Date [DD-MM-YYYY]:", borrowDate, fontLabel, fontField, labelColor));
        add(createRow("Return Date:", returnDate, fontLabel, fontField, labelColor));

        numberOfBooksField = new JTextField(5);
        numberOfBooksField.setFont(fontField);
        JButton generateBtn = ImageButtonFactory.createImageButton("search_icon.jpeg", 24, 24, "Generate Book Fields");

        bookPanel = new BookEntryPanel();
        add(createRowWithButton("How many books to borrow:", numberOfBooksField, generateBtn, fontLabel, fontField, labelColor));
        add(bookPanel);

        generateBtn.addActionListener(e -> {
            try {
                int count = Integer.parseInt(numberOfBooksField.getText().trim());
                if (count <= 0) throw new NumberFormatException();
                bookPanel.generateBookInputs(count);
                bookPanel.revalidate();
                bookPanel.repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        });

        enterBtn = new JButton("Enter");
        enterBtn.setFont(fontButton);
        enterBtn.setForeground(Color.WHITE);
        enterBtn.setBackground(new Color(0, 102, 204));
        enterBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        enterBtn.addActionListener(this);

        bckBtn = new JButton("Back");
        bckBtn.setFont(fontButton);
        bckBtn.setForeground(Color.WHITE);
        bckBtn.setBackground(new Color(150, 150, 150));
        bckBtn.setFocusPainted(false);
        bckBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        bckBtn.addActionListener(this);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        btnPanel.setOpaque(false);
        bckBtn.setPreferredSize(new Dimension(100, 30));
        enterBtn.setPreferredSize(new Dimension(100, 30));
        btnPanel.add(bckBtn);
        btnPanel.add(enterBtn);
        add(btnPanel);

        borrowDate.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                updateReturnDate();
            }
        });
    }

    private JPanel createRow(String labelText, JTextField field, Font labelFont, Font fieldFont, Color labelColor) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(labelColor);

        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(250, 25));
        field.setHorizontalAlignment(JTextField.CENTER);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row.setOpaque(false);
        row.add(label);
        row.add(field);
        row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        return row;
    }

    private JPanel createRowWithButton(String labelText, JTextField field, JButton button, Font labelFont, Font fieldFont, Color labelColor) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(labelColor);

        field.setFont(fieldFont);
        field.setPreferredSize(new Dimension(100, 25));
        field.setHorizontalAlignment(JTextField.CENTER);

        button.setPreferredSize(new Dimension(28, 28));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
        row.setOpaque(false);
        row.add(label);
        row.add(field);
        row.add(button);
        row.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
        return row;
    }

    private void updateReturnDate() {
        try {
            LocalDate borrow = LocalDate.parse(borrowDate.getText(), formatter);
            LocalDate ret = borrow.plusDays(6);
            returnDate.setText(ret.format(formatter));
        } catch (Exception e) {
            returnDate.setText("Invalid Date");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterBtn) {
            StringBuilder bookInfo = new StringBuilder();
            String[][] entries = bookPanel.getBookEntries();

            for (int i = 0; i < entries.length; i++) {
                bookInfo.append("Book ").append(i + 1).append(": ")
                        .append(entries[i][0]).append(" - ")
                        .append(entries[i][1]).append("\n");
            }

            String info = "Student ID: " + stdID.getText()
                    + "\nStudent Name: " + stdName.getText()
                    + "\nBorrow Date: " + borrowDate.getText()
                    + "\nReturn Date: " + returnDate.getText()
                    + "\n\nBorrowed Books:\n" + bookInfo.toString();

            JOptionPane.showMessageDialog(this, "Issued:\n" + info);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("Book Issue Record.txt", true));
                bw.write("STUDENT_ID: " + stdID.getText()); bw.newLine();
                bw.write("NAME: " + stdName.getText()); bw.newLine();
                bw.write("BORROW_DATE: " + borrowDate.getText()); bw.newLine();
                bw.write("RETURN_DATE: " + returnDate.getText()); bw.newLine();

                for (int i = 0; i < entries.length; i++) {
                    bw.write("BOOK_" + (i + 1) + ": " + entries[i][0] + " - " + entries[i][1]);
                    bw.newLine();
                }
                bw.write("---");
                bw.newLine();
                bw.newLine();
                bw.close();

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving to BookIssue.txt: " + ex.getMessage());
                return;
            }

            parentFrame.setVisible(false);
            JFrame returnFrame = new JFrame("BOOK RETURN SYSTEM");
            returnFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            returnFrame.setSize(700, 400);
            returnFrame.setLocationRelativeTo(null);

            returnFrame.setContentPane(new ReturnBookPanel(() -> {
                returnFrame.setVisible(false);
                parentFrame.setVisible(true);
            }));
            returnFrame.setVisible(true);

        } else if (e.getSource() == bckBtn) {
            SwingUtilities.getWindowAncestor(this).dispose();
            //enter the prev GUI from Iwani nanti
        }
        updateReturnDate();
    }
}
