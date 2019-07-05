import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.managers.GuildController;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArcCore extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        if (event.getMessage().getAuthor().getName().equals("DuckHunt")) {
            return;
        } else {
            Main.panelArc.addLog(event.getAuthor().getName() + " in " + event.getChannel() + ": " + event.getMessage().getContentDisplay());
            if (event.getMessage().getContentRaw().startsWith("Arc")) {
                //All Commands Beginning with Arc
                String received = event.getMessage().getContentRaw();
                received = received.replaceFirst("Arc", "");
                if(received.equals("getID")){
                    event.getChannel().sendMessage(event.getAuthor().getId()).queue();
                }
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
                if (received.equals("Time")) {
                    event.getChannel().sendMessage("According to my sources, it is currently " + sdf.format(cal.getTime()) + " CST").queue();
                }
                if (received.equals("Test")) {
                    event.getChannel().sendMessage("Test successful! Hey, " + event.getAuthor().getName() + "!").queue();
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
                if (received.equals("Summon")) {
                    received = received.replace("Summon", "");
                    if (event.getMember().getVoiceState().inVoiceChannel()) {
                        try {
                            VoiceChannel mychannel = event.getMember().getVoiceState().getChannel();
                            audioManager.openAudioConnection(mychannel);
                        } catch (NullPointerException ex) {
                            event.getChannel().sendMessage("You're not in a voice channel!").queue();
                        }
                    }
                }
                if (received.equals("Leave")) {
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
                if (received.equals("Link")) {
                    try {
                        event.getChannel().sendMessage("Screenshare link for "+event.getMember().getVoiceState().getChannel().getName()+": https://discordapp.com/channels/" + (event.getGuild().getId()) + "/" + event.getMember().getVoiceState().getChannel().getId()).queue();
                    } catch (NullPointerException ex) {
                        System.out.println("Not Found");
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
                    if (input.startsWith("building")) {
                        input = input.replace("building ", "");
                        building = true;
                    }
                    StringBuilder TotalOutput = new StringBuilder();
                    int total = 0;
                    StringBuilder output;
                    if (input.contains(" + ")) {
                        String inArr[] = input.split("\\+");
                        for (String a : inArr) {
                            a = a.replace(" ", "");
                            String[] numbers = a.split("d");
                            int dice = Integer.parseInt(numbers[0]);
                            int sides = Integer.parseInt(numbers[1]);
                            int[] arr;
                            int x = 0;
                            int i;
                            arr = new int[dice];
                            output = new StringBuilder();
                            output.append("(");
                            while (dice > 0) {
                                dice--;
                                i = (int) (Math.random() * sides) + 1;
                                total += i;
                                arr[x] = i;
                                x++;
                            }
                            for (int b = 0; b < arr.length - 1; b++) {
                                output.append(arr[b]);
                                output.append(", ");
                            }
                            String output2 = output.toString();
                            output2 += (arr[arr.length - 1]);
                            output2 += ")";
                            TotalOutput.append(output2);
                            TotalOutput.append(" + ");
                        }
                    } else {
                        String[] numbers = input.split("d");
                        int dice = Integer.parseInt(numbers[0]);
                        int sides = Integer.parseInt(numbers[1]);
                        int[] arr;
                        int x = 0;
                        int i;
                        arr = new int[dice];
                        output = new StringBuilder();
                        output.append("(");
                        while (dice > 0) {
                            dice--;
                            i = (int) (Math.random() * sides) + 1;
                            total += i;
                            arr[x] = i;
                            x++;
                        }
                        for (int b = 0; b < arr.length - 1; b++) {
                            output.append(arr[b]);
                            output.append(", ");
                        }
                        output.append(arr[arr.length - 1]);
                        output.append(")");
                        TotalOutput.append(output);
                        if (building) {
                            TotalOutput = TotalOutput.replace(0, 1, "");
                            TotalOutput = TotalOutput.replace(TotalOutput.length() - 1, TotalOutput.length(), "");
                            for (String c : TotalOutput.toString().split(", ")) {
                                if (Integer.parseInt(c) == sides) {
                                    boolean max = true;
                                    while (max) {
                                        i = (int) (Math.random() * sides) + 1;
                                        if (i != sides) {
                                            max = false;
                                        }
                                        total += i;
                                    }
                                }
                            }
                        }
                    }
                    if (TotalOutput.lastIndexOf(" + ") == TotalOutput.length() - 3) {
                        TotalOutput.replace((TotalOutput.length() - 3), (TotalOutput.length()), "");
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
            //Commands only for DnD Server.
            if (event.getGuild().getName().equals("D&D Plaza")|| event.getGuild().getName().equals("Arceus's Testing Grounds")) {
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
                //Structure Campaign Name, Dungeon Master.
                if (event.getMessage().getContentRaw().startsWith("New Campaign")) {
                    Long DMRole = 0L;
                    if (event.getGuild().getName().equals("D&D Plaza")) {
                        DMRole = 375005981798825985L;
                    }
                    if (event.getGuild().getName().equals("Arceus's Testing Grounds")) {
                        DMRole = 596532612902813716L;
                    }
                    String input = (event.getMessage().getContentRaw().replace("New Campaign ", ""));
                    String[] info = input.split(" / ");
                    String campaignName = info[0];
                    Long dungeonMaster = event.getMessage().getMentionedMembers().get(0).getIdLong();
                    GuildController gc = event.getMessage().getGuild().getController();
                    gc.createCategory(campaignName).queue();
                    gc.addSingleRoleToMember(event.getGuild().getMemberById(dungeonMaster), event.getGuild().getRoleById(DMRole)).queue();
                    event.getChannel().sendMessage("Creating: " + campaignName + " DM: <@!" + dungeonMaster + ">").queue();
                    gc.createRole().setName(campaignName).setColor(new Color(((int)(Math.random()*255)+1),((int)(Math.random()*255)+1),((int)(Math.random()*256)+1))).setHoisted(true).setMentionable(true).queue();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        event.getChannel().sendMessage("Something went wrong! You fucked up, Arceus").queue();
                    }
                }
                if(event.getMember().getId().equals("487696031417499649") && event.getMessage().getContentRaw().startsWith("Creating")){
                    String info = event.getMessage().getContentRaw().replace("Creating: ", "");
                    String campaignName = info.substring(0, info.indexOf(" DM"));
                    String dungeonMaster = info.substring(info.indexOf(" DM"));
                    dungeonMaster = dungeonMaster.replaceFirst(" DM: ", "");
                    Category cat = event.getMessage().getGuild().getCategoriesByName(campaignName, false).get(0);
                    cat.createTextChannel("gameboard").queue();
                    cat.createTextChannel("dungeon-master-notes").queue();
                    cat.createTextChannel("planning-room").queue();
                    cat.createTextChannel("session-summary").queue();
                    cat.createTextChannel("pc-graveyard").queue();
                    cat.createVoiceChannel("The Dungeon").queue();
                    Role role = event.getGuild().getRolesByName(campaignName, false).get(0);
                    cat.createPermissionOverride(event.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL).queue();
                    cat.createPermissionOverride(role).setAllow(Permission.VIEW_CHANNEL).queue();
                    event.getChannel().sendMessage("Created: "+campaignName+ " DM: "+dungeonMaster).queue();
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch(InterruptedException ex){
                        event.getChannel().sendMessage("Something went wrong! You fucked up, Arceus").queue();
                    }
                }
                if(event.getMember().getId().equals("487696031417499649") && (event.getMessage().getContentRaw().startsWith("Created: "))){
                    String info = event.getMessage().getContentRaw().replace("Created: ", "");
                    String campaignName = info.substring(0, info.indexOf(" DM"));
                    String dungeonMaster = info.substring(info.indexOf(" DM"));
                    Category cat = event.getGuild().getCategoriesByName(campaignName, false).get(0);
                    List<GuildChannel> channels = cat.getChannels();
                    for(int i = 0; i<channels.size(); i++){
                        if(i==1){
                            //Only the DM can type and read
                            TextChannel currentChannel = (TextChannel)channels.get(i);
                            currentChannel.putPermissionOverride(event.getGuild().getRolesByName(campaignName, false).get(0)).setDeny(Permission.VIEW_CHANNEL).queue();
                            currentChannel.createPermissionOverride(event.getMessage().getMentionedMembers().get(0)).setAllow(Permission.VIEW_CHANNEL).queue();
                        }
                        if(i==2){
                            //Only the players can read this
                            TextChannel currentChannel = (TextChannel)channels.get(i);
                            currentChannel.createPermissionOverride(event.getMessage().getMentionedMembers().get(0)).setDeny(Permission.VIEW_CHANNEL).queue();
                        }
                        if(i==4){
                            //DM Can type, everyone can read
                            TextChannel currentChannel = (TextChannel)channels.get(i);
                            currentChannel.putPermissionOverride(event.getGuild().getRolesByName(campaignName, false).get(0)).setDeny(Permission.MESSAGE_WRITE).queue();
                            currentChannel.createPermissionOverride(event.getMessage().getMentionedMembers().get(0)).setAllow(Permission.MESSAGE_WRITE).queue();
                        }
                    }
                    GuildController gc = new GuildController(event.getGuild());
                }
            }
            if (event.getGuild().getName().equals("Dungeons and Dickholes")) {
                if (event.getMessage().getContentRaw().contains("= (1)")) {
                    event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/375004521212149772/490557385320955915/32875754_2091003187856028_8205528177125097472_n.png").queue();
                }
                if (event.getMessage().getContentRaw().contains("= (20)")) {
                    event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527532487329710111/completely-erect-jenkins-im-back-from-my-long-sabbatical-and-3956744-picsay.png").queue();
                }
                if (event.getMessage().getContentRaw().contains("= (2)")) {
                    event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527212607073943553/2.jpg").queue();
                }
                if (event.getMessage().getContentRaw().contains("= (19)")) {
                    event.getChannel().sendMessage("https://cdn.discordapp.com/attachments/501451663148843035/527567698415190027/i-have-the-weirdest-boner.jpg").queue();
                }
            }
            //Sunflower Academy Commands
            if(event.getGuild().getId().equals("581287208594571265")){
                if(!event.getMessage().getAuthor().getId().equals("487696031417499649")) {
                    //Anyone Commands
                    if(event.getMessage().getContentRaw().equals("SFAHelp")) {
                        String output = "SFAPoints <User> - Gets the points of a specified user, just use SFAPoints for own points\n"+
                                "==Leader Only Commands==\n"+
                                "SFAAddPoints <User> <Points> - Adds points to a specified user\n"+
                                "SFADeletePoints <User> <Points> - Removes points from a specified user\n"+
                                "SFAAddUser <User> - Registers a new user to the SFA\n"+
                                "SFADeleteUser <User> - Removes a user from the SFA";
                        event.getChannel().sendMessage(output).queue();
                    }
                    if(event.getMessage().getContentRaw().startsWith("SFAPoints")){
                        Member target;
                        String targetID;
                        List<Member> victims = event.getMessage().getMentionedMembers();
                        if(victims.size()>=1) {
                            target = victims.get(0);
                            targetID = target.getId();
                        }
                        else{
                            targetID = event.getMessage().getAuthor().getId();
                        }
                        BufferedReader reader;
                        try{
                            reader = new BufferedReader(new FileReader(new File("SunflowerAcademyData.txt")));
                            String line = reader.readLine();
                            boolean found = false;
                            while(line!=null){
                                if(line.startsWith(targetID)){
                                    found = true;
                                    break;
                                }
                                else{
                                    line = reader.readLine();
                                }
                            }
                            if(found){
                                event.getChannel().sendMessage("<@"+targetID+"> has "+(line.substring(line.indexOf("/")+1))+" points!").queue();
                            }
                            else{
                                event.getChannel().sendMessage("That user wasn't found!").queue();
                            }
                        }
                        catch(IOException ex){
                            event.getChannel().sendMessage("Something went wrong! Contact Arceus3251 for more Information").queue();
                        }
                    }
                    if(event.getMessage().getContentRaw().startsWith("SFAGive")){
                        String giverID = event.getAuthor().getId();
                        List<Member> recipient = event.getMessage().getMentionedMembers();
                        String recipientID = recipient.get(0).getId();
                        String[] parts = event.getMessage().getContentRaw().split(" ");
                        int pointsTransfer = Integer.parseInt(parts[2]);
                        if(pointsTransfer<0){
                            event.getChannel().sendMessage("Don't steal!").queue();
                        }
                        else {
                            try {
                                File file = new File("SunflowerAcademyData.txt");
                                BufferedReader reader = new BufferedReader(new FileReader(file));
                                boolean existA = false;
                                boolean existB = false;
                                ArrayList<String> lines = new ArrayList<>();
                                String line = reader.readLine();
                                while (line != null) {
                                    if (line.startsWith(giverID)) {
                                        existA = true;
                                    }
                                    if (line.startsWith(recipientID)) {
                                        existB = true;
                                    }
                                    lines.add(line);
                                    line = reader.readLine();
                                }
                                reader.close();
                                if (!existA) {
                                    event.getChannel().sendMessage("You don't appear to be registered! Talk with a leader!").queue();
                                }
                                if (!existB) {
                                    event.getChannel().sendMessage("The recipient doesn't appear to be registered! Have them speak with a leader!").queue();
                                }
                                if (existA && existB) {
                                    for (String a : lines) {
                                        if (a.startsWith(giverID)) {
                                            int currentPoints = Integer.parseInt(a.substring(a.indexOf("/") + 1));
                                            currentPoints -= pointsTransfer;
                                            String newLine = giverID + "/" + currentPoints;
                                            lines.set(lines.indexOf(a), newLine);
                                        }
                                        if (a.startsWith(recipientID)) {
                                            int currentPoints = Integer.parseInt(a.substring(a.indexOf("/") + 1));
                                            currentPoints += pointsTransfer;
                                            String newLine = recipientID + "/" + currentPoints;
                                            lines.set(lines.indexOf(a), newLine);
                                        }
                                    }
                                    file.delete();
                                    file = new File("SunflowerAcademyData.txt");
                                    file.canWrite();
                                    file.canRead();
                                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                                    for (String a : lines) {
                                        writer.write(a);
                                        writer.newLine();
                                    }
                                    writer.close();
                                }
                            } catch (IOException ex) {
                                event.getChannel().sendMessage("Something went wrong! Contact Arceus3251 for more information!").queue();
                            }
                        }
                    }
                    //Leader Only Commands
                    if((event.getMember().getRoles()).toString().contains("R:Leaders(581289763282223104)")){
                        if(event.getMessage().getContentRaw().startsWith("SFAAddPoints")){
                            int pointsToAdd = 1;
                            List<Member> victims = event.getMessage().getMentionedMembers();
                            Member target = victims.get(0);
                            String targetID = target.getId();
                            String[] temp = event.getMessage().getContentRaw().split(" ");
                            if(temp.length == 4) {
                                pointsToAdd = Integer.parseInt(temp[3]);
                            }
                            if(temp.length == 3){
                                pointsToAdd = Integer.parseInt(temp[2]);
                            }
                            File file = new File("SunflowerAcademyData.txt");
                            file.canRead();
                            BufferedReader reader;
                            BufferedWriter writer;
                            try{
                                reader = new BufferedReader(new FileReader(file));
                                String line = reader.readLine();
                                ArrayList<String> lines = new ArrayList<>();
                                while(line!=null){
                                    lines.add(line);
                                    line = reader.readLine();
                                }
                                reader.close();
                                for(String a: lines){
                                    if(a.contains(targetID)){
                                        int points = Integer.parseInt(a.substring(a.indexOf("/")+1));
                                        points += pointsToAdd;
                                        String newLine = (targetID+"/"+points);
                                        lines.set(lines.indexOf(a), newLine);
                                    }
                                }
                                file.delete();
                                file = new File("SunflowerAcademyData.txt");
                                file.canWrite();
                                writer = new BufferedWriter(new FileWriter(file));
                                for(String a: lines){
                                    writer.write(a);
                                    writer.newLine();
                                }
                                writer.close();
                            }
                            catch(IOException ex){
                                event.getChannel().sendMessage("Something went wrong! Contact Arceus3251 for more information!").queue();
                            }
                        }
                        if(event.getMessage().getContentRaw().startsWith("SFADeletePoints")){
                            int pointsToDelete = 1;
                            List<Member> victims = event.getMessage().getMentionedMembers();
                            Member target = victims.get(0);
                            String targetID = target.getId();
                            String[] temp = event.getMessage().getContentRaw().split(" ");
                            if(temp.length == 4) {
                                pointsToDelete = Integer.parseInt(temp[3]);
                            }
                            if(temp.length == 3){
                                pointsToDelete = Integer.parseInt(temp[2]);
                            }
                            File file = new File("SunflowerAcademyData.txt");
                            file.canRead();
                            BufferedReader reader;
                            BufferedWriter writer;
                            try{
                                reader = new BufferedReader(new FileReader(file));
                                String line = reader.readLine();
                                ArrayList<String> lines = new ArrayList<>();
                                while(line!=null){
                                    lines.add(line);
                                    line = reader.readLine();
                                }
                                reader.close();
                                for(String a: lines){
                                    if(a.contains(targetID)){
                                        int points = Integer.parseInt(a.substring(a.indexOf("/")+1));
                                        points -= pointsToDelete;
                                        String newLine = (targetID+"/"+points);
                                        lines.set(lines.indexOf(a), newLine);
                                    }
                                }
                                file.delete();
                                file = new File("SunflowerAcademyData.txt");
                                file.canWrite();
                                writer = new BufferedWriter(new FileWriter(file));
                                for(String a: lines){
                                    writer.write(a);
                                    writer.newLine();
                                }
                                writer.close();
                            }
                            catch(IOException ex){
                                event.getChannel().sendMessage("Something went wrong! Contact Arceus3251 for more information!").queue();
                            }
                        }
                        if(event.getMessage().getContentRaw().startsWith("SFAAddUser")){
                            List<Member> victims = event.getMessage().getMentionedMembers();
                            String targetID = victims.get(0).getId();
                            BufferedReader reader;
                            BufferedWriter writer;
                            ArrayList<String> members = new ArrayList<>();
                            try{
                                File file = new File("SunflowerAcademyData.txt");
                                reader = new BufferedReader(new FileReader(file));
                                String line = reader.readLine();
                                boolean found = false;
                                while(line!=null){
                                    members.add(line);
                                    if(line.startsWith(targetID)){
                                        found = true;
                                        break;
                                    }
                                    else{
                                        line = reader.readLine();
                                    }
                                }
                                reader.close();
                                if(found){
                                    event.getChannel().sendMessage("Already a member!").queue();
                                }
                                if(!found){
                                    writer = new BufferedWriter(new FileWriter(file));
                                    for(String a: members){
                                        writer.write(a);
                                        writer.newLine();
                                    }
                                    writer.write(targetID+"/0");
                                    writer.close();
                                }
                            }
                            catch(IOException ex){
                                event.getChannel().sendMessage("Something went wrong! Consult Arceus3251 for more information!").queue();
                            }
                        }
                        if(event.getMessage().getContentRaw().startsWith("SFADeleteUser")){
                            List<Member> victims = event.getMessage().getMentionedMembers();
                            String targetID = victims.get(0).getId();
                            BufferedReader reader;
                            BufferedWriter writer;
                            try{
                                File file = new File("SunflowerAcademyData.txt");
                                reader = new BufferedReader(new FileReader(file));
                                String line = reader.readLine();
                                boolean found = false;
                                while(line!=null){
                                    if(line.contains(targetID)){
                                        found = true;
                                        break;
                                    }
                                    else{
                                        line = reader.readLine();
                                    }
                                }
                                if(found){
                                    ArrayList<String> members = new ArrayList<>();
                                    ListIterator<String> iterator = new ListIterator<String>() {
                                        @Override
                                        public boolean hasNext() {
                                            return false;
                                        }

                                        @Override
                                        public String next() {
                                            return null;
                                        }

                                        @Override
                                        public boolean hasPrevious() {
                                            return false;
                                        }

                                        @Override
                                        public String previous() {
                                            return null;
                                        }

                                        @Override
                                        public int nextIndex() {
                                            return 0;
                                        }

                                        @Override
                                        public int previousIndex() {
                                            return 0;
                                        }

                                        @Override
                                        public void remove() {

                                        }

                                        @Override
                                        public void set(String s) {

                                        }

                                        @Override
                                        public void add(String s) {

                                        }
                                    };
                                    reader = new BufferedReader(new FileReader(file));
                                    line = reader.readLine();
                                    while(line!=null){
                                        iterator.add(line);
                                        line = reader.readLine();
                                    }
                                    reader.close();
                                    while(iterator.hasNext()){
                                        String a = iterator.next();
                                        if(a.contains(targetID)){
                                            members.remove(a);
                                        }
                                    }
                                    file.delete();
                                    file = new File("SunflowerAcademyData.txt");
                                    writer = new BufferedWriter(new FileWriter(file));
                                    for(String a: members){
                                        writer.write(a);
                                        writer.newLine();
                                    }
                                    writer.close();
                                }
                            }
                            catch(IOException ex){
                                event.getChannel().sendMessage("Something went Wrong! Consult Arceus3251 for more information!").queue();
                            }
                        }
                    }
                }
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
    private static String DecText(String received, int todo) {
        String output = "";
        StringBuilder sb = new StringBuilder();
        while (received.contains(" ")) {
            String[] parts = received.split(" ", 2);
            String part1 = parts[0];
            String input3 = parts[1];
            int input2 = Integer.parseInt(part1);
            char ch = (char) input2;
            sb.append(ch);
            received = input3;
        }
        int input2 = Integer.parseInt(received);
        char ch = (char) input2;
        sb.append(ch);
        switch (todo) {
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
    private static String TextBin(String received) {
        byte[] bytes = received.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
        }
        return binary.toString();
    }

    //Changes Text to Hexadecimal
    private static String TextHex(String received) {
        int length = received.length() * 2;
        return String.format("%" + length + "x", new BigInteger(1, received.getBytes()));
    }

    //Changes Text to Decimal
    private static String TextDec(String received) {
        StringBuilder decimal = new StringBuilder();
        int length = received.length();
        int i = 0;
        while (length > 0) {
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
        for (char a: charArray) {
            switch (a) {
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
        return (outputMorse);
    }
}

