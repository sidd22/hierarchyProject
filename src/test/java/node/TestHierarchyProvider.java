package node;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.HierarchyRepo;
import service.HierarchyProviderImpl;
import service.IHierarchyProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = TestHierarchyProvider.TestConfig.class)
public class TestHierarchyProvider {
	
	
	@Autowired
	private IHierarchyProvider provider;
	
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


	@Test
	public void testAFindDescendent() {
		
		assertEquals("Incorrect : ", 9, provider.getDescendents("root").size());
		
		assertEquals("Incorrect : ", 0, provider.getDescendents("8").size());
		
		assertEquals("Incorrect : ", 2, provider.getDescendents("6").size());

		assertEquals("Incorrect : ", 1, provider.getDescendents("3").size());


	}
	
     /*   tree 
      * 	

    root
	/  \
   1    2
  / \   /\
 3	 4 5  6
         /  \
/        8   9
7     *
     */

	@Test
	public void testUpdateParent() {
		
		assertEquals("Incorrect Parent Id : ", "2", provider.getNode("5").getParentId());
		provider.updateParentOfNode("1", "5");

		// assert that the parent node has changed
		assertEquals("Incorrect Parent Id : ", "1", provider.getNode("5").getParentId());

		// now node 1 will have 4 children,
		assertEquals("Incorrect : ", 4, provider.getDescendents("1").size());

		assertEquals("Incorrect : ", 3, provider.getDescendents("2").size());
		
		// the sibling 6 should not change its parent
		assertEquals("Incorrect Parent Id : ", "2", provider.getNode("6").getParentId());

		// previous parent should not contain the node
		Node five = provider.getRealNode("5");
		assertFalse("Incorrect Children for previous parent: ", provider.getRealNode("2").getChildren().contains(five));

		//assert that the height of the displaced parent node is recomputed
		try {
			provider.updateParentOfNode("4", "4");
		}
		catch(Exception e) {
			assertEquals("Incorrect Exception Message when set the same node as its own parent: ", "Invalid request: can not set same node as its parent", e.getMessage());
		}
		
		assertEquals("Incorrect Height: ", 1, provider.getNode("1").getHeight());
		assertEquals("Incorrect Height: ", 3, provider.getNode("9").getHeight());
		provider.updateParentOfNode("1", "9");
		assertEquals("Incorrect Height: ", 2, provider.getNode("9").getHeight());
		assertEquals("Incorrect Height: ", 1, provider.getNode("1").getHeight());

		
	}
	
	private static List<Node> mockDBNodes() {
		List<Node> list = new ArrayList<Node>();
		Node root = new Node("root", "", "",0);
		Node one = new Node("1", "root", "root",1);
		Node two = new Node("2", "root", "root",1);
		Node three = new Node("3", "1", "root",2);
		Node four = new Node("4", "1", "root",2);
		Node five = new Node("5", "2", "root",2);
		Node six = new Node("6", "2", "root",2);
		Node seven = new Node("7","3", "root",3);
		Node eight = new Node("8", "6", "root",3);
		Node nine = new Node("9", "6", "root",3);
		
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
	
	@TestConfiguration
	@ComponentScan(basePackages = "model")
	static class TestConfig {
		
		@Bean
		public IHierarchyProvider provider() {
			return new HierarchyProviderImpl();
		}
		@Bean(name="repo")
        public HierarchyRepo repo() {
			List<Node> nodes = mockDBNodes();
			
			HierarchyRepo repo = new HierarchyRepo() {
				
				@Override
				public <S extends Node> Optional<S> findOne(Example<S> example) {
					return null;
				}
				
				@Override
				public <S extends Node> Page<S> findAll(Example<S> example, Pageable pageable) {
					return null;
				}
				
				@Override
				public <S extends Node> boolean exists(Example<S> example) {
					return false;
				}
				
				@Override
				public <S extends Node> long count(Example<S> example) {
					return 0;
				}
				
				@Override
				public <S extends Node> S save(S entity) {
					return null;
				}
				
				@Override
				public Optional<Node> findById(Long id) {
					return null;
				}
				
				@Override
				public boolean existsById(Long id) {
					return false;
				}
				
				@Override
				public void deleteById(Long id) {
					
				}
				
				@Override
				public void deleteAll(Iterable<? extends Node> entities) {
				}
				
				@Override
				public void deleteAll() {
					
				}
				
				@Override
				public void delete(Node entity) {
				}
				
				@Override
				public long count() {
					return 0;
				}
				
				@Override
				public Page<Node> findAll(Pageable pageable) {
					return null;
				}
				
				@Override
				public <S extends Node> S saveAndFlush(S entity) {
					return null;
				}
				
				@Override
				public <S extends Node> List<S> saveAll(Iterable<S> entities) {
					return null;
				}
				
				@Override
				public Node getOne(Long id) {
					return null;
				}
				
				@Override
				public void flush() {
				}
				
				@Override
				public List<Node> findAllById(Iterable<Long> ids) {
					return null;
				}
				
				@Override
				public <S extends Node> List<S> findAll(Example<S> example, Sort sort) {
					return null;
				}
				
				@Override
				public <S extends Node> List<S> findAll(Example<S> example) {
					return null;
				}
				
				@Override
				public List<Node> findAll(Sort sort) {
					return nodes;
				}
				
				@Override
				public void deleteInBatch(Iterable<Node> entities) {
					
				}
				
				@Override
				public void deleteAllInBatch() {
				}
				
				@Override
				public List<Node> findAll() {
					return nodes;
				}
			};
			
			return repo;
        }
		
	}

}
