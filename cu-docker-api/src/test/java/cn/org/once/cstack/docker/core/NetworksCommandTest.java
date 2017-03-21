package cn.org.once.cstack.docker.core;

import cn.org.once.cstack.docker.builders.ConfigBuilder;
import cn.org.once.cstack.docker.builders.ContainerBuilder;
import cn.org.once.cstack.docker.builders.HostConfigBuilder;
import cn.org.once.cstack.docker.model.Config;
import cn.org.once.cstack.docker.model.DockerContainer;
import cn.org.once.cstack.docker.model.HostConfig;
import cn.org.once.cstack.docker.model.Network;
import cn.org.once.cstack.exception.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class NetworksCommandTest {

    public static String DOCKER_HOST;

    private static DockerCloudUnitClient dockerCloudUnitClient;
    private static final String CONTAINER_NAME = "myContainer";
    private static DockerContainer container;

    @BeforeClass
    public static void setupClass() {
        DOCKER_HOST = "cloudunit.dev:4243";
        dockerCloudUnitClient = new DockerCloudUnitClient();
        dockerCloudUnitClient.setDriver(new SimpleDockerDriver(false, "http", DOCKER_HOST, null));
    }

    @Test
    public void createThenDeleteNetworkAsBridge() throws IOException, ServiceException {
        dockerCloudUnitClient.createNetwork("myNetwork", "group1");
        Network network = dockerCloudUnitClient.findNetwork("myNetwork");
        Assert.assertEquals("myNetwork", network.getName());
        Assert.assertTrue(network.getLabels().keySet().contains("cloudunit.type"));
        Assert.assertTrue(network.getLabels().values().contains("group1"));
        container = getContainerConfig();
        dockerCloudUnitClient.createContainer(container);
        container = dockerCloudUnitClient.findContainer(container);

        dockerCloudUnitClient.connectToNetwork(network.getId(), container.getId());

        container = dockerCloudUnitClient.findContainer(container);

        Assert.assertTrue(container.getNetworkSettings().getNetworks().keySet().stream()
                .filter(n-> n.equalsIgnoreCase("myNetwork"))
                .findAny()
                .isPresent());
        container = ContainerBuilder.aContainer().withName(CONTAINER_NAME).build();
        dockerCloudUnitClient.removeContainer(container);

       dockerCloudUnitClient.removeNetwork(network.getId());
    }

    private DockerContainer getContainerConfig() {
        HostConfig hostConfig = HostConfigBuilder.aHostConfig()
                .withBinds(Arrays.asList("/etc/localtime:/etc/localtime:ro")).build();
        Config config = ConfigBuilder.aConfig().withAttachStdin(Boolean.FALSE).withAttachStdout(Boolean.TRUE)
                .withAttachStderr(Boolean.TRUE)
                .withCmd(Arrays.asList("/bin/bash", "/cloudunit/scripts/start-service.sh", "johndoe", "abc2015",
                        "192.168.2.116", "172.17.0.221", "aaaa", "AezohghooNgaegh8ei2jabib2nuj9yoe", "main"))
                .withImage("cloudunit/tomcat-8:latest").withHostConfig(hostConfig).withExposedPorts(new HashMap<>())
                .withMemory(0L).withMemorySwap(0L).build();
        container = ContainerBuilder.aContainer().withName(CONTAINER_NAME).withConfig(config).build();
        return container;
    }


}
