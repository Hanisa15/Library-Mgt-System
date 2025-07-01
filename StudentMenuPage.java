import javax.swing.*;
import java.awt.*;

class BackgroundPanelStudent extends JPanel {
    private Image backgroundImage;

    public BackgroundPanelStudent(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}

public class StudentMenuPage {
    JFrame frame;
    JButton bookSearchBtn, studentInfoBtn, backBtn;

    public StudentMenuPage() {
        frame = new JFrame("Student Menu");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BackgroundPanelStudent backgroundPanel = new BackgroundPanelStudent("stbg.png");
        backgroundPanel.setLayout(new BorderLayout());
        //header
        JPanel header = UIComponents.createHeaderPanel(() ->{
            frame.dispose();
            new LibrarySystem();
        });
        backgroundPanel.add(header, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(null);
        contentPanel.setOpaque(false);
        

        bookSearchBtn = createStyledButton("Book Search");
        bookSearchBtn.setBounds(530, 220, 220, 40);

        studentInfoBtn = createStyledButton("Student Info");
        studentInfoBtn.setBounds(530, 338, 220, 40);

        backBtn = createStyledButton("Back");
        backBtn.setBounds(487, 455, 220, 40);

        backgroundPanel.add(bookSearchBtn);
        backgroundPanel.add(studentInfoBtn);
        backgroundPanel.add(backBtn);
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        frame.setContentPane(backgroundPanel);
        frame.setSize(400, 280);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        bookSearchBtn.addActionListener(e -> {
            frame.dispose();
            new BookSearchPage();
        });

        studentInfoBtn.addActionListener(e -> {
            String studentID = JOptionPane.showInputDialog(frame, "Enter your student id:");
            if (studentID != null && !studentID.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Your ID: " + studentID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid ID!");
                }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new LibrarySystem();
        });
    }

    private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setOpaque(false);                      // Make it transparent
    button.setContentAreaFilled(false);           // No background fill
    button.setBorderPainted(false);               // No border
    button.setForeground(Color.BLACK);            // Black text
    button.setFont(new Font("Segoe UI", Font.PLAIN, 30));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Optional hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() 
    {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setForeground(Color.BLUE);     // Hover color
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setForeground(Color.BLACK);    // Default color
        }
    });

    return button;
    }

}



