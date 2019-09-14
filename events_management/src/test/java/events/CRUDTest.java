package events;

import events.Solution;
import events.business.Event;
import events.business.EventList;
import events.business.ReturnValue;
import events.business.User;
import org.junit.Test;

import static events.business.ReturnValue.*;
import static org.junit.Assert.*;

public class CRUDTest extends AbstractTest {

    private ReturnValue res;
    @Test
    public void testAddUser() {
        User u = new User();
        u.setId(236363);
        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        u.setCity("Tel Aviv");
        u.setName("Yossi");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);
        u.setId(234123);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        u.setId(0);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setId(-5);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setId(236319);
        u.setName(null);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setName("Yossi");
        u.setCity(null);

        res = Solution.addUser(u);
        assertEquals(BAD_PARAMS, res);

        u.setCity("Jerusalem");

        res = Solution.addUser(u);
        assertEquals(OK, res);
    }

    @Test
    public void testGetUserProfile() {
        res = Solution.addUser(User.badUser());
        assertEquals(BAD_PARAMS, res);

        User res_u;
        res_u = Solution.getUserProfile(236363);
        assertEquals(User.badUser(), res_u);

        User u = new User();
        u.setId(236363);
        u.setCity("Haifa");
        u.setName("Benny");
        u.setPolitician(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res_u = Solution.getUserProfile(236363);
        assertEquals(u, res_u);

        u.setCity("Tel Aviv");
        res = Solution.addUser(u);
        assertEquals(ALREADY_EXISTS, res);

        res_u = Solution.getUserProfile(236363);
        assertNotEquals(u, res_u);
        u.setCity("Haifa");
        assertEquals(u, res_u);

        res_u = Solution.getUserProfile(234123);
        assertEquals(User.badUser(), res_u);
    }

    @Test
    public void testDeleteUser() {
        res = Solution.deleteUser(User.badUser());
        assertEquals(NOT_EXISTS, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(false);

        res = Solution.deleteUser(User.badUser());
        assertEquals(NOT_EXISTS, res);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        User res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, u);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, User.badUser());

        res = Solution.deleteUser(u);
        assertEquals(NOT_EXISTS, res);

        res_u = Solution.getUserProfile(u.getId());
        assertEquals(res_u, User.badUser());

        res = Solution.addUser(u);
        assertEquals(OK, res);

        User u2 = new User();
        u2.setId(234123);
        u2.setName("Benny");
        u2.setCity("Haifa");
        u2.setPolitician(false);

        res = Solution.deleteUser(u2);
        assertEquals(NOT_EXISTS, res);

        u.setName("Still Benny");
        u.setCity("Still Haifa");
        u.setPolitician(true);

        res = Solution.deleteUser(u);
        assertEquals(OK, res); // id not changes, so it is indeed the same user
    }

    @Test
    public void testAddEvent() {
        res = Solution.addEvent(Event.badEvent());
        assertEquals(BAD_PARAMS, res);

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        m.setUserName("Not Benny");
        m.setFamilyName("Not Kimelfeld");
        m.setCity("Not Haifa");
        m.setGuestCount(499);
        m.setPoliticianComing(false);

        res = Solution.addEvent(m);
        assertEquals(ALREADY_EXISTS, res);

        m.setId(234123);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        m.setId(0);

        res = Solution.addEvent(m);
        assertEquals(BAD_PARAMS, res);

        m.setId(-3);

        res = Solution.addEvent(m);
        assertEquals(BAD_PARAMS, res);

        m.setId(236319);
        m.setUserName(null);

        res = Solution.addEvent(m);
        assertEquals(BAD_PARAMS, res);

        m.setUserName("Yossi");
        m.setFamilyName(null);

        res = Solution.addEvent(m);
        assertEquals(BAD_PARAMS, res);

        m.setFamilyName("Gil");
        m.setCity(null);

        res = Solution.addEvent(m); // City can't be null according to recent update
        assertEquals(BAD_PARAMS, res);

        m.setId(234218);
        m.setGuestCount(-5);
        m.setCity("Haifa");

        res = Solution.addEvent(m); // guests = -5 is invalid but add should overwrite that with 0
        assertEquals(OK, res);
    }



    @Test
    public void testGetEvent() {
        Event res_m = Solution.getEvent(236363);
        assertEquals(Event.badEvent(), res_m);

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236364);
        assertEquals(Event.badEvent(), res_m);

        res_m = Solution.getEvent(236363); // according to peleg, non zero guest count gets overwitten
        assertNotEquals(m, res_m);
        m.setPoliticianComing(false);       // same for true politician
        m.setGuestCount(0);
        assertEquals(m, res_m);
    }

    @Test
    public void testDeleteEvent() {
        res = Solution.deleteEvent(Event.badEvent());
        assertEquals(NOT_EXISTS, res);

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.deleteEvent(m);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        res = Solution.deleteEvent(m);
        assertEquals(OK, res);

        res = Solution.deleteEvent(m);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteEvent(m);
        assertEquals(OK, res);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteUser(u);
        assertEquals(OK, res);

        res = Solution.deleteEvent(m);
        assertEquals(OK, res);

        Event res_m = Solution.getEvent(m.getId());
        assertEquals(Event.badEvent(), res_m);

        res = Solution.deleteEvent(m);
        assertEquals(NOT_EXISTS, res);

        res_m = Solution.getEvent(m.getId());
        assertEquals(Event.badEvent(), res_m);
    }

    @Test
    public void testAddEventList() {
        res = Solution.addEventlist(EventList.badEventlist());
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventlist(EventList.badEventlist());
        assertEquals(BAD_PARAMS, res);

        EventList ml = new EventList();
        ml.setId(236363);
        ml.setCity("Haifa");

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        res = Solution.addEventlist(ml);
        assertEquals(ALREADY_EXISTS, res);

        ml.setCity("Tel Aviv");

        res = Solution.addEventlist(ml);
        assertEquals(ALREADY_EXISTS, res);

        ml.setCity("Haifa");
        ml.setId(234123);

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        ml.setId(0);

        res = Solution.addEventlist(ml);
        assertEquals(BAD_PARAMS, res);

        ml.setId(-4);

        res = Solution.addEventlist(ml);
        assertEquals(BAD_PARAMS, res);

        ml.setId(236319);
        ml.setCity(null);

        res = Solution.addEventlist(ml);
        assertEquals(BAD_PARAMS, res);

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);
    }

    @Test
    public void testGetEventList() {
        EventList res_ml = Solution.getEventlist(236363);
        assertEquals(EventList.badEventlist(), res_ml);

        EventList ml = new EventList();
        ml.setId(236363);
        ml.setCity("Haifa");

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        res_ml = Solution.getEventlist(234123);
        assertEquals(EventList.badEventlist(), res_ml);

        res_ml = Solution.getEventlist(236363);
        assertEquals(ml, res_ml);
    }

    @Test
    public void testDeleteEventList() {
        res = Solution.deleteEventlist(EventList.badEventlist());
        assertEquals(NOT_EXISTS, res);

        EventList ml = new EventList();
        ml.setId(236363);
        ml.setCity("Haifa");

        res = Solution.deleteEventlist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        res = Solution.deleteEventlist(ml);
        assertEquals(OK, res);

        res = Solution.deleteEventlist(ml);
        assertEquals(NOT_EXISTS, res);

        Event m = new Event();
        m.setId(236363);
        m.setUserName("Benny");
        m.setFamilyName("Kimelfeld");
        m.setCity("Haifa");
        m.setGuestCount(500);
        m.setPoliticianComing(true);

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        User u = new User();
        u.setId(236363);
        u.setName("Benny");
        u.setCity("Haifa");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.deleteEventlist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.deleteEventlist(ml);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        res = Solution.deleteEventlist(ml);
        assertEquals(OK, res);
    }

    @Test
    public void testDoubleDrop() {
        try {
            Solution.dropTables();
            Solution.dropTables();
            Solution.dropTables();
            Solution.dropTables();
            Solution.dropTables();
        } catch (Exception e) {
            fail(); // if this fails: you need to add support for trying to drop the same table twice
                    // do it with "DROP TABLE IF EXISTS" / "DROP VIEW IF EXISTS"
        } finally {
            Solution.createTables();
        }
    }
}
