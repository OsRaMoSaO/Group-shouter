package com.ylf.gshout.listeners;

import java.util.Timer;
import java.util.TimerTask;
import com.ylf.gshout.shouterBot;
import com.ylf.gshout.httpChecker;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {


        System.out.println("Bot is ready!");

        String channelID = shouterBot.config.get("CHANNEL");
        TextChannel channel = event.getJDA().getTextChannelById(channelID);

        String tagEveryone = shouterBot.config.get("TAG_EVERYONE");

        String startedMessage = shouterBot.config.get("MESSAGE_STARTED");
        String endedMessage = shouterBot.config.get("MESSAGE_ENDED");

        httpChecker Httpcheck = new httpChecker();
        if (channel != null) {
            new Thread(() -> {
                while (true) {
                    try {
                        String[] status = Httpcheck.checkCondition();
                        if (status != (null))
                        {
                            if (status[0].contains("started")) {
                                if (tagEveryone.equals("True")){
                                    channel.sendMessage("@everyone " + status[1] + " " + startedMessage).queue();
                                }
                                else {
                                    channel.sendMessage(status[1] + " " + startedMessage).queue();
                                }
                            }
                            if (status[0].contains("ended")) {
                                channel.sendMessage(status[1] + " " +endedMessage).queue();
                            }
                        }
                        // Check every 5 minutes (300000 milliseconds)
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}

class FixedIntervalLoop {
    private int x = 0;

    public FixedIntervalLoop() {
        Integer periodI = Integer.parseInt(shouterBot.config.get("INTERVAL"));

        // Create a new Timer
        Timer timer = new Timer();

        // Schedule the task to run every 3 seconds
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Get the current value of x and increment it
                int currentValue = x;
                x = currentValue + 1;

                // Print the new value of x
            }
        }, 0, periodI *100); // 3000 milliseconds = 3 seconds
    }

    public static void main(String[] args) {
        // Create a new instance of the FixedIntervalLoop class
        FixedIntervalLoop loop = new FixedIntervalLoop();
    }
}
