package terraformDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ServletComponentScan("terraformDemo")

public class My_terraformDemo_Application {

    public static void main(String[] args) {
        SpringApplication.run(My_terraformDemo_Application.class, args);
    }

}
