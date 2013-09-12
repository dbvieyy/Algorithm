
import net.datastructures.*;
// necessary import
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
//running time: I initially indentify which tree is bigger, and then search all the keys in smaller tree, insert them in the bigger tree
// therefore, running time of finding keys in smaller tree is O(log n) in this case, it should be O(log 6)
//an AVL tree storing n keys is O(log n), in this case, it should be O(log 6)
// as a result running time for "merge" function is O(log n)*O(log n), in this case, O(log 6)*O(log 6)

// function creation:
// public static <K, V> void clonenew(Position<Entry<K, V>> v, AVLTree<K, V> tree, Position<Entry<K, V>> v2, AVLTree<K, V> NewTree)
//               used to copy nodes from given tree, tree and root of tree are required
//public static <K, V> void output(Position<Entry<K, V>> v, AVLTree<K, V> tree, int Height) 
//               used to search the AVLTree and store keys in a List , structure conversion from tree to list
// class creation:
//class Drawtree extends Frame -----  which contains a few functions
//public int getParent(int index)
// public int has_left_child(int index)
// public int has_right_child(int index)  these three are used to find certain key from a list
// public Drawtree(List LofK, List LofV, List LofH)-------- this class extends Frame class and rewrite paint() 
//        
public class ExtendedAVLTree<K, V> extends AVLTree<K, V> {

    public static List LofK = new ArrayList();
    public static List LofV = new ArrayList();
    public static List LofH = new ArrayList();

    public static <K, V> AVLTree<K, V> clone(AVLTree<K, V> tree) {

        AVLTree<K, V> NewTree = new AVLTree<K, V>();
        NewTree.addRoot(tree.root().element());
        clonenew(tree.root(), tree, NewTree.root(), NewTree);
        return NewTree;
    }
    /*
     * function name: clonenew ; 
     * parameters: Position<Entry<K, V>> v, AVLTree<K,V> tree, Position<Entry<K, V>> v2, AVLTree<K, V> NewTree return: AVLTree
     * description: copy node v in tree and create a new node in the same
     * postion in NewTree
     */

    public static <K, V> void clonenew(Position<Entry<K, V>> v, AVLTree<K, V> tree, Position<Entry<K, V>> v2, AVLTree<K, V> NewTree) {

        if (tree.hasLeft(v)) {
            NewTree.insertLeft(v2, tree.left(v).element());
            clonenew(tree.left(v), tree, NewTree.left(v2), NewTree);
        }
        if (tree.hasRight(v)) {
            NewTree.insertRight(v2, tree.right(v).element());
            clonenew(tree.right(v), tree, NewTree.right(v2), NewTree);
        }
    }

    public static <K, V> AVLTree<K, V> merge(AVLTree<K, V> tree1, AVLTree<K, V> tree2) {
        LofK.clear();
        LofV.clear();
        LofH.clear();
        int Height = 0;
        Position<Entry<K, V>> RootP;

        if (tree1.size() >= tree2.size()) {
            RootP = tree2.root();
            output(RootP, tree2, Height);
            for (int i = 0; i < LofK.size(); i++) {
                tree1.insert((K) LofK.get(i), (V) LofV.get(i));
            }
            return tree1;
        } else {
            RootP = tree1.root();
            output(RootP, tree1, Height);
            for (int i = 0; i < LofK.size(); i++) {
                tree2.insert((K) LofK.get(i), (V) LofV.get(i));
            }
            return tree2;
        }
    }

    static <K, V> void print(AVLTree<K, V> tree) {
        LofK.clear();
        LofV.clear();
        LofH.clear();
        Position<Entry<K, V>> RootP = tree.root();
        int Height = 0;
        output(RootP, tree, Height);
       

//        System.out.println(LofK);
//        System.out.println(LofV);
//        System.out.println(LofH);
        Drawtree drtree = new Drawtree(LofK, LofV, LofH);

    }
    /*
     * function name: output 
     * parameters: Position<Entry<K, V>> v, AVLTree<K, V> tree, int Height
     * return: void
     * description: search the AVLTree and store keys in a List , structure conversion from tree to list
     */

