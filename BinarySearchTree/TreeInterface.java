public interface TreeInterface<T> {
  public T getRootData() throws EmptyTreeException;
  public int getHeight() throws EmptyTreeException;
  public int getNumberOfNodes() throws EmptyTreeException;
  public boolean isEmpty();
  public void levelOrderTraverse();
  public void clear();

}
