package br.com.ordermanager.infra.adapters.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.Objects;

public class ContainerConfig {

    public static final String DOCKER_IMAGE = "localstack/localstack:latest";
    private static final Logger log = LoggerFactory.getLogger(ContainerConfig.class);
    private static final long memoryInBytes = 512L * 1024L * 1024L;
    private static final long memorySwapInBytes = 1024L * 1024L * 1024L;
    private static final LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse(DOCKER_IMAGE)).withCreateContainerCmdModifier(cmd -> Objects.requireNonNull(cmd.getHostConfig()).withMemory(memoryInBytes).withMemorySwap(memorySwapInBytes));


    private static void setUpLocalStack() {
        log.info("Setting up container localstack");

        localStackContainer.start();

        localStackContainer.addEnv("SERVICES", "sqs");
        localStackContainer.addEnv("AWS_DEFAULT_REGION", "us-east-1");
        localStackContainer.addEnv("AWS_ACCESS_KEY_ID", "localstack");
        localStackContainer.addEnv("AWS_SECRET_ACCESS_KEY", "localstack");
        localStackContainer.addEnv("DEBUG", "1");
        log.info("Container initialized");
        try {
            var orderQueue = localStackContainer.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "order-queue").getStdout();
            log.info("Create sqs queue command: {}", orderQueue);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalStackContainer localStackContainer() {
        setUpLocalStack();
        return localStackContainer;
    }

    public static PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16-alpine")
                .withFileSystemBind("src/test/resources/db",
                        "/docker-entrypoint-initdb.d",
                        BindMode.READ_ONLY);
    }
}