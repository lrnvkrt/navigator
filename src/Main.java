import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static final NavigatorImpl navigator = new NavigatorImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        MyList<Route> routes = RouteGenerator.generate(10000);
        for (Route route: routes) {
            navigator.addRoute(route);
        }
        
        System.out.println(navigator.searchRoutes("Москва", "Пермь"));


    }
}