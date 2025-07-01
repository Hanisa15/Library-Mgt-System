import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class BookEntryPanel extends JPanel {
    private JTextField[] bookCodeFields;
    private JLabel[] bookTitleLabels;
    private HashMap<String, String> bookMap;
    private Font labelFont = new Font("Times New Roman", Font.BOLD, 14);
    private Font fieldFont = new Font("Times New Roman", Font.PLAIN, 13);
    private Color kaler = Color.WHITE;

    public BookEntryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        loadBooksFromFile();
    }

    private void loadBooksFromFile() {
        bookMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Book Mgt.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", -1); //split 
                if (parts.length >= 3) {
                    String bookCode = parts[1].trim();//Baca Book Code
                    String bookTitle = parts[2].trim();//Baca Book Title
                    bookMap.put(bookCode, bookTitle);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load book list.");
        }
    }

    public void generateBookInputs(int count) {
        removeAll();
        bookCodeFields = new JTextField[count];
        bookTitleLabels = new JLabel[count];

        for (int i = 0; i < count; i++) {
            int index = i;

            JLabel codeLabel = new JLabel("Book Code [" + (index + 1) + "]:");
            codeLabel.setFont(labelFont);
            codeLabel.setForeground(kaler);

            JTextField codeField = new JTextField(12);
            codeField.setFont(fieldFont);
            codeField.setHorizontalAlignment(JTextField.CENTER);
            codeField.setMaximumSize(new Dimension(180, 28));

            codeField.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    String upper = codeField.getText().toUpperCase();
                    codeField.setText(upper);
                    String title = bookMap.getOrDefault(upper.trim(), "Not Found");
                    bookTitleLabels[index].setText(title);
                }
            });

            JLabel titleLabel = new JLabel("Title:");
            titleLabel.setFont(fieldFont);
            titleLabel.setForeground(kaler);

            JLabel titleText = new JLabel(" ");
            titleText.setFont(fieldFont);
            titleText.setForeground(kaler);
            titleText.setPreferredSize(new Dimension(200, 28));

            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            row.setOpaque(false);
            row.add(codeLabel);
            row.add(codeField);
            row.add(titleLabel);
            row.add(titleText);
            row.setAlignmentX(Component.CENTER_ALIGNMENT);
            row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

            bookCodeFields[index] = codeField;
            bookTitleLabels[index] = titleText;

            add(row);
        }

        revalidate();
        repaint();
    }

    public String[][] getBookEntries() {
        String[][] result = new String[bookCodeFields.length][2];
        for (int i = 0; i < bookCodeFields.length; i++) {
            result[i][0] = bookCodeFields[i].getText();
            result[i][1] = bookTitleLabels[i].getText();
        }
        return result;
    }
}
