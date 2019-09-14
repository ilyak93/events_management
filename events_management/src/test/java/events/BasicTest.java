package events;

import events.Solution;
import events.business.Event;
import events.business.EventList;
import events.business.ReturnValue;
import events.business.User;
import org.junit.Before;
import org.junit.Test;

import static events.business.ReturnValue.*;
import static org.junit.Assert.*;

public class BasicTest extends AbstractTest{
    private ReturnValue res;

    @Before
    public void initDatabase() {
        User u1 = new User();
        u1.setId(236363);
        u1.setName("Benny");
        u1.setCity("Haifa");
        u1.setPolitician(false);

        Solution.addUser(u1);

        User u2 = new User();
        u2.setId(234123);
        u2.setName("Leonid");
        u2.setCity("Tel Aviv");
        u2.setPolitician(true);

        Solution.addUser(u2);

        Event m1 = new Event();
        m1.setId(236319);
        m1.setUserName("Yossi");
        m1.setFamilyName("Gil");
        m1.setCity("Jerusalem");

        Solution.addEvent(m1);

        Event m2 = new Event();
        m2.setId(234218);
        m2.setUserName("Tamer");
        m2.setFamilyName("Salman");
        m2.setCity("Naharia");

        Solution.addEvent(m2);

        Event m3 = new Event();
        m3.setId(236363);
        m3.setUserName("Benny");
        m3.setFamilyName("Kimelfeld");
        m3.setCity("Naharia");

        Solution.addEvent(m3);

        Event m4 = new Event();
        m4.setId(9999999);
        m4.setUserName("Benny");
        m4.setFamilyName("Kimelfeld2");
        m4.setCity("Naharia");

        Solution.addEvent(m4);

        Event m5 = new Event();
        m5.setId(88888);
        m5.setUserName("Benny");
        m5.setFamilyName("Kimelfeld");
        m5.setCity("Naharia");

        Solution.addEvent(m5);

        EventList ml1  = new EventList();
        ml1.setId(111);
        ml1.setCity("Naharia");

        Solution.addEventlist(ml1);

        EventList ml2  = new EventList();
        ml2.setId(112);
        ml2.setCity("Naharia");

        Solution.addEventlist(ml2);

        EventList ml3  = new EventList();
        ml3.setId(113);
        ml3.setCity("Naharia");

        Solution.addEventlist(ml3);
    }

    @Test
    public void testAttendMimount() {
        res = Solution.attendEvent(-5, 10);
        assertEquals(NOT_EXISTS, res);

        res = Solution.attendEvent(500, 10);
        assertEquals(NOT_EXISTS, res);

        res = Solution.attendEvent(500, -5);
        assertEquals(NOT_EXISTS, res);

        res = Solution.attendEvent(236363, 0);
        assertEquals(OK, res);

        Event res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 0);

        res = Solution.attendEvent(236363, -1);
        assertEquals(BAD_PARAMS, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 0);

