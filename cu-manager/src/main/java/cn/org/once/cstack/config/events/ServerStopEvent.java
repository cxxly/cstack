package cn.org.once.cstack.config.events;

import cn.org.once.cstack.model.Server;
import org.springframework.context.ApplicationEvent;

/**
 * Created by nicolas on 03/08/2016.
 */
public class ServerStopEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ServerStopEvent(Server source) {
		super(source);
	}

}
