package cn.org.once.cstack.config.events;

import cn.org.once.cstack.model.Module;
import org.springframework.context.ApplicationEvent;

/**
 * Created by nicolas on 03/08/2016.
 */
public class ModuleStartEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ModuleStartEvent(Module source) {
		super(source);
	}

}
