//package node;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.test.context.ContextConfiguration;
//
//import model.PersistenceHierarchyRepo;
//
//
//@SpringBootApplication
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(classes=TestPostGresRepo.TestConfig.class)
//@EnableMongoRepositories(basePackages = {"model"})
//public class TestPostGresRepo implements CommandLineRunner{
//
//@Autowired
//private PersistenceHierarchyRepo repository;
//
//public static void main(String[] args) {
//    SpringApplication.run(TestPostGresRepo.class, args);
//}
//
//@Override
//public void run(String... args) {
//
//    repository.save(new Node("Root","","",0));
//    repository.save(new Node("One","Root","Root",1));
//    repository.save(new Node("Two","Root","Root",2));
//    repository.save(new Node("Root","","",0));
//
//    System.out.println("\nfindAll()");
//    repository.findAll().forEach(x -> System.out.println(x));
//
//    System.out.println("\nfindById(1L)");
//    repository.findById(1l).ifPresent(x -> System.out.println(x));
//
//    System.out.println("\nfindByName('Node')");
//    repository.findByNodeId("Root");
//
//}
//
//static class TestConfig {
//	
//	
//}
//}
