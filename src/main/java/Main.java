import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println(
                "[1] - insert data to db\n" +
                "[2] - enter sql\n"
        );

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String action = reader.readLine();

        switch (action){
            case "1":
                checkTable();
                getImdb();
                break;
            case "2":
                String sql = reader.readLine();
                goSql(sql);
                break;
        }
    }

    public static void goSql(String sql){
        DBConnect.printSql(sql);
    }

    public static void checkConnect(){
        System.out.println(DBConnect.hasConnect());
    }

    public static void checkTable(){
        try {
            if (!DBConnect.tableExistsSQL()){
                DBConnect.createTable();
                System.out.println("Table created");
            } else {
                System.out.println("Table imdb exists");
            }
        } catch (Exception e){
            System.out.println("тут");
            System.out.println(e.getMessage());
        }
    }

    public static void getImdb(){
        try {
            Document doc = Jsoup.connect("https://www.imdb.com/chart/top/").get();
            Elements elements = doc.getElementsByClass("lister-list").get(0).getElementsByTag("tr");
            List<Film> films = new ArrayList<>();

            for(Element element: elements){
                var film = element.getElementsByClass("titleColumn").get(0);
                var rate = element.getElementsByClass("ratingColumn").get(0).text();
                films.add(new Film(film.getElementsByTag("a").get(0).text(), Integer.parseInt(film.getElementsByTag("span").get(0).text().replace("(", "").replace(")", "")), Double.parseDouble(rate)));

//                System.out.println("Name " + film.getElementsByTag("a").get(0).text());
//                System.out.println("Age  " + film.getElementsByTag("span").get(0).text().replace("(", "").replace(")", ""));
//                System.out.println("Rate " + rate);
//                System.out.println("==============");

            }

            DBConnect.insertData(films);
            System.out.println("бд наполнена");

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
