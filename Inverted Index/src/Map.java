package data_structures_hw_1;

public class Map<K, V> {
	private K key;
	private V value;

	public Map(K key, V value) {
		this.key   = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public V setValue(V value) {
		V temp = this.value;
		this.value = value;
		return temp;
	}

	public String toString() {
		return "Map [key=" + key + ", value=" + value + "]";
	}
}
