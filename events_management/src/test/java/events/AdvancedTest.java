package events;

import events.Solution;
import events.business.Event;
import events.business.EventList;
import events.business.ReturnValue;
import events.business.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static events.business.ReturnValue.OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdvancedTest extends AbstractTest {
    private ReturnValue res;

    @Before
    public void initDatabase() {
        User u1 = new User();
        u1.setId(1);
        u1.setName("Raymond");
        u1.setCity("NY");
        u1.setPolitician(false);

        Solution.addUser(u1);

        User u2 = new User();
        u2.setId(2);
        u2.setName("Debra");
        u2.setCity("NY");
        u2.setPolitician(false);

        Solution.addUser(u2);

        User u3 = new User();
        u3.setId(3);
        u3.setName("Robert");
        u3.setCity("NY");
        u3.setPolitician(false);

        Solution.addUser(u3);

        User u4 = new User();
        u4.setId(4);
        u4.setName("Marie");
        u4.setCity("NY");
        u4.setPolitician(false);

        Solution.addUser(u4);

        User u5 = new User();
        u5.setId(5);
        u5.setName("Frank");
        u5.setCity("NY");
        u5.setPolitician(true);

        Solution.addUser(u5);

        Event m1 = new Event();
        m1.setId(1);
        m1.setUserName("Raymond");
        m1.setFamilyName("Barone");
        m1.setCity("NY");

        Solution.addEvent(m1);

        Event m2 = new Event();
        m2.setId(2);
        m2.setUserName("Robert");
        m2.setFamilyName("Barone");
        m2.setCity("NY");

        Solution.addEvent(m2);

        Event m3 = new Event();
        m3.setId(3);
        m3.setUserName("Hank");
        m3.setFamilyName("MacDougall");
        m3.setCity("NY");

        Solution.addEvent(m3);

        Event m4 = new Event();
        m4.setId(4);
        m4.setUserName("Lois");
        m4.setFamilyName("Whelan");
        m4.setCity("NY");

        Solution.addEvent(m4);

        Event m5 = new Event();
        m5.setId(5);
        m5.setUserName("Bernie");
        m5.setFamilyName("Gruenfelder");
        m5.setCity("NY");

        Solution.addEvent(m5);

        EventList ml1 = new EventList();
        ml1.setId(1);
        ml1.setCity("NY");

        Solution.addEventlist(ml1);

        EventList ml2 = new EventList();
        ml2.setId(2);
        ml2.setCity("NY");

        Solution.addEventlist(ml2);

        EventList ml3 = new EventList();
        ml3.setId(3);
        ml3.setCity("NY");

        Solution.addEventlist(ml3);
    }

    @Test
    public void testGetMostRatedEventList() {
        ArrayList<Integer> res_arr;

        res_arr = Solution.getMostRatedEventList();
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.addEventToEventlist(1, 1);
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Collections.singletonList(1), res_arr);

        res = Solution.addEventToEventlist(2, 2);
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 2), res_arr);

        res = Solution.addEventToEventlist(3, 3);
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.addEventToEventlist(4, 3);
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(3, 1, 2), res_arr);

        res = Solution.addEventToEventlist(5, 2);
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(2, 3, 1), res_arr);

        res = Solution.attendEvent(1, 1);
        assertEquals(OK, res);

        // list 2 and 3 have each 2 mimounas with 0 guests each.
        // list 1 has one mimouna with 1 guest.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.confirmAttendancePoliticianToEvent(2, 5); // politicians make no difference
        assertEquals(OK, res);

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.attendEvent(1, -1);
        assertEquals(OK, res);

        // list 2 and 3 have each 2 mimounas with 0 guests each.
        // list 1 has 0 mimouna with 0 guest.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(2, 3, 1), res_arr);

        res = Solution.attendEvent(1, 2);
        assertEquals(OK, res);

        // list 2 and 3 have each 2 mimounas with 0 guests each.
        // list 1 has one mimouna with 2 guests.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.attendEvent(5, 2);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 2 + 0 + 2; list3: 2 + 0 + 0.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(2, 1, 3), res_arr);

        res = Solution.attendEvent(4, 1);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 2 + 0 + 2; list3: 2 + 0 + 1.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(2, 1, 3), res_arr);

        res = Solution.addEventToEventlist(5, 3);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 2 + 0 + 2; list3: 3 + 0 + 1 + 2.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(3, 2, 1), res_arr);

        Event res_m = Solution.getEvent(5);
        assertNotEquals(Event.badEvent(), res_m);

        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 1 + 0; list3: 2 + 0 + 1.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 3, 2), res_arr);

        res = Solution.addEvent(res_m);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 1 + 0; list3: 2 + 0 + 1.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 3, 2), res_arr);

        res_m = Solution.getEvent(2);
        assertNotEquals(Event.badEvent(), res_m);

        res = Solution.deleteEvent(res_m);
        assertEquals(OK, res);

        // list1: 1 + 2; list2: 0; list3: 2 + 0 + 1.
        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1, 3), res_arr);
    }

    @Test
    public void testListLargerThat10_A() {
        for (int i = 0; i < 20; ++i) {
            EventList ml = new EventList();
            ml.setId(1000 + i);
            ml.setCity("NY");

            res = Solution.addEventlist(ml);
            assertEquals(OK, res);
        }

        ArrayList<Integer> res_arr;

        res_arr = Solution.getMostRatedEventList();
        assertEquals(new ArrayList<Integer>(), res_arr);

        for (int i = 0; i < 20; ++i) {
            res = Solution.addEventToEventlist(1, 1000 + i);
            assertEquals(OK, res);
        }

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009), res_arr);

        for (int i = 0; i < 20; ++i) {
                Event m = new Event();
                m.setId(i*1000 + 1000);
                m.setCity("NY");
                m.setUserName("SomeName");
                m.setFamilyName("SomeFName");

                res = Solution.addEvent(m);
                assertEquals(OK, res);

                res = Solution.attendEvent(m.getId(), i);
                assertEquals(OK, res);

                res = Solution.addEventToEventlist(m.getId() , 1000 + i);
                assertEquals(OK, res);
        }

        res_arr = Solution.getMostRatedEventList();
        assertEquals(Arrays.asList(1019, 1018, 1017, 1016, 1015, 1014, 1013, 1012, 1011, 1010), res_arr);
    }

    @Test
    public void testGetCloseUsers() {
        ArrayList<Integer> res_arr;

        res_arr = Solution.getCloseUsers(-5);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getCloseUsers(49);
        assertEquals(new ArrayList<Integer>(), res_arr);

        // User with 0 follows has no close users
        res_arr = Solution.getCloseUsers(1);
        assertEquals(new ArrayList<>(), res_arr);

        for (int i = 2; i <= 5; ++i) {
            res = Solution.followEventlist(i, i % 3 + 1);
            assertEquals(OK, res);
        }
        res_arr = Solution.getCloseUsers(1);
        assertEquals(new ArrayList<>(), res_arr);

        /*
        1 follows nothing.
        2 follows 3.
        3 follows 1.
        4 follows 2.
        5 follows 3.
         */

        res = Solution.followEventlist(1, 1);
        assertEquals(OK, res);

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Collections.singletonList(3), res_arr);

        for (int i = 2; i <= 5; ++i) {
            if (i != 3) {
                res = Solution.followEventlist(i, 1);
                assertEquals(OK, res);
            }
        }

        /*
        1 follows 1.
        2 follows 1, 3.
        3 follows 1.
        4 follows 1, 2.
        5 follows 1, 3.
         */


        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3, 4, 5), res_arr);

        res_arr = Solution.getCloseUsers(2);
        assertEquals(Collections.singletonList(5), res_arr);

        res_arr = Solution.getCloseUsers(3);
        assertEquals(Arrays.asList(1, 2, 4, 5), res_arr);

        res_arr = Solution.getCloseUsers(4);
        assertEquals(new ArrayList<>(), res_arr);

        res_arr = Solution.getCloseUsers(5);
        assertEquals(Collections.singletonList(2), res_arr);


        res = Solution.followEventlist(5, 2);
        assertEquals(OK, res);

        /*
        1 follows 1.
        2 follows 1, 3.
        3 follows 1.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(5);
        assertEquals(new ArrayList<>(), res_arr);

        res_arr = Solution.getCloseUsers(2);
        assertEquals(Collections.singletonList(5), res_arr);

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3, 4, 5), res_arr);

        res = Solution.followEventlist(2, 2);
        assertEquals(OK, res);

        res = Solution.followEventlist(3, 3);
        assertEquals(OK, res);

        /*
        1 follows 1.
        2 follows 1, 2, 3.
        3 follows 1, 3.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3, 4, 5), res_arr);

        res = Solution.followEventlist(1, 2);
        assertEquals(OK, res);

        /*
        1 follows 1, 2.
        2 follows 1, 2, 3.
        3 follows 1, 3.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 4, 5), res_arr);

        res = Solution.followEventlist(1, 3);
        assertEquals(OK, res);

        /*
        1 follows 1, 2, 3.
        2 follows 1, 2, 3.
        3 follows 1, 3.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 5), res_arr);

        res = Solution.followEventlist(3, 2);
        assertEquals(OK, res);

        /*
        1 follows 1, 2, 3.
        2 follows 1, 2, 3.
        3 follows 1, 2, 3.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3, 5), res_arr);

        User res_u = Solution.getUserProfile(3);
        assertNotEquals(User.badUser(), res_u);

        res = Solution.deleteUser(res_u);
        assertEquals(OK, res);

        /*
        1 follows 1, 2, 3.
        2 follows 1, 2, 3.
        3 doesn't exist
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 5), res_arr);

        res = Solution.addUser(res_u);
        assertEquals(OK, res);

        /*
        1 follows 1, 2, 3.
        2 follows 1, 2, 3.
        3 follows nothing.
        4 follows 1, 2.
        5 follows 1, 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 5), res_arr);

        EventList res_ml = Solution.getEventlist(1);
        assertNotEquals(EventList.badEventlist(), res_ml);

        res = Solution.deleteEventlist(res_ml);
        assertEquals(OK, res);

        /*
        1 follows 2, 3.
        2 follows 2, 3.
        3 follows nothing.
        4 follows 2.
        5 follows 2, 3.
         */
        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 5), res_arr);

        res = Solution.addEventlist(res_ml);
        assertEquals(OK, res);

        /*
        1 follows 2, 3.
        2 follows 2, 3.
        3 follows nothing.
        4 follows 2.
        5 follows 2, 3.
         */
        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 5), res_arr);

        res = Solution.followEventlist(1, 1);
        assertEquals(OK, res);

        /*
        1 follows 1, 2, 3.
        2 follows 2, 3.
        3 follows nothing.
        4 follows 2.
        5 follows 2, 3.
         */

        res_arr = Solution.getCloseUsers(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getCloseUsers(2);
        assertEquals(Arrays.asList(1, 5), res_arr);
    }

    @Test
    public void testGetCloseUsers2() {
        // just a quick test to check the order of the list
        // didn't want to mess with the previous test
        res = Solution.followEventlist(1, 1);
        assertEquals(OK, res);

        res = Solution.followEventlist(2, 1);
        assertEquals(OK, res);

        res = Solution.followEventlist(3, 1);
        assertEquals(OK, res);

        res = Solution.followEventlist(3, 2);
        assertEquals(OK, res);

        ArrayList<Integer> res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3), res_arr);

        res = Solution.stopFollowEventlist(3, 2);
        assertEquals(OK, res);

        res = Solution.followEventlist(2, 2);
        assertEquals(OK, res);

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3), res_arr);
    }


    @Test
    public void testListLargerThat10_B() {
        for (int i = 0; i < 20; ++i) {
            User u = new User();
            u.setId(1000 + i);
            u.setName("SomeName");
            u.setCity("SomeCity");

            res = Solution.addUser(u);
            assertEquals(OK, res);
        }

        ArrayList<Integer> res_arr;

        for (int i = 1; i <= 5; ++i) {
            res = Solution.followEventlist(i, 1);
            assertEquals(OK, res);
        }

        for (int i = 1000; i < 1020; ++i) {
            res = Solution.followEventlist(i, 1);
            assertEquals(OK, res);
        }

        res_arr = Solution.getCloseUsers(1);
        assertEquals(Arrays.asList(2, 3, 4, 5, 1000, 1001, 1002, 1003, 1004, 1005), res_arr);

        res_arr = Solution.getCloseUsers(4);
        assertEquals(Arrays.asList(1, 2, 3, 5, 1000, 1001, 1002, 1003, 1004, 1005), res_arr);

        res_arr = Solution.getCloseUsers(1003);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 1000, 1001, 1002, 1004, 1005), res_arr);

        res_arr = Solution.getCloseUsers(1019);
        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 1000, 1001, 1002, 1003, 1004), res_arr);

        res = Solution.followEventlist(1019, 2);
        assertEquals(OK, res);

        res_arr = Solution.getCloseUsers(1019);
        assertEquals(new ArrayList<Integer>(), res_arr);

        for (int i = 5; i < 19; ++i) {
            res = Solution.followEventlist(1000 + i, 2);
            assertEquals(OK, res);
        }

        res_arr = Solution.getCloseUsers(1019);
        assertEquals(Arrays.asList(1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014), res_arr);

    }

    @Test
    public void testGetEventListRecommendation() {
        ArrayList<Integer> res_arr;

        res_arr = Solution.getEventListRecommendation(700);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getEventListRecommendation(-5);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        EventList ml = new EventList();
        ml.setId(80);
        ml.setCity("NY");

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        res = Solution.followEventlist(2, 3);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(new ArrayList<>(), res_arr);

        for (int i = 1; i <= 5; ++i) {
            res = Solution.followEventlist(i, 80);
            assertEquals(OK, res);
        }

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(Collections.singletonList(3), res_arr);

        res = Solution.followEventlist(3, 2);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(Arrays.asList(2, 3), res_arr);

        res = Solution.followEventlist(4, 1);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.followEventlist(1, 1);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1); // only 4 is close, but mimounalist 1 is already followed by 1
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.followEventlist(4, 2);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(Collections.singletonList(2), res_arr);

        res = Solution.stopFollowEventlist(1, 1);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(1); // mimouna 2 is followed twice
        assertEquals(Arrays.asList(2, 1, 3), res_arr);
    }

    @Test
    public void testListLargerThan3() {
        ArrayList<Integer> res_arr;

        EventList ml = new EventList();
        ml.setId(80);
        ml.setCity("NY");

        res = Solution.addEventlist(ml);
        assertEquals(OK, res);

        for (int i = 1; i <= 5; ++i) {
            res = Solution.followEventlist(i, 80);
            assertEquals(OK, res);
        }

        for (int i = 0; i < 10; ++i) {
            ml = new EventList();
            ml.setId(1000 + i);
            ml.setCity("Haifa");

            res = Solution.addEventlist(ml);
            assertEquals(OK, res);

            res = Solution.followEventlist(1, ml.getId());
            assertEquals(OK, res);
        }

        res_arr = Solution.getEventListRecommendation(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getEventListRecommendation(2);
        assertEquals(Arrays.asList(1000, 1001, 1002), res_arr);

        for (int i = 1000; i < 1003; ++i) {
            res = Solution.followEventlist(2, i);
            assertEquals(OK, res);
        }

        res_arr = Solution.getEventListRecommendation(2);
        assertEquals(Arrays.asList(1003, 1004, 1005), res_arr);

        for (int i = 1000; i < 1003; ++i) {
            res = Solution.stopFollowEventlist(2, i);
            assertEquals(OK, res);
        }

        for (int i = 1006; i <= 1009; ++i) {
            res = Solution.followEventlist(3, i);
            assertEquals(OK, res);
        }

        res_arr = Solution.getEventListRecommendation(2);
        assertEquals(Arrays.asList(1006, 1007, 1008), res_arr);

        res = Solution.followEventlist(4, 1009);
        assertEquals(OK, res);

        res_arr = Solution.getEventListRecommendation(2);
        assertEquals(Arrays.asList(1009, 1006, 1007), res_arr);
    }

    @Test
    public void testGetTopPoliticianEventList() {

        Event m6 = new Event();
        m6.setId(6);
        m6.setUserName("Bernie");
        m6.setFamilyName("Gruenfelder");
        m6.setCity("NOT NY");

        Solution.addEvent(m6);

        EventList ml4 = new EventList();
        ml4.setId(4);
        ml4.setCity("NOT NY");

        Solution.addEventlist(ml4);

        ArrayList<Integer> res_arr;

        res_arr = Solution.getTopPoliticianEventList(500);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getTopPoliticianEventList(-5);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.addEventToEventlist(6, 4);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.confirmAttendancePoliticianToEvent(6, 5);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1); // mimouna 5 not in same city
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.addEventToEventlist(1, 1);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        res = Solution.confirmAttendancePoliticianToEvent(1, 5);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Collections.singletonList(1), res_arr);

        res_arr = Solution.getTopPoliticianEventList(5); // no list for politicians!
        assertEquals(new ArrayList<Integer>(), res_arr);

        for (int i = 2; i <= 5; ++i) {
            res = Solution.confirmAttendancePoliticianToEvent(i, 5);
            assertEquals(OK, res);
        }

        for (int i = 2; i <= 3; ++i) {
            res = Solution.addEventToEventlist(i, i);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        User res_u = Solution.getUserProfile(5);
        assertNotEquals(User.badUser(), res_u);

        res = Solution.deleteUser(res_u);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1); // removing the politician does not affect mimounas
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.addUser(res_u);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.removeEventFromEventlist(2, 2);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1, 3), res_arr);

        res = Solution.addEventToEventlist(2, 2);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1, 2, 3), res_arr);

        res = Solution.attendEvent(3, 50);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(3, 1, 2), res_arr);

        res = Solution.attendEvent(1, 50);
        assertEquals(OK, res);

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1, 3, 2), res_arr);
    }

    @Test
    public void testListLargerThan10_C() {
        ArrayList<Integer> res_arr = new ArrayList<>();

        for (int i = 1000; i < 1020; ++i) {
            EventList ml = new EventList();
            ml.setId(i);
            ml.setCity("NY");

            res = Solution.addEventlist(ml);
            assertEquals(OK, res);

            Event m = new Event();
            m.setId(i);
            m.setCity("NY");
            m.setUserName("Some name");
            m.setFamilyName("Some family");

            res = Solution.addEvent(m);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        for (int i = 1000; i < 1020; ++i) {
            res = Solution.confirmAttendancePoliticianToEvent(i, 5);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(new ArrayList<Integer>(), res_arr);

        for (int i = 1000; i < 1020; ++i) {
            res = Solution.addEventToEventlist(i, i);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009), res_arr);

        for (int i = 1008; i < 1020; ++i) {
            res = Solution.attendEvent(i, 50);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017), res_arr);

        for (int i = 1008; i < 1012; ++i) {
            res = Solution.addEventToEventlist(i, i + 3);
            assertEquals(OK, res);
        }

        res_arr = Solution.getTopPoliticianEventList(1);
        assertEquals(Arrays.asList(1011, 1012, 1013, 1014, 1008, 1009, 1010, 1015, 1016, 1017), res_arr);
    }
}