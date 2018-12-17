/**
 * BinaryTree
 */
public class BinaryTree {
  private TreeNode root;

  public BinaryTree() {

  }

  public BinaryTree(TreeNode root) {
    this.root = root;
  }

  public void insert(TreeNode node) {
    if (node == null) {
      return;
    }

    if (this.root == null) {
      this.root = node;
      return;
    }

    insertRecursive(this.root, node);
  }

  // precondition: key of newNode should not already exist in tree
  // may be already handled but have not checked
  private TreeNode insertRecursive(TreeNode currNode, TreeNode newNode) {
    if (currNode == null) {
      return newNode;
    }

    if (currNode.getKey() > newNode.getKey()) {
      TreeNode leftChild = insertRecursive(currNode.getLeftChild(), newNode);
      leftChild.setParent(currNode);
      currNode.setLeftChild(leftChild);
    } else if (currNode.getKey() < newNode.getKey()) {
      TreeNode rightChild = insertRecursive(currNode.getRightChild(), newNode);
      rightChild.setParent(currNode);
      currNode.setRightChild(rightChild);
    }

    return currNode;
  }

  public TreeNode searchByKey(int key, TreeNode currNode) {
    if (currNode == null) {
      return null;
    }

    if (key > currNode.getKey()) {
      return searchByKey(key, currNode.getRightChild());
    } else if (key < currNode.getKey()) {
      return searchByKey(key, currNode.getLeftChild());
    } else {
      return currNode;
    }
  }

  public TreeNode searchByKey(int key) {
    return this.searchByKey(key, this.root);
  }

  public TreeNode findMinNode(TreeNode currNode) {
    // handle null root node
    if (currNode == null || currNode.getLeftChild() == null) {
      return currNode;
    } else {
      return findMinNode(currNode.getLeftChild());
    }
  }

  // null if this.root is null
  public TreeNode findMinNode() {
    return findMinNode(this.root);
  }

  // null if this.root is null
  public TreeNode findMaxNode(TreeNode currNode) {
    // handle null root node
    if (currNode == null || currNode.getRightChild() == null) {
      return currNode;
    } else {
      return findMaxNode(currNode.getRightChild());
    }
  }

  public TreeNode findMaxNode() {
    return findMaxNode(this.root);
  }

  public int height(TreeNode currNode) {
    if (currNode == null) {
      return 0;
    }

    return Math.max(height(currNode.getLeftChild()),
                    height(currNode.getRightChild())
    ) + 1;
  }

  public int height() {
    return height(this.root);
  }

  public TreeNode findSuccessor(TreeNode node) {
    // handle when node does not exist
    if (node == null) {
      return null;
    }

    // if has a right subtree, successor is the min node in there.
    if (node.getRightChild() != null) {
      return findMinNode(node.getRightChild());
    }

    // if node has no right subtree, the successor is one of its parent
    // that means root nodes with no right subtree's do not have sucessors.
    if (node.getParent() == null) {
      return null;
    }

    // if node is left child, successor is node's parent
    if (node.getKey() < node.getParent().getKey()) {
      return node.getParent();
    }

    // if node is right child, successor is first parent that is larger
    if (node.getKey() > node.getParent().getKey()) {
      TreeNode parent = node.getParent();
      while (parent != null) {
        if (parent.getKey() > node.getKey()) {
          return parent;
        }
        parent = parent.getParent();
      }
      return null;
    }
    return null;
  }

  public TreeNode findPredecessor(TreeNode node) {
    if (node == null) {
      return null;
    }

    // if has a left subtree, predecessor is the max node in there.
    if (node.getLeftChild() != null) {
      return findMaxNode(node.getLeftChild());
    }

    // if node has no left subtree, the predecessor is one of its parent
    // that means root nodes with no left subtree's do not have predecessors.
    if (node.getParent() == null) {
      return null;
    }

    // if node is right child, predecessor is node's parent
    if (node.getKey() > node.getParent().getKey()) {
      return node.getParent();
    }

    // if node is left child, predecessor is first parent that is smaller
    if (node.getKey() < node.getParent().getKey()) {
      TreeNode parent = node.getParent();
      while (parent != null) {
        if (parent.getKey() < node.getKey()) {
          return parent;
        }
        parent = parent.getParent();
      }
      return null;
    }
    return null;
  }

