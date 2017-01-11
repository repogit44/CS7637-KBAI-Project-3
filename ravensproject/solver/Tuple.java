package ravensproject.solver;

public class Tuple<L, R> {
    private L left;
    private R right;

    public Tuple(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + left.hashCode();
        hashCode = 31 * hashCode + right.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!this.getClass().isInstance(obj)) return false;

        Tuple<L, R> tuple = (Tuple<L, R>) obj;
        return left.equals(tuple.left) && right.equals(tuple.right);
    }

    @Override
    public String toString() {
        return left.toString() + "to" + right.toString();
    }
}
