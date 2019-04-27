import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ArcPanel extends ListenerAdapter {
    private JPanel content;
    private JButton devPanelButton;
    private JList<String> logList;
    private static DefaultListModel<String> logModel = new DefaultListModel<>();
    public ArcPanel() throws LoginException {
        logList.setModel(logModel);
        devPanelButton.addActionListener(ActionEvent -> {
            JFrame devFrame = new JFrame("Developer Panel");
            JButton ghostSpeak = new JButton("Ghost Talk");
            ghostSpeak.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame ghostSpeakFrame = new JFrame("Ghost Speak");
                    ghostSpeakFrame.setContentPane(new GhostSpeak().getContent());
                    ghostSpeakFrame.setVisible(true);
                    ghostSpeakFrame.setSize(300, 300);
                    ghostSpeakFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                }
            });
            devFrame.add(ghostSpeak);
            devFrame.setSize(300, 300);
            devFrame.setVisible(true);
            devFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });
    }

    public void addLog(String message) {
        logModel.addElement(message);
        if(logModel.size()>200){
            logModel.remove(0);
        }
    }

    public JPanel getContent() {
        return content;
    }
}
