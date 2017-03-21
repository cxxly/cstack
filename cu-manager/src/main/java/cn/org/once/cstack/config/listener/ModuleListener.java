package cn.org.once.cstack.config.listener;

import javax.inject.Inject;

import cn.org.once.cstack.config.events.ModuleStopEvent;
import cn.org.once.cstack.enums.RemoteExecAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.org.once.cstack.config.events.ModuleStartEvent;
import cn.org.once.cstack.model.Module;
import cn.org.once.cstack.model.Status;
import cn.org.once.cstack.service.DockerService;
import cn.org.once.cstack.service.ModuleService;

/**
 * Created by nicolas on 03/08/2016.
 */
@Component
public class ModuleListener {

	private Logger logger = LoggerFactory.getLogger(ModuleListener.class);

	@Inject
	DockerService dockerService;

	@Inject
	ModuleService moduleService;

	@EventListener
	public void onModuleStart(ModuleStartEvent moduleStartEvent) {
		Module module = (Module) moduleStartEvent.getSource();
		try {
			String containerName = module.getName();
			int counter = 0;
			boolean started = false;
			do {
				String command = RemoteExecAction.CHECK_RUNNING.getCommand();
				String exec = dockerService.execCommand(containerName, command);
				exec = exec.replaceAll(System.getProperty("line.separator"), "");
				if ("0".equalsIgnoreCase(exec.trim())) {
					started = true;
					break;
				}
				Thread.sleep(1000);
			} while (counter++ < 30 && !started);
			if (counter <= 30) {
				module.setStatus(Status.START);
			} else {
				module.setStatus(Status.FAIL);
			}
			logger.info("Module status : " + module.getStatus());
			moduleService.update(module);
		} catch (Exception e) {
			logger.error(module.toString(), e);
			e.printStackTrace();
		}
	}

	@EventListener
	@Async
	public void onModuleStop(ModuleStopEvent moduleStopEvent) {
		Module module = (Module) moduleStopEvent.getSource();
		try {
			int counter = 0;
			boolean isStopped = false;
			do {
				isStopped = dockerService.isStoppedGracefully(module.getName());
				Thread.sleep(1000);
			} while (counter++ < 30 && !isStopped);
			if (counter <= 30) {
				module.setStatus(Status.STOP);
			} else {
				module.setStatus(Status.FAIL);
			}
			logger.info("Server status : " + module.getStatus());
			moduleService.update(module);
		} catch (Exception e) {
			logger.error(module.getName(), e);
			e.printStackTrace();
		}
	}

}
