package process.helpers;

/**
 * Created by M. Bonon on 7/31/2015.
 */
public final class Triple<A, B, C> {
    public A first;
    public B second;
    public C third;

    public Triple(A first, B second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
