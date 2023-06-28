package org.uengine.five.rpa;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

@Configuration
public class DockerConfig {
    
    public DockerClient dockerInit() {
        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .withDockerTlsVerify(false)
                .build();
        return DockerClientBuilder.getInstance(config).build();
    }

    public void killDocker () {
        DockerClient dockerClient = dockerInit();
        Boolean killed = false;
        while(!killed) {
            List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
            Iterator containerIterator = containers.iterator();
            while(containerIterator.hasNext()) {
                Container container = (Container) containerIterator.next();
                String status = container.getState();
                System.out.println(status);
                if(status.equals("exited")) {
                    killed = true;
                    System.out.println("remove");
                    dockerClient.removeContainerCmd(container.getId()).exec();
                }
            }
        }
    }
}
