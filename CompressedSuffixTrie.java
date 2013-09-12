
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/* written by z3342248 yang yu
 * Running Time analyse 
 * function CompressedSuffixTrie() 
 * n is how many characters found based on "A","C","G","T" 
 * reading file will cost O(n)
 * inserting all the suffix string will cost O(n);
 * time complexity of building a trie is O(n);
 * 
 *  public int findString(String s)   n is size of string "s"
 * searching from the root of trie, then children.
 * comparision have been made n times totally,
 * when it ends at a leaf node, it's fine; if not a leaf node, it will take O(t) to find longest child's starting index, t < n, 
 * because t is the number of current node's children
 * O(n)>O(t), 
 * time complexity of searing a string in a trie is O(n);
 * 
 *  public static String findLongestCommonSubsequence(String f1, String f2) 
 * n is size of string1 coming from f1;m is size of string2 coming from f2;
 * reading files' time cost are  O(n) and O(m) respectively
 * there is a two level loop.
 * both string 1 and string 2 are checked one by one
 * to build L matrix, it will take O(m*n)
 * to convert the common string from L matrix, it will take O(x) ,here x <m*n
 * time complexity of finding Longest Common Subsequence is  O(m*n)
 */

public class CompressedSuffixTrie {

    /**
     * function CompressedSuffixTrie() n is how many characters found based on
     * "A","C","G","T" read file will cost O(n)
     *
     */
    SuffixTrieNode SuffixTrie;
    String ss = "";
    int IndexOfChild = -1;

