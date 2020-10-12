package br.com.beertechtalents.lupulo.pocmq.config;

import br.com.beertechtalents.lupulo.pocmq.rest.TransacaoAdapter;
import br.com.beertechtalents.lupulo.pocmq.rest.TransferenciaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    final TransacaoAdapter transacaoAdapter;
    final TransferenciaAdapter transferenciaAdapter;

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange operationExchange() {
        return new TopicExchange("operation", true, false);
    }

    @Bean
    public Queue inboundOperationQueue() {
        return new Queue("operation_inbound", true, false, false);
    }

    @Bean
    public Queue transferenciaInboundOperationQueue() {
        return new Queue("transferencia_inbound", true, false, false);
    }

    @Bean
    public Binding inboundOperationExchangeBinding() {
        return BindingBuilder.bind(inboundOperationQueue()).to(operationExchange()).with("*");
    }

    @Bean
    public Binding transferenciaInboundOperationExchangeBinding() {
        return BindingBuilder.bind(transferenciaInboundOperationQueue()).to(operationExchange()).with("*");
    }

    @RabbitListener(queues = {"operation_inbound"})
    public void receive(final String msg) {
        System.out.println(msg);
        transacaoAdapter.call(msg);
    }

    //{"contaOrigem":"322f84d1-abf4-4607-8fc8-27d8970c17d0", "contaDestino":"7dba8e0a-3b06-4579-ad82-f9eaa2cab3ba", "valor": 10.00}
    @RabbitListener(queues = {"transferencia_inbound"})
    public void receiveTranferencia(final String msg) {
        System.out.println(msg);
        transferenciaAdapter.call(msg);
    }

}
