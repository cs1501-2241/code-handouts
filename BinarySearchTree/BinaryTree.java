import java.util.Stack;
public class BinaryTree<T> implements BinaryTreeInterface<T> {
  private BinaryNode<T> root;

  protected void setRoot(BinaryNode<T> root){
    this.root = root;
  }

  public void clear(){
    root = null;
  }

  public BinaryTree(){
    root = null;
  }

  public BinaryTree(T rootData){
    privateBuildTree(rootData, null, null);
  }

  public BinaryTree(T rootData, BinaryTree<T> leftTree,
                    BinaryTree<T> rightTree){
    privateBuildTree(rootData, leftTree, rightTree);
  }

  public void buildTree(T rootData) {
    privateBuildTree(rootData, null, null);
  }

  public void buildTree(T rootData,
                        BinaryTreeInterface<T> left,
                        BinaryTreeInterface<T> right) {
    privateBuildTree(rootData, (BinaryTree<T>)left, (BinaryTree<T>)right);
  }

  private void privateBuildTree(T rootData, BinaryTree<T> leftTree,
                                BinaryTree<T> rightTree){
      root = new BinaryNode<>(rootData);
      if((leftTree != null) && (!leftTree.isEmpty())){
        root.setLeftChild(leftTree.getRoot());
      }
      if((rightTree != null)&&(!rightTree.isEmpty())){
        if(leftTree == rightTree) {
          root.setRightChild(rightTree.getRoot().copy());
        } else {
          root.setRightChild(rightTree.getRoot());
        }
      }

      if((leftTree!=null)&&(leftTree != this)){
        leftTree.clear();
      }
      if((rightTree != null)&&(rightTree != this)){
        rightTree.clear();
      }
  }

  public T getRootData() throws EmptyTreeException {
    if(root == null)
      throw new EmptyTreeException("Empty tree");
    return root.getData();
  }

  protected BinaryNode<T> getRoot(){
    return root;
  }
  public int getHeight() throws EmptyTreeException {
    if(root == null)
      throw new EmptyTreeException("Empty tree");
    return root.getHeight();
  }
  public int getNumberOfNodes() throws EmptyTreeException {
    if(root == null)
      throw new EmptyTreeException("Empty tree");
    return root.getNumberOfNodes();
  }

  public boolean isEmpty(){
    return root == null;
  }
  public void levelOrderTraverse(){

  }


  public void preorderTraverse(){
    //preorderTraverse(root);
    iterativePreOrder();
  }

  public void inorderTraverse(){
    //inorderTraverse(root);
    iterativeInOrder();
  }
  public void postorderTraverse(){
    //postorderTraverse(root);
    iterativePostOrder();
  }

  private void preorderTraverse(BinaryNode<T> root){
    if(root != null){
      //visit the root
      System.out.println(root.getData());
      //left
      preorderTraverse(root.getLeftChild());
      //right
      preorderTraverse(root.getRightChild());
    }
  }

  private void inorderTraverse(BinaryNode<T> root){
    if(root != null){
      //left
      inorderTraverse(root.getLeftChild());
      //visit the root
      System.out.println(root.getData());
      //right
      inorderTraverse(root.getRightChild());
      //have you seen about time?
    }
  }

  private void postorderTraverse(BinaryNode<T> root){
    if(root != null){
      //left
      postorderTraverse(root.getLeftChild());
      //right
      postorderTraverse(root.getRightChild());
      //visit the root
      System.out.println(root.getData());
    }
  }

  private void iterativeInOrder(){
    Stack<BinaryNode<T>> nodeStack =
      new Stack<>();
    BinaryNode<T> currentNode = root;

    while (!nodeStack.isEmpty()
          || (currentNode != null)){
       // Find leftmost node with no left child
       while (currentNode != null)
       {
          nodeStack.push(currentNode);
          currentNode = currentNode.getLeftChild();
       } // end while

       // Visit leftmost node, then traverse its right subtree
       if (!nodeStack.isEmpty())
       {
          BinaryNode<T> nextNode = nodeStack.pop();
          System.out.print(nextNode.getData() + " ");
          currentNode = nextNode.getRightChild();
       } // end if
    } // end while
  }

  private void iterativePreOrder(){
    Stack<BinaryNode<T>> nodeStack =
      new Stack<>();
    if (root != null)
       nodeStack.push(root);
    BinaryNode<T> nextNode;
    while (!nodeStack.isEmpty()){
      nextNode = nodeStack.pop();
      System.out.print(nextNode.getData() + " ");
      BinaryNode<T> leftChild = nextNode.getLeftChild();
      BinaryNode<T> rightChild = nextNode.getRightChild();

      // Push into stack in reverse order of recursive calls
      if (rightChild != null)
        nodeStack.push(rightChild);

      if (leftChild != null)
        nodeStack.push(leftChild);
    } // end while
  }

  private void iterativePostOrder(){
    Stack<BinaryNode<T>> nodeStack = new Stack<>();
    BinaryNode<T> currentNode;
    BinaryNode<T> leftChild, rightChild, nextNode = null;

    currentNode = root;
    while(!nodeStack.isEmpty()
          || (currentNode != null)){
       // Find leftmost leaf
       while (currentNode != null){
          nodeStack.push(currentNode);
          leftChild = currentNode.getLeftChild();
          if (leftChild == null)
             currentNode = currentNode.getRightChild();
          else
             currentNode = leftChild;
       } // end while

       // Stack is not empty either because we just pushed a node, or
       // it wasn't empty to begin with.
       if (!nodeStack.isEmpty())
       {
          nextNode = nodeStack.pop();
          System.out.print(nextNode.getData() + " ");
          BinaryNode<T> parent = null;
          if (!nodeStack.isEmpty())
          {
             parent = nodeStack.peek();
             if (nextNode == parent.getLeftChild())
                currentNode = parent.getRightChild();
             else
                currentNode = null;
          }
          else
             currentNode = null;
       }
     }
    }
  }
