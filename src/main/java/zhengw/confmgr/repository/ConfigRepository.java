package zhengw.confmgr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.Config;

public interface ConfigRepository extends CrudRepository<Config, Integer> {

	@Query(value = "SELECT COUNT(1) FROM config WHERE app_id = :appId AND env_id = :envId AND name = :name", nativeQuery = true)
	long countByName(@Param("appId") int appId, @Param("envId") int envId, @Param("name") String name);
}
