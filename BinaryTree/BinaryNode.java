public class BinaryNode<T> {
  private T data;
  private BinaryNode<T> left;
  private BinaryNode<T> right;

  public BinaryNode(T data){
    this(data, null, null);
  }

  public BinaryNode(T data, BinaryNode<T> left,
                   BinaryNode<T> right){
      this.data = data;
      this.left = left;
      this.right = right;
  }

  public void setData(T data){
    this.data = data;
  }

  public T getData(){
    return data;
  }

  public void setLeftChild(BinaryNode<T> left){
    this.left = left;
  }

  public BinaryNode<T> getLeftChild(){
    return left;
  }

  public void setRightChild(BinaryNode<T> right){
    this.right = right;
  }

  public BinaryNode<T> getRightChild(){
    return right;
  }

  public boolean hasLeftChild(){
    return left != null;
  }

  public boolean hasRightChild(){
    return right != null;
  }

  public int getNumberOfNodes(){
    int result = 1;
    if(left != null){
      result += left.getNumberOfNodes();
    }
    if(right != null){
      result += right.getNumberOfNodes();
    }
    return result;
  }

  public int getHeight(){
    int max = 0;
    if(left != null){
      max = left.getHeight();
    }
    if(right != null){
      if(right.getHeight() > max){
        max = right.getHeight();
      }
    }

    return 1 + max;
  }

  public BinaryNode<T> copy(){
    BinaryNode<T> newRoot = new BinaryNode<>(data);
    if(left != null){
      newRoot.left = left.copy();
    }
    if(right != null){
      newRoot.right = right.copy();
    }
    return newRoot;
  }
}
