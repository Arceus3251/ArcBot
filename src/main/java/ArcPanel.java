import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArcPanel {
    private JPanel content;
    private JButton devPanelButton;
    private JList logList;
    private static DefaultListModel<String> logModel = new DefaultListModel<>();

    public ArcPanel(){
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
                    }
                });
                devFrame.add(ghostSpeak);
                devFrame.setSize(300,300);
                devFrame.setVisible(true);
                devFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
    }
    public JPanel getContent(){
        return content;
    }
}
