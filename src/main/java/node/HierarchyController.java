package node;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.HierarchyProviderImpl;
import service.NodeUI;

@RestController
@EnableJpaRepositories("model")
public class HierarchyController {

	   @Autowired 
	   private HierarchyProviderImpl provider;
	    

	    @GetMapping(path = "/getDescendents")
	    public Collection<NodeUI> getDescendents(@RequestParam(value="nodeId", defaultValue="root") String nodeId) {
	        return provider.getDescendents(nodeId);
	    }
	    	
	    @PostMapping(path = "/updateParent")
	    public NodeUI updateParent(@RequestParam String newParentId,@RequestParam String childId) {
	    	
	       return provider.updateParentOfNode(newParentId, childId); 
	    
	    }

}