    public static <K, V> void output(Position<Entry<K, V>> v, AVLTree<K, V> tree, int Height) {
        Height++;
        if (tree.hasLeft(v)) {
            output(tree.left(v), tree, Height);
        }
        if (tree.isInternal(v)) {
            LofK.add(v.element().getKey());
            LofV.add(v.element().getValue());
            LofH.add(Height);
        }
        if (tree.hasRight(v)) {
            output(tree.right(v), tree, Height);
        }
    }

    public static void main(String[] args) {
        String values1[] = {"Sydney", "Beijing", "Shanghai", "New York", "Tokyo", "Berlin", "Athens", "Paris", "London", "Cairo"};
        int keys1[] = {20, 8, 5, 30, 22, 40, 12, 10, 3, 5};
        String values2[] = {"Fox", "Lion", "Dog", "Sheep", "Rabbit", "Fish"};
        int keys2[] = {40, 7, 5, 32, 20, 30};

        /*
         * Create the first AVL tree with an external node as the root and the
         * default comparator
         */

        AVLTree<Integer, String> tree1 = new AVLTree<Integer, String>();

        // Insert 10 nodes into the first tree

        for (int i = 0; i < 10; i++) {
            tree1.insert(keys1[i], values1[i]);
        }

        /*
         * Create the second AVL tree with an external node as the root and the
         * default comparator
         */

        AVLTree<Integer, String> tree2 = new AVLTree<Integer, String>();

        // Insert 6 nodes into the tree

        for (int i = 0; i < 6; i++) {
            tree2.insert(keys2[i], values2[i]);
        }

        ExtendedAVLTree.print(tree1);
        ExtendedAVLTree.print(tree2);
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree1));
        ExtendedAVLTree.print(ExtendedAVLTree.clone(tree2));
        ExtendedAVLTree.print(ExtendedAVLTree.merge(tree1, tree2));

    }
}
  /*
     * Class name: Drawtree 
     * description: draw trees
     */
class Drawtree extends Frame {

    public List Keys = new ArrayList();
    public List Values = new ArrayList();
    public List Height = new ArrayList();
    int Heightest = 0;

    public void setKeys(List LofK) {
        for (int i = 0; i < LofK.size(); i++) {
            this.Keys.add(LofK.get(i));
        }
    }

    public void setValues(List LofV) {
        for (int i = 0; i < LofV.size(); i++) {
            this.Values.add(LofV.get(i));
        }
    }

    public void setHeight(List LofH) {
        for (int i = 0; i < LofH.size(); i++) {
            this.Height.add(LofH.get(i));
            if ((int) this.Height.get(i) > Heightest) {
                Heightest = (int) this.Height.get(i);
            }
        }
    }

    public Drawtree(List LofK, List LofV, List LofH) {
        //super(); 
        setKeys(LofK);
        setValues(LofV);
        setHeight(LofH);
        setBounds(0, 0, 100, 100);

        setBackground(Color.white);
        setSize(1000, 700);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Drawtree.this.dispose();
            }
        });

    }
