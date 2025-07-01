import javax.swing.*;
import java.awt.*;

public class BookIssuePanel extends JPanel {
    private Image background;

    public BookIssuePanel(String imagePath) {
        background = new ImageIcon(imagePath).getImage();
        setLayout(new BorderLayout());
        add(createTopBar(), BorderLayout.NORTH);
    }

    public void addForm(JPanel formPanel) {
        BorderLayout layout = (BorderLayout) getLayout();
        Component oldCenter = layout.getLayoutComponent(BorderLayout.CENTER);
        if (oldCenter != null) {
            remove(oldCenter);
        }

        JPanel w = new JPanel();
        w.setOpaque(false);
        w.setLayout(new GridLayout());
        w.add(formPanel);
        add(w, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel();
        topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        topBar.setBackground(new Color(36, 42, 63));
        topBar.setPreferredSize(new Dimension(0, 110));

        ImageIcon logo = new ImageIcon("Agile Access.png");
        Image image = logo.getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(image));

        JPanel leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BoxLayout(leftTextPanel, BoxLayout.Y_AXIS));
        leftTextPanel.setBackground(new Color(36, 42, 63));

        JLabel line1 = new JLabel("AGILE ACCESS LIBRARY");
        line1.setFont(new Font("Serif", Font.BOLD, 18));
        line1.setForeground(Color.WHITE);

        JLabel line2 = new JLabel("MANAGEMENT SYSTEM");
        line2.setFont(new Font("Serif", Font.BOLD, 18));
        line2.setForeground(Color.WHITE);

        leftTextPanel.add(line1);
        leftTextPanel.add(line2);

        JLabel bookIssueLabel = new JLabel(" || BOOK ISSUE SYSTEM");
        bookIssueLabel.setFont(new Font("Serif", Font.BOLD, 36));
        bookIssueLabel.setForeground(Color.WHITE);
        bookIssueLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

        JPanel textWrapper = new JPanel();
        textWrapper.setLayout(new BoxLayout(textWrapper, BoxLayout.X_AXIS));
        textWrapper.setBackground(new Color(36, 42, 63));
        textWrapper.add(leftTextPanel);
        textWrapper.add(bookIssueLabel);

        topBar.add(logoLabel);
        topBar.add(textWrapper);

        return topBar;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}