package com.ylf.gshout;

import com.ylf.gshout.listeners.EventListener;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import java.net.http.HttpRequest;

import javax.security.auth.login.LoginException;

public class shouterBot {

    private final ShardManager shardManager;

    public static Dotenv config;

    public shouterBot() throws LoginException{
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.IDLE);
        builder.setActivity(Activity.playing("Your group!"));
        shardManager = builder.build();

        shardManager.addEventListener(new EventListener());
    }

    public ShardManager getShardManager(){
        return shardManager;
    }

    public Dotenv getConfig()
    {
        return config;
    }


    public static void main(String[] args){

        try{
            shouterBot bot = new shouterBot();
        } catch (LoginException e)
        {
            System.out.println("Worng token");
        }
    }


}
