import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class RouteGenerator {
    private static final Random random = new Random();
    private static final String[] cities = {
            "Москва", "Тверь", "Подольск", "Смоленск", "Брянск", "Адлер", "Казань", "Санкт-Петербург",
            "Владивосток", "Иркутск", "Мурманск", "Норильск", "Ульяновск", "Новосибирск", "Кемерово",
            "Самара", "Пермь", "Ростов-на-Дону", "Воронеж", "Волгоград", "Краснодар", "Екатеринбург",
            "Тюмень", "Уфа", "Челябинск", "Омск", "Красноярск", "Улан-Удэ", "Магадан", "Анадырь"
    };

    public static MyList<Route> generate(int quantity) {
        MyList<Route> routes = new MyList<>();
        for(int i = 0; i < quantity; i++) {
            String id = UUID.randomUUID().toString();
            double distance = random.nextDouble()*1000;
            distance = Double.parseDouble(new DecimalFormat("#.#").format(distance).replace(",", "."));
            int popularity = random.nextInt(0, 100);
            MyList<String> locationPoints = generateLocationPoints();
            routes.add(new Route(id, distance, popularity, locationPoints.size()>3, locationPoints));
        }
        return routes;
    }

    private static MyList<String> generateLocationPoints() {
        MyList<String> locationPoints = new MyList<>();
        for (int i = 0; i < random.nextInt(2, 10); i++) {
            String randomCity = cities[random.nextInt(cities.length)];
            if (!locationPoints.contains(randomCity)) {
                locationPoints.add(randomCity);
            }
        }
        return locationPoints;
    }
}
