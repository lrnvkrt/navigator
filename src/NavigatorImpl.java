public class NavigatorImpl implements Navigator{

    private final Map<String, Route> map = new Map<>();

    @Override
    public void addRoute(Route route) {
        map.add(route.getId(), route);
    }

    @Override
    public void removeRoute(String routeId) {
        map.remove(routeId);
    }

    @Override
    public boolean contains(Route route) {
        return map.containsKey(route.getId());
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return map.get(routeId);
    }

    @Override
    public void chooseRoute(String routeId) {
        Route route = map.get(routeId);
        route.setPopularity(route.getPopularity() + 1);
        map.add(routeId, route);
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        MyList<Route> routes = new MyList<>();
        for (Route route : map.values()) {
            if(isLogical(route, startPoint, endPoint)) {
                routes.add(route);
            }
        }
        routes.quickSort(new Route.RouteComparator(startPoint, endPoint, map));
        return routes;
    }

    private boolean isLogical(Route route, String startPoint, String endPoint) {
        MyList<String> locationPoints = route.getLocationPoints();
        int indexOfStart = locationPoints.indexOf(startPoint);
        int indexOfEnd = locationPoints.indexOf(endPoint);
        return indexOfStart > 0 && indexOfEnd > 0 && indexOfStart < indexOfEnd;
    }


    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        MyList<Route> routes = new MyList<>();
        for (Route route : map.values()) {
            if (route.isFavorite() && !route.getLocationPoints().get(0).equals(destinationPoint) && route.getLocationPoints().contains(destinationPoint)) {
                routes.add(route);
            }
        }
        routes.quickSort(new Route.FavoriteComparator(map));
        return routes;
    }


    @Override
    public Iterable<Route> getTop5Routes() {
        MyList<Route> routes = new MyList<>();
        routes.addAll(map.values());
        routes.quickSort(new Route.TopComparator(map));
        return routes.subList(0, 5);
    }
}
