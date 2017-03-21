package cn.org.once.cstack.deployments;

public class SpringBootDeploymentControllerIT
        extends AbstractFatjarDeploymentControllerIT
{
    public SpringBootDeploymentControllerIT()
    {
        super.release = "fatjar";
    }
}
