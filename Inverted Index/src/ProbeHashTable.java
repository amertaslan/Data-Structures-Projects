package data_structures_hw_1;

import java.util.ArrayList;

public class ProbeHashTable<K, V> extends HashTable<K, V> {
	private Map<K, V>[] table;
	private Map<K, V> DEFUNCT = new Map<K, V>(null,null);
	private int collusionCounter = 0;

	public ProbeHashTable() {
		createTable();
	}
	@SuppressWarnings("unchecked")
	public void createTable() {
		table = new Map[capacity];
	}
	public int hashFunction(K key, int j) {
		return ((int)key+j) % capacity;
	}
	private boolean isAvailable(int j) {
		return (table[j] == null || table[j] == DEFUNCT);
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
	public ArrayList<Map<K, V>> addToArrayList(ArrayList<Map<K, V>> templist) {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				templist.add(table[i]);
			}
		}
		return templist;
	}
	public void setValue(K key, String s, V value) {
		int j = 0;
		int h = hashFunction(key, j);
		ArrayList<Node> temp = (ArrayList<Node>) table[h].getValue();
		while (!temp.get(0).getName().equals(s)) {
			j++;
			h = hashFunction(key, j);
			if (!isAvailable(h)) {
				temp = (ArrayList<Node>) table[h].getValue();
				if (temp.get(0).getName().equals(s)) {
					table[h].setValue(value);	
				}
			}
		}
	}
	public V put(K key, V value) {
		int j = 0;
		int h = hashFunction(key, j);
		if(h<0) {
			h = h + capacity;
		}
		while (!isAvailable(h)) {
			h = hashFunction(key, j);
			j += 1;
			collusionCounter++;
		} 
		table[h] = new Map<K, V>(key,value);
		n++;
		if (n > loadFactor1()) {
			resize(resizeCapactiy(capacity));
		}
		return value;
	}
	public V get(K key, String s) {
		int j = 0;
		int h = hashFunction(key, j);
		if (isAvailable(h)) {
			return null;
		}
		else {
			ArrayList<Node> temp = (ArrayList<Node>) table[h].getValue();
			if (temp.get(0).getName().equals(s)) {
				return table[h].getValue();
			}
			else {
				boolean flag = false;
				while (!isAvailable(h)) {
					j++;
					h = hashFunction(key, j);
					if (!isAvailable(h)) {
						temp = (ArrayList<Node>) table[h].getValue();
					}
					if (temp.get(0).getName().equals(s)) {
						return table[h].getValue();
					}
				}
			}
		}
		return null;
	}
	public V search(K key, String s) {
		int j = 0;
		int h = hashFunction(key, j);
		if (isAvailable(h)) {
			return null;
		}
		else {
			ArrayList<Node> temp = (ArrayList<Node>) table[h].getValue();
			if (temp.get(0).getName().equals(s)) {
				return table[h].getValue();
			}
			else {
				boolean flag = false;
				while (!isAvailable(h)) {
					j++;
					h = hashFunction(key, j);
					if (!isAvailable(h)) {
						temp = (ArrayList<Node>) table[h].getValue();
					}
					if (temp.get(0).getName().equals(s)) {
						return table[h].getValue();
					}
				}
			}
		}
		return null;
	}
	public void display() {
		for (int i = 0; i < table.length; i++) {
			if (table[i]!=null) {
				System.out.println( i + "    " +table[i].toString());
			}
		}
	}
	public int collusionCounter() {
		return collusionCounter;
	}
}
