import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

public class TerminalMain extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = "NTY5ODU5NTM1OTIyMjY2MTQy.XMCB-Q.HSSRhFKhipBPsCa0wjYyeuUoccc";
        JDA builder = new JDABuilder(token).build();
        builder.addEventListener(new MyListener());
    }
}
