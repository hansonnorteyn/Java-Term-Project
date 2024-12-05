package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
public class TwoFourTree
        implements Dictionary {

    private Comparator treeComp;
    private int size = 0;
    private TFNode treeRoot = null;

    public TwoFourTree(Comparator comp) {
        treeComp = comp;
    }

    private TFNode root() {
        return treeRoot;
    }

    private void setRoot(TFNode root) {
        treeRoot = root;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }
    
    
    
    /**
     * Searches dictionary to determine if key is present
     * @param key to be searched for
     * @return object corresponding to key; null if not found
     */
    public Object findElement(Object key) {
        // Find the node that is storing the key
        TFNode searchNode = search(root(),key);
        
        // Iterate through the keys
        for (int i = 0; i < searchNode.getNumItems(); i++) {
            // Test if node contains desired key
            if (treeComp.isEqual(searchNode.getItem(i), key)) {
                return searchNode.getItem(FFGTE(searchNode, key)).element();
            }
        }
        
        // If key not found, throw exception
        throw new UnsuccessfulSearchException("Key not found");                
    }

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {        
        TFNode insertAtNode = search(root(), key);
        Item insertItem = new Item(key, element);     
        int index;
 
        // Remember: insert always happens at the leaves,
        // and new nodes grow at the top
        
        // Case 1: We do not find key/we find the key at a node
        if (insertAtNode.getChild(0) == null) {
            // Insert
            index = FFGTE(insertAtNode, key);        
            insertAtNode.insertItem(index, insertItem);                  
            
            // Fix overflow
            fixOverflow(insertAtNode);

        }

 
        // Case 2: find key at internal node, i.e., duplicate
            // Move to inorder successor location and insert there
            // Check that node for overflow
        

        // DO NOT call FFGTE if there are duplicates
        // Instead, call whatChildIsThis
        
        
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        return null;
    }

    public static void main(String[] args) {
        Comparator myComp = new IntegerComparator();
        TwoFourTree myTree = new TwoFourTree(myComp);

        Integer myInt1 = 47;
        myTree.insertElement(myInt1, myInt1);
        Integer myInt2 = 83;
        myTree.insertElement(myInt2, myInt2);
        Integer myInt3 = 22;
        myTree.insertElement(myInt3, myInt3);

        Integer myInt4 = 16;
        myTree.insertElement(myInt4, myInt4);

        Integer myInt5 = 49;
        myTree.insertElement(myInt5, myInt5);

        Integer myInt6 = 100;
        myTree.insertElement(myInt6, myInt6);

        Integer myInt7 = 38;
        myTree.insertElement(myInt7, myInt7);

        Integer myInt8 = 3;
        myTree.insertElement(myInt8, myInt8);

        Integer myInt9 = 53;
        myTree.insertElement(myInt9, myInt9);

        Integer myInt10 = 66;
        myTree.insertElement(myInt10, myInt10);

        Integer myInt11 = 19;
        myTree.insertElement(myInt11, myInt11);

        Integer myInt12 = 23;
        myTree.insertElement(myInt12, myInt12);

        Integer myInt13 = 24;
        myTree.insertElement(myInt13, myInt13);

        Integer myInt14 = 88;
        myTree.insertElement(myInt14, myInt14);

        Integer myInt15 = 1;
        myTree.insertElement(myInt15, myInt15);

        Integer myInt16 = 97;
        myTree.insertElement(myInt16, myInt16);

        Integer myInt17 = 94;
        myTree.insertElement(myInt17, myInt17);

        Integer myInt18 = 35;
        myTree.insertElement(myInt18, myInt18);

        Integer myInt19 = 51;
        myTree.insertElement(myInt19, myInt19);

        myTree.printAllElements();
        System.out.println("done");

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(i, i);
            //          myTree.printAllElements();
            //         myTree.checkTree();
        }
        System.out.println("removing");
        for (int i = 0; i < TEST_SIZE; i++) {
            int out = (Integer) myTree.removeElement(i);
            if (out != i) {
                throw new TwoFourTreeException("main: wrong element removed");
            }
            if (i > TEST_SIZE - 15) {
                myTree.printAllElements();
            }
        }
        System.out.println("done");
    }

    public void printAllElements() {
        int indent = 0;
        if (root() == null) {
            System.out.println("The tree is empty");
        }
        else {
            printTree(root(), indent);
        }
    }

    public void printTree(TFNode start, int indent) {
        if (start == null) {
            return;
        }
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
        printTFNode(start);
        indent += 4;
        int numChildren = start.getNumItems() + 1;
        for (int i = 0; i < numChildren; i++) {
            printTree(start.getChild(i), indent);
        }
    }

    public void printTFNode(TFNode node) {
        int numItems = node.getNumItems();
        for (int i = 0; i < numItems; i++) {
            System.out.print(((Item) node.getItem(i)).element() + " ");
        }
        System.out.println();
    }

    // checks if tree is properly hooked up, i.e., children point to parents
    public void checkTree() {
        checkTreeFromNode(treeRoot);
    }

    private void checkTreeFromNode(TFNode start) {
        if (start == null) {
            return;
        }

        if (start.getParent() != null) {
            TFNode parent = start.getParent();
            int childIndex = 0;
            for (childIndex = 0; childIndex <= parent.getNumItems(); childIndex++) {
                if (parent.getChild(childIndex) == start) {
                    break;
                }
            }
            // if child wasn't found, print problem
            if (childIndex > parent.getNumItems()) {
                System.out.println("Child to parent confusion");
                printTFNode(start);
            }
        }

        if (start.getChild(0) != null) {
            for (int childIndex = 0; childIndex <= start.getNumItems(); childIndex++) {
                if (start.getChild(childIndex) == null) {
                    System.out.println("Mixed null and non-null children");
                    printTFNode(start);
                }
                else {
                    if (start.getChild(childIndex).getParent() != start) {
                        System.out.println("Parent to child confusion");
                        printTFNode(start);
                    }
                    for (int i = childIndex - 1; i >= 0; i--) {
                        if (start.getChild(i) == start.getChild(childIndex)) {
                            System.out.println("Duplicate children of node");
                            printTFNode(start);
                        }
                    }
                }

            }
        }

        int numChildren = start.getNumItems() + 1;
        for (int childIndex = 0; childIndex < numChildren; childIndex++) {
            checkTreeFromNode(start.getChild(childIndex));
        }

    }
    
    // Find First Greater Than Or Equal 
    private int FFGTE(TFNode node, Object key) {
        int i;
        for (i = 0; i < node.getNumItems(); i++) {
            if (treeComp.isGreaterThanOrEqualTo(node.getItem(i), key));
        }        
        return i;
    }
    
    private int whatChildIsThis(TFNode node) {
        TFNode parent = node.getParent();
        int i;
        for (i = 0; i < parent.getNumItems() + 1; i++) {
            if (parent.getChild(i) == node) {
                return i;
            }
        }
        // Uncertain what to actually return
        return -1;
    }
    
    // Returns the node that contains the FFGTE
    private TFNode search(TFNode searchMe, Object findKey) {   
        // SearchMe and know me, O God
        int index = FFGTE(searchMe, findKey);
        
        // If index == findKey, we are done
        if (treeComp.isEqual(searchMe.getItem(index), findKey)) {
            return searchMe;
        }

        // If no children, key does not exist
        if (searchMe.getNumItems() == 0) {
            return searchMe;
        }
        
        // Recursively call search on child
        return search(searchMe.getChild(index), findKey);
    }   
    
    
    private void fixOverflow(TFNode node) {
        int index;
        Item temp;
        TFNode parent;
        TFNode newNode = new TFNode();
        
        // If there is no overflow, return
        if (node.getNumItems() <= node.getMaxItems()) {
            return;
        }
        
        // Special case: overflow at root
        if (node == root()) {
            // Remove overflow item (at final index)
            temp = node.removeItem(node.getMaxItems());
            // Initialize newNode
            newNode.addItem(0, temp);
            // Fix pointers
            newNode.setChild(0, root());
            node.setParent(newNode);
            setRoot(newNode);
        }
        
        if (node.getNumItems() == node.getMaxItems()) {
            index = whatChildIsThis(node);
            parent = node.getParent();
            
            // Non-shifting remove
            temp = node.deleteItem(2);
            // Shifting add (Read comment on children pointers in function)
            parent.insertItem(index, temp);
            
            // Item at index 3 into new sibling node
            temp = node.deleteItem(3);            
            newNode.addItem(0, temp);
            
            // Fix pointers
            index = whatChildIsThis(newNode);
            parent.setChild(index, newNode);
            newNode.setParent(parent);
        }
        
        // Recursively calling fixOverflow
        fixOverflow(node.getParent());
        
        // Make sure everything is hooked up properly
        System.out.println("Overflow: calling checkTree()");
        checkTree();
    }
    
}
