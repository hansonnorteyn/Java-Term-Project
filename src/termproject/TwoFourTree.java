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
* if (node == root()) ... ln 516
* Both fusions
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
        System.out.println("");
        
        // Case 1: Tree is empty
        if (root() == null || root().getNumItems() == 0) {
            throw new TwoFourTreeException("Empty tree");
        }
        
        // Find the node that is storing the key
        TFNode node = search(root(),key);
        TFNode inOrderNode;
        Item inOrderItem;
        int index = FFGTE(node, key); 
        Object returnElement;        
        
        // Case 2: Key not found
        if (!(treeComp.isEqual(node.getItem(index).key(), key))) {
            throw new ElementNotFoundException("Key not found");
        }   
                   
        // Temporarily store element
        returnElement = node.getItem(index).element();                
        
        // Case 2: Node is external
        if (node.getChild(0) == null) {
            
            // Remove item 
            node.removeItem(index);                    
            
            // Fix underflow
            fixUnderflow(node);
        }
       
        
        // Case 3: Node is internal
        else {
            // Get key 0 from inorder node
            inOrderNode = getInOrderSuccessor(node);
            inOrderItem = inOrderNode.removeItem(0);
            
            // Replce item with the item 0 from in order successor
            node.addItem(index, inOrderItem);
                                
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
        

        myTree = new TwoFourTree(myComp);
        final int TEST_SIZE = 10000;

        System.out.println("Inserting elements");        
        for (int i = 0; i < TEST_SIZE; i++) {
            
            myTree.insertElement(i, i);
        }

        for (int i = 0; i < TEST_SIZE; i++) {
            System.out.println("");
            System.out.println("Removing " + i);
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
        if (treeComp.isEqual(searchMe.getItem(index).element(), findKey)) {
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
    
    // TODO: remove checkTree()
    private void fixUnderflow(TFNode node) {
        checkTree();
        // Base case: No underflow
        if (size() <= 1 || node.getNumItems() != 0) {
            return;
        }                
        
        // Special case: underflow at root
        if (node == root()) {
            TFNode leftChild = node.getChild(0);
            Item temp;
            // Put left-most child's right-most item into root 
            // See zybooks Participation Activity 11.5.5
            // https://learn.zybooks.com/zybook/CEDARVILLECS2210GallagherFall2024/chapter/11/section/5
            if (leftChild != null) {
                temp = leftChild.deleteItem(leftChild.getNumItems() - 1);
                node.addItem(0, temp);
            }
                        
            // Reassign node to the left child (fixUnderflow purposes)
            node = leftChild;
        }                        
        
        // Left Transfer 
        // If node has left sib and left sib has >1 items:        
        if (hasLeftSib(node) && getLeftSib(node).getNumItems() > 1) {
            leftTransfer(node);
        }
        
        // Right Transfer
        // If node has right sib and right sib has >1 items:
        else if (hasRightSib(node) && getRightSib(node).getNumItems() > 1) {
            rightTransfer(node);
        }
        
        // Left Fusion
        // If node has left sib:
        else if (hasLeftSib(node)) {
            node = leftFusion(node);
            // Recursively call fixUnderflow
            
            if (node != root()) {
                fixUnderflow(node.getParent());
            }
        }
        
        // Right Fusion
        // Else
        else {
            node = rightFusion(node);
            // Recursively call fixUnderflow
            
            if (node != root()) {
                fixUnderflow(node.getParent());
            }
        }                            
    }
    
    // Transfer from left sibling
    private void leftTransfer(TFNode node) {           
        TFNode parent = node.getParent();
        TFNode leftSib = getLeftSib(node);
        TFNode reassign;
        int nodeIndex = whatChildIsThis(node);
        int leftNumItems = leftSib.getNumItems();
        Item fromParent = parent.getItem(nodeIndex);
        Item fromLeftSib;
        
        // Shifting insert
        node.insertItem(0, fromParent);
        
        // Which node to reassign to parent
        reassign = leftSib.getChild(leftNumItems);        
        
        // Non-shifting delete
        fromLeftSib = leftSib.deleteItem(leftNumItems - 1);
        // Add item to parent
        parent.addItem(nodeIndex, fromLeftSib);
        
        // Reassign parent
        if (reassign != null) {
            reassign.setParent(node);
        }          
        
        // Add child
        node.setChild(0, reassign);
    }
    
    // Transfer from right sibling
    private void rightTransfer(TFNode node) {
        TFNode parent = node.getParent();
        TFNode rightSib = getRightSib(node);
        TFNode reassign;
        int nodeIndex = whatChildIsThis(node);        
        Item fromParent = parent.getItem(nodeIndex);
        Item fromRightSib;
                
        // Non-shifting add
        node.addItem(0, fromParent);
        
        // Store child
        reassign = rightSib.getChild(0);
        
        // Shifting remove
        fromRightSib = rightSib.removeItem(0);         
        
        // Add item to parent
        parent.replaceItem(nodeIndex, fromRightSib);
        
        
        // Replacement of parent
        if (reassign != null) {
            reassign.setParent(node);
            // Add child
            node.setChild(1, reassign); 
        }    
        
    }
    
    // Fuse node with left sibling, return joined node
    private TFNode leftFusion(TFNode node) {
        int nodeIndex = whatChildIsThis(node) - 1;
        // Store child node 
        TFNode reassign = node.getChild(0);
        // Left sibling
        TFNode leftSib = getLeftSib(node);
        // Parent
        TFNode parent = node.getParent();
        // Get/remove item from parent
        Item fromParent = parent.removeItem(nodeIndex);
        
        if (parent == root() && parent.getNumItems() == 1) {
            setRoot(leftSib);
        }
        
        // Left fusion only occurs when left sibling has 1 item,
        // So add item from parent at index 1
        leftSib.addItem(1, fromParent);
        
        // Fix pointers
        // Reassign child from underflowed node
        if (reassign != null) {
            leftSib.setChild(2, reassign);
            reassign.setParent(leftSib);
        }
                
        // Reassign left child to parent
        leftSib.setParent(parent);
        parent.setChild(0, leftSib);
        return leftSib;
    }
    
    // Fuse node with right sibling, return joined node
    private TFNode rightFusion(TFNode node) {
        int nodeIndex = whatChildIsThis(node);
        // Store child node 
        TFNode reassign = node.getChild(0);
        // Right sibling
        TFNode rightSib = getRightSib(node);
        // Parent
        TFNode parent = node.getParent();
        
        // If parent has 1 item, preemptively 
        // reassign root
        if (parent == root() && parent.getNumItems() == 1) {
            setRoot(rightSib);
        }
        
        // Get/remove item from parent
        Item fromParent = parent.removeItem(nodeIndex);       
        
        // Insert item into right sibling
        rightSib.insertItem(0, fromParent);            
                             
        // Fix pointers
        if (reassign != null) {
            rightSib.setChild(0, reassign);
            reassign.setParent(rightSib);            
        }
        return rightSib;
    }
    
    // Check if a node has a left sibling
    private boolean hasLeftSib(TFNode node) {
        return !(node == root() || whatChildIsThis(node) == 0);
    }
    
    // Check if a node has a right sibling    
    private boolean hasRightSib(TFNode node) {
        return !(node == root() || 
                whatChildIsThis(node) == node.getParent().getNumItems() - 1);
    }
    
    // Get a node's left sibling 
    private TFNode getLeftSib(TFNode node) {
        int index = whatChildIsThis(node);
        return node.getParent().getChild(index - 1);
    }
    
    // Get a node's right sibling
    private TFNode getRightSib(TFNode node) {
        int index = whatChildIsThis(node);
        return node.getParent().getChild(index + 1);
    }    
}
