package arsw.tarea.tablero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
public class WebSiteController {
    @Resource 
    private HttpServletRequest request;
    public static void main(String[] args) {
        SpringApplication.run(WebSiteController.class, args);
    }

    @GetMapping("/status") 
    public String status() { 
        return "{\"status\":\"Greetings from Spring Boot. " + 
                java.time.LocalDate.now() + ", " +
                java.time.LocalDate.now() +
                ". "+"The server is Running!\"}";
    }

    @GetMapping("/setname") 
    public String setName(@RequestParam(value = "name", defaultValue = "Anónimo") String name) { 
            request.getSession().setAttribute("name", name); 
            return String.format("Hello %s!", name); 
        } 
    public void sessionManagement() { 
            System.out.println(request.getSession(true).getId()); 
    } 
}