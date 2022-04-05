package bot;

import discord4j.core.object.entity.channel.TextChannel;

public class Week implements Runnable{
    private TextChannel systemC;
    @Override
    public void run() {
        if (systemC != null) {
            systemC.createMessage("月曜日ですよ！");
        } else {
            System.out.println("システムチャンネルがありません");
        }
    }

    public void setSystemC(TextChannel channel) {
        systemC = channel;
    }
}
