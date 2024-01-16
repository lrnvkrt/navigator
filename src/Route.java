import java.util.Comparator;
import java.util.Objects;

public class Route {
    private String id;
    private double distance;
    private int popularity;
    private boolean isFavorite;
    private MyList<String> locationPoints;

    public static class RouteComparator implements Comparator<Route> {
        private final String startPoint;
        private final String endPoint;
        private final Map<String, Route> map;

        public RouteComparator(String startPoint, String endPoint, Map<String, Route> map) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.map = map;
        }

        @Override
        public int compare(Route o1, Route o2) {
            if (o1.isFavorite() && !o2.isFavorite()) {
                return -1;
            }
            if (!o1.isFavorite() && o2.isFavorite()) {
                return 1;
            }
            int pointSpacingFirst = countDistance(o1.locationPoints);
            int pointSpacingSec = countDistance(o2.locationPoints);
            if (pointSpacingFirst != pointSpacingSec) {
                return pointSpacingFirst-pointSpacingSec;
            }
            if (o1.popularity != o2.popularity) {
                return o2.popularity - o1.popularity;
            }
            return map.getOrder(o1.id) - map.getOrder(o2.id);
        }

        private int countDistance(MyList<String> list) {
            int indexOfStart = list.indexOf(startPoint);
            int indexOfEnd = list.indexOf(endPoint);
            return indexOfEnd-indexOfStart-1;
        }
    }

    public static class FavoriteComparator implements Comparator<Route> {

        private final Map<String, Route> map;

        public FavoriteComparator(Map<String, Route> map) {
            this.map = map;
        }

        @Override
        public int compare(Route o1, Route o2) {
            if (o1.distance != o2.distance) {
                return Double.compare(o1.distance, o2.distance);
            }
            if (o1.popularity != o2.popularity) {
                return o2.popularity-o1.popularity;
            }
            return map.getOrder(o1.id) - map.getOrder(o2.id);
        }
    }

    public static class TopComparator implements Comparator<Route> {
        private Map<String, Route> map;

        public TopComparator(Map<String, Route> map) {
            this.map = map;
        }

        @Override
        public int compare(Route o1, Route o2) {
            if (o1.popularity != o2.popularity) {
                return o2.popularity - o1.popularity;
            }
            if (o1.distance != o2.distance) {
                return Double.compare(o1.distance, o2.distance);
            }
            if (o1.locationPoints.size() != o2.locationPoints.size()) {
                return o1.locationPoints.size()-o2.locationPoints.size();
            }
            return map.getOrder(o1.id)-map.getOrder(o2.id);
        }
    }

    public Route(String id, Double distance, int popularity, boolean isFavorite, MyList<String> locationPoints) {
        this.id = id;
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public MyList<String> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(MyList<String> locationPoints) {
        this.locationPoints = locationPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Double.compare(distance, route.distance) == 0 && popularity == route.popularity && isFavorite == route.isFavorite && Objects.equals(id, route.id) && Objects.equals(locationPoints, route.locationPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, distance, popularity, isFavorite, locationPoints);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", distance=" + distance +
                ", popularity=" + popularity +
                ", isFavorite=" + isFavorite +
                ", locationPoints=" + locationPoints +
                '}';
    }
}
