

public class IndexableElement<T extends Comparable<T>> implements Comparable<IndexableElement<T>> {
	//CAMPI
	private T element;
	private int index = -1;
	
	//COSTRUTTORI
	public IndexableElement(T ob, int ind){
		this.element=ob;
		this.index=ind;
	}
	
	//METODI
	
	public T getElement() {
		return this.element;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public void setElement(T e) {
		this.element = e;
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	//TODO equals
	
	@Override
	public int compareTo(IndexableElement<T> o) {
		return (this.element).compareTo(o.element);
	}
	
	public String toString() {
		return this.element.toString();
	}
	
}
