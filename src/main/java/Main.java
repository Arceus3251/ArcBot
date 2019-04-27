import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Main extends ListenerAdapter {
    static ArcPanel panelArc;
    public static void main(String[] args)throws LoginException {
        String token = "NDg3Njk2MDMxNDE3NDk5NjQ5.XMS8ZA.qCaDkk6l8EWkx4frYG2W8MCjkDA";
        JDA builder = new JDABuilder(token).build();
        JFrame frame = new JFrame("ArcBot");
        panelArc = new ArcPanel();
        frame.setContentPane(panelArc.getContent());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        builder.addEventListener(new ArcCore());
    }
}
