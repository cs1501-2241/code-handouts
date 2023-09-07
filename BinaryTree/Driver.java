public class Driver {
  public static void main(String[] args){
    testBinaryTree();
  }

 
  public static void testBinaryTree(){
    BinaryTree<Character> bTree =
      new BinaryTree<>('B');
    BinaryTree<Character> fTree =
      new BinaryTree<>('F');
    BinaryTree<Character> gTree =
      new BinaryTree<>('G');
    BinaryTree<Character> dTree =
      new BinaryTree<>('D', null, fTree);
    BinaryTree<Character> aTree =
        new BinaryTree<>('A', bTree, dTree);
    BinaryTree<Character> cTree =
      new BinaryTree<>('C', null, gTree);
    BinaryTree<Character> eTree =
      new BinaryTree<>('E', aTree, cTree);
    System.out.print("Preorder: ");
    eTree.preorderTraverse();
    System.out.print("\nInorder: ");
    eTree.inorderTraverse();
    System.out.print("\nPostorder: ");
    eTree.postorderTraverse();
  }
}
