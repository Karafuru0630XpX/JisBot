package bot;

import discord4j.core.object.entity.channel.TextChannel;

public class Morning implements Runnable{
    private TextChannel systemC;
    @Override
    public void run() {
        if (systemC != null) {
            systemC.createMessage("朝です!");
        } else {
            System.out.println("システムチャンネルがありません");
        }
    }

    public void setTextChannel(TextChannel channel) {
        systemC = channel;
    }
}
