package service;

import java.util.Collection;

import node.Node;

public interface IHierarchyProvider {
	
	public Collection<NodeUI> getDescendents(String nodeId); 
	public NodeUI updateParentOfNode(String newParentId, String node); 
	public NodeUI getNode(String nodeId); 
	public Node getRealNode(String nodeId); 

}
