package events.business;

public class User {

    public static User badUser() {return new User(); };

    int id = -1;
    String name = null;
    String city = null;
    boolean politician = false;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean getPolitician() {
        return politician;
    }

    public void setPolitician(boolean politician) {
        this.politician = politician;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getCity() != null ? !getCity().equals(user.getCity()) : user.getCity() != null) return false;
        if (getPolitician() != user.getPolitician()) return false;
        return getName() != null ? getName().equals(user.getName()) : user.getName() == null;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", politician='").append(politician).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