// rewrite function
    public void paint(Graphics g) {
        g.setColor(Color.black);
        //x of key is x of cycle + 1; y of key is y of cycle + 13; so that number is suitable for the cycle
        // draw internal nodes
        for (int i = 0; i < Keys.size(); i++) {
            int x = 100;
            int y = 500;
            x = x + 60 * i;
            y = y + 80 * ((int) Height.get(i) - Heightest);
            g.drawOval(x, y, 16, 16);
            String data1 = Keys.get(i).toString();
            int KV = (int) Keys.get(i);
            if (KV / 10 == 0) {
                g.drawString(data1, x + 5, y + 13);
            } else {
                g.drawString(data1, x + 1, y + 13);
            }
        }
        // draw leaves and branches between nodes and leaves
        for (int i = 1; i <= Heightest; i++) {
            for (int k = 0; k < Keys.size(); k++) {
                if ((int) Height.get(k) == i) {
                    int x = 100 + 60 * k;
                    int y = 500 + 80 * ((int) Height.get(k) - Heightest);
                    if (has_left_child(k) == -1) {

                        int LX = x - 30;
                        int LY = y + 80;
                        g.drawRect(LX, LY, 15, 10);
                        g.drawLine(x + 8, y + 16, LX + 5, LY);
                    }
                    if (has_right_child(k) == -1) {

                        int LX = x + 30;
                        int LY = y + 80;
                        g.drawRect(LX, LY, 15, 10);
                        g.drawLine(x + 8, y + 16, LX + 5, LY);
                    }
                }
            }
        }

        // draw branches between nodes
        for (int i = 1; i <= Heightest; i++) {
            for (int k = 0; k < Keys.size(); k++) {
                if ((int) Height.get(k) == i) {
                    int x = 100 + 60 * k;
                    int y = 500 + 80 * ((int) Height.get(k) - Heightest);
                    if (has_left_child(k) != -1) {
                        int dex = has_left_child(k);
                        int LX = 100 + 60 * dex;
                        int LY = 500 + 80 * ((int) Height.get(dex) - Heightest);
                        g.drawLine(x + 8, y + 16, LX + 8, LY);
                    }
                    if (has_right_child(k) != -1) {
                        int dex = has_right_child(k);
                        int LX = 100 + 60 * dex;
                        int LY = 500 + 80 * ((int) Height.get(dex) - Heightest);
                        g.drawLine(x + 8, y + 16, LX + 8, LY);
                    }
                }
            }
        }
    }

    public int getParent(int index) {
        int no1 = -1;
        int no2 = -1;
        if ((int) Height.get(index) == 1) {
            return index;
        }
        for (int i = index - 1; i >= 0; i--) {
            if ((int) Height.get(index) == (int) Height.get(i) + 1) {
                no1 = i;
                break;
            }
        }
        for (int i = index + 1; i < Keys.size(); i++) {
            if ((int) Height.get(index) == (int) Height.get(i) + 1) {
                no2 = i;
                break;
            }
        }
        if (no1 == -1 && no2 != -1) {
            return no2;
        } else if (no2 == -1 && no1 != -1) {
            return no1;
        } else if (no2 == -1 && no1 == -1) {
            return index;
        } else {
            if (index - no1 < no2 - index) {
                return no1;
            } else {
                return no2;
            }
        }
    }

    public int has_left_child(int index) {
        int no = -1;// no child
        if ((int) Keys.get(index) <= (int) Keys.get(getParent(index))) {
            for (int i = index - 1; i >= 0; i--) {
                if ((int) Height.get(i) - (int) Height.get(index) < 0) {
                    break;
                }
                if ((int) Height.get(index) + 1 == (int) Height.get(i)) {
                    no = i;
                    break;
                }
            }
        } else {
            for (int i = index - 1; i >= getParent(index); i--) {
                if ((int) Height.get(i) - (int) Height.get(index) < 0) {
                    break;
                }
                if ((int) Height.get(index) + 1 == (int) Height.get(i)) {
                    no = i;
                    break;
                }
            }
        }
        return no;
    }

    public int has_right_child(int index) {
        int no = -1;// no child
        if ((int) Keys.get(index) < (int) Keys.get(getParent(index))) {
            for (int i = index + 1; i <= getParent(index); i++) {
                if ((int) Height.get(i) - (int) Height.get(index) < 0) {
                    break;
                }
                if ((int) Height.get(index) + 1 == (int) Height.get(i)) {
                    no = i;
                    break;
                }
            }
        } else {
            for (int i = index + 1; i < Keys.size(); i++) {
                if ((int) Height.get(i) - (int) Height.get(index) < 0) {
                    break;
                }
                if ((int) Height.get(index) + 1 == (int) Height.get(i)) {
                    no = i;
                    break;
                }
            }
        }
        return no;
    }
}
//running time: I initially indentify which tree is bigger, and then search all the keys in smaller tree, insert them in the bigger tree
// therefore, running time of finding keys in smaller tree is O(log n) in this case, it should be O(log 6)
//an AVL tree storing n keys is O(log n), in this case, it should be O(log 6)
// as a result running time for "merge" function is O(log n)*O(log n), in this case, O(log 6)*O(log 6)
