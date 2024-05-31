# Group shouter
A discord bot that detects keywords from shouts in a specified Roblox group and sends a custom message to a specified channel.
Perfect for detecting a training has started or ended and to report that to a discord server!
This bot was made using JDA 5 and a roblox api https://groups.roblox.com/docs/index.html#!/v1

## How to use
1. Make a .env file following the rules of .env.example
2. Run the bot (shouterBot.java) on your computer or host it in the cloud

## .env explained line by line
* Token: Your discord bot token
* Group_ID: The id of your roblox group
* Channel: The discord channel in which you want the bot to send messages
* Keyword_started: The keyword that the bot will look for inside the group shout for the started event
* Keyword_ended: The keyword that the bot will look for inside the group shout for the ended event
* Tag_everyone: Wheteher the bot should tag everyone on the started event
* Message_started: The message the bout should send on start event this is how the messages look: @everyone (person who posted message on roblox group) message_started
* Message_ended: The message the bout should send on end event this is how the messages look:(person who posted message on roblox group) message_ended
* Interval: How often in seconds the bot should ping the api, longer times result in more time for the bot to update, less time might make the api throttle the connection.

> The started keyword is set to "hosting":
![image](https://github.com/OsRaMoSaO/Group-shouter/assets/83728344/c62c2fc5-0479-4e37-85a6-bb5078e9ef04)
> This is what the bot returns in specified channel:
![image](https://github.com/OsRaMoSaO/Group-shouter/assets/83728344/d9c7dbe0-4845-4539-9f03-b7c4b54d7d6d)
> The ended keyword is set to "over":
![image](https://github.com/OsRaMoSaO/Group-shouter/assets/83728344/ab9b861c-96b6-4407-9ab9-f0cdeb2e5b7f)
> This is what the bot returns in specified channel:
![image](https://github.com/OsRaMoSaO/Group-shouter/assets/83728344/3c6c2721-37a0-49b3-9417-cf1433a4e7b7)

This ofcourse works for any keywords, and keywords need to cycle. Meaning you cant run hositng twice or over twice, started event <-> ended event.

> [!NOTE]
> If the bot dosent work try running both keywords again.
