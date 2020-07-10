package io.pivotal.accessingdatamongodb;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access-data-mongodb/health")
public class HealthController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/mongodb")
    public String mongoDbHealthCheck() throws Exception {
        // return result of health check, need to prevent DDoS, hide behind /actuator?

        // how to swallow expception and return proper HTTP error code: service unavailable
        return doMongoHealthCheck();
    }

    private String doMongoHealthCheck() throws Exception {
        Assert.notNull(mongoTemplate, "MongoOps must not be null");
        Document result = mongoTemplate.executeCommand("{ buildInfo: 1 }");

        System.out.println("version: " + result.get("version"));
        Assert.notNull(result.get("version"), "MongoDB version not in result");
        return "up";
    }
}
