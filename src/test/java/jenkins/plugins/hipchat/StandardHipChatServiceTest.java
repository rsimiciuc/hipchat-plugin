package jenkins.plugins.hipchat;

import jenkins.plugins.zabbix.StandardZabbixService;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class StandardHipChatServiceTest {
    /*@Test
    public void publishWithBadHostShouldNotRethrowExceptions() {
        StandardZabbixService service = new StandardZabbixService("badhost", "token", "room", "from");
        service.publish("message");
    }

    @Test
    public void shouldBeAbleToOverrideHost() {
        StandardZabbixService service = new StandardZabbixService("some.other.host", "token", "room", "from");
        assertEquals("some.other.host", service.getServer());
    }

    @Test
    public void shouldSplitTheRoomIds() {
        StandardZabbixService service = new StandardZabbixService(null, "token", "room1,room2", "from");
        assertArrayEquals(new String[]{"room1", "room2"}, service.getRoomIds());
    }

    @Test
    public void shouldTrimTheRoomIds() {
        StandardZabbixService service = new StandardZabbixService(null, "token", "room1, room2", "from");
        assertArrayEquals(new String[]{"room1", "room2"}, service.getRoomIds());
    }

    @Test
    public void shouldNotSplitTheRoomsIfNullIsPassed() {
        StandardZabbixService service = new StandardZabbixService(null, "token", null, "from");
        assertArrayEquals(new String[0], service.getRoomIds());
    }

    @Test
    public void shouldBeAbleToOverrideFrom() {
        StandardZabbixService service = new StandardZabbixService(null, "token", "room", "from");
        assertEquals("from", service.getSendAs());
    }*/
}
