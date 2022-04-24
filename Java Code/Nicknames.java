import java.util.*;
import java.io.*;

public class Nicknames {
    public static void main(String[] args) throws Exception {
        Kattio io = new Kattio(System.in, System.out);

        int a = io.getInt();
        HashMap<String,Integer> counts = new HashMap<>();
        AVL tree = new AVL();
        for (int i = 0; i < a; i++) {
            tree.insert(io.getWord());
        }
        int b = io.getInt();
        for (int i = 0; i < b; i++) {
            String nickname = io.getWord();
            if(counts.get(nickname)==null) {
                int smallest = tree.rank(tree.root, nickname);
                int largest = tree.rank(tree.root, nickname.concat("~"));
                io.println(largest - smallest);
                counts.put(nickname,largest - smallest);
            }
            else{
                io.println(counts.get(nickname));
            }
        }
        io.flush();
    }
}
class AVLVertex {
    AVLVertex(String v) {
        key = v;
        parent = left = right = null;
        height = 0;
        size = 1;
    }

    // all these attributes remain public to slightly simplify the code
    public AVLVertex parent, left, right;
    public String key;
    public int height; // will be used in lecture on AVL
    public int size; // will be used in lecture on AVL
}

// This is just a sample implementation
// There are other ways to implement AVL concepts...
class AVL {
    public AVLVertex root;
    Kattio io = new Kattio(System.in, System.out);

    public AVL() {
        root = null;
    }

    // public method called to search for a value v.
    // Return v if it is found in the AVL otherwise return -1.
    // Here the assumption is that -1 is never a valid key value.

    public int getHeight(AVLVertex T) {
        if (T == null) {
            return -1;
        }
        return T.height;
    }

    public int getSize(AVLVertex T) {
        if (T == null) {
            return 0;
        }
        return T.size;
    }

    public int getBalanceFactor(AVLVertex T) {
        if (T == null) {
            return 0;
        }
        return getHeight(T.left) - getHeight(T.right);
    }

    public int rank(AVLVertex T, String v) {
        if(T == null){
            return 0;
        }
        if(T.key.compareTo(v)<0){
            if(T.left == null){
                return 1+rank(T.right,v);
            }
            if(T.right == null) {
                return 1 + getSize(T.left);
            }
            return 1+ getSize(T.left)+rank(T.right,v);
        }
        return rank(T.left,v);
    }

    AVLVertex leftRotate(AVLVertex T) {
        AVLVertex w = T.right;
        w.parent = T.parent;
        T.parent = w;
        T.right = w.left;
        if (w.left != null) {
            w.left.parent = T;
        }
        w.left = T;

        //update the height and size of T and then w
        T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
        w.height = Math.max(getHeight(w.left), getHeight(w.right)) + 1;
        T.size = getSize(T.left) + getSize(T.right) + 1;
        w.size = getSize(w.left) + getSize(w.right) + 1;

        return w;
    }

    AVLVertex rightRotate(AVLVertex T) {
        AVLVertex w = T.left;
        w.parent = T.parent;
        T.parent = w;
        T.left = w.right;
        if (w.right != null) {
            w.right.parent = T;
        }
        w.right = T;

        //update the height and size of T and then w
        T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
        w.height = Math.max(getHeight(w.left), getHeight(w.right)) + 1;
        T.size = getSize(T.left) + getSize(T.right) + 1;
        w.size = getSize(w.left) + getSize(w.right) + 1;

        return w;
    }
    // helper method to perform search

    public AVLVertex search(AVLVertex T, String v) {
        if (T == null) return null;                     // not found
        else if (T.key.compareTo(v) == 0) return T;                        // found
        else if (T.key.compareTo(v) < 0) return search(T.right, v);       // search to the right
        else return search(T.left, v);        // search to the left
    }

    // public method called to find Minimum key value in AVL

    // helper method to perform findMin
    // Question: What happens if AVL is empty?
    public String findMin(AVLVertex T) {
        if (T.left == null) return T.key;                    // this is the min
        else                return findMin(T.left);           // go to the left
    }

    // public method called to find Maximum key value in AVL

    // helper method to perform findMax
    // Question: Again, what happens if AVL is empty?

    // public method to find successor to given value v in AVL.
    public String successor(String v) {
        AVLVertex vPos = search(root, v);
        return vPos == null ? null : successor(vPos);
    }

