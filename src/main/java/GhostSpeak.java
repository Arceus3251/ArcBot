import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GhostSpeak {
    public JPanel content;
    public JButton enterButton;
    public JTextArea textArea1;
    public JTree tree1;

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
                while(line.startsWith("#")){
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
        tree1.addTreeSelectionListener(TreeSelectionEvent -> {
            TreePath test = TreeSelectionEvent.getPath();
            System.out.println(test);
        });
        enterButton.addActionListener(ActionEvent -> {
            String output = textArea1.getText();
            System.out.println(output);
            textArea1.setText("");
            textArea1.updateUI();
        });
    }
    public JPanel getContent(){
        return content;
    }
}
