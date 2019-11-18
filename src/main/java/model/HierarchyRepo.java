package model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import node.Node;

@Repository(value="repo")
public interface HierarchyRepo extends JpaRepository<Node, Long>{

	public List<Node> findAll(); 

}
