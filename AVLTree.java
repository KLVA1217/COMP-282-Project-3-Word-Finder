class AVLTree
{
    Node root;
 
    //Gets the height of the tree
    int height(Node N)
    {
        if (N == null)
             return 0;
         return N.height;
    }
 
    //Get the max of two integers
    int max(int a, int b)
    {
        return (a > b) ? a : b;
    }
 

    //A method for right rotations
    Node rightRotate(Node y)
    {
        Node x = y.left;
        Node T2 = x.right;
 
        // Perform rotation
        x.right = y;
        y.left = T2;
 
        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;
 
        // Return new root
        return x;
    }
 
    // A method for left rotations
    Node leftRotate(Node x)
    {
        Node y = x.right;
        Node T2 = y.left;
 
        // Perform rotation
        y.left = x;
        x.right = T2;
 
        //  Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
 
        // Return new root
        return y;
    }
 
    // Get Balance factor of node n
    int getBalance(Node n)
    {
        if (n == null)
            return 0;
        return height(n.left) - height(n.right);
    }
 
    //Recursive calls to find the node with the given key
    Node find(Node node, double key) {
    	
    	//Node doesn't exist
    	if (node == null) {
   
    	   System.out.println("Couldn't find the queried word!");
		   
           return node;
    	}
 
    	//If smaller key value compared to the current node so check left
        if (key < node.key)
            node.left = find(node.left, key);
        
        //else if greater key value compared to the current node so check right
        else if (key > node.key)
            node.right = find(node.right, key);
        
        //else found the node!!!
        else { 
        	
        	System.out.println("Found the queried word, " + node.word +  " , in the following text files"
        			+ "\n" +node.fileNames);
					
        	return node;
        }
        
        return node;
    }
    
    Node insert(Node node, double key, String word, String fileName)
    {
        
    	//Do a normal BST Insert
        if (node == null)
            return (new Node(key, word, fileName));
 
        //If key value is smaller than the current node's, go to the left subtree
        if (key < node.key)
            node.left = insert(node.left, key, word, fileName);
        
        //else if key value is greater than the current node's, go to the right subtree
        else if (key > node.key)
            node.right = insert(node.right, key, word, fileName);
        
        //else the key is the same and contains the same word, so update the node's fileNames list
        else { // Equal keys not allowed
        	node.addFileName(fileName);
            return node;
            
        }
 
        //Update height of the node 
        node.height = 1 + max(height(node.left),
                              height(node.right));
 
        //Check to see if node is unbalanced
        int balance = getBalance(node);
 
        // If node is unbalanced, do one of the following

        //If unbalanced on outside of the left subtree so do a double right rotation
        if (balance > 1 && key < node.left.key)
			
            return rightRotate(node);
 
        // if unbalanced on outside of the right subtree do a double left rotation
        if (balance < -1 && key > node.right.key)
			
            return leftRotate(node);
 
        // If the node unbalanced is an inside case where the node is on the right subtree of the parent do a left right rotation
        if (balance > 1 && key > node.left.key)
        {
            node.left = leftRotate(node.left);
			
            return rightRotate(node);
        }
 
        // if the node unbalanced is an inside case where the node is on the left subtree of the parent do a right left rotation
        if (balance < -1 && key < node.right.key)
        {
            node.right = rightRotate(node.right);
			
            return leftRotate(node);
        }
 
        return node;
    }
 
    //Return the given node's smallest child
    Node minValueNode(Node node)
    {
        Node current = node;
 
        while (current.left != null)
           current = current.left;
 
        return current;
    }
 
    //Recursively delete the node with the key
    Node deleteNode(Node root, double key)
    {
    	
        //Delete the node 
        if (root == null) {
        	
        	System.out.println("Deleted the queried word ");
        	
            return root;
        }
 
        // If key smaller than the current node's key then search the left of the node
        if (key < root.key) {
        	
            root.left = deleteNode(root.left, key);}
 
        // Else if the key is greater than the current node's key, then search the right of the node
        
        else if (key > root.key) {  
        	
            root.right = deleteNode(root.right, key);}
 
        // Else this is the node to be deleted
        else
        { 
 
            // if the node has one or no children
            if ((root.left == null) || (root.right == null))
            {
                Node temp = null;
                
                //If there's nothing on the left subtree copy the right subtree's information
                if (temp == root.left)
                    temp = root.right;
                
                //Else there's nothing on the right subtree so copy the left subtree's information
                else
                    temp = root.left;
 
                // If the node has no children, just delete the node
                if (temp == null)
                {
                    temp = root;
                    root = null;
                }
                
             // Else, the node only has one child
                else   
                	
                	//So copy its contents to a non-empty child
                    root = temp;
            }
            
            //Else, we assume that the node has two children
            else
            {
 
                // So get the smallest child in the node's right subtree 
                Node temp = minValueNode(root.right);
 
                // Copy the smallest child's data
                root.key = temp.key;
                root.word = temp.word;
                root.fileNames = temp.fileNames;
 
                // Then delete the child 
                root.right = deleteNode(root.right, temp.key);
            }
        }
 
        // If only the root exists, then return the root
        if (root == null)
            return root;
 
        // Update height of the current node
        root.height = max(height(root.left), height(root.right)) + 1;
 
        // Check whether this node is unbalanced
        int balance = getBalance(root);
 
        // If unbalanced do one of the following
        
        // If the unbalanced node is an outside case on the left subtree, do a double right rotation
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);
 
        // If the unbalanced node is an inside case where the node is the right child, do a left right rotation
        if (balance > 1 && getBalance(root.left) < 0)
        {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
 
        // If the unbalanced node is an outside case on the right subtree, do a double left rotation
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);
 
        // If the unbalanced node is an inside case where the node is the left child, then do a right left rotation
        if (balance < -1 && getBalance(root.right) > 0)
        {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
 
        return root;
    }
}