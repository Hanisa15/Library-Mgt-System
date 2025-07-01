import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.Font;

public class BookIssueFrame extends JFrame
{
   public BookIssueFrame(){
       //JFrame = a GUI window to add components to
       setTitle("BOOK ISSUE SYSTEM");
       setFont(new Font("Times New Roman", Font.PLAIN, 25));
       setSize(900,600);
       setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
       setLocationRelativeTo(null);//Center this window on the screen
       
       ImageIcon img = new ImageIcon("Agile Access.png");//create and Image Icon
       setIconImage(img.getImage());//change icon of frame
       
       BookIssueForm form = new BookIssueForm(this); //pass this frame to BookIssueForm
       BookIssuePanel panel = new BookIssuePanel("AA bg.jpeg");//image bg 
       panel.addForm(form);
       setContentPane(panel);
       setVisible(true);
       
       
   }
}
