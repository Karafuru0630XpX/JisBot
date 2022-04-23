package bot;

import com.what3words.javawrapper.What3WordsV3;
import com.what3words.javawrapper.response.ConvertToCoordinates;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.ApplicationInfo;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.GuildMessageChannel;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.json.UserData;
import discord4j.rest.util.Image;
import it.sauronsoftware.cron4j.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public class Main {
    public static void main(String[] arg) throws Exception {
        //ファイル生成系統
        System.out.println("-----ファイル生成-----");

        //コンフィグフォルダを作成
        System.out.println("  ---config folder---");
        File configFolder = new File("config");
        if (configFolder.exists()) {
            System.out.println("フォルダが見つかりました。生成しません");
        }
        //フォルダがない場合
        if (!configFolder.exists()) {
            System.out.println("フォルダが見つかりませんでした。生成します");
            if (configFolder.mkdir()) {
                System.out.println("フォルダ生成 : 成功");
            } else {
                System.out.println("フォルダ生成 : 失敗");
            }
        }

        //コンフィグフォルダのパスを定義
        //あとで簡単に変更できるように定義
        String folder = "config/";

        //プロパティファイルを作成
        System.out.println("  ---config.properties--- ");

        Properties config = new Properties();
        File propertiesPath = new File(folder + "config.properties");

        //プロパティに情報を追加
        config.setProperty("BotName","your bot name");
        config.setProperty("token","your bot token");
        config.setProperty("prefix","/");
        config.setProperty("WordsApiKey","What3Words Api key");
        config.setProperty("WeatherForecastWeek","true");
        config.setProperty("WeatherForecastMorning","true");
        config.setProperty("MorningTime","30 7 * * *");
        config.setProperty("WeekTime","0 12 * * Mon");

        //プロパティファイルがないときに実行
        if (propertiesPath.exists()) {
            System.out.println("ファイルが見つかりました。生成しません");
        }
        if (!propertiesPath.exists()) {
            System.out.println("ファイルが見つかりませんでした。生成します");
            //プロパティを生成
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(folder + "config.properties");
                config.store(out, "Setting file");
                System.out.println("ファイル生成 : 成功");
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }

        System.out.println("----------------");

        //上記で生成したプロパティファイルを読み込み
        //ここは同じ変数を使っているためエラーが出るかも、要確認(config変数) ->多分出なかった
        FileInputStream in = null;
        try {
            in = new FileInputStream(folder + "config.properties");
            config.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }

        //読み込んだプロパティの情報を定義
        String botName = config.getProperty("BotName");
        String token = config.getProperty("token");
        String prefix = config.getProperty("prefix");
        String apiKey = config.getProperty("WordsApiKey");
        String weekB = config.getProperty("WeatherForecastWeek");
        String morningB = config.getProperty("WeatherForecastMorning");
        String morningT = config.getProperty("MorningTime");
        String weekT = config.getProperty("WeekTime");

        //読み込んだ情報をコンソールにて伝える
        System.out.println("-----Setteings-----");
        System.out.println(" <   Discord Bot Settings  >");
        System.out.println("  Discord Bot Token(token) : " + token);
        System.out.println("  Command Prefix(prefix) : " + prefix);
        System.out.println(" <   Api Keys  >");
        System.out.println("  What 3Words Api Key(WordsApiKey) : " + apiKey);
        System.out.println(" <  Weather Forecast  >");
        System.out.println("  Weather forecast morning(WeatherForecastMorning) : " + morningB);
        System.out.println("  Weather forecast week(WeatherForecastWeek) : " + weekB);
        System.out.println("  WF morning time (MorningTime) : " + morningT);
        System.out.println("  WF week time(WeekTime) : " + weekT);
        System.out.println("-------------------");

        //ここから実際のコード
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        //開始時間をログに残す
        Date date = new Date();
        System.out.println("Login was successful!");
        System.out.println("Start time : " + date);

        if (gateway == null) return;

        //ギルドのシステムチャンネルを取得
        TextChannel systemChannel = gateway.getGuilds().blockFirst().getSystemChannel().block();

        //what3wordsのapiキーの定義
        final What3WordsV3 wordKey = new What3WordsV3(apiKey);

        //天気予報を定期的に通知するプログラム
        Scheduler scheduler = new Scheduler();

        //メゾットにしたのは後で変更がやりやすいからです
        if (Objects.equals(morningB, "true")) {
            Morning mor = new Morning();
            mor.setTextChannel(systemChannel);
            scheduler.schedule(morningT,new Morning());
        }
        if (Objects.equals(weekB, "true")) {
            Week wee = new Week();
            wee.setSystemC(systemChannel);
            scheduler.schedule(weekT,new Week());
        }
        scheduler.start();

        //クライアントの情報を手に入れる
        ApplicationInfo appInfo = gateway.getApplicationInfo().block();

        //コマンド
        //イベント系はeで略しています
        gateway.on(MessageCreateEvent.class).subscribe(e -> {
            Guild guild = e.getGuild().block();
            Message message = e.getMessage();
            String text = message.getContent();
            MessageChannel channel = message.getChannel().block();
            UserData member = message.getUserData();

            String[] args = text.split("\\s+");

            if (channel == null) return;
            if (text.startsWith(prefix + "bosyu")) {
                if (args.length == 1) {
                    sendMsg("募集人数などが入力されていません！```usage : /bosyu <条件、人数など>```",channel);
                } else {
                    sendMsg(member.username() + " さんが募集をかけました@" + args[1], channel);
                    sendReactMessage("↓参加を希望する場合はこちらからどうぞ","U+270B",channel);
                }
            }
            if (text.startsWith(prefix + "wordLoc")) {
                if (wordKey == null) return;
                if (args.length != 4) {
                    sendMsg("ワードが足りません！```usage: /wordLoc <単語1> <単語2> <単語3>```",channel);
                } else {
                    sendMsg(WordConvert(args[1],args[1],args[1],wordKey).toString(),channel);
                }
            }
            if (text.startsWith(prefix + "guildInfo")) {
                if (guild != null) {
                    GuildMessageChannel gmc = (GuildMessageChannel)channel;
                    EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
                    builder.author(botName, "https://github.com/Karafuru0630XpX/JisBot", appInfo.getIconUrl(Image.Format.PNG).get());
                    builder.image(guild.getIconUrl(Image.Format.JPEG).get());
                    builder.title("Guild Info / ギルド情報");
                    builder.url("");
                    builder.description("setDescription\n" +
                            "big D: is setImage\n" +
                            "small D: is setThumbnail\n" +
                            "<-- setColor");
                    builder.addField("addField", "inline = true", true);
                    builder.addField("addFIeld", "inline = true", true);
                    builder.addField("addFile", "inline = false", false);
                    builder.footer("setFooter --> setTimestamp", appInfo.getIconUrl(Image.Format.PNG).get());
                    builder.timestamp(Instant.now());
                    channel.createMessage(builder.build()).block();
                }
            }
        });

        gateway.onDisconnect().block();
    }

    //メゾットの定義
    //メッセージを送るメゾット
    public static void sendMsg(String text, @NotNull MessageChannel channel) {
        channel.createMessage(text).block();
    }
    //リアクション付きのメッセージを送るメゾット
    public static void sendReactMessage(String message,@NotNull String codePoints,@NotNull MessageChannel channel) {
        channel.createMessage(message)
                .flatMap(msg -> msg.addReaction(ReactionEmoji.codepoints(codePoints)))
                .subscribe();
    }
    //3つの単語を座標に変換するメゾット(省略用)
    @NotNull
    public static ConvertToCoordinates WordConvert(String word1, String word2, String word3, @NotNull What3WordsV3 apiKey) {
        String words = word1 + "." + word2 + "." + word3;
        return apiKey.convertToCoordinates(words).execute();
    }
}
