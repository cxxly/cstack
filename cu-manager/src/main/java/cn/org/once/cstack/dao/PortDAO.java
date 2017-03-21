package cn.org.once.cstack.dao;

import cn.org.once.cstack.model.Port;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by guillaume on 25/09/16.
 */
public interface PortDAO extends JpaRepository<Port, Integer>{
}
