package cn.org.once.cstack.config.events;

import org.springframework.context.ApplicationEvent;

import cn.org.once.cstack.model.Module;

/**
 * Created by nicolas on 03/08/2016.
 */
public class ModuleStopEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ModuleStopEvent(Module source) {
		super(source);
	}

}
