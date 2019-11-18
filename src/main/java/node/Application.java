package node;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import service.HierarchyProviderImpl;

@SpringBootApplication
@EnableJpaRepositories("model")
public class Application extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
    }
    
    @Bean
    public HierarchyProviderImpl getProvider() {
    	return new HierarchyProviderImpl();
    }
  
}
