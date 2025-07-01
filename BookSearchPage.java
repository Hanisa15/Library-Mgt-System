import javax.swing.*;

public class BookSearchPage {
    JFrame frame;
    JButton backBtn;

    public BookSearchPage() {
        frame = new JFrame("Book Search (Coming Soon)");

        JLabel label = new JLabel("Book search feature will be added.");
        label.setBounds(50, 50, 250, 30);

        backBtn = new JButton("Back");
        backBtn.setBounds(100, 100, 100, 30);

        frame.add(label);
        frame.add(backBtn);

        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.setVisible(true);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new StudentMenuPage();
        });
    }
}

