package service;

import java.io.Serializable;

public class NodeUI implements Serializable{
	
	private static final long serialVersionUID = -3536010408462056960L;
	
	private String nodeId;
	private String parentId;
	private String rootId;
	private int height;
	
	public NodeUI(String nodeId, String parentId, String rootId, int height) {
		this.nodeId = nodeId;
		this.parentId = parentId;
		this.rootId = rootId;
		this.height = height;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	} 
	

}
