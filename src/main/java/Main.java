import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Main extends ListenerAdapter {
    public static ArcPanel panelArc;
    private static String token = "";
    private static JDA builder;
    public static void main(String[] args)throws LoginException {
        JFrame frame = new JFrame("ArcBot");
        builder = new JDABuilder(token).build();
        panelArc = new ArcPanel();
        frame.setContentPane(panelArc.getContent());
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        builder.addEventListener(new ArcCore());
    }
    public static JDA getBuilder(){
        return builder;
    }
}
