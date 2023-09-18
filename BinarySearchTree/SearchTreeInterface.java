public interface
  SearchTreeInterface<T extends Comparable<? super T>> {

  public boolean contains(T entry);
    
  public T getEntry(T entry);

  public T add(T entry);

  public T remove(T entry);
}
