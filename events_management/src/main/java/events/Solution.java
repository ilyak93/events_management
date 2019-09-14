package events;

import events.business.Event;
import events.business.EventList;
import events.business.ReturnValue;
import events.business.User;
import events.data.DBConnector;
import events.data.PostgreSQLErrorCodes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static events.business.ReturnValue.*;

public class Solution {

    private static ReturnValue catcher(SQLException e){
        int error = Integer.parseInt(e.getSQLState());
        if (error == PostgreSQLErrorCodes.CHECK_VIOLATION.getValue())
            return BAD_PARAMS;
        if (error == PostgreSQLErrorCodes.UNIQUE_VIOLATION.getValue())
            return ALREADY_EXISTS;
        if (error == PostgreSQLErrorCodes.FOREIGN_KEY_VIOLATION.getValue())
            return NOT_EXISTS;
        if (error == PostgreSQLErrorCodes.NOT_NULL_VIOLATION.getValue())
            return BAD_PARAMS;
        return ERROR;
    }

    public static void createTables(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {

            pstmt = connection.prepareStatement("CREATE TABLE Event\n" +
                    "(\n" +
                    "    id INTEGER,\n" +
                    "    username TEXT NOT NULL,\n" +
                    "    familyname TEXT NOT NULL,\n" +
                    "    city TEXT NOT NULL,\n" +
                    "    guestCount INTEGER NOT NULL,\n" +
                    "    isPoliticianComing BOOLEAN NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0),\n" +
                    "    CHECK (guestCount >= 0)\n" +
                    "); \n" +
                    " CREATE TABLE Users\n" +
                    "(\n" +
                    "    id INTEGER,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    city TEXT NOT NULL,\n" +
                    "    politician BOOLEAN NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    "); \n" +
                    " CREATE TABLE EventList\n" +
                    "(" +
                    "    id INTEGER,\n" +
                    "    city TEXT NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    "); \n" +
                    " CREATE TABLE Follows\n" +
                    "(\n" +
                    "    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE,\n" +
                    "    mimouna_list_id INTEGER REFERENCES EventList(id) ON DELETE CASCADE,\n" +
                    "    PRIMARY KEY (user_id,mimouna_list_id)\n" +
                    "); \n" +
                    " CREATE TABLE InList\n" +
                    "(\n" +
                    "    mimouna_id INTEGER REFERENCES Event(id) ON DELETE CASCADE,\n" +
                    "    mimouna_list_id INTEGER REFERENCES EventList(id) ON DELETE CASCADE,\n" +
                    "    PRIMARY KEY (mimouna_id,mimouna_list_id)\n" +
                    ");" );
            pstmt.execute();
            /*

            pstmt = connection.prepareStatement("CREATE TABLE Users\n" +
                    "(\n" +
                    "    id INTEGER,\n" +
                    "    name TEXT NOT NULL,\n" +
                    "    city TEXT NOT NULL,\n" +
                    "    politician BOOLEAN NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE EventList\n" +
                    "(\n" +
                    "    id INTEGER,\n" +
                    "    city TEXT NOT NULL,\n" +
                    "    PRIMARY KEY (id),\n" +
                    "    CHECK (id > 0)\n" +
                    ")");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE Follows\n" +
                    "(\n" +
                    "    user_id INTEGER REFERENCES Users(id) ON DELETE CASCADE,\n" +
                    "    mimouna_list_id INTEGER REFERENCES EventList(id) ON DELETE CASCADE,\n" +
                    "    PRIMARY KEY (user_id,mimouna_list_id)\n" +
                    ")");
            pstmt.execute();

            pstmt = connection.prepareStatement("CREATE TABLE InList\n" +
                    "(\n" +
                    "    mimouna_id INTEGER REFERENCES Event(id) ON DELETE CASCADE,\n" +
                    "    mimouna_list_id INTEGER REFERENCES EventList(id) ON DELETE CASCADE,\n" +
                    "    PRIMARY KEY (mimouna_id,mimouna_list_id)\n" +
                    ")");
            pstmt.execute();*/

        } catch (SQLException e) {
            //e.printStackTrace();
        }

        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static void clearTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM Event " );
            pstmt.execute();
            pstmt = connection.prepareStatement(
                    "DELETE FROM Users " );
            pstmt.execute();
            pstmt = connection.prepareStatement(
                    "DELETE FROM EventList " );
            pstmt.execute();
            pstmt = connection.prepareStatement(
                    "DELETE FROM Follows " );
            pstmt.execute();
            pstmt = connection.prepareStatement(
                    "DELETE FROM InList " );
            pstmt.execute();
        } catch (SQLException e) {
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static void dropTables() {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Follows CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS InList CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Event CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS Users CASCADE");
            pstmt.execute();
            pstmt = connection.prepareStatement("DROP TABLE IF EXISTS EventList CASCADE");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
    }

    public static ReturnValue addUser(User user) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Users" +
                    " VALUES (?, ?,?, ?)");
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getCity());
            pstmt.setBoolean(4, user.getPolitician());

            pstmt.execute();

        } catch (SQLException e) {
            //e.printStackTrace();
            return catcher(e);

        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }


    public static User getUserProfile(Integer userId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        User user = new User();
        try {
            pstmt = connection.prepareStatement("SELECT id,  name, city, politician "+" FROM Users WHERE id = (?);");
            pstmt.setInt(1, userId);
            ResultSet results = pstmt.executeQuery();
            results.next(); //added
            user.setId(results.getInt(1));
            user.setName(results.getString(2));
            user.setCity(results.getString(3));
            user.setPolitician(results.getBoolean(4));
            results.close();

        } catch (SQLException e) {
            return User.badUser();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return user;
    }


    public static ReturnValue deleteUser(User user) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM Users " +

                            "where id = ?");
            pstmt.setInt(1, user.getId());
            if ( pstmt.executeUpdate()==0)
                return NOT_EXISTS;
            //System.out.println("deleted " + affectedRows + " rows");
        } catch (SQLException e) {
            e.printStackTrace();
            return catcher(e);

        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;

    }

    public static ReturnValue addEvent(Event mimouna) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {


            pstmt = connection.prepareStatement("INSERT INTO Event" +
                    " VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1,mimouna.getId());
            pstmt.setString(2, mimouna.getUserName());
            pstmt.setString(3,mimouna.getFamilyName());
            pstmt.setString(4,mimouna.getCity());
            pstmt.setInt(5,0);
            pstmt.setBoolean(6,false);
            pstmt.execute();

        } catch (SQLException e) {
            //e.printStackTrace()();
            return catcher(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static Event getEvent(Integer mimounaId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        Event mimouna = new Event();
        try {
            pstmt = connection.prepareStatement("SELECT * FROM Event WHERE id =" + mimounaId);
            ResultSet results = pstmt.executeQuery();
            results.next(); //added
            mimouna.setId(results.getInt(1));
            mimouna.setUserName(results.getString(2));
            mimouna.setFamilyName(results.getString(3));
            mimouna.setCity(results.getString(4));
            mimouna.setGuestCount(results.getInt(5));
            mimouna.setPoliticianComing(results.getBoolean(6));
            //DBConnector.printResults(results);
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace()();
            return Event.badEvent();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return mimouna;
    }

    public static ReturnValue deleteEvent(Event mimouna) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM  Event" +

                            " WHERE id = ?");
            pstmt.setInt(1,mimouna.getId());
            int deleted = pstmt.executeUpdate(); // added
            if(deleted == 0) return NOT_EXISTS; //added
        } catch (SQLException e) {
            //e.printStackTrace()();
            return catcher(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();

            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;

    }


    public static ReturnValue addEventlist(EventList mimounaList) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO EventList" +
                    " VALUES (?, ?)");
            pstmt.setInt(1, mimounaList.getId());
            pstmt.setString(2, mimounaList.getCity());
            pstmt.execute();

        } catch (SQLException e) {
            return catcher(e);
            //e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static EventList getEventlist(Integer mimounalistId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        EventList mimouna_list = new EventList();
        try {
            pstmt = connection.prepareStatement("SELECT "+"id, city"+" FROM EventList WHERE id = (?)");
            pstmt.setInt(1, mimounalistId);
            ResultSet results = pstmt.executeQuery();
            results.next(); //added
            mimouna_list.setId(results.getInt(1));
            mimouna_list.setCity(results.getString(2));
            results.close();

        } catch (SQLException e) {
            return EventList.badEventlist();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return mimouna_list;
    }

    public static ReturnValue deleteEventlist(EventList mimounalist) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM EventList " +

                            "where id = ?");
            pstmt.setInt(1, mimounalist.getId());

            //int affectedRows =
            int deleted =  pstmt.executeUpdate(); //added
            if(deleted == 0) return NOT_EXISTS; //added
            //System.out.println("deleted " + affectedRows + " rows");
        } catch (SQLException e) {
            return catcher(e);
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue attendEvent(Integer mimounaId, Integer guests){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Event " +
                            "SET guestCount = guestCount + ? " +
                            "where id = ?");
            pstmt.setInt(1,guests);
            pstmt.setInt(2, mimounaId);
            int updated = pstmt.executeUpdate(); //added
            if(updated == 0) return NOT_EXISTS;
        } catch (SQLException e) {
            //e.printStackTrace();
            return catcher(e);
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue confirmAttendancePoliticianToEvent(Integer mimounaId, Integer userId){
        if (getUserProfile(userId).getId() == Event.badEvent().getId())
            return NOT_EXISTS;
        if (!getUserProfile(userId).getPolitician())
            return BAD_PARAMS;
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Event " +
                            "SET isPoliticianComing = ? " +
                            "where id = ?");
            pstmt.setBoolean(1,true);
            pstmt.setInt(2, mimounaId);
            int updated = pstmt.executeUpdate(); //added
            if(updated == 0) return NOT_EXISTS; //added
        } catch (SQLException e) {
            return catcher(e);
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;

    }

    public static ReturnValue addEventToEventlist(Integer mimounaId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;


        try {


            pstmt = connection.prepareStatement("INSERT INTO InList " +
                    " SELECT Event.id AS mimouna_id , EventList.id AS mimouna_list_id" +
                    " FROM EventList , Event" +
                    " WHERE Event.id = ? AND EventList.id = ? AND Event.city = EventList.city ");
            pstmt.setInt(1,mimounaId);
            pstmt.setInt(2,mimounalistId);
            if(pstmt.executeUpdate()==0)
                return BAD_PARAMS;

        } catch (SQLException e) {
            //e.printStackTrace();
            return catcher(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;


    }

    public static ReturnValue removeEventFromEventlist(Integer mimounaId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;

        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM  InList " +

                            "WHERE mimouna_id = ? AND mimouna_list_id = ?");
            pstmt.setInt(1, mimounaId);
            pstmt.setInt(2, mimounalistId);
            int deleted = pstmt.executeUpdate(); // added
            if(deleted == 0) return NOT_EXISTS; // added

        } catch (SQLException e) {
            //e.printStackTrace()();
            return catcher(e);
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();

            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;

    }

    public static ReturnValue followEventlist(Integer userId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement("INSERT INTO Follows" +
                    " VALUES (?, ?)");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, mimounalistId);
            pstmt.execute();

        } catch (SQLException e) {
            return catcher(e);
            //e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;
    }

    public static ReturnValue stopFollowEventlist(Integer userId, Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(
                    "DELETE FROM Follows " +

                            "WHERE user_id = ? AND mimouna_list_id = ?");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, mimounalistId);

            //int affectedRows =
            int deleted = pstmt.executeUpdate(); // added
            if(deleted == 0) return NOT_EXISTS; // added
            //System.out.println("deleted " + affectedRows + " rows");
        } catch (SQLException e) {
            return catcher(e);
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return OK;

    }

    public static Integer getEventlistTotalGuests(Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int guest_num = 0;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT SUM(guestCount) FROM Event WHERE id IN( SELECT mimouna_id FROM InList WHERE mimouna_list_id = ?)"
            );
            pstmt.setInt(1,mimounalistId);
            ResultSet results = pstmt.executeQuery();

            results.next();
            guest_num = results.getInt(1);
            //DBConnector.printResults(results);
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace()();
            return 0;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return guest_num;
    }

    public static Integer getEventlistFollowersCount(Integer mimounalistId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int count = 0;
        try {
            pstmt = connection.prepareStatement(

                    "SELECT COUNT(user_id) FROM Follows " + "WHERE mimouna_list_id = ?");
            pstmt.setInt(1, mimounalistId);
            ResultSet results = pstmt.executeQuery();
            results.next(); //added
            count = results.getInt(1);
        } catch (SQLException e) {
            return count;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return count;

    }

    public static String getMostKnownEvent(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        String most_known_family_name;
        try {
            pstmt = connection.prepareStatement(
                    "SELECT familyname FROM InList, Event " +
                            " WHERE mimouna_id = id GROUP BY id " +
                            " ORDER BY COUNT(mimouna_list_id) DESC , id DESC" +
                            " LIMIT 1");
            ResultSet results = pstmt.executeQuery();
            results.next();
            most_known_family_name = results.getString(1);
        } catch (SQLException e) {
            return "";
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return most_known_family_name;


    }

    public static Integer getMostPopularEventlist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int most_popular_mimouna_list_id = 0,most_popular_guests=0;
        try {
            pstmt = connection.prepareStatement(

                    "SELECT COALESCE("+
                            "(SELECT EventList.id FROM EventList, InList, Event" +
                            " WHERE mimouna_id = Event.id AND EventList.id = mimouna_list_id" +
                            " AND Event.guestCount > 0 "+
                            " GROUP BY EventList.id" +
                            " ORDER BY SUM(guestCount) DESC, EventList.id DESC" +
                            " LIMIT 1)," +
                            "(SELECT EventList.id FROM EventList " +
                            "ORDER BY EventList.id DESC " +"LIMIT 1))");

            ResultSet results = pstmt.executeQuery();
            boolean not_empty = results.next();
            if(not_empty) {
                most_popular_mimouna_list_id = results.getInt(1);
            } else {
                most_popular_mimouna_list_id = 0;
            }

        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return most_popular_mimouna_list_id;

    }

    /*
    public static Integer getMostPopularEventlist(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int most_popular_mimouna_list_id = 0,most_popular_guests=0;
        try {
            pstmt = connection.prepareStatement(

                    " SELECT EventList.id, SUM(guestCount) FROM EventList, InList, Event " +
                            " WHERE mimouna_id = Event.id AND EventList.id = mimouna_list_id" +
                            " GROUP BY EventList.id " +
                            " ORDER BY SUM(guestCount) DESC , EventList.id DESC " +
                            " LIMIT 1");

            ResultSet results = pstmt.executeQuery();
            boolean is_not_empty = results.next();
            if (is_not_empty) {
                most_popular_guests = results.getInt(2);
            }
            if (is_not_empty == false || most_popular_guests ==0) {
                pstmt = connection.prepareStatement(

                        " SELECT EventList.id FROM EventList " +
                                " ORDER BY EventList.id DESC " +
                                " LIMIT 1");

                results = pstmt.executeQuery();
                results.next();
            }
            most_popular_mimouna_list_id = results.getInt(1);
            results.next();



        } catch (SQLException e) {
            return 0;
            //e.printStackTrace()();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return most_popular_mimouna_list_id;

    }
     */

    public static ArrayList<Integer> getMostRatedEventList(){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> most_rated_mimouna_list = new ArrayList<Integer>();
        try {
            pstmt = connection.prepareStatement("SELECT mimouna_list_id , (SUM(guestCount)+COUNT(guestCount)) AS rating " +
                    "FROM InList,Event WHERE mimouna_id = id "+
                    "GROUP BY mimouna_list_id ORDER BY rating DESC , mimouna_list_id ASC "+
                    "LIMIT 10"
            );
            ResultSet results = pstmt.executeQuery();
            while (results.next()){
                most_rated_mimouna_list.add(results.getInt(1));
            }


            //DBConnector.printResults(results);
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return most_rated_mimouna_list;
    }


    public static ArrayList<Integer> getCloseUsers(Integer userId){
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        int g = 0;
        ArrayList<Integer> closest_users = new ArrayList<Integer>();
        try {
            ResultSet results;/*
            pstmt = connection.prepareStatement(
                    "CREATE VIEW users_count AS\n "+
                            " SELECT COUNT(mimouna_list_id) FROM Follows WHERE user_id = (?)" );
            pstmt.setInt(1,userId);
            //pstmt.setInt(1, userId);
            results = pstmt.executeQuery();
            pstmt = connection.prepareStatement(
                            " CREATE VIEW mimouna_lists_of_userId AS\n" +
                            " (SELECT mimouna_list_id FROM Follows WHERE user_id = (?))");
            pstmt.setInt(1, userId);
            results = pstmt.executeQuery();*/
            pstmt = connection.prepareStatement(
                    " SELECT user_id FROM Follows WHERE mimouna_list_id IN (SELECT mimouna_list_id FROM Follows WHERE user_id = (?))" +
                            " GROUP BY user_id " +
                            " HAVING COUNT(mimouna_list_id) >= 0.67 * (SELECT COUNT(mimouna_list_id) FROM Follows WHERE user_id = (?))" +
                            " AND user_id != (?)" +
                            " ORDER BY COUNT(mimouna_list_id) DESC , user_id ASC  " +
                            " LIMIT 10");
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, userId);

            results = pstmt.executeQuery();
            while (results.next()){
                closest_users.add(results.getInt(1));
            }
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return closest_users;
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return closest_users;

    }

    public static ArrayList<Integer> getEventListRecommendation (Integer userId) {

        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> mimouna_list_recommendation = new ArrayList<Integer>();
        try {
            pstmt = connection.prepareStatement(/*"CREATE VIEW mimouna_lists_of_userId AS\n" +
                    "SELECT mimouna_list_id FROM Follows WHERE user_id = (?);"
                    + "CREATE VIEW users AS\n " +
                    "SELECT user_id FROM Follows WHERE mimouna_list_id IN [mimouna_lists_of_userId];" +
                    "CREATE VIEW users_count AS\n " +
                    "SELECT COUNT(mimouna_list_id) FROM Follows WHERE user_id = (?);" +*/

                    "SELECT mimouna_list_id FROM Follows " +
                            " WHERE user_id IN (SELECT user_id FROM Follows WHERE mimouna_list_id IN (SELECT mimouna_list_id FROM Follows WHERE user_id = (?))" +
                            " GROUP BY user_id " +
                            " HAVING COUNT(mimouna_list_id) > 0.67 * (SELECT COUNT(mimouna_list_id) FROM Follows WHERE user_id = (?))" +
                            " AND user_id != (?) ORDER BY COUNT(mimouna_list_id) DESC , user_id ASC ) " +
                            " AND  mimouna_list_id NOT IN (SELECT mimouna_list_id FROM Follows WHERE user_id = (?))" +
                            " GROUP BY mimouna_list_id ORDER BY COUNT(user_id) DESC , mimouna_list_id ASC " +
                            " LIMIT 3"
            );
            pstmt.setInt(1, userId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, userId);
            pstmt.setInt(4, userId);

            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                mimouna_list_recommendation.add(results.getInt(1));
            }


            //DBConnector.printResults(results);
            results.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return mimouna_list_recommendation;
    }

    public static ArrayList<Integer> getTopPoliticianEventList(Integer userId) {
        Connection connection = DBConnector.getConnection();
        PreparedStatement pstmt = null;
        ArrayList<Integer> top_politician_mimouna_list = new ArrayList<Integer>();
        try {
            pstmt = connection.prepareStatement("SELECT mimouna_list_id " +
                    " FROM InList,Event,Users" +
                    " WHERE mimouna_id = Event.id AND Users.id = ? AND politician = FALSE AND Users.city = Event.city"+
                    " GROUP BY mimouna_list_id " +
                    " HAVING bool_or(isPoliticianComing)= true " +
                    " ORDER BY SUM(guestCount) DESC , mimouna_list_id ASC  "+
                    " LIMIT 10"
            );
            pstmt.setInt(1,userId);
            ResultSet results = pstmt.executeQuery();
            while (results.next()){
                top_politician_mimouna_list.add(results.getInt(1));
            }


            //DBConnector.printResults(results);
            results.close();

        } catch (SQLException e) {
            //e.printStackTrace();
        }
        finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                //e.printStackTrace()();
            }
        }
        return top_politician_mimouna_list;
    }

}

