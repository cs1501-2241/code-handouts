public interface TreeInterface<T> {
  public T getRootData() throws EmptyTreeException;
  public int getHeight() throws EmptyTreeException;
  public int getNumberOfNodes() throws EmptyTreeException;
  public boolean isEmpty();
  public void levelorderTraverse();
  public void preorderTraverse();
  public void postorderTraverse();
  public void clear();
}
