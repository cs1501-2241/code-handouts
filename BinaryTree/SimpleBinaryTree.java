public final class SimpleBinaryTree {
    public static void main(String[] args){
        new SimpleBinaryTree();
    }

    public SimpleBinaryTree(){
        BinaryNode<Character> A, B, C, D;
        A = new BinaryNode<Character>('A', null, null);
        B = new BinaryNode<Character>('B', null, null);
        C = new BinaryNode<Character>('C', null, null);
        D = new BinaryNode<Character>('D', null, null);
        A.left = D;
        A.right = C;
        C.right = B;
    }

    private class BinaryNode<T> {
        private T data;
        private BinaryNode<T> left;
        private BinaryNode<T> right;

        private BinaryNode(T data, 
                            BinaryNode<T> left, 
                            BinaryNode<T> right){
                this.data = data;
                this.left = left;
                this.right = right;
        }
    }
}