package cn.org.once.cstack.docker.core;

import cn.org.once.cstack.docker.builders.ConfigBuilder;
import cn.org.once.cstack.docker.model.Config;
import cn.org.once.cstack.docker.model.DockerContainer;
import cn.org.once.cstack.docker.model.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import cn.org.once.cstack.docker.builders.ContainerBuilder;
import cn.org.once.cstack.docker.builders.HostConfigBuilder;
import cn.org.once.cstack.docker.builders.ImageBuilder;
import cn.org.once.cstack.docker.model.HostConfig;
import cn.org.once.cstack.exception.DockerJSONException;
import cn.org.once.cstack.exception.ErrorDockerJSONException;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by guillaume on 22/10/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageCommandTests {

    static String DOCKER_HOST;
    static Boolean isUnixSocketConnection;
    static String socketPathAsString;

    private static DockerCloudUnitClient dockerCloudUnitClient;
    private static final String CONTAINER_NAME = "myContainer" + System.currentTimeMillis();
    private final String TAG = "mytag";

    @Before
    public void setup() {
        DOCKER_HOST = "cloudunit.dev:4243";
        isUnixSocketConnection = true;
        socketPathAsString = "/var/run/docker.sock";

        dockerCloudUnitClient = new DockerCloudUnitClient();
        HostConfig hostConfig = HostConfigBuilder.aHostConfig()
                .withVolumesFrom(new ArrayList<>())
                .build();
        Config config = ConfigBuilder.aConfig()
                .withAttachStdin(Boolean.FALSE)
                .withAttachStdout(Boolean.TRUE)
                .withAttachStderr(Boolean.TRUE)
                .withCmd(Arrays.asList("/bin/bash"))
                .withImage("alpine")
                .withHostConfig(hostConfig)
                .withExposedPorts(new HashMap<>())
                .withMemory(0L)
                .withMemorySwap(0L)
                .build();
        DockerContainer container = ContainerBuilder.aContainer().withName(CONTAINER_NAME).withConfig(config).build();
        try {
            if(isUnixSocketConnection){
                dockerCloudUnitClient.setDriver(new SimpleDockerDriver(true, null, socketPathAsString, null));
            } else {
               dockerCloudUnitClient.setDriver(new SimpleDockerDriver(false, "http", DOCKER_HOST, null));
            }
            dockerCloudUnitClient.pullImage("latest", "alpine");
            dockerCloudUnitClient.createContainer(container, DOCKER_HOST);
        } catch (DockerJSONException e) {
            Assert.fail();
        }
    }

    @After
    public void tearDown() throws DockerJSONException {
        DockerContainer container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .build();
        dockerCloudUnitClient.removeContainer(container);
        Image image = ImageBuilder.anImage().withName("alpine:latest").build();
        image = dockerCloudUnitClient.findAnImage(image);
        dockerCloudUnitClient.removeImage(image);

    }

    @Test
    public void test01_commitAnImage() throws DockerJSONException {
        DockerContainer container = ContainerBuilder.aContainer()
                .withName(CONTAINER_NAME)
                .build();
        container = dockerCloudUnitClient.findContainer(container, DOCKER_HOST);
        dockerCloudUnitClient.commitImage(container, TAG, container.getConfig().getImage());
        Image image = ImageBuilder.anImage().withName("alpine:"+TAG).build();
        image = dockerCloudUnitClient.findAnImage(image);
        Assert.assertTrue("Alpine found !", image.getId() != null);
        dockerCloudUnitClient.removeImage(image);

    }

    @Test
    public void test02_PullAndFindAnImage() throws DockerJSONException, JsonProcessingException {
        dockerCloudUnitClient.pullImage("latest", "busybox");
        Image image = ImageBuilder.anImage().withName("busybox:latest").build();
        image = dockerCloudUnitClient.findAnImage(image);
        Assert.assertTrue("Busybox found !", image.getId() != null);
    }

    @Test(expected=ErrorDockerJSONException.class)
    public void test05_removeImage() throws DockerJSONException {
        dockerCloudUnitClient.pullImage("latest", "busybox");
        Image image = ImageBuilder.anImage().withName("busybox:latest").build();
        image = dockerCloudUnitClient.findAnImage(image);
        Assert.assertTrue("Busybox found !", image.getId() != null);
        dockerCloudUnitClient.removeImage(image);
        dockerCloudUnitClient.findAnImage(image);
    }

}
