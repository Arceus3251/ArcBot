import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ArcPanel extends ListenerAdapter {
    private JPanel content;
    private JButton devPanelButton;
    private JList logList;
    private static DefaultListModel<String> logModel = new DefaultListModel<>();
    public ArcPanel() throws LoginException {
        logList.setModel(logModel);
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTY5ODU5NTM1OTIyMjY2MTQy.XMBjkQ.siMoyWmcxtXntaH-KrNJ5qD_NaI";
        builder.setToken(token);
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
        builder.build();
    }
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
        logModel.addElement(event.getMessage().getContentRaw());
        logList.setModel(logModel);
        logList.updateUI();
    }
    public JPanel getContent(){
        return content;
    }
}
