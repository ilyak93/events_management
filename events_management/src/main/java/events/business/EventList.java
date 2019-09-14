package events.business;

import java.util.Objects;

public class EventList {

    int id = -1;
    String city = null;


    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static EventList badEventlist()
    {
        return new EventList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventList that = (EventList) o;
        return id == that.id &&
                Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventList{");
        sb.append("id=").append(id);
        sb.append(",city='").append(city).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
