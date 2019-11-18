package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import model.HierarchyRepo;
import node.Node;

@Component
@EnableJpaRepositories("model")
public class HierarchyProviderImpl implements IHierarchyProvider {

	@Autowired
	@Qualifier("repo")
	private HierarchyRepo repo;

	List<Node> enrichedNodes;

	@PostConstruct
	private void initializeHierarchy() {

		enrichedNodes = repo.findAll();

		List<String> placeHolder = new ArrayList<String>();

		for (Node node : enrichedNodes) {

			if (!placeHolder.contains(node.getId())) {

				List<Node> children = findChildren(node, enrichedNodes);

				if (!CollectionUtils.isEmpty(children))
					node.addChildren(children);

				node.setParent(findParent(node, enrichedNodes));
				placeHolder.add(node.getId());

			}
		}
	}

	void recalculateHeight(Node newParent) {
		if (newParent == null)
			return;

		Node node = enrichedNodes.stream().filter(r -> r.getParentId().equals("")).findAny().orElse(null);

		LinkedList<Node> queue = new LinkedList<Node>();
		queue.push(node);

		int height = 0;

		while (!queue.isEmpty()) {

			Node n = queue.poll();
			if (!CollectionUtils.isEmpty(n.getChildren())) {
				height++;

				for (Node child : n.getChildren()) {

					if (child.getId().equals(newParent.getId())) {
						newParent.setHeight(height);
						return;
					}
					queue.add(child);
				}
			}
		}
	}

	private List<Node> findChildren(Node node, List<Node> allNodes) {
		return allNodes.stream().filter(n -> n.getParentId().equals(node.getId())).collect(Collectors.toList());
	}

	private Node findParent(Node node, List<Node> allNodes) {
		return allNodes.stream().filter(n -> n.getId().equals(node.getParentId())).findAny().orElse(null);
	}

	@Override
	public Collection<NodeUI> getDescendents(String nodeId) {
		Node node = findNode(nodeId);
		Collection<NodeUI> nodesForUI = convertAll(getDescendents(node));
		return nodesForUI;
	}

	private Collection<NodeUI> convertAll(Collection<Node> nodes) {
		List<NodeUI> nodesForUI = new ArrayList<>();
		for (Node node : nodes) {
			nodesForUI.add(getNodeUI(node));
		}
		return nodesForUI;
	}

	private NodeUI getNodeUI(Node node) {
		return new NodeUI(node.getNodeId(), node.getParentId(), node.getRootId(), node.getHeight());
	}

	private Node findNode(String nodeId) {
		Node node = enrichedNodes.stream().filter(n -> n.getNodeId().equals(nodeId)).findAny().orElse(null);
		return node;
	}

	private Collection<Node> getDescendents(Node node) {

		List<Node> descendentds = new ArrayList<Node>();

		Stack<Node> stack = new Stack<Node>();

		if (node == null)
			return descendentds;

		stack.push(node);

		while (!stack.isEmpty()) {

			Node n = stack.pop();

			if (!n.getId().equals(node.getId()))
				descendentds.add(n);

			if (n != null) {

				if (n.getChildren() != null) {
					for (Node child : n.getChildren())
						stack.push(child);
				}
			}
		}
		return descendentds;
	}

	/*
	 * root / \ 1 2 / \ /\ 3 4 5 6 / \ / 8 9 7
	 */

	private Node updateParentOfNode(Node newParent, Node node) {

		if (node == null || newParent == null)
			return null;

		if (newParent.getId().equals(node.getId()))
			throw new RuntimeException("Invalid request: can not set same node as its parent");

		Node oldParent = node.getParent();
		oldParent.removeChild(node);

		node.setParent(newParent);
		newParent.addChild(node);
		recalculateHeight(node);
		repo.save(node);
		return node;
	}

	@Override
	public NodeUI updateParentOfNode(String newParentId, String node) {

		Node newParentNode = findNode(newParentId);
		Node child = findNode(node);

		if (newParentId == null || child == null)
			return null;

		return getNodeUI(updateParentOfNode(newParentNode, child));

	}

	@Override
	public NodeUI getNode(String nodeId) {
		return getNodeUI(findNode(nodeId));
	}

	public Node getRealNode(String nodeId) {
		return findNode(nodeId);
	}

}
