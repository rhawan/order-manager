package br.com.ordermanager.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Value("${server.port}")
    private String port;

    @Bean
    public OpenAPI defineOpenApi() {
        var myContact = new Contact();
        myContact.setName("Rhawan");
        myContact.setEmail("rb.brenner10@gmail.com");

        var information = new Info()
                .title("ORDER MANAGER")
                .version("1.0")
                .description("ORDER MANAGER")
                .contact(myContact);
        return new OpenAPI().info(information).servers(getServers());
    }

    private List<Server> getServers() {
        var local = new Server();
        local.setUrl("http://localhost:" + port);

        return List.of(local);
    }
}