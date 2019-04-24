import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Main{
    public static void main(String[] args)throws LoginException {
        JFrame frame = new JFrame("ArcBot");
        frame.setSize(300, 300);
        frame.setContentPane(new ArcPanel().getContent());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
