public class Driver {
  public static void main(String[] args){
    testBinaryTree();
    System.out.println("\n===============");
    testBST();
  }

  public static void testBST(){
    BinarySearchTree<Character> bst = new BinarySearchTree<>();
    bst.add('G');
    bst.add('D');
    bst.add('B');
    bst.add('E');
    bst.add('A');
    bst.add('C');
    bst.add('F');
    bst.add('K');
    bst.add('N');
    bst.add('I');
    bst.add('L');
    bst.add('H');
    bst.add('J');
    bst.add('M');

    System.out.println(bst.contains('H'));
    System.out.println(bst.contains('A'));
    System.out.println(bst.contains('X'));
    System.out.println(bst.getEntry('G'));
    System.out.println(bst.getEntry('X'));
    bst.remove('G');
    System.out.println(bst.contains('G'));
    bst.remove('M');
    bst.remove('A');
    bst.remove('C');

    bst.inorderTraverse();
  }

  public static void testBinaryTree(){
    BinaryTree<Character> bTree =
      new BinaryTree<>('B');
    BinaryTree<Character> fTree =
      new BinaryTree<>('F');
    BinaryTree<Character> gTree =
      new BinaryTree<>('G');
    BinaryTree<Character> dTree =
      new BinaryTree<>();
    dTree.buildTree('D', null, fTree);
    BinaryTree<Character> aTree =
        new BinaryTree<>();
    aTree.buildTree('A', bTree, dTree);
    BinaryTree<Character> cTree =
      new BinaryTree<>();
    cTree.buildTree('C', null, gTree);
    BinaryTree<Character> eTree =
      new BinaryTree<>();
    eTree.buildTree('E', aTree, cTree);
    System.out.print("Preorder: ");
    eTree.preorderTraverse();
    System.out.print("\nInorder: ");
    eTree.inorderTraverse();
    System.out.print("\nPostorder: ");
    eTree.postorderTraverse();
  }
}
