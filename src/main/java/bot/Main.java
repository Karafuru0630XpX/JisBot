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
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.discordjson.json.UserData;
import discord4j.rest.util.Image;
import it.sauronsoftware.cron4j.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.io.*;
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
            System.out.println("ファイルが見つかりました。生成しません");
        }
        //フォルダがない場合
        if (!configFolder.exists()) {
            System.out.println("ファイルが見つかりませんでした。生成します");
            if (configFolder.mkdir()) {
                System.out.println("ファイル生成 : 成功");
            } else {
                System.out.println("ファイル生成 : 失敗");
            }
        }

        //コンフィグフォルダのパスを定義
        //あとで簡単に変更できるように定義
        String folder = "config/";

        //天気予報の時間指定用テキストファイルを生成
        createAndWriteTextFile( folder + "Morning.txt",cron4jTextFileContents("30","7","*","*","*","bot.WeatherForecast","morningForecast"));
        createAndWriteTextFile(folder + "Week.txt",cron4jTextFileContents("0","12","*","*","Mon","bot.WeatherForecast","weekForecast"));
        createAndWriteTextFile(folder + "Month.txt",cron4jTextFileContents("0","8","1","*","*","bot.WeatherForecast","monthForecast"));

        //プロパティファイルを作成
        System.out.println("  ---config.properties--- ");

        Properties config = new Properties();
        File propertiesPath = new File(folder + "config.properties");

        //プロパティに情報を追加
        config.setProperty("BotName","your bot name");
        config.setProperty("token","your token");
        config.setProperty("prefix","/");
        config.setProperty("WordsApiKey","What3Words Api key");
        config.setProperty("WeatherForecastWeek","true");
        config.setProperty("WeatherForecastMonth","true");
        config.setProperty("WeatherForecastMorning","true");



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
        String week = config.getProperty("WeatherForecastWeek");
        String month = config.getProperty("WeatherForecastMonth");
        String morning = config.getProperty("WeatherForecastMorning");

        //読み込んだ情報をコンソールにて伝える
        System.out.println("-----Setteings-----");
        System.out.println(" <   Discord Bot Settings  >");
        System.out.println("  Discord Bot Token(token) : " + token);
        System.out.println("  Command Prefix(prefix) : " + prefix);
        System.out.println(" <   Api Keys  >");
        System.out.println("  What 3Words Api Key(WordsApiKey) : " + apiKey);
        System.out.println(" <  Weather Forecast  >");
        System.out.println("  Weather forecast morning(WeatherForecastMorning) : " + morning);
        System.out.println("  Weather forecast week(WeatherForecastWeek) : " + week);
        System.out.println("  Weather forecast month(WeatherForecastMonth) : " + month);
        System.out.println("-------------------");

        //ここから実際のコード
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        //開始時間をログに残す
        Date date = new Date();
        System.out.println("Login was successful!");
        System.out.println("Start time : " + date);

        //what3wordsのapiキーの定義
        final What3WordsV3 wordKey = new What3WordsV3(apiKey);

        //天気予報を定期的に通知するプログラム
        Scheduler scheduler = new Scheduler();
        //プロパティをboolean型で保存出来たらよかったけど今はとりあえずString型で判別してます
        //TODO boolean型で保存できるようにする(要検索)
        if (Objects.equals(morning, "true")) {
            scheduler.scheduleFile(new File(folder + "Morning.txt"));
        }
        if (Objects.equals(week, "true")) {
            scheduler.scheduleFile(new File(folder + "Week.txt"));
        }
        if (Objects.equals(month, "true")) {
            scheduler.scheduleFile(new File(folder + "Month.txt"));
        }
        scheduler.start();

        //コマンド
        //イベント系はeで略しています
        if (gateway == null) return;

        //クライアントの情報を手に入れる
        ApplicationInfo appInfo = gateway.getApplicationInfo().block();

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
                    sendMsg("@" + member.username() + " さんが募集をかけました@" + args[1], channel);
                    sendReactMessage("↓参加を希望する場合はこちらからどうぞ","U+270B",channel);
                }
            }
            if (text.startsWith(prefix + "wordLoc")) {
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
                    builder.author(botName, "https://github.com/Karafuru0630XpX/JavaDiscordBot", appInfo.getIconUrl(Image.Format.PNG).get());
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
                    builder.footer("setFooter --> setTimestamp", "https://cdn.discordapp.com/attachments/869278213354967071/959069549762846720/code-image.png");
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
    //テキストファイルを作成して書き込むメゾット
    public static void createAndWriteTextFile(String FileName,String detail) throws IOException {
        //ファイルのパスを設定
        File textFile = new File(FileName);
        //ログ
        System.out.println("  ---" + FileName + "---");
        //生成
        //時間指定用テキストファイルがある場合
        if (textFile.exists()) {
            System.out.println("ファイルが見つかりました。生成しません");
        }
        //時間指定用テキストファイルがない場合
        if (!textFile.exists()) {
            System.out.println("ファイルが見つかりませんでした。生成します");
            if (textFile.createNewFile()) {
                //書き込み
                FileWriter writer = new FileWriter(textFile);
                PrintWriter pw = new PrintWriter(new BufferedWriter(writer));
                //分 時 日 月　曜
                pw.println(detail);
                pw.close();
                System.out.println("ファイル生成 : 成功");
            } else {
                System.out.println("ファイル生成 : 失敗");
            }
        }
    }
    public static String cron4jTextFileContents(String minute, String hour, String day, String month,String dayOfWeek, String JavaClass, String method) {
        //"* * * * * java:Class# Method
        return String.join(" ", minute, hour, day, month, dayOfWeek) + " java:" + JavaClass + "#" + method;
    }
}
