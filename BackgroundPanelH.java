import javax.swing.*;
import java.awt.*;

public class BackgroundPanelH extends JPanel {
    private Image bgImage;

    public BackgroundPanelH(String imagePath) {
        this.bgImage = new ImageIcon(imagePath).getImage();
        setLayout(new BorderLayout()); // So we can add components in positions
    }
    
    public void addForm(JPanel formPanel){
        add(formPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
/*
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private Image bgImage;
    private boolean blurEnabled = false;
    private int parallaxOffset = 0;

    public BackgroundPanel(String imagePath) {
        setLayout(new BorderLayout());
        loadImage(imagePath);
    }

    private void loadImage(String imagePath) {
        try {
            this.bgImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Background image not found: " + imagePath);
        }
    }

    public void addForm(JPanel formPanel) {
        add(formPanel, BorderLayout.CENTER);
    }

    public void setBlurEnabled(boolean enabled) {
        this.blurEnabled = enabled;
        repaint();
    }

    public void setParallaxOffset(int offset) {
        this.parallaxOffset = offset;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // 1. Fallback gradient if image fails
        if (bgImage == null) {
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(30, 60, 90),
                getWidth(), getHeight(), new Color(10, 20, 30)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            return;
        }

        // 2. Draw background image (with parallax)
        int x = parallaxOffset / 2; // Adjust divisor for effect strength
        g2d.drawImage(bgImage, 
            x, 0, getWidth(), getHeight(),
            x, 0, 
            x + bgImage.getWidth(null), bgImage.getHeight(null),
            this
        );

        // 3. Dark overlay for readability
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
*/