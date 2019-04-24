import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import javax.security.auth.login.LoginException;

public class TerminalMain extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "NTY5ODU5NTM1OTIyMjY2MTQy.XMCB-Q.HSSRhFKhipBPsCa0wjYyeuUoccc";
        JDA api = new JDABuilder(token).build();
        api.addEventListener(new Bot());
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        System.out.println(event.getMessage().getContentDisplay());
        if(event.getMessage().getContentRaw().equals("Ping")){
            event.getChannel().sendMessage("Pong").queue();
        }
    }
}
