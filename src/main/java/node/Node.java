package node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
@Entity
@Table(name = "hierarchy")
public class Node implements Serializable{
	
	private static final long serialVersionUID = 7034723325537627470L;

	@Id
	@Column(name = "id")
	private String id;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
	@Column(name = "node_id")
	private String nodeId;
	
	@JsonIgnore
	@Transient
	private Node parent;
	
	@JsonIgnore
	@Transient
	private Node root;
	
	@Column(name = "parent_id")
	private String parentId;
	
	@Column(name = "root_id")
	private String rootId;
	
	@Column(name = "height")
	private int height;
	
	@JsonIgnore
	@Transient
	private List<Node> children = new ArrayList<Node>();

	public Node() {}
	
	public Node(String nodeId, Node parent, Node root,int height) {

		this.nodeId = nodeId;
		this.parent = parent;
		this.parentId = (parent == null) ? " " : parent.getId();
		this.root = root;
		this.rootId= (root == null) ? " " : root.getId();
		this.height = height;
	}
	
	public Node(String nodeId, String parentId, String rootId,int height) {

		this.nodeId = nodeId;
		this.parentId = parentId;
		this.rootId=rootId ;
		this.height = height;
		children = new ArrayList<Node>();
	}
	
	public String getNodeId() {
		return this.nodeId;
	}
	
	public Node getNode() {
		return this;
	}
	

	public String getParentId() {
		return this.parentId;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}

	public String getRootId() {
		return this.rootId;
	}

	public String getId() {
		return nodeId;
	}

	public void setId(String id) {
		this.nodeId = id;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
		if (parent != null)
			this.parentId = parent.getId();
	}

	public int getHeight() {
		return this.height;
	}

	public void addChild(Node child) {
		children.add(child);
	}

	public void removeChild(Node node) {
		if (getChildren().contains(node))
			getChildren().remove(node);
	}

	public List<Node> getChildren() {
		return children;
	}

	public void addChildren(List<Node> children) {
		this.children.addAll(children);
	}
	
	
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Node [id=" + nodeId + ", parentId=" + getParentId() + ", rootId=" + getRootId() + ", height=" + height + "]";

	}

}
