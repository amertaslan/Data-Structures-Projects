package data_structures_hw_1;

import java.util.ArrayList;

public class HashTable<K, V> {
	protected int capacity = 128;
	protected Map<K, V>[] table;
	protected int n = 0;
	
	public HashTable() {
		createTable();
	}
	@SuppressWarnings("unchecked")
	public void createTable() {
		table = new Map[capacity];
	}
	public int hashFunction(K key) {
		return (int)key % capacity;
	}
	public int size() {
		return n;
	}
	public boolean isEmpty() {
		return n==0;
	}
	public void resize(int newCap) {
		ArrayList<Map<K, V>> templist = new ArrayList<>(n);
		templist = addToArrayList(templist);
		capacity = newCap;
		n = 0;
		createTable();
		for (Map<K, V> m : templist) {
			put(m.getKey(), m.getValue());
		}
	}
	//----------------------------------BACKING HASH TABLE VALUE-----------------------------
	public ArrayList<Map<K, V>> addToArrayList(ArrayList<Map<K, V>> templist) {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				templist.add(table[i]);
			}
		}
		return templist;
	}
	public V put(K key, V value) throws IllegalArgumentException {
		int h = hashFunction(key);
		table[h] = new Map<K, V>(key,value);
		n++;
		if (n > loadFactor1()) {
			resize(2*capacity-1);
		}
		return value;
	}
	public V get(K key, V value) {
		int h = hashFunction(key);
		if (!table[h].getValue().equals(null)) {
			return table[h].getValue();
		}
		else {
			return null;
		}
	}
	//------------------------------------LOAD FACTOR-----------------------------
	public int loadFactor1() {
		return capacity/2;
	}
	public int loadFactor2() {
		return (capacity*8)/10;
	}
	//------------------------------------FIND RESIZE CAPACITY-----------------------------
	public int resizeCapactiy(int oldCapacitiy) {
		oldCapacitiy = oldCapacitiy*2;
		int a, b, flag = 0;
		for (a = oldCapacitiy; a < 1000000; a++) {
			flag=0;
			for (b = 2; b < a; b++) {
				if (a%b==0) {
					flag = 1;
					break;
				}
			}
			if (flag==0) {
				return a;
			}
		}
		return 0;
	}
}
