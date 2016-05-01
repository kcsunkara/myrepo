package com.csc.config;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@PropertySource(value = "classpath:application.properties")
@EnableElasticsearchRepositories(basePackages = "com.csc.repository")
//@EnableJpaRepositories(basePackages = "com.csc.repository")
public class ElasticsearchConfiguration {
    @Resource
    private Environment environment;
    @Bean
    public Client client() {
    	
    	Settings settings = Settings.settingsBuilder()
    	        .put("cluster.name", "myelasticcluster").build();
    	TransportClient client = TransportClient.builder().settings(settings).build();
//      TransportAddress address = new InetSocketTransportAddress(environment.getProperty("elasticsearch.host"), Integer.parseInt(environment.getProperty("elasticsearch.port")));
        TransportAddress address =  new InetSocketTransportAddress(new InetSocketAddress(environment.getProperty("elasticsearch.host"), Integer.parseInt(environment.getProperty("elasticsearch.port"))));
        client.addTransportAddress(address);
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }


}