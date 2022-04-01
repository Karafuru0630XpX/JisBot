package bot;

public class WeatherForecast {
    /**
    テキストファイルで時間、クラスを指定することで改変しやすく、拡張機能を作りやすくしています
    **/
    //cron4jのファイル形式で読み込むためにpublic staticで、Stringの配列リストが必要
    public static void morningForecast(String[] strings) {
        System.out.println("morning");
    }
    public static void weekForecast(String[] strings) {
        System.out.println("week");
    }
    public static void monthForecast(String[] strings) {
        System.out.println("month");
    }
}
