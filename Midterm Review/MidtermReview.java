public BinaryNode<T> 
iterativeLeftMostNode(BinaryNode<T> root){
    if(root != null){
     while(root.left != null){
        root = root.left;
     }   
    }    
    return root;
}

public BinaryNode<T> 
iterativeLeftMostLeaf(BinaryNode<T> root){
    while(root.left != null || 
          root.right != null){
        if(root.left != null){
            root = root.left;
        } else {
            root = root.right;
        }
    }
    return root;
}

public boolean isFull(BinaryNode<T> root){
    boolean result = false;
    if(root == null){
        result = true;
    } else {
        if(getHeight(root.left) 
            == getHeight(root.right) &&
            isFull(root.left) &&
            isFull(root.right)){
                result = true;
            }
    }

    return result;
}

public boolean isComplete(BinaryNode<T> root){
    return isFull(root) || 
           (isFull(root.left) && isFull(root.right) &&
           getHeight(root.left) == 1+getHeight(root.right))||
           (isFull(root.right) && isComplete(root.left) &&
           getHeight(root.left) == 1+getHeight(root.right))||
           (isFull(root.left) && isComplete(root.right) &&
           getHeight(root.left) == getHeight(root.right));

}

public boolean isBST(BinaryNode<T> root){
    boolean result = false;
    int res1 = 0, res2 = 0;
    if(root == null) result = true;
    else {
        BinaryNode<T> largest = findLargest(root.left);
        BinaryNode<T> smallest = findSmallest(root.right);
        if(largest != null)
            res1 = root.data.compareTo(largest.data);
        else res1 = 1;
        if(smallest != null)
            res2 = root.data.compareTo(smallest.data);
        else res2 = -1;

        if(isBST(root.left) && isBST(root.right) &&
            res1 > 0 && res2 < 0)
            result = true;
    }
    return result;
}







