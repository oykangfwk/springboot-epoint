package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
        String[] beanNamesForType = run.getBeanNamesForType(WebMvcAutoConfiguration.class);
        for (String type :beanNamesForType){
            System.out.println(beanNamesForType);
        }

        ok:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("i="+i+",j="+j);
                if(i==1){
                  break ok;
              }
            }
        }
    }
}