  // precondition: node must already be in the tree
  public void delete(TreeNode node) {
    // if node is a leaf, just remove it
    if (node.getLeftChild() == null && node.getRightChild() == null) {
      TreeNode parent = node.getParent();
      // if node is not a root node
      if (parent != null) {
        if (parent.getLeftChild().getKey() == node.getKey()) {
          parent.setLeftChild(null);
        } else {
          parent.setRightChild(null);
        }
        // help GC
        node.setParent(null);
        node = null;
      } else {
        this.root = null;
      }
    } 
    // if node only has one child, connect it to node's parent
    else if (node.getLeftChild() == null ^ node.getRightChild() == null) {
      TreeNode childNode;
      if (node.getLeftChild() != null) {
        childNode = node.getLeftChild();
      } else {
        childNode = node.getRightChild();
      }
      TreeNode parentNode = node.getParent();
      if (parentNode.getLeftChild().getKey() == node.getKey()) {
        parentNode.setLeftChild(childNode);
      } else {
        parentNode.setRightChild(childNode);
      }
      childNode.setParent(parentNode);
      // cleanup
      node.setParent(null);
      node = null;
    }
    // if node has two children, replace it with it's successor
    // successor's original parent should be attached to successor's right child (succ cannot have left child)
    else {
      TreeNode successor = findSuccessor(node);
      TreeNode parentNode = node.getParent();
      TreeNode successorParent = successor.getParent();
      TreeNode successorRightChild = successor.getRightChild();
      
      successor.setParent(node.getParent());
      successor.setLeftChild(node.getLeftChild());
      successor.setRightChild(node.getRightChild());
      node.getLeftChild().setParent(successor);
      node.getRightChild().setParent(successor);
      
      // set successor's parent's child to successor's right child
      if (successorParent.getLeftChild().getKey() == successor.getKey()) {
        successorParent.setLeftChild(successorRightChild);
      } else {
        successorParent.setRightChild(successorRightChild);
      }

      // need to set deleted node's child to the successor
      // CASE: node is not a root node
      if (parentNode != null) {
        if (parentNode.getLeftChild().getKey() == node.getKey()) {
          parentNode.setLeftChild(successor);
        } else {
          parentNode.setRightChild(successor);
        }
      } else {
        this.root = successor;
      }

      // cleanup
      node.setParent(null);
      node = null;
    } 
  }

  public static void main(String[] args) {

    // insert works
    // BinaryTree bst = new BinaryTree(new TreeNode(50));
    // bst.insert(new TreeNode(25));
    // bst.insert(new TreeNode(75));
    // bst.insert(new TreeNode(10));
    // bst.insert(new TreeNode(40));
    // bst.insert(new TreeNode(60));
    // bst.insert(new TreeNode(100));

    // find 
    // BinaryTree bst = new BinaryTree(new TreeNode(50));
    // bst.insert(new TreeNode(25));
    // bst.insert(new TreeNode(75));
    // bst.insert(new TreeNode(10));
    // bst.insert(new TreeNode(40));
    // bst.insert(new TreeNode(60));
    // bst.insert(new TreeNode(100));

    // // true
    // Boolean a = bst.searchByKey(25);
    // Boolean b = bst.searchByKey(75);
    // Boolean c = bst.searchByKey(10);
    // Boolean d = bst.searchByKey(40);
    // Boolean e = bst.searchByKey(60);
    // Boolean f = bst.searchByKey(100);
    // Boolean g = bst.searchByKey(50);

    // // false
    // Boolean h = bst.searchByKey(69);

    // TreeNode max = bst.findMaxNode();
    // TreeNode min = bst.findMinNode();

    BinaryTree emptyBST = new BinaryTree();
    // TreeNode maxNULL = emptyBST.findMaxNode();
    // TreeNode minNULL = emptyBST.findMinNode();


    // // height

    // int height = bst.height();
    // bst.insert(new TreeNode(69));
    // int heightWhenImbalanced = bst.height();

    BinaryTree bst = new BinaryTree(new TreeNode(41));
    bst.insert(new TreeNode(20));
    bst.insert(new TreeNode(65));
    bst.insert(new TreeNode(11));
    bst.insert(new TreeNode(21));
    bst.insert(new TreeNode(15));
    bst.insert(new TreeNode(16));
    bst.insert(new TreeNode(50));
    bst.insert(new TreeNode(91));
    bst.insert(new TreeNode(49));
    bst.insert(new TreeNode(55));
    bst.insert(new TreeNode(51));
    bst.insert(new TreeNode(72));
    bst.insert(new TreeNode(99));

    // TreeNode a = bst.searchByKey(65);
    // // 72
    // TreeNode hasRightSubtree = bst.findSuccessor(bst.searchByKey(65));
    // // 55
    // TreeNode hasNoRightSubtreeIsLeftChild = bst.findSuccessor(bst.searchByKey(51));
    // // null
    // TreeNode hasNoRightSubtreeIsRightChildButNoSuccessor = bst.findSuccessor(bst.searchByKey(99));
    // // 65
    // TreeNode hasNoRightSubtreeIsRightChild = bst.findSuccessor(bst.searchByKey(55));
    // //
    // BinaryTree rootOnlyBST = new BinaryTree(new TreeNode(1));
    // TreeNode onlyRoot = rootOnlyBST.findSuccessor(rootOnlyBST.searchByKey(1));

    // // 21
    // TreeNode hasLeftSubtree = bst.findPredecessor(bst.searchByKey(41));
    // // 41
    // TreeNode hasNoLeftSubtreeIsLeftChild = bst.findPredecessor(bst.searchByKey(49));
    // // null
    // TreeNode hasNoLeftSubtreeIsLeftChildButNoPredecessor = bst.findPredecessor(bst.searchByKey(11));
    // // 91
    // TreeNode hasNoLeftSubtreeIsRightChild = bst.findPredecessor(bst.searchByKey(99));

    // bst.delete(bst.searchByKey(99));
    // bst.delete(bst.searchByKey(11));
    //bst.delete(bst.searchByKey(65));
    bst.delete(bst.searchByKey(41));

  }
}