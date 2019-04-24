import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ArcCore extends ListenerAdapter {
    public String message = "Test";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Main.panelArc.addLog(message = (event.getAuthor().getName() + " in " + event.getChannel() + ": " + event.getMessage().getContentDisplay()));
    }
}

