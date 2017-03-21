package cn.org.once.cstack.docker.core;

import cn.org.once.cstack.docker.model.DockerContainer;
import cn.org.once.cstack.docker.model.Image;
import cn.org.once.cstack.docker.model.Network;
import cn.org.once.cstack.docker.model.Volume;
import cn.org.once.cstack.dto.DockerResponse;
import cn.org.once.cstack.exception.FatalDockerJSONException;

import java.io.IOException;

/**
 * Created by nicolas on 03/08/2016.
 */
public interface DockerDriver {

	DockerResponse find(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse findAll() throws FatalDockerJSONException;

	DockerResponse create(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse start(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse stop(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse kill(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse remove(DockerContainer container) throws FatalDockerJSONException;

	DockerResponse findAnImage(Image image) throws FatalDockerJSONException;

	DockerResponse commit(DockerContainer container, String tag, String repository) throws FatalDockerJSONException;

	DockerResponse pull(String tag, String repository) throws FatalDockerJSONException;

	DockerResponse removeImage(Image image) throws FatalDockerJSONException;

	DockerResponse createVolume(Volume volume) throws FatalDockerJSONException;

	DockerResponse findVolume(Volume volume) throws FatalDockerJSONException;

	DockerResponse removeVolume(Volume volume) throws FatalDockerJSONException;

	DockerResponse createNetwork(Network network) throws FatalDockerJSONException, IOException;

	DockerResponse findNetwork(Network network) throws FatalDockerJSONException;

    DockerResponse listNetworks() throws FatalDockerJSONException;

    DockerResponse connectToNetwork(Network network, String containerId) throws FatalDockerJSONException;

    DockerResponse removeNetwork(Network network) throws FatalDockerJSONException;
}
