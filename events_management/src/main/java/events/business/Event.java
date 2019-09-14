package events.business;

import java.util.Objects;

public class Event {
    @Override
    public int hashCode() {
        return Objects.hash(id, username, familyname, city, guestCount, isPoliticianComing);
    }

    int id = -1;
    String username = null;
    String familyname = null;
    String city = null;
    int guestCount = 0;
    boolean isPoliticianComing = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFamilyName(String familyname) {
        this.familyname = familyname;
    }

    public boolean getIsPoliticianComing() {
        return isPoliticianComing;
    }

    public void setPoliticianComing(boolean politicianComing) {
        isPoliticianComing = politicianComing;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String name) {
        this.username = name;
    }

    public String getFamilyName() {
        return familyname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String country) {
        this.city = country;
    }

    public int getGuestCount() {
        return guestCount;
    }
    public void setGuestCount(int guestCount) {
        this.guestCount = guestCount;
    }

    public static Event badEvent()
    {
        return new Event();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event mimouna = (Event) o;
        return id == mimouna.id &&
                guestCount == mimouna.guestCount &&
                isPoliticianComing == mimouna.isPoliticianComing &&
                Objects.equals(username, mimouna.username) &&
                Objects.equals(familyname, mimouna.familyname) &&
                Objects.equals(city, mimouna.city);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Song{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", familyname='").append(familyname).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", guestCount='").append(guestCount).append('\'');
        sb.append(", isPoliticianComing='").append(isPoliticianComing).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
