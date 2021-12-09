import java.util.ArrayList;
import java.util.LinkedList;

public class HashMap<K, V> {
	public static int iterations;
	private int size;
	private double loadFactor;
	private LinkedList<HashMapEntry<K, V>>[] hashTable;

	// Constructors
	public HashMap() { // O(log n)
		this(100, 0.9);
	}

	public HashMap(int c) { // O(log n)
		this(c, 0.9);
	}

	public HashMap(int c, double lf) { // O(log n)
		hashTable = new LinkedList[trimToPowerOf2(c)];
		loadFactor = lf;
		size = 0;
	}

	// private methods
	private int trimToPowerOf2(int c) { // O(log n)
		int capacity = 1;
		while (capacity < c)
			capacity = capacity << 1; // * 2
		return capacity;
	}

	private int hash(int hashCode) { //O(1)
		return hashCode & (hashTable.length - 1);
	}

	private void rehash() { // O(n)
		ArrayList<HashMapEntry<K,V>> list = toList(); // O(n)
		hashTable = new LinkedList[hashTable.length << 1]; // Doubling
		size = 0;
		for(HashMapEntry<K,V> entry: list) // O(n)
			put(entry.getKey(), entry.getValue()); //O(1)
			
	}

	// public interface
	public int size() { // O(1)
		return size;
	}

	public void clear() { // O(n)
		size = 0;
		for (int i = 0; i < hashTable.length; i++)
			if (hashTable[i] != null)
				hashTable[i].clear();
	}

	public boolean isEmpty() { // O(1)
		return (size == 0);
	}

	// search for key - returns true if found
	public boolean containsKey(K key) { // O(1)
		if (get(key) != null)
			return true;
		return false;
	}
	
	// returns the value of key if found, null otherwise
	public V get(K key) { // O(1)
		iterations = 0;
		int HTIndex = hash(key.hashCode());
		if (hashTable[HTIndex] != null) {
			LinkedList<HashMapEntry<K, V>> ll = hashTable[HTIndex];
			for (HashMapEntry<K, V> entry : ll) {
				iterations++;
				if (entry.getKey().equals(key))
					return entry.getValue();
			}
		}
		return null;
	}
	
	// remove a key if found
	public void remove(K key) { // O(1)
		int HTIndex = hash(key.hashCode());
		if (hashTable[HTIndex] != null) { // key is in the hash map
			LinkedList<HashMapEntry<K, V>> ll = hashTable[HTIndex];
			for (HashMapEntry<K, V> entry : ll) {
				if (entry.getKey().equals(key)) {
					ll.remove(entry);
					size--;
					break;
				}
			}
		}
	}
	
	// adds a new key or modifies an existing key
	public V put(K key, V value) { // O(1) - O(n) if we do rehash
		if(get(key) != null) { // The key is in the hash map
			int HTIndex = hash(key.hashCode());
			LinkedList<HashMapEntry<K,V>> ll;
			ll = hashTable[HTIndex];
			for(HashMapEntry<K,V> entry: ll) {
				if(entry.getKey().equals(key)) {
					V old = entry.getValue();
					entry.setValue(value); 
					return old;
				}
			}
		}
		// key not in the hash map - check load factor
		if(size >= hashTable.length * loadFactor)
			rehash();
		int HTIndex = hash(key.hashCode());
		//create a new LL if empty
		if(hashTable[HTIndex] == null){
				hashTable[HTIndex] = new LinkedList<>();
		}
		hashTable[HTIndex].add(new HashMapEntry<>(key, value));
		size++; 
		return value;
	}
	
	// returns the elements of the hash map as a list
		public ArrayList<HashMapEntry<K,V>> toList(){ // O(n)
			ArrayList<HashMapEntry<K,V>> list = new ArrayList<>();
			for(int i=0; i< hashTable.length; i++) {
				if(hashTable[i]!= null) {
					LinkedList<HashMapEntry<K,V>> ll = hashTable[i];
					for(HashMapEntry<K,V> entry: ll)
						list.add(entry);
				}
			} 
			return list;
		}
	// returns the elements of the hash map as a string
		public String toString() { // O(n)
			String out = "[";
			for(int i=0; i<hashTable.length; i++) {
				if(hashTable[i]!=null) {
					for(HashMapEntry<K,V> entry: hashTable[i])
						out += entry.toString();
					out += "\n";
				}
			}
			out += "]"; 
			return out;
		}

}