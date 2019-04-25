import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GhostSpeak {
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
        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                TreePath test = e.getPath();
                System.out.println(test);
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = textArea1.getText();
                System.out.println(output);
                textArea1.setText("");
                textArea1.updateUI();
            }
        });
    }
    public JPanel getContent(){
        return content;
    }
}
