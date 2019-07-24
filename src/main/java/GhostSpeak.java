import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.requests.Route;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

public class GhostSpeak {
    public JPanel content;
    public JButton enterButton;
    public JTextArea textArea1;
    public JTree tree1;

    public GhostSpeak(){
        File serverList = new File("./ServerList.txt");
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
        enterButton.addActionListener(ActionEvent -> {
            String output = textArea1.getText();
            JDA builder = Main.getBuilder();
            List<Guild> guilds = builder.getGuilds();
            Guild targetGuild = null;
            TreePath serverPath = tree1.getSelectionPath();
            for(Guild a: guilds){
                if(a.getName().equals(serverPath.getPathComponent(1).toString())){
                    targetGuild = a;
                    break;
                }
            }
            TextChannel targetChannel = null;
            for(TextChannel a: targetGuild.getTextChannels()){
                String b = serverPath.getPathComponent(2).toString();
                b = b.replace("#", "");
                if(a.getName().equals(b)){
                    targetChannel=a;
                }
            }
            targetChannel.sendMessage(output).queue();
            textArea1.setText("");
            textArea1.updateUI();
        });
    }
    public JPanel getContent(){
        return content;
    }
}
