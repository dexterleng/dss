/**
 * TreeNode
 */
public class TreeNode {
  private int key;
  private TreeNode leftChild;
  private TreeNode rightChild;
  private TreeNode parent;

  public TreeNode(int key) {
    this.key = key;
  }

  public TreeNode(int key, TreeNode leftChild, TreeNode rightChild) {
    this.key = key;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  public int getKey() {
    return this.key;
  }

  public TreeNode getLeftChild() {
    return this.leftChild;
  }

  public TreeNode getRightChild() {
    return this.rightChild;
  }

  public TreeNode getParent() {
    return this.parent;
  }

  public void setLeftChild(TreeNode leftChild) {
    this.leftChild = leftChild;
  }

  public void setRightChild(TreeNode rightChild) {
    this.rightChild = rightChild;
  }

  public void setParent(TreeNode parent) {
    this.parent = parent;
  }
}