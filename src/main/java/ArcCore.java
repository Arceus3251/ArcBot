import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ArcCore extends ListenerAdapter {
    public String message = "Test";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Main.panelArc.addLog(message = (event.getAuthor().getName() + " in " + event.getChannel() + ": " + event.getMessage().getContentDisplay()));
        if (event.getMessage().getContentRaw().startsWith("Arc")) {
            String received = event.getMessage().getContentRaw();
            received = received.replaceFirst("Arc", "");
            if (received.equals("Help")) {
                event.getChannel().sendMessage("Hi! I'm ArcBot! A discord bot written in Java by Arceus3251. This command is currently under development, so please check back at a later time for when it will be available. If you have any questions or suggestions please DM Arceus3251#3251.").queue();
            }
            if (received.equals("Ping")) {
                if (event.getAuthor().getId().equals("239598274103738369")) {
                    event.getChannel().sendMessage("I work, you dumbfuck").queue();
                } else {
                    event.getChannel().sendMessage("Pong!").queue();
                }
            }
            if(received.equals("Time")){
                event.getChannel().sendMessage("According to my sources, it is currently " +sdf.format(cal.getTime())+ " CST").queue();
            }
            if (received.equals("Test")) {
                event.getChannel().sendMessage("Test successful! Hey, " +event.getAuthor().getName()+ "!").queue();
            }
            if (received.equals("Shutdown")) {
                if (event.getAuthor().getId().equals("239598274103738369")) {
                    event.getChannel().sendMessage("Thank you for using ArcBot!").queue();
                    System.exit(1);
                } else {
                    event.getChannel().sendMessage("Only Arceus3251 can perform that command").queue();
                }
            }
            if (received.startsWith("Sayd")) {
                if (event.getAuthor().getName().equals("ArcBot")) {
                    return;
                } else {
                    received = received.replaceFirst("Sayd ", "");
                    if (!event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                        event.getChannel().sendMessage("Sorry! I don't have the permissions to perform that command! Contact @Arceus3251#3251 for more information!").queue();
                    } else {
                        event.getMessage().delete().queue();
                        event.getChannel().sendMessage(received).queue();
                    }
                }
            }
            if (received.equals("Summon")){
                received = received.replace("Summon","");
                VoiceChannel myChannel = event.getMember().getVoiceState().getChannel();
                audioManager.openAudioConnection(myChannel);
            }
            if (received.equals("Leave")){
                audioManager.closeAudioConnection();
                event.getChannel().sendMessage("I have left your voice channel").queue();
            }
            if (received.equals("Flip")) {
                int coin = (int) (Math.random() * 2) + 1;
                if (coin == 1) {
                    event.getChannel().sendMessage("Heads").queue();
                }
                if (coin == 2) {
                    event.getChannel().sendMessage("Tails").queue();
                }
            }
            //Conversion Clusterfuck
            //To-Do Key: Text = 0; Binary = 1; Hex = 2; Decimal = 3;
            if (received.startsWith("Convert ")) {
                String output = "";
                int todo = -1;
                received = received.replaceFirst("Convert ", "");

                //Input is Binary
                if (received.startsWith("Binary ")) {
                    received = received.replaceFirst("Binary ", "");
                    if (received.startsWith("Text ")) {
                        todo = 0;
                        received = received.replaceFirst("Text ", "");
                    }
                    if (received.startsWith("Hex ")) {
                        todo = 2;
                        received = received.replaceFirst("Hex ", "");
                    }
                    if (received.startsWith("Decimal ")) {
                        todo = 3;
                        received = received.replaceFirst("Decimal ", "");
                    }
                    output = BinText(received, todo);
                }
                //Input is Raw Text
                if (received.startsWith("Text ")) {
                    received = received.replaceFirst("Text ", "");
                    if (received.startsWith("Binary")) {
                        received = received.replaceFirst("Binary ", "");
                        output = TextBin(received);
                    }
                    if (received.startsWith("Hex ")) {
                        received = received.replaceFirst("Hex ", "");
                        output = TextHex(received);
                    }
                    if (received.startsWith("Decimal ")) {
                        received = received.replaceFirst("Decimal ", "");
                        output = TextDec(received);
                    }
                    if (received.startsWith("Morse ")) {
                        received = received.replaceFirst("Morse ", "");
                        output = TextMorse(received);
                    }
                }
                //Input is Hexadecimal
                if (received.startsWith("Hex ")) {
                    received = received.replaceFirst("Hex ", "");
                    if (received.startsWith("Text ")) {
                        received = received.replaceFirst("Text ", "");
                        todo = 0;
                    }
                    if (received.startsWith("Binary ")) {
                        todo = 1;
                        received = received.replaceFirst("Binary ", "");
                    }
                    if (received.startsWith("Decimal ")) {
                        todo = 3;
                        received = received.replaceFirst("Decimal ", "");
                    }
                    output = HexText(received, todo);
                }
                //Input is Decimal
                if (received.startsWith("Decimal ")) {
                    received = received.replaceFirst("Decimal ", "");
                    if (received.startsWith("Text ")) {
                        todo = 0;
                        received = received.replaceFirst("Text ", "");
                    }
                    if (received.startsWith("Binary ")) {
                        todo = 1;
                        received = received.replaceFirst("Binary ", "");
                    }
                    if (received.startsWith("Hex ")) {
                        todo = 2;
                        received = received.replaceFirst("Hex ", "");
                    }
                    output = DecText(received, todo);
                }
                event.getChannel().sendMessage(output).queue();
            }
            //DiceRolls
            if (received.startsWith("Roll")) {
                boolean building = false;
                String input = received.replace("Roll ", "");
                if(input.startsWith("building")){
                    input = input.replace("building ","");
                    building = true;
                }
                StringBuilder TotalOutput = new StringBuilder();
                int total = 0;
                String output = new String();
                if(input.contains(" + ")){
                    String inArr[] = input.split("\\+");
                    for(String a:inArr){
                        a = a.replace(" ", "");
                        String[] numbers = a.split("d");
                        int dice = Integer.parseInt(numbers[0]);
                        int sides = Integer.parseInt(numbers[1]);
                        int[] arr;
                        int x = 0;
                        int i;
                        arr = new int[dice];
                        output = "(";
                        while(dice>0){
                            dice--;
                            i = (int)(Math.random()*sides)+1;
                            total += i;
                            arr[x] = i;
                            x++;
                        }
                        for (int b = 0; b< arr.length-1; b++){
                            output += (arr[b] + ", ");
                        }
                        output += (arr[arr.length-1]);
                        output += ")";
                        TotalOutput.append(output+ " + ");
                    }
                }
                else{
                    String[] numbers = input.split("d");
                    int dice = Integer.parseInt(numbers[0]);
                    int sides = Integer.parseInt(numbers[1]);
                    int[] arr;
                    int x = 0;
                    int i;
                    arr = new int[dice];
                    output = "(";
                    while(dice>0){
                        dice--;
                        i = (int)(Math.random()*sides)+1;
                        total += i;
                        arr[x] = i;
                        x++;
                    }
                    for (int b = 0; b< arr.length-1; b++){
                        output += (arr[b] + ", ");
                    }
                    output += (arr[arr.length-1]);
                    output += ")";
                    TotalOutput.append(output);
                    if(building){
                        TotalOutput = TotalOutput.replace(0,1,"");
                        TotalOutput = TotalOutput.replace(TotalOutput.length()-1, TotalOutput.length(), "");
                        for(String c: TotalOutput.toString().split(", ")){
                            if(Integer.parseInt(c)==sides){
                                boolean max = true;
                                while(max){
                                    i = (int)(Math.random()*sides)+1;
                                    if(i!=sides){
                                        max = !max;
                                    }
                                    total += i;
                                }
                            }
                        }
                    }
                }
                if(TotalOutput.lastIndexOf(" + ") == TotalOutput.length()-3){
                    TotalOutput.replace((TotalOutput.length()-3),(TotalOutput.length()), "");
                }
                event.getChannel().sendMessage(TotalOutput + " Total: " + total).queue();
            }
        }
        //Reactions
        if (event.getMessage().getContentRaw().equalsIgnoreCase("Nani")) {
            if (event.getAuthor().getId().equals("239598274103738369")) {
                event.getChannel().sendMessage("https://i.imgur.com/okVleUj.jpg").queue();
            }
            if (event.getAuthor().getId().equals("365700264843280394")) {
                event.getChannel().sendMessage("https://i.imgur.com/okVleUj.jpg").queue();
            }
            if (event.getAuthor().getId().equals("312087474073632769")) {
                event.getChannel().sendMessage("https://i.imgur.com/okVleUj.jpg").queue();
            }
            if (event.getAuthor().getId().equals("433994095778594816")) {
                event.getChannel().sendMessage("https://i.imgur.com/okVleUj.jpg").queue();
            }
        }
        if (event.getMessage().getContentRaw().equalsIgnoreCase("inan")) {
            event.getChannel().sendMessage("It doesn't work like that, " + event.getAuthor().getName()).queue();
        }
        if (event.getMessage().getContentRaw().equals("F")) {
            if (event.getAuthor().isBot()) {
                return;
            } else {
                event.getChannel().sendMessage("F").queue();
            }
        }
        if (event.getMessage().getContentRaw().equals("QwQ")) {
            if (event.getAuthor().getId().equals("476105194539712513")) {
                event.getChannel().sendMessage("<:MyaQwQ:514864285545922584>").queue();
            }
        }
        if (event.getMessage().getContentRaw().equals("->slap <@487696031417499649>")) {
            event.getChannel().sendMessage("Ow ;-;").queue();
        }
        if (event.getMessage().getContentRaw().equals("Is this the Krusty Krab?")) {
            if (event.getAuthor().getId().equals("242111856259366913")) {
                event.getChannel().sendMessage("***No, this is Momo***").queue();
            } else {
                event.getChannel().sendMessage("No, this is Patrick.").queue();
            }
        }
        if (event.getMessage().getContentRaw().equals("What's up?")) {
            event.getChannel().sendMessage("According to Wikipedia.com: Up is a 2009 Pixar film. https://en.wikipedia.org/wiki/Up_(2009_film)").queue();
        }
        if (event.getMessage().getContentRaw().equals("Send nudes")) {
            event.getChannel().sendMessage("No, send dunes, I wanna see them sexy landforms").queue();
        }
        if (event.getGuild().getName().equals("~The Harmonoids~")) {
            if (event.getMessage().getContentRaw().startsWith("+suggest")) {
                String input = event.getMessage().getContentRaw();
                String suggestion = input.replace("+suggest", "");
                event.getMessage().delete().queue();
                event.getGuild().getTextChannelById("496215882960338945").sendMessage(event.getAuthor().getName() + " suggests: " + suggestion).queue();
            }
        }
        //Commands only for DnD Server.
        if (event.getGuild().getName().equals("D&D Plaza")) {
            if (event.getMessage().getContentRaw().equals("1")) {
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/375004521212149772/490557385320955915/32875754_2091003187856028_8205528177125097472_n.png").queue();
            }
            if (event.getMessage().getContentRaw().equals("20")) {
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/375004521212149772/490557407756288031/tumblr_inline_mqrh26jIU11qz4rgp.jpg").queue();
            }
            if (event.getMessage().getContentRaw().equals("10")) {
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/488083894088761345/491763358056448007/image0.jpg").queue();
            }
            if (event.getMessage().getContentRaw().equalsIgnoreCase("lol")) {
                event.getChannel().sendMessage("http://img0.liveinternet.ru/images/attach/c/10/111/18/111018368_RRyoSRR__SRRRRRSRRSSRRSRyo_RRyoSRRS.gif").queue();
            }

        }
        if (event.getGuild().getName().equals("Dungeons and Dickholes")){
            if(event.getMessage().getContentRaw().contains("= (1)")){
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/375004521212149772/490557385320955915/32875754_2091003187856028_8205528177125097472_n.png").queue();
            }
            if(event.getMessage().getContentRaw().contains("= (20)")){
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527532487329710111/completely-erect-jenkins-im-back-from-my-long-sabbatical-and-3956744-picsay.png").queue();
            }
            if(event.getMessage().getContentRaw().contains("= (2)")){
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527212607073943553/2.jpg").queue();
            }
            if(event.getMessage().getContentRaw().contains("= (19)")){
                event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527567698415190027/i-have-the-weirdest-boner.jpg").queue();
            }
        }
    }
    //Conversion Functions
    //To-do Key: 0 = Text; 1 = Binary; 2 = Hexadecimal; 3 = Decimal;

    //Changes Binary to Text
    private static String BinText(String received, int todo) {
        StringBuilder outputBin = new StringBuilder();
        char nextChar;
        for (int i = 0; i <= received.length() - 8; i += 9) {
            nextChar = (char) Integer.parseInt(received.substring(i, i + 8), 2);
            outputBin.append(nextChar);
        }
        String output = outputBin.toString();
        switch (todo) {
            case 2:
                received = output;
                output = TextHex(received);
                break;
            case 3:
                received = output;
                output = TextDec(received);
                break;
            default:
                break;
        }
        return output;
    }

    //Changes Hexadecimal to Text
    private static String HexText(String received, int todo) {
        String outputHex = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < received.length(); i += 2) {
            String str = received.substring(i, i + 2);
            sb.append((char) Integer.parseInt(str, 16));
        }
        switch (todo) {
            case 0:
                outputHex = sb.toString();
                break;
            case 1:
                received = sb.toString();
                outputHex = TextBin(received);
                break;
            case 3:
                received = sb.toString();
                outputHex = TextDec(received);
                break;
        }
        return outputHex;
    }

    //Changes Decimal to Text
    private static String DecText(String received, int todo){
        String output = "";
        StringBuilder sb = new StringBuilder();
        while(received.contains(" ")){
            String[] parts = received.split(" ", 2);
            String part1 = parts[0];
            String input3 = parts[1];
            int input2 = Integer.parseInt(part1);
            char ch=(char)input2;
            sb.append(ch);
            received = input3;
        }
        int input2 = Integer.parseInt(received);
        char ch =(char)input2;
        sb.append(ch);
        switch(todo){
            case 0:
                output = sb.toString();
                break;
            case 1:
                received = sb.toString();
                output = TextBin(received);
                break;
            case 2:
                received = sb.toString();
                output = TextHex(received);
                break;
        }
        return output;
    }
    //Changes Text to Binary
    private static String TextBin(String received){
        byte[] bytes = received.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes){
            int val = b;
            for (int i = 0; i<8;i++){
                binary.append((val&128)==0?0:1);
                val<<=1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }
    //Changes Text to Hexadecimal
    private static String TextHex(String received){
        int length = received.length()*2;
        return String.format("%"+length+"x", new BigInteger(1,received.getBytes()));
    }
    //Changes Text to Decimal
    private static String TextDec(String received){
        StringBuilder decimal = new StringBuilder();
        int length = received.length();
        int i = 0;
        while (length>0){
            length--;
            decimal.append((int) received.charAt(i));
            decimal.append(' ');
            i++;
        }
        return decimal.toString();
    }
    //Changes Text to Morse
    private static String TextMorse(String received) {
        String outputMorse;
        StringBuilder sb = new StringBuilder();
        String input = received.toLowerCase();
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char check = charArray[i];
            switch (check) {
                case 'a':
                    sb.append(".- ");
                    break;
                case 'b':
                    sb.append("-... ");
                    break;
                case 'c':
                    sb.append("-.-. ");
                    break;
                case 'd':
                    sb.append("-.. ");
                    break;
                case 'e':
                    sb.append(" ");
                    break;
                case 'f':
                    sb.append("..-. ");
                    break;
                case 'g':
                    sb.append("--. ");
                    break;
                case 'h':
                    sb.append(".... ");
                    break;
                case 'i':
                    sb.append("build ");
                    break;
                case 'j':
                    sb.append(".--- ");
                    break;
                case 'k':
                    sb.append("-.- ");
                    break;
                case 'l':
                    sb.append(".-.. ");
                    break;
                case 'm':
                    sb.append("-- ");
                    break;
                case 'n':
                    sb.append("-. ");
                    break;
                case 'o':
                    sb.append("--- ");
                    break;
                case 'p':
                    sb.append(".--. ");
                    break;
                case 'q':
                    sb.append("--.- ");
                    break;
                case 'r':
                    sb.append(".-. ");
                    break;
                case 's':
                    sb.append("... ");
                    break;
                case 't':
                    sb.append("- ");
                    break;
                case 'u':
                    sb.append("..- ");
                    break;
                case 'v':
                    sb.append("...- ");
                    break;
                case 'w':
                    sb.append(".-- ");
                    break;
                case 'x':
                    sb.append("-..- ");
                    break;
                case 'y':
                    sb.append("-.-- ");
                    break;
                case 'z':
                    sb.append("--.. ");
                    break;
                case '1':
                    sb.append(".---- ");
                    break;
                case '2':
                    sb.append("..--- ");
                    break;
                case '3':
                    sb.append("...-- ");
                    break;
                case '4':
                    sb.append("....- ");
                    break;
                case '5':
                    sb.append("..... ");
                    break;
                case '6':
                    sb.append("-.... ");
                    break;
                case '7':
                    sb.append("--... ");
                    break;
                case '8':
                    sb.append("---.. ");
                    break;
                case '9':
                    sb.append("----. ");
                    break;
                case '0':
                    sb.append("-----");
                    break;
                case ' ':
                    sb.append("/ ");
                    break;
                case '$':
                    sb.append("...-..- ");
                    break;
                case '(':
                    sb.append("-.--. ");
                    break;
                case ')':
                    sb.append("-.--.- ");
                    break;
                case '[':
                    sb.append("-.--. ");
                    break;
                case '}':
                    sb.append("-.--.- ");
                    break;
                case '+':
                    sb.append(".-.-. ");
                    break;
                case '!':
                    sb.append("---. ");
                    break;
                case '?':
                    sb.append("..--.. ");
                    break;
                case '-':
                    sb.append("-....- ");
                    break;
                case '=':
                    sb.append("-...- ");
                    break;
            }
        }
        outputMorse = sb.toString();
        return(outputMorse);
    }
}

