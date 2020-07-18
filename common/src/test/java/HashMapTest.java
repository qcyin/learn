import java.util.HashMap;
import java.util.Map;

/**
 * @author yqc
 * @date 2020/6/714:56
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<Key, Object> map = new HashMap<>();
        Object o = new Object();
        Key key0 = new Key(1);
        Key key1 = new Key(1);
        Key key2 = new Key(1);
        Key key3 = new Key(1);
        Key key4 = new Key(1);
        Key key5 = new Key(1);
        Key key6 = new Key(1);
        Key key7 = new Key(1);
        Key key8 = new Key(1);
        Key key9 = new Key(1);
        Key key10 = new Key(1);
        Key key11 = new Key(1);
        Key key12 = new Key(1);
        Key key13 = new Key(1);
        Key key14 = new Key(1);
        Key key15 = new Key(1);
        Key key16 = new Key(1);
        Key key17 = new Key(1);
        Key key18 = new Key(1);
        Key key19 = new Key(1);
        Key key20 = new Key(1);

        map.put(key0, o);
        map.put(key1, o);
        map.put(key2, o);
        map.put(key3, o);
        map.put(key4, o);
        map.put(key5, o);
        map.put(key6, o);
        map.put(key7, o);
        map.put(key8, o);
        map.put(key9, o);
        map.put(key10, o);
        map.put(key11, o);
        map.put(key12, o);
        map.put(key13, o);
        map.put(key14, o);
        map.put(key15, o);
        map.put(key16, o);
        map.put(key17, o);
        map.put(key18, o);
        map.put(key19, o);
        map.put(key20, o);
        System.out.println(map.containsKey(key20));
        System.out.println(map.size());
    }

}

class Key {
    private int hashcode;


    public Key(int hashcode) {
        this.hashcode = hashcode;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public String toString() {
        return "Key{" +
                "hashcode=" + hashcode +
                '}';
    }
}
