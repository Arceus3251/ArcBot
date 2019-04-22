import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GhostSpeak {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setContentPane(new GhostSpeak().getContent());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    private JPanel content;
    private JButton enterButton;
    private JTextArea textArea1;
    private JTree tree1;

    public GhostSpeak(){
        File serverList = new File("src/main/ServerList");
        BufferedReader reader;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");
        try {
            reader = new BufferedReader(new FileReader(serverList));
            String line = reader.readLine();
            while (line != null) {
                DefaultMutableTreeNode serverName = new DefaultMutableTreeNode(line);
                line = reader.readLine();
                while(line.startsWith("-")){
                    DefaultMutableTreeNode channelName = new DefaultMutableTreeNode(line);
                    serverName.add(channelName);
                    line = reader.readLine();
                    if(line==null){
                        break;
                    }
                }
                top.add(serverName);
            }
            reader.close();
        }
        catch(IOException ex) {
            System.out.println("That ain't it, chief");
        }
        DefaultTreeModel tree1Model = new DefaultTreeModel(top, true);
        tree1.setModel(tree1Model);
    }
    public JPanel getContent(){
        return content;
    }
}
