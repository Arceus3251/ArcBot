import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ArcPanel extends ListenerAdapter {
    private JPanel content;
    private JButton devPanelButton;
    private JList<String> logList;
    private static DefaultListModel<String> logModel = new DefaultListModel<>();
    public ArcPanel() throws LoginException {
        logList.setModel(logModel);
        devPanelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }

    public void addLog(String message) {
        logModel.addElement(message);
        logList.updateUI();
    }

    public JPanel getContent() {
        return content;
    }
}
