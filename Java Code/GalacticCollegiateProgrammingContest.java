import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStream;

public class GalacticCollegiateProgrammingContest {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int numOfTeam = io.getInt();
        int numOfEvents = io.getInt();

        AVLTree tree = new AVLTree();
        Team[] teams = new Team[numOfTeam];
        for(int i = 0; i < numOfTeam; i++) {
            teams[i] = new Team(i+1);
            tree.insert(teams[i]);
        }

        for(int i = 0; i < numOfEvents; i++) {
            int teamNum = io.getInt();
            int penalty = io.getInt();

            tree.delete(teams[teamNum -1]);
            teams[teamNum -1].gameSolved += 1;
            teams[teamNum -1].penalty += penalty;
            tree.insert(teams[ teamNum -1]);

            io.println(tree.rank(tree.root, teams[0]));
        }
        io.flush();
    }
}

class Team implements Comparable<Team>{
    public int gameSolved;
    public int penalty;
    public int teamID;

    Team(int teamID) {
        this.gameSolved = 0;
        this.penalty = 0;
        this.teamID = teamID;

    }

    //most left of AVLTree will be the worst of the table
    public int compareTo(Team t1) {
        if(this.gameSolved == t1.gameSolved) {
            if(this.penalty < t1.penalty) {
                return 1;
            } else if(this.penalty > t1.penalty) {
                return -1;
            }else {
                if (this.teamID < t1.teamID){
                    return 1;
                } else if (this.teamID > t1.teamID) {
                    return -1;
                } else {
                     return 0;
                }
            }
        } else if (this.gameSolved > t1.gameSolved) {
            return 1;
        } else {
            return -1;
        }
    }
}

class Vertex {
    Team team;
    int height;
    int size;
    Vertex left;
    Vertex right;
    Vertex parent;

    Vertex(Team team) {
        size = 1;
        height = 0;
        this.team = team;
        parent = left = right = null;
    }
}

class AVLTree {
    public Vertex root;

    public AVLTree() {
        root = null;
    }
    public int getHeight(Vertex T) {
        if (T == null) {
            return -1;
        }
        return T.height;
    }

    public int getSize(Vertex T) {
        if (T == null) {
            return 0;
        }
        return T.size;
    }

    public int getBalanceFactor(Vertex T) {
        if (T == null) {
            return 0;
        }
        return getHeight(T.left) - getHeight(T.right);
    }


    public int rank(Vertex T, Team v) {

        if (T.team.compareTo(v) == 0) {
            return getSize(T.right) + 1;
        } else if (T.team.compareTo(v) == -1) {
            return rank(T.right, v);
        } else {
            return getSize(T.right) + 1 + rank(T.left, v);
        }
    }

    Vertex leftRotate(Vertex T) {
        Vertex w = T.right;
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

    Vertex rightRotate(Vertex T) {
        Vertex w = T.left;
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

    public Team search(Team v) {
        Vertex res = search(root, v);
        return res == null ? null : res.team;
    }

    // helper method to perform search
    public Vertex search(Vertex T, Team v) {
        if (T == null) return null;                     // not found
        else if (T.team.compareTo(v) == 0) return T;                        // found
        else if (T.team.compareTo(v) == -1) return search(T.right, v);       // search to the right
        else return search(T.left, v);        // search to the left
    }

    // public method called to find Minimum key value in AVLTree
    public Team findMin() {
        return findMin(root);
    }

    // helper method to perform findMin
    public Team findMin(Vertex T) {
        if (T.left == null) return T.team;                    // this is the min
        else return findMin(T.left);           // go to the left
    }

    // public method called to find Maximum key value in AVLTree
    public Team findMax() {
        return findMax(root);
    }

    // helper method to perform findMax
    public Team findMax(Vertex T) {
        if (T.right == null) return T.team;                   // this is the max
        else return findMax(T.right);        // go to the right
    }

    // public method to find successor to given Team v in AVLTree.
    public Team successor(Team v) {
        Vertex vPos = search(root, v);
        return vPos == null ? null : successor(vPos);
    }

    // helper recursive method to find successor to for a given vertex T in AVLTree
    public Team successor(Vertex T) {
        if (T.right != null)                       // this subtree has right subtree
            return findMin(T.right);  // the successor is the minimum of right subtree
        else {
            Vertex par = T.parent;
            Vertex cur = T;
            // if par(ent) is not root and cur(rent) is its right children
            while ((par != null) && (cur == par.right)) {
                cur = par;                                         // continue moving up
                par = cur.parent;
            }
            return par == null ? null : par.team;           // this is the successor of T
        }
    }

    // public method called to insert a new key with value v into AVLTree
    public void insert(Team v) {
        root = insert(root, v);
    }

    // helper recursive method to perform insertion of new vertex into AVLTree
    public Vertex insert(Vertex T, Team v) {
        if (T == null) return new Vertex(v);          // insertion point is found

        if (T.team.compareTo(v) == -1) {                                      // search to the right
            T.right = insert(T.right, v);
            T.right.parent = T;
        } else {                                                 // search to the left
            T.left = insert(T.left, v);
            T.left.parent = T;
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

    // public method to delete a vertex containing key with value v from AVLTree
    public void delete(Team v) {
        root = delete(root, v);
    }

    // helper recursive method to perform deletion
    public Vertex delete(Vertex T, Team v) {
        if (T == null) return T;              // cannot find the item to be deleted

        if (T.team.compareTo(v) == -1)                                    // search to the right
            T.right = delete(T.right, v);
        else if (T.team.compareTo(v) == 1)                               // search to the left
            T.left = delete(T.left, v);
        else {                                            // this is the node to be deleted
            if (T.left == null && T.right == null) {
                T = null;
                return T;             // this is a leaf // simply erase this node
            } else if (T.left == null && T.right != null) {   // only one child at right
                T.right.parent = T.parent;
                T = T.right;                                                 // bypass T
            } else if (T.left != null && T.right == null) {    // only one child at left
                T.left.parent = T.parent;
                T = T.left;                                               // bypass T
            } else {                                 // has two children, find successor
                Team successorV = successor(v);
                T.team = successorV;         // replace this key with the successor's key
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
