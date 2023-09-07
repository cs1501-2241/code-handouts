public interface BinaryTreeInterface<T> extends TreeInterface<T>{
  public void buildTree(T rootData);
  public void buildTree(T rootData,
                        BinaryTreeInterface<T> left,
                        BinaryTreeInterface<T> right);
  public void inorderTraverse();
}
