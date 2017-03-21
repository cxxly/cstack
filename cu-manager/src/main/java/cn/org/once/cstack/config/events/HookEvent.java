package cn.org.once.cstack.config.events;

import org.springframework.context.ApplicationEvent;

import cn.org.once.cstack.dto.Hook;

public class HookEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public HookEvent(Hook source) {
        super(source);
    }

}
