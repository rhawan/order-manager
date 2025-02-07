package br.com.ordermanager.infra.config;

import br.com.ordermanager.application.gateway.MessageGateway;
import br.com.ordermanager.application.gateway.OrderGateway;
import br.com.ordermanager.application.usecase.UseCase;
import br.com.ordermanager.application.usecase.impl.CreateOrderUseCase;
import br.com.ordermanager.application.usecase.impl.ProcessOrderUseCase;
import br.com.ordermanager.application.usecase.impl.SendMessageUseCase;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationBeanFactory {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ParameterNamesModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Bean("createOrderUseCase")
    public UseCase<CreateOrderUseCase.In, CreateOrderUseCase.Out> createOrderUseCase(OrderGateway orderGateway) {
        return new CreateOrderUseCase(orderGateway);
    }

    @Bean("processOrderUseCase")
    public UseCase<ProcessOrderUseCase.In, ProcessOrderUseCase.Out> processOrderUseCase() {
        return new ProcessOrderUseCase();
    }

    @Bean("sendMessageUseCase")
    public UseCase<SendMessageUseCase.In, Void> sendMessageUseCase(MessageGateway messageGateway) {
        return new SendMessageUseCase(messageGateway);
    }

}
