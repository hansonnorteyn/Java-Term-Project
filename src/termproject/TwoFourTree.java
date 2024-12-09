package termproject;

/**
 * Title:        Term Project 2-4 Trees
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */


/* 
TODO
* Delete: Just everything
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
        int index = FFGTE(searchNode, key); 
        
        // If key not found, throw exception
        if (!(treeComp.isEqual(searchNode.getItem(index), key))) {
            throw new ElementNotFoundException("Key not found");
        }                      
        
        return searchNode.getItem(index).element();        
    }

    /**
     * Inserts provided element into the Dictionary
     * @param key of object to be inserted
     * @param element to be inserted
     */
    public void insertElement(Object key, Object element) {      
        TFNode insertAtNode;
        Item insertItem = new Item(key, element);     
        
        // Special case: tree is empty
        if (root() == null) {
            insertAtNode = new TFNode();
            insertAtNode.addItem(0, insertItem);
            setRoot(insertAtNode);
        }            
        
        else {
            insertAtNode = search(root(), key);     
            
            // If key is at internal node, there are duplicates
            // Move to In-Order successor 
            if (insertAtNode.getChild(0) != null) {
                insertAtNode = getInOrderSuccessor(insertAtNode);           
            }

            // Insert at index FFGTE
            insertAtNode.insertItem(FFGTE(insertAtNode, key), insertItem);  
        }
        
        // Fix overflow
        fixOverflow(insertAtNode);        
        
        // Increment size
        size++;
    }

    /**
     * Searches dictionary to determine if key is present, then
     * removes and returns corresponding object
     * @param key of data to be removed
     * @return object corresponding to key
     * @exception ElementNotFoundException if the key is not in dictionary
     */
    public Object removeElement(Object key) throws ElementNotFoundException {
        // Find the node that is storing the key
        TFNode searchNode = search(root(),key);
        TFNode inOrderNode;
        Item inOrderItem;
        int index = FFGTE(searchNode, key); 
        Object returnElement;

        // Case 1: Key not found
        if (!(treeComp.isEqual(searchNode.getItem(index), key))) {
            throw new ElementNotFoundException("Key not found");
        }   
                   
        // Temporarily store element
        returnElement = searchNode.getItem(index).element();
        
        // Case 2: Node is external
        if (searchNode.getChild(0) == null) {            
            // Remove item 
            searchNode.removeItem(index);
        
            // Fix underflow
            fixUnderflow(searchNode);
        }
        
        // Special case: Node is root
        
        // Case 3: Node is internal
        else {                          
            // Get key 0 from inorder node
            inOrderNode = getInOrderSuccessor(searchNode);
            inOrderItem = inOrderNode.removeItem(0);
            
            // Replce item with the item 0 from in order successor
            searchNode.addItem(index, inOrderItem);
                                
            // Call fixUnderflow on inorder successor
            fixUnderflow(inOrderNode);
        }
                        
        // Decrement size
        size--;
        
        // Return element
        return returnElement;
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
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt4 = 16;
        myTree.insertElement(myInt4, myInt4);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt5 = 49;
        myTree.insertElement(myInt5, myInt5);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt6 = 100;
        myTree.insertElement(myInt6, myInt6);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt7 = 38;
        myTree.insertElement(myInt7, myInt7);

        myTree.printAllElements();
        System.out.println("");
        
        Integer myInt8 = 3;
        myTree.insertElement(myInt8, myInt8);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt9 = 53;
        myTree.insertElement(myInt9, myInt9);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt10 = 66;
        myTree.insertElement(myInt10, myInt10);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt11 = 19;
        myTree.insertElement(myInt11, myInt11);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt12 = 23;
        myTree.insertElement(myInt12, myInt12);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt13 = 24;
        myTree.insertElement(myInt13, myInt13);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt14 = 88;
        myTree.insertElement(myInt14, myInt14);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt15 = 1;
        myTree.insertElement(myInt15, myInt15);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt16 = 97;
        myTree.insertElement(myInt16, myInt16);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt17 = 94;
        myTree.insertElement(myInt17, myInt17);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt18 = 35;
        myTree.insertElement(myInt18, myInt18);
        
        myTree.printAllElements();
        System.out.println("");

        Integer myInt19 = 51;
        myTree.insertElement(myInt19, myInt19);
        
        myTree.printAllElements();
        System.out.println("done");
        

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;


        for (int i = 0; i < TEST_SIZE; i++) {
            myTree.insertElement(i, i);
            //       myTree.printAllElements();
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
            if (treeComp.isGreaterThanOrEqualTo(node.getItem(i).element(), key)) {
                break;
            }
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
        //throw new UnsuccessfulSearchException("Not a child");
    }
    
    // Returns the node that contains the FFGTE
    private TFNode search(TFNode searchMe, Object findKey) {   
        // Child is null -> unsuccessful search
        // Return the final node we searched
        if (searchMe.getChild(0) == null) {
            return searchMe;
        }              
        
        // FFGTE item in node
        int index = FFGTE(searchMe, findKey);        
        
        if (index == searchMe.getNumItems()) {
            return search(searchMe.getChild(index), findKey);
        }
        
        // If key at index == findKey, return node
        // Return node        
        else if (treeComp.isEqual(searchMe.getItem(index).element(), findKey)) {
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
            TFNode newRoot = new TFNode();
            
            // Set new root
            newRoot.setChild(0, root());            
            setRoot(newRoot);
            node.setParent(newRoot);
        }

        splitNode(node);
        
        // Recursively call fixOverflow
        fixOverflow(node.getParent());
        
        // Make sure everything is hooked up properly
        checkTree();
    }
    
    private TFNode getInOrderSuccessor(TFNode node) {
        int childIndex = whatChildIsThis(node);
                
        // Go to right sibling unless node is the right sibling
        if ((node.getParent().getChild(childIndex + 1)) != null) {
            node = node.getParent().getChild(childIndex + 1);
        }
                
        // Walk down the left nodes
        while (node.getChild(0) != null) {
            node = node.getChild(0);
        }

        return node;
    }
    
    private void splitNode(TFNode node) {
        TFNode newNode = new TFNode();
        TFNode parent = node.getParent();
        TFNode newNodeLChild;
        TFNode newNodeRChild;
        Item temp;
        int index = whatChildIsThis(node);
        
        // Temporarily store children        
        newNodeLChild = node.getChild(3);
        newNodeRChild = node.getChild(4);        
        
        // Item at index 3 
        temp = node.deleteItem(3);    
        // Put into new sibling node
        newNode.addItem(0, temp);
        
        // Item at index 2
        temp = node.deleteItem(2);
        // Shifting add to the parent
        parent.insertItem(index, temp);
                    
        // Index is the one after the original node
        index = 1 + whatChildIsThis(node);      
        
        // Fix pointers     
        parent.setChild(index, newNode);
        newNode.setParent(parent);
        if (newNodeLChild != null) {
            newNode.setChild(0, newNodeLChild);
            newNode.setChild(1, newNodeRChild);
            newNodeLChild.setParent(newNode);
            newNodeRChild.setParent(newNode);
        }
        
        
    }
    
    private void fixUnderflow(TFNode node) {
        if (node.getNumItems() != 0) {
            return;
        }
        
        // Left Transfer 
        // If node has left sib and left sib has >1 items:
        if (hasLeftSib(node) && getLeftSib(node).getNumItems() > 1) {
            
        }
        
        // Right Transfer
        // If node has right sib and right sib has >1 items:
        else if (hasRightSib(node) && getRightSib(node).getNumItems() > 1) {
            
        }
        
        // Left Fusion
        // If node has left sib:
        else if (hasLeftSib(node)) {
            
        }
        
        // Right Fusion
        // else
        else {
            
        }
        
        
        // Recursively call fixUnderflow
        if (node != root()) {
            fixUnderflow(node.getParent());
        }
        
    }
    
    private void leftTransfer(TFNode node) {
        // Shifting insert
        // Non-shifting delete
        // Replacement of parent
    }
    
    private void rightTransfer(TFNode node) {
        // Non-shifting add
        // Shifting remove
        // Replacement of parent
    }
    
    private void leftFusion(TFNode node) {
        // Shifting remove
        // save pointer to child
    }
    
    private void rightFusion(TFNode node) {
        
    }
    
    private boolean hasLeftSib(TFNode node) {
        return !(node == root() || whatChildIsThis(node) == 0);
    }
    
    private boolean hasRightSib(TFNode node) {
        return !(node == root() || 
                whatChildIsThis(node) == node.getParent().getNumItems() - 1);
    }
    
    private TFNode getLeftSib(TFNode node) {
        int index = whatChildIsThis(node);
        return node.getParent().getChild(index - 1);
    }
    
    private TFNode getRightSib(TFNode node) {
        int index = whatChildIsThis(node);
        return node.getParent().getChild(index + 1);
    }
    
}
