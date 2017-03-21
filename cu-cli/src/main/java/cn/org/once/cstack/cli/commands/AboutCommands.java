package cn.org.once.cstack.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

import cn.org.once.cstack.cli.utils.AboutUtils;

@Component
public class AboutCommands implements CommandMarker {
    @Autowired
    private AboutUtils aboutUtils;
    
    @CliCommand(value = "about", help = "Print information about CloudUnit Manager")
    public String about() {
        return aboutUtils.getAbout();
    }
}
