package process.helpers;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by M. Bonon on 7/19/2015.
 * From http://stackoverflow.com/questions/8938528/how-do-i-get-a-unique-id-per-object-in-java
 */
public abstract class Ided {
    protected static AtomicLong NEXT_ID = new AtomicLong(1);
    protected long id;

    public Ided() {
        id = NEXT_ID.getAndIncrement();
    }

    public long getId() {
        return id;
    }
}