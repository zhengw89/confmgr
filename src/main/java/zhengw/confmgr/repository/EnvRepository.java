package zhengw.confmgr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.Env;

public interface EnvRepository extends CrudRepository<Env, Integer> {

	@Query(value = "SELECT id,name FROM env WHERE name = :name", nativeQuery = true)
	Env findByName(@Param("name") String name);
}