    // helper recursive method to find successor to for a given vertex T in AVL
    public String successor(AVLVertex T) {
        if (T.right != null)                       // this subtree has right subtree
            return findMin(T.right);  // the successor is the minimum of right subtree
        else {
            AVLVertex par = T.parent;
            AVLVertex cur = T;
            // if par(ent) is not root and cur(rent) is its right children
            while ((par != null) && (cur == par.right)) {
                cur = par;                                         // continue moving up
                par = cur.parent;
            }
            return par == null ? null : par.key;           // this is the successor of T
        }
    }

    // public method to find predecessor to given value v in AVL

    // helper recursive method to find predecessor to for a given vertex T in AVL

    // helper method to perform inorder traversal

    // public method called to insert a new key with value v into AVL
    public void insert(String v) { root = insert(root, v); }

    // helper recursive method to perform insertion of new vertex into AVL
    public AVLVertex insert(AVLVertex T, String v) {
        if (T == null) return new AVLVertex(v);          // insertion point is found

        if (T.key.compareTo(v) < 0) {                                      // search to the right
            T.right = insert(T.right, v);
            T.right.parent = T;
        } else {                                                 // search to the left
            T.left = insert(T.left, v);
            T.left.parent = T;
        }

        T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
        T.size = getSize(T.left) + getSize(T.right) + 1;

        //left left case
        if (getBalanceFactor(T) == 2 && (getBalanceFactor(T.left) <=1)) {
            return rightRotate(T);

        }
        //left right case
        if (getBalanceFactor(T) == 2 && getBalanceFactor(T.left) == -1) {
            T.left = leftRotate(T.left);
            return rightRotate(T);
        }
        //right right case
        if (getBalanceFactor(T) == -2 && (getBalanceFactor(T.right) == 0 || getBalanceFactor(T.right) == -1)) {
            return leftRotate(T);
        }
        //right left case
        if (getBalanceFactor(T) == -2 && getBalanceFactor(T.right) == 1) {
            T.right = rightRotate(T.right);
            return leftRotate(T);
        }
        return T;                                          // return the updated AVLTree
    }

    // public method to delete a vertex containing key with value v from AVL
    public void delete(String v) { root = delete(root, v); }

    // helper recursive method to perform deletion
    public AVLVertex delete(AVLVertex T, String v) {
        if (T == null) return T;              // cannot find the item to be deleted

        if (T.key.compareTo(v) < 0)                                    // search to the right
            T.right = delete(T.right, v);
        else if (T.key.compareTo(v) > 0)                               // search to the left
            T.left = delete(T.left, v);
        else {                                            // this is the node to be deleted
            if (T.left == null && T.right == null) {      // this is a leaf
                T = null;
                return T;                                // simply erase this node
            } else if (T.left == null && T.right != null) {   // only one child at right
                T.right.parent = T.parent;
                T = T.right;                                                 // bypass T
            } else if (T.left != null && T.right == null) {    // only one child at left
                T.left.parent = T.parent;
                T = T.left;                                               // bypass T
            } else {                                 // has two children, find successor
                String successorV = successor(v);
                T.key = successorV;         // replace this key with the successor's key
                T.right = delete(T.right, successorV);      // delete the old successorV

            }
        }

        T.height = Math.max(getHeight(T.left), getHeight(T.right)) + 1;
        T.size = getSize(T.left) + getSize(T.right) + 1;

        //left left case
        if (getBalanceFactor(T) == 2 && (getBalanceFactor(T.left) == 0 || getBalanceFactor(T.left) == 1)) {
            return rightRotate(T);

        }
        //left right case
        if (getBalanceFactor(T) == 2 && getBalanceFactor(T.left) == -1) {
            T.left = leftRotate(T.left);
            return rightRotate(T);
        }
        //right right case
        if (getBalanceFactor(T) == -2 && (getBalanceFactor(T.right) == 0 || getBalanceFactor(T.right) == -1)) {
            return leftRotate(T);
        }
        //right left case
        if (getBalanceFactor(T) == -2 && getBalanceFactor(T.right) == 1) {
            T.right = rightRotate(T.right);
            return leftRotate(T);
        }
        return T;                                          // return the updated AVLTree
    }

}

class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}


