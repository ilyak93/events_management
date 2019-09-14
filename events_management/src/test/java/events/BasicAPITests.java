
package events;

import events.business.Event;
import events.business.EventList;
import events.business.ReturnValue;
import events.business.User;
import org.junit.Test;

import static events.business.ReturnValue.*;
import static org.junit.Assert.assertEquals;


public class BasicAPITests extends AbstractTest {
    @Test
    public void songPlayTest() {

        ReturnValue res;
        Event m = new Event();
        m.setId(1);
        m.setUserName("Yonatan");
        m.setCity("Tel Aviv");
        m.setFamilyName("Cohen");

        res = Solution.addEvent(m);
        assertEquals(OK, res);

        res = Solution.attendEvent(1, 5);
        assertEquals(OK, res);

        res = Solution.attendEvent(1, -19);
        assertEquals(BAD_PARAMS, res);
    }

    @Test
    public void followPlaylistTest() {

        ReturnValue res;
        EventList p = new EventList();
        p.setId(10);
        p.setCity("Tel Aviv");

        res = Solution.addEventlist(p);
        assertEquals(OK, res);

        User u = new User();
        u.setId(100);
        u.setName("Nir");
        u.setCity("Tel Aviv");
        u.setPolitician(false);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.followEventlist(100, 10);
        assertEquals(OK , res);

        res = Solution.followEventlist(100, 10);
        assertEquals(ALREADY_EXISTS , res);

        res = Solution.followEventlist(101, 10);
        assertEquals(NOT_EXISTS , res);

        User u2 = new User();
        u.setId(5);
        u.setName("Nir");
        u.setCity("Ramat Aviv");
        u.setPolitician(true);

        res = Solution.addUser(u);
        assertEquals(OK, res);

        res = Solution.followEventlist(5, 10);
        assertEquals(OK , res);
    }
    @Test
    public void attendEvent() {
        
        User u1 = new User();
        u1.setId(100);
        u1.setName("Nir");
        u1.setCity("Tel Aviv");
        u1.setPolitician(false);

        Event m = new Event();
        m.setId(1);
        m.setUserName("Yonatan");
        m.setCity("Tel Aviv");
        m.setFamilyName("Cohen");

        ReturnValue res = Solution.addEvent(m);
        assertEquals(OK , res);

        res = Solution.attendEvent(1,100);
        assertEquals(OK , res);

        User u2 = new User();
        u1.setId(101);
        u1.setName("Nir");
        u1.setCity("Ramat Aviv");
        u1.setPolitician(false);

        res = Solution.attendEvent(1,100);
        assertEquals(OK , res);
    }
}


