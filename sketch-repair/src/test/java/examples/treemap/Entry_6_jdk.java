package examples.treemap;
public class Entry_6_jdk<K, V> {
//	 private static final boolean RED   = false;
	    private static final boolean BLACK = true;
        K key;
        V value;
        Entry_6_jdk<K,V> left = null;
        Entry_6_jdk<K,V> right = null;
        Entry_6_jdk<K,V> parent;
        boolean color = BLACK;

        Entry_6_jdk(K key, V value, Entry_6_jdk<K,V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Entry_6_jdk))
                return false;
            @SuppressWarnings("unchecked")
			Entry_6_jdk<K, V> e = (Entry_6_jdk<K, V>)o;

            return key.equals(e.getKey()) && value.equals(e.getValue());
        }

        public int hashCode() {
            int keyHash = (key==null ? 0 : key.hashCode());
            int valueHash = (value==null ? 0 : value.hashCode());
            return keyHash ^ valueHash;
        }

        public String toString() {
            return key + "=" + value;
        }
    }