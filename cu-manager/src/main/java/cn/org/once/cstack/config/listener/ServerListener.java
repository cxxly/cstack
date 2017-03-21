package cn.org.once.cstack.config.listener;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import cn.org.once.cstack.enums.RemoteExecAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import cn.org.once.cstack.config.events.ServerStartEvent;
import cn.org.once.cstack.config.events.ServerStopEvent;
import cn.org.once.cstack.model.Server;
import cn.org.once.cstack.model.Status;
import cn.org.once.cstack.service.DockerService;
import cn.org.once.cstack.service.ServerService;

/**
 * Created by nicolas on 03/08/2016.
 */
@Component
public class ServerListener {

	private Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

	@Inject
	DockerService dockerService;

	@Inject
	ServerService serverService;

	@EventListener
	public void onServerStart(ServerStartEvent serverStartEvent) {
		Server server = (Server) serverStartEvent.getSource();
		try {
			String containerName = server.getName();
			int counter = 0;
			boolean started = false;
			do {
				Map<String, String> kvStore = new HashMap<String, String>() {
					private static final long serialVersionUID = 1L;
					{
						put("CU_USER", server.getApplication().getUser().getLogin());
						put("CU_PASSWORD", server.getApplication().getUser().getPassword());
					}
				};
				String command = RemoteExecAction.CHECK_RUNNING.getCommand(kvStore);
				try {
					String exec = dockerService.execCommand(containerName, command);
					exec = exec.replaceAll(System.getProperty("line.separator"), "");
					if ("0".equalsIgnoreCase(exec.trim())) {
						started = true;
						break;
					} else {
						logger.warn(exec);
					}
					Thread.sleep(1000);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			} while (counter++ < 30 && !started);
			if (counter <= 30) {
				server.setStatus(Status.START);
			} else {
				server.setStatus(Status.FAIL);
			}
			logger.info("Server status : " + server.getStatus());
			serverService.update(server);
		} catch (Exception e) {
			logger.error(server.toString(), e);
			e.printStackTrace();
		}
	}

	@EventListener
	public void onServerStop(ServerStopEvent serverStopEvent) {
		Server server = (Server) serverStopEvent.getSource();
		try {
			int counter = 0;
			boolean isStopped = false;
			do {
				isStopped = dockerService.isStoppedGracefully(server.getName());
				Thread.sleep(1000);
			} while (counter++ < 30 && !isStopped);
			if (counter <= 30) {
				server.setStatus(Status.STOP);
			} else {
				server.setStatus(Status.FAIL);
			}
			logger.info("Server status : " + server.getStatus());
			serverService.update(server);
		} catch (Exception e) {
			logger.error(server.getName(), e);
			e.printStackTrace();
		}
	}

}
