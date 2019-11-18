package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import node.Node;

@Component
public class HierarchyRepoDummyLoader {
	
	private Node root;
	
	
	public HierarchyRepoDummyLoader () {
    
		root=initializeHierarchy();
		
	}
	
	public Node getNode(String id) {
		
		Stack<Node> stack = new Stack<Node>();
		
		stack.push(root);

		while (!stack.isEmpty()) {

			Node node = stack.pop();

			if (node.getId().equals(id))
				return node;

			if (node != null) {

				if (node.getChildren() != null) {
					for (Node child : node.getChildren())
						stack.push(child);
				}
			}
		}

		return null;
	}
	
	
	
		/* Creating the following tree 
		 * 	
	root
	/  \
   1    2
  / \   /\
 3	 4 5  6
         /  \
/        8   9
7
		     *
		     */
		
	private Node initializeHierarchy() {

		List<Node> allNodes = findAll();

		List<String> placeHolder = new ArrayList<String>();

		for (Node node : allNodes) {

			if (!placeHolder.contains(node.getId())) {

				List<Node> children = findChildren(node, allNodes);

				if (!CollectionUtils.isEmpty(children))
					node.addChildren(children);

				node.setParent(findParent(node, allNodes));
				placeHolder.add(node.getId());

				if (node.getId().equals("root"))
					root = node;
			}
		}
		return root;
	}
		
		private List<Node> findChildren(Node node,List<Node> allNodes) {
			return allNodes.stream().filter(n->n.getParentId().equals(node.getId())).collect(Collectors.toList());
		}
		
		private Node findParent(Node node,List<Node> allNodes) {
			return allNodes.stream().filter(n->n.getId().equals(node.getParentId())).findAny().orElse(null);
		}

		public List<Node> findAll() {
			
			List<Node> list = new ArrayList<Node>();
			Node root = new Node("root", "", "",0);
			Node one = new Node("1", root, root,1);
			Node two = new Node("2", root, root,1);
			Node three = new Node("3", one, root,2);
			Node four = new Node("4", one, root,2);
			Node five = new Node("5", two, root,2);
			Node six = new Node("6", two, root,2);
			Node seven = new Node("7",three, root,3);
			Node eight = new Node("8", six, root,3);
			Node nine = new Node("9", six, root,3);
			
			list.add(root);
			list.add(one);
			list.add(two);
			list.add(three);
			list.add(four);
			list.add(five);
			list.add(six);
			list.add(seven);
			list.add(eight);
			list.add(nine);
			
			return list;

		}

}