    public CompressedSuffixTrie(String f) // Create a compressed suffix trie from file f 
    {

        int chs[] = {'A', 'C', 'G', 'T'};
        int s;
        StringBuilder sb = new StringBuilder();
        File file = new File(f);
        if (!file.exists()) {
            System.out.println("can not find the file");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((s = br.read()) != -1) {
                for (int i = 0; i < chs.length; i++) {
                    if (s == chs[i]) {
                        char cs = (char) s;
                        sb.append(cs);
                        break;
                    }
                }
            }
            ss = sb.toString();
            //Initial suffix tree... each index refers a suffix string
            SuffixTrie = new SuffixTrieNode(-1, "", 0, ss);
            for (int index = 0; index < ss.length(); index++) {
                String str = ss.substring(index);
                //    System.out.println(index + "---------" + str + "---------------" + str.length());
                SuffixTrie.insert(index, str, 0, ss);
            }
            // return SuffixTree;
            //Display the whole trie for checking
//            SuffixTrieNode tt = SuffixTrie.child;
//            System.out.println("root");
//            displaytrieNode(tt);
//            System.out.print("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displaytrieNode(SuffixTrieNode tt) {
        for (int i = 0; i < tt.level; i++) {
            System.out.print("     ");
        }
        System.out.print(tt.label + "(" + tt.sIndex + "," + tt.eIndex + ")" + tt.index);
        if (tt.child != null) {
            System.out.print("\n");
            displaytrieNode(tt.child);
        }
        if (tt.next != null) {
            System.out.print("\n");
            displaytrieNode(tt.next);
        }
    }

    public int findString(String s) {
        // Pattern Matching Using Suffi Trie P.24 in PDF of "Text Processing"
//        System.out.println(s);
//        System.out.println(ss);
//        System.out.println(ss.indexOf(s)+"-----is  answer-----------");
        IndexOfChild = -1;
        int p = s.length();
        if (p < 1 || ss.length() < 1) {
            return -1;
        }
        int j = 0;
        SuffixTrieNode v = SuffixTrie;
        boolean f = true;
        int stepin = 0;
        while (f) {
            f = true;
            SuffixTrieNode stn = v.child;
            while (stn != null) {
                int i = stn.sIndex;
                if (s.charAt(j) == ss.charAt(i)) {
                    int x = stn.eIndex - i + 1;
                    if (p <= x) {
                        if (s.substring(j, j + p).equals(ss.substring(i, i + p))) {
                            if (stn.index == -1) {
                                SearchLongestChild(stn.child);
                                return IndexOfChild;
                            } else {
                                return stn.index + stepin - j;
                            }
                        } else {
                            return -1;
                        }
                    } else {
                        if (s.substring(j, j + x).equals(ss.substring(i, i + x))) {
                            p = p - x;
                            j = j + x;
                            stepin = stepin + x;
                            v = stn;
                            // f=false;
                            if (stn.IsLeaf() == true) {
                                f = false;
                            }
                            break;
                        } else {
                            return -1;
                        }
                    }
                } else {
                    stn = stn.next;
                }
            }
            if (stn == null) {
                f = false;
            }
        }
        return -1;
    }

    public void SearchLongestChild(SuffixTrieNode tt) {
        if ((IndexOfChild == -1 || IndexOfChild > tt.index) && tt.index != -1) {
            IndexOfChild = tt.index;
        }
        if (tt.child != null) {
            SearchLongestChild(tt.child);
        }
        if (tt.next != null) {
            SearchLongestChild(tt.next);
        }
    }

    public static String findLongestCommonSubsequence(String f1, String f2) {
        int chs[] = {'A', 'C', 'G', 'T'};
        int s;
        StringBuilder sb = new StringBuilder();
        StringBuilder lcs = new StringBuilder();
        File file = new File(f1);
        String ret = "";
        try {
            // read file1
            if (!file.exists()) {
                return "can not find the file";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            while ((s = br.read()) != -1) {
                for (int i = 0; i < 4; i++) {
                    if (s == chs[i]) {
                        char cs = (char) s;
                        sb.append(cs);
                        break;
                    }
                }
            }
            String str1 = sb.toString();
            // read file2
            file = new File(f2);
            if (!file.exists()) {
                return "can not find the file";
            }
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            sb.delete(0, sb.length());

            while ((s = br.read()) != -1) {
                for (int i = 0; i < 4; i++) {
                    if (s == chs[i]) {
                        char cs = (char) s;
                        sb.append(cs);
                        break;
                    }
                }
            }
            String str2 = sb.toString();
            //  System.out.println("start longestsubstring......................................");
            String Y = str2;
            String X = str1;
//            System.out.println(Y);
//            System.out.println(X);
            //build matrix L to record longest substring of substring1 from X and substring 2 from Y
            int[][] L = new int[X.length() + 1][Y.length() + 1];
            for (int i = X.length() - 1; i >= 0; i--) {
                for (int j = Y.length() - 1; j >= 0; j--) {
                    if (X.charAt(i) == Y.charAt(j)) {
                        L[i][j] = L[i + 1][j + 1] + 1;
                    } else {
                        L[i][j] = Math.max(L[i + 1][j], L[i][j + 1]);
                    }
                }
            }
            //convert matrix to a real string and save it into lcs
            int i = 0;
            int j = 0;
            while (i < X.length() && j < Y.length()) {
                if (X.charAt(i) == Y.charAt(j)) {
                    // System.out.print(X.charAt(i));
                    lcs.append(X.charAt(i));
                    i++;
                    j++;
                } else if (L[i + 1][j] >= L[i][j + 1]) {
                    i++;
                } else {
                    j++;
                }
            }
            ret = lcs.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}

class SuffixTrieNode {
    //Node structure

    int index; // current Suffix String's index based on loop, which starts with the longest suffix string
    int level; // height of current node
    int sIndex; // starting index of current suffix string comparing with the whole string
    int eIndex; // ending index of current suffix string comparing with the whole string
    String label; // current Suffix String content
    SuffixTrieNode next;//current node's closest brother
    SuffixTrieNode child; // current node's first child

    SuffixTrieNode(int i, String s, int level, String T) {
        this.index = i;
        this.label = s;
        this.level = level;
        this.sIndex = T.indexOf(s);
        this.eIndex = this.sIndex + s.length() - 1;
    }
    // to indentify if a node is external node or internal node

    public boolean IsLeaf() {
        if (this.child == null) {
            return true;
        } else {
            return false;
        }
    }

    void insert(int index, String str, int level, String T) {
        SuffixTrieNode NewNode, TNode, PrevNode;
        String strtemp, prefix;
        int ii;
        if (str.length() < 1) {
            return;
        }
        if (this.IsLeaf()) {
            NewNode = new SuffixTrieNode(index, str, level + 1, T);
            this.child = NewNode;
            return;
        }
        TNode = this.child;
        if (TNode.label.charAt(0) > str.charAt(0)) {
            NewNode = new SuffixTrieNode(index, str, level + 1, T);
            this.child = NewNode;
            NewNode.next = TNode;
            return;
        }
        PrevNode = TNode;
        while ((TNode != null) && (TNode.label.charAt(0) < str.charAt(0))) {
            PrevNode = TNode;
            TNode = TNode.next;
        }
        if (TNode == null) {
            NewNode = new SuffixTrieNode(index, str, level + 1, T);
            PrevNode.next = NewNode;
            return;
        }
        if (TNode.label.charAt(0) > str.charAt(0)) {
            NewNode = new SuffixTrieNode(index, str, level + 1, T);
            PrevNode.next = NewNode;
            NewNode.next = TNode;
            return;
        }
        int size = 0;
        if (TNode.label.length() >= str.length()) {
            size = str.length();
        } else {
            size = TNode.label.length();
        }
        for (ii = 1; ii < size; ii++) {
            if (str.length() <= 1) {
                break;
            }
            if (TNode.label.charAt(ii) != str.charAt(ii)) {
                break;
            }
        }
        if (ii == TNode.label.length()) {
            strtemp = str.substring(ii);
            if (str.equals(TNode.label)) {
                return;
            }
            if (strtemp.length() > 0) {
                TNode.insert(index, strtemp, level + 1, T);
            }
            return;
        }
        prefix = TNode.label.substring(0, ii);
        strtemp = TNode.label.substring(ii);
        PrevNode = new SuffixTrieNode(TNode.index, strtemp, level + 1, T);
        PrevNode.child = TNode.child;
        TNode.index = -1;
        TNode.child = PrevNode;
        TNode.label = prefix;
        TNode.sIndex = T.indexOf(prefix);
        TNode.eIndex = TNode.sIndex + prefix.length() - 1;
        PrevNode.UpdateLevel();
        strtemp = str.substring(ii);
        TNode.insert(index, strtemp, level + 1, T);
        return;
    }
    //renew level of current node and its children

    void UpdateLevel() {
        SuffixTrieNode stn;
        this.level++;
        if (this.IsLeaf()) {
            return;
        }
        stn = this.child;
        while (stn != null) {
            stn.UpdateLevel();
            stn = stn.next;
        }
    }
    // Get how many children of a certain Node 

    int getChindrenNum(SuffixTrieNode stn) {
        SuffixTrieNode tt = stn;
        int no = 1;
        if (tt.child == null) {
            return 0;
        }
        tt = tt.child;
        while (true) {
            if (tt.next != null) {
                no++;
                tt = tt.next;
            } else {
                break;
            }
        }
        return no;
    }
}
