import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import javax.security.auth.login.LoginException;
import javax.swing.*;

public class Main extends ListenerAdapter {
    private static ArcPanel panelArc;
    public static void main(String[] args)throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        String token = "NTY5ODU5NTM1OTIyMjY2MTQy.XMBjkQ.siMoyWmcxtXntaH-KrNJ5qD_NaI";
        builder.setToken(token);
        builder.build();
        panelArc = new ArcPanel();
        JFrame frame = new JFrame("ArcBot");
        frame.setSize(300, 300);
        frame.setContentPane(panelArc.getContent());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        System.out.println("Message: "+event.getAuthor().getName()+": "+event.getMessage().getContentDisplay());
        panelArc.addLog(event.getMessage().getContentDisplay());
        panelArc.getContent().updateUI();
    }
}
