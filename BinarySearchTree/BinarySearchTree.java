public class
  BinarySearchTree<T extends Comparable<? super T>>
  extends BinaryTree<T> implements SearchTreeInterface<T> {

    public BinarySearchTree(){
      super();
    }

    public BinarySearchTree(T rootData){
      super();
      setRoot(new BinaryNode<T>(rootData));

    }

    public BinarySearchTree(BinaryNode<T> root){
      super(null);
      setRoot(root);//Have you seen About Time?
    }

    public void buildTree(T rootData) {
      throw new UnsupportedOperationException();
    }

    public void buildTree(T rootData,
                          BinaryTreeInterface<T> left,
                          BinaryTreeInterface<T> right) {
      throw new UnsupportedOperationException();
    }

    public boolean contains(T entry){
      return getEntry(entry) != null;
    }

    public T getEntry(T entry){
      return findEntry(getRoot(), entry);
    }

    private T findEntry(BinaryNode<T> root, T entry){
      T result = null;
      if(root != null){
        int compResult = root.getData().compareTo(entry);
        if(compResult == 0){
          result = root.getData();
        } else if(compResult < 0){
          result = findEntry(root.getRightChild(), entry);
        } else {
          result = findEntry(root.getLeftChild(), entry);
        }
      }
      return result;
    }

    public T add(T entry){
      T result = null;
      if(isEmpty()){
        setRoot(new BinaryNode<>(entry));
      } else {
        result = addEntry(getRoot(), entry);
      }
      return result;
    }

    private T addEntry(BinaryNode<T> root, T entry){
      T result = null;
      int comparison = root.getData().compareTo(entry);
      if(comparison == 0){
        result = root.getData();
        root.setData(entry);
      } else if(comparison < 0){
        if(root.hasRightChild()){
          result = addEntry(root.getRightChild(), entry);
        } else {
          root.setRightChild(new BinaryNode<>(entry));
        }
      } else {
        if(root.hasLeftChild()){
          result = addEntry(root.getLeftChild(), entry);
        } else {
          root.setLeftChild(new BinaryNode<>(entry));
        }
      }
      return result;
    }

    public T remove(T entry){
      // T result = findEntry(entry);
      ReturnObject envelope = new ReturnObject(null);
      setRoot(removeEntry(getRoot(), entry, envelope));

      return envelope.get();

    }

    private BinaryNode<T> removeEntry(BinaryNode<T> root,
                                      T entry, ReturnObject item){
      if(root != null){
        int comparison = root.getData().compareTo(entry);
        if(comparison == 0){
          item.set(root.getData());
          root = removeFromRoot(root);

        } else if (comparison < 0){
          root.setRightChild(removeEntry(root.getRightChild(), entry, item));
        } else {
          root.setLeftChild(removeEntry(root.getLeftChild(), entry, item));
        }

      }
      return root;
    }

    private BinaryNode<T> removeFromRoot(BinaryNode<T> root){
      //handles the three cases
      if(root.hasLeftChild() && root.hasRightChild()){
        BinaryNode<T> largest = findLargest(root.getLeftChild());
        root.setData(largest.getData());
        root.setLeftChild(removeLargest(root.getLeftChild()));
      } else {
        if(root.hasLeftChild()){
          root = root.getLeftChild();
        } else {
          root = root.getRightChild();
        }
      }
      return root;
    }

    private BinaryNode<T> findLargest(BinaryNode<T> root){
      if(root.hasRightChild()){
        return findLargest(root.getRightChild());
      } else {
        return root;
      }
    }

    private BinaryNode<T> removeLargest(BinaryNode<T> root){
      if(root.hasRightChild()){
         root.setRightChild(removeLargest(root.getRightChild()));
      } else {
        root = root.getLeftChild();
      }
      return root;
    }

    private class ReturnObject {
      T item;
      private ReturnObject(T entry){
        item = entry;
      }
      private void set(T entry){
        item = entry;
      }
      private T get(){
        return item;
      }
    }
}
