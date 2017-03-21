package cn.org.once.cstack.cli.integration.volumes;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.util.Random;

import cn.org.once.cstack.cli.integration.AbstractShellIntegrationTest;
import cn.org.once.cstack.cli.integration.ShellMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;

public class VolumesCommandsIT extends AbstractShellIntegrationTest {
    private String volumeName;

    public VolumesCommandsIT() {
        super("tomcat-8");
    }

    @Before
    public void setUp() {
        volumeName = "Volume" + new Random().nextInt(100000);
        
        connect();
    }

    @Test
    public void test_createAndRemoveVolume() {
        // Create volume
        CommandResult result = createVolume(volumeName);
        
        assertThat("Volume created", result, ShellMatchers.isSuccessfulCommand());
        assertEquals(volumeName, result.getResult().toString());
        // List volumes and check
        result = listVolumes();
        assertThat("List of volumes contains volume name",
                result.getResult().toString(), containsString(volumeName));
        // Remove volume
        result = removeVolume(volumeName);
        
        assertThat(result, ShellMatchers.isSuccessfulCommand());
    }

    @Test
    public void test_two_identical_volumes() {
        // Create volume
        CommandResult result = createVolume(volumeName);
        try {
            assumeThat(result, ShellMatchers.isSuccessfulCommand());
            
            result = createVolume(volumeName);
            
            assertThat(result, ShellMatchers.isFailedCommand());
            assertThat(result.getException().getMessage(), containsString("already exists"));
        } finally {
            removeVolume(volumeName);
        }
    }


    @Test
    public void test_shouldNotCreateUnconsistentName() {
        // Create volume with wrong name
        CommandResult result = createVolume(volumeName + "/2");

        assertThat(result, ShellMatchers.isFailedCommand());
    }

    @Test
    public void test_shouldNotRemoveNonExistantName() {
        CommandResult result = removeVolume(volumeName);
        
        assertThat(result, ShellMatchers.isFailedCommand());
    }

    @Test
    public void test_shouldNotCreateEmptyName() {
        CommandResult result = createVolume("");
        
        assertThat(result, ShellMatchers.isFailedCommand());
    }
    
    @Test
    public void test_shouldNotCreateBlankName() {
        CommandResult result = createVolume("  ");
        
        assertThat(result, ShellMatchers.isFailedCommand());
    }

    @Test
    public void test_shouldNotRemoveEmptyName() {
        CommandResult result = removeVolume("");
        assertThat(result, ShellMatchers.isFailedCommand());
    }
    
    @Test
    public void test_shouldNotRemoveBlankName() {
        CommandResult result = removeVolume("  ");
        assertThat(result, ShellMatchers.isFailedCommand());
    }

    private CommandResult createVolume(String name) {
        return getShell().executeCommand(String.format("create-volume --name %s", name));
    }

    private CommandResult removeVolume(String name) {
        return getShell().executeCommand(String.format("rm-volume --name %s", name));
    }

    private CommandResult listVolumes() {
        return getShell().executeCommand("list-volumes");
    }
}
