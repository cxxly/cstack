package cn.org.once.cstack.config.events;

import cn.org.once.cstack.model.Application;
import org.springframework.context.ApplicationEvent;

/**
 * Created by nicolas on 03/08/2016.
 */
public class ApplicationStopEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ApplicationStopEvent(Application source) {
		super(source);
	}

}