        res = Solution.attendEvent(236363, 5);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 5);

        res = Solution.attendEvent(236363, 6);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 11);

        res = Solution.attendEvent(236363, -12);
        assertEquals(BAD_PARAMS, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 11);

        res = Solution.attendEvent(236363, -7);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 4);

        res = Solution.attendEvent(236363, 0);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 4);

        res = Solution.attendEvent(236363, -5);
        assertEquals(BAD_PARAMS, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 4);

        res = Solution.attendEvent(236363, -4);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertEquals(res_m.getGuestCount(), 0);
    }

    @Test
    public void testConfirmAttendancePoliticianToEvent() {
        res = Solution.confirmAttendancePoliticianToEvent(555, 556);
        assertEquals(NOT_EXISTS, res);

        res = Solution.confirmAttendancePoliticianToEvent(236363, 556);
        assertEquals(NOT_EXISTS, res);

        Event res_m = Solution.getEvent(236363);
        assertFalse(res_m.getIsPoliticianComing());

        res = Solution.confirmAttendancePoliticianToEvent(555, 234123);
        assertEquals(NOT_EXISTS, res);

        res = Solution.confirmAttendancePoliticianToEvent(236363, 234123);
        assertEquals(OK, res);

        res = Solution.confirmAttendancePoliticianToEvent(236363, 234123);
        assertEquals(OK, res);

        res = Solution.confirmAttendancePoliticianToEvent(236363, -5);
        assertEquals(NOT_EXISTS, res);

        res = Solution.confirmAttendancePoliticianToEvent(236363, 236363);
        assertEquals(BAD_PARAMS, res);

        res_m = Solution.getEvent(236363);
        assertTrue(res_m.getIsPoliticianComing());
        assertEquals(0, res_m.getGuestCount());

        res = Solution.confirmAttendancePoliticianToEvent(236363, 234123);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363);
        assertTrue(res_m.getIsPoliticianComing());

        res = Solution.confirmAttendancePoliticianToEvent(236363, 236363);
        assertEquals(BAD_PARAMS, res);

        res_m = Solution.getEvent(236363);
        assertTrue(res_m.getIsPoliticianComing());

        User res_u = Solution.getUserProfile(234123);
        assertNotEquals(User.badUser(), res_u);

        res = Solution.deleteUser(res_u);
        assertEquals(OK, res);

        res_m = Solution.getEvent(236363); // deletion of the politician does not affect mimouna.
        assertTrue(res_m.getIsPoliticianComing());

    }

    @Test
    public void testAddEventToEventList() {
        res = Solution.addEventToEventlist(123, 456);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236363, 456);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(123, 111);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236319, 111);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236319, 111);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236319, 111);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);

        Event res_m = Solution.getEvent(236363); // mimouna deletion should cascade it
        assertNotEquals(Event.badEvent(), res_m);
        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(BAD_PARAMS, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.addEvent(res_m);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(ALREADY_EXISTS, res);

        EventList res_ml = Solution.getEventlist(111); // list deletion should cascade
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(BAD_PARAMS, res);

        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);

        res = Solution.addEventlist(res_ml);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(ALREADY_EXISTS, res);
    }

    @Test
    public void testRemoveEventFromEventList() {
        res = Solution.removeEventFromEventlist(500, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.removeEventFromEventlist(236363, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.removeEventFromEventlist(500, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEventToEventlist(236363, 112); // check that rest of database didn't change
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(ALREADY_EXISTS, res);
        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(ALREADY_EXISTS, res);

        Event res_m = Solution.getEvent(236363);
        assertNotEquals(Event.badEvent(), res_m);
        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEvent(res_m);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.removeEventFromEventlist(234218, 111);
        assertEquals(OK, res);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(ALREADY_EXISTS, res);

        EventList res_ml = Solution.getEventlist(111);
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res = Solution.removeEventFromEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeEventFromEventlist(236363, 112);
        assertEquals(OK, res);
        res = Solution.removeEventFromEventlist(234218, 112);
        assertEquals(OK, res);
        res = Solution.removeEventFromEventlist(236363, 112);
        assertEquals(NOT_EXISTS, res);
        res = Solution.removeEventFromEventlist(234218, 112);
        assertEquals(NOT_EXISTS, res);
    }

    @Test
    public void testFollowEventlist() {
        res = Solution.followEventlist(500, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(236363, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(500, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(111, 236363);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.followEventlist(234123, 111);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.followEventlist(234123, 111);
        assertEquals(ALREADY_EXISTS, res);

        User res_u = Solution.getUserProfile(236363);
        assertNotEquals(User.badUser(), res_u);
        res = Solution.deleteUser(res_u);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);
        res = Solution.followEventlist(234123, 111);
        assertEquals(ALREADY_EXISTS, res);

        res = Solution.addUser(res_u);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 112);
        assertEquals(OK, res);
        res = Solution.followEventlist(234123, 112);
        assertEquals(OK, res);

        EventList res_ml = Solution.getEventlist(111);
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);
        res = Solution.followEventlist(236363, 112);
        assertEquals(ALREADY_EXISTS, res);
    }

    @Test
    public void testStopFollowEventList() {
        res = Solution.stopFollowEventlist(500, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.stopFollowEventlist(236363, 600);
        assertEquals(NOT_EXISTS, res);

        res = Solution.stopFollowEventlist(500, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(111, 236363);
        assertEquals(NOT_EXISTS, res);

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.followEventlist(236363, 112);
        assertEquals(OK, res);

        res = Solution.followEventlist(234123, 111);
        assertEquals(OK, res);

        res = Solution.followEventlist(234123, 112);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res = Solution.stopFollowEventlist(236363, 112);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(234123, 111);
        assertEquals(OK, res);

        res = Solution.stopFollowEventlist(234123, 112);
        assertEquals(OK, res);
    }

    @Test
    public void testGetEventListTotalGuests() {
        Integer res_i = Solution.getEventlistTotalGuests(500);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(236363);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(0, res_i.intValue());

        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(0, res_i.intValue());

        res = Solution.attendEvent(236319, 50);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(0, res_i.intValue());

        res = Solution.attendEvent(234218, 60);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(60, res_i.intValue());

        res = Solution.attendEvent(234218, 60);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(120, res_i.intValue());

        res = Solution.attendEvent(236363, 15);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(135, res_i.intValue());

        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(135, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(120, res_i.intValue());

        res = Solution.attendEvent(234218, -40);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111);
        assertEquals(95, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(80, res_i.intValue());

        Event res_m = Solution.getEvent(234218);
        assertNotEquals(Event.badEvent(), res_m);
        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111); // cascades
        assertEquals(15, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(0, res_i.intValue());

        res_m.setGuestCount(0);
        res = Solution.addEvent(res_m);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111); // cascades
        assertEquals(15, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(0, res_i.intValue());

        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111); // cascades
        assertEquals(15, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(0, res_i.intValue());

        res = Solution.attendEvent(236363, 17);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);

        EventList res_ml = Solution.getEventlist(111);
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(111); // cascades
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(32, res_i.intValue());


        res_i = Solution.getEventlistTotalGuests(111); // cascades
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(32, res_i.intValue());

        res = Solution.confirmAttendancePoliticianToEvent(236363, 234123);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(112);
        assertEquals(32, res_i.intValue());

    }

    @Test
    public void testGetEventListFollowersCount() {
        Integer res_i = Solution.getEventlistFollowersCount(500);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistFollowersCount(236363);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(0, res_i.intValue());

        res = Solution.followEventlist(234211, 111);
        assertEquals(NOT_EXISTS, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(0, res_i.intValue());

        res = Solution.followEventlist(234123, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.followEventlist(234123, 111);
        assertEquals(ALREADY_EXISTS, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());


        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(2, res_i.intValue());

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.stopFollowEventlist(236363, 111);
        assertEquals(NOT_EXISTS, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(2, res_i.intValue());

        // test cascade
        User res_u = Solution.getUserProfile(234123);
        assertNotEquals(User.badUser(), res_u);
        res = Solution.deleteUser(res_u);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.addUser(res_u);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.followEventlist(236363, 112);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(112);
        assertEquals(1, res_i.intValue());

        EventList res_ml = Solution.getEventlist(111);
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistFollowersCount(112);
        assertEquals(1, res_i.intValue());

        res = Solution.addEventlist(res_ml);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getEventlistFollowersCount(112);
        assertEquals(1, res_i.intValue());

        res = Solution.followEventlist(234123, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(1, res_i.intValue());

        res = Solution.followEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getEventlistFollowersCount(111);
        assertEquals(2, res_i.intValue());

        res_i = Solution.getEventlistFollowersCount(112);
        assertEquals(1, res_i.intValue());
    }

    @Test
    public void testGetMostKnownEvent() {
        String res_s = Solution.getMostKnownEvent();
        assertEquals("", res_s);

        res_s = Solution.getMostKnownEvent();
        assertEquals("", res_s);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Kimelfeld", res_s);

        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Kimelfeld", res_s);

        res = Solution.addEventToEventlist(9999999, 111);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Kimelfeld2", res_s);

        Event res_m = Solution.getEvent(9999999);
        assertNotEquals(Event.badEvent(), res_m);
        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Kimelfeld", res_s);

        EventList res_ml = Solution.getEventlist(111);
        assertNotEquals(EventList.badEventlist(), res_ml);
        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Salman", res_s);

        res = Solution.addEventlist(res_ml);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Salman", res_s);

        res = Solution.addEventToEventlist(234218, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(234218, 113);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Salman", res_s);

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(236363, 113);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(88888, 112);
        assertEquals(OK, res);
        res = Solution.addEventToEventlist(88888, 113);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent(); /* lists contain 4 mimounas with family "Kimelfeld", but these are 2
                                                * separate ones, so mimouna of family "Salman" beats them */
        assertEquals("Salman", res_s);

        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);

        res_s = Solution.getMostKnownEvent();
        assertEquals("Kimelfeld", res_s);
    }

    @Test
    public void testGetMostPopularEventList() {
        Integer res_i;
        res_i = Solution.getMostPopularEventlist();
        assertEquals(113, res_i.intValue()); // all are 0 guests and this is highest id.

        res = Solution.addEventToEventlist(236363, 111);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(113, res_i.intValue());

        res = Solution.addEventToEventlist(234218, 112);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(113, res_i.intValue());

        res = Solution.attendEvent(236363, 40);
        assertEquals(OK, res);

        res_i = Solution.getEventlistTotalGuests(113);
        assertEquals(0, res_i.intValue());

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());

        res = Solution.attendEvent(234218, 40);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        res = Solution.attendEvent(234218, -1);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());

        res = Solution.addEventToEventlist(236363, 112);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        res = Solution.addEventToEventlist(88888, 111);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        res = Solution.attendEvent(88888, 40);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());

        Solution.attendEvent(234218, 500);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        Event res_m = Solution.getEvent(234218);
        assertNotEquals(Event.badEvent(), res_m);

        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());

        Event res_m2 = Solution.getEvent(88888);
        assertNotEquals(Event.badEvent(), res_m);

        res = Solution.deleteEvent(res_m2);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        res = Solution.addEventToEventlist(9999999, 111);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(112, res_i.intValue());

        res = Solution.attendEvent(9999999, 1);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());

        res_m.setGuestCount(5000);

        res = Solution.addEvent(res_m);
        assertEquals(OK, res);

        res_i = Solution.getMostPopularEventlist();
        assertEquals(111, res_i.intValue());
    }
}
