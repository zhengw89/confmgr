package zhengw.confmgr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.Config;

public interface ConfigRepository extends CrudRepository<Config, Integer>, JpaSpecificationExecutor<Config> {

	@Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN 'true' ELSE 'false' END FROM config WHERE app_id = :appId AND env_id = :envId AND id = :configId", nativeQuery = true)
	boolean exists(@Param("appId") int appId, @Param("envId") int envId, @Param("configId") int configId);

	@Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN 'true' ELSE 'false' END FROM config WHERE app_id = :appId AND env_id = :envId AND name = :name", nativeQuery = true)
	boolean exists(@Param("appId") int appId, @Param("envId") int envId, @Param("name") String name);

	@Query(value = "SELECT id, app_id, env_id, name, value, create_time FROM config WHERE app_id = :appId AND env_id = :envId AND name = :name", nativeQuery = true)
	Config findByName(@Param("appId") int appId, @Param("envId") int envId, @Param("name") String name);

	@Query(value = "SELECT id, app_id, env_id, name, value, create_time FROM config WHERE app_id = :appId AND env_id = :envId AND id = :configId", nativeQuery = true)
	Config findById(@Param("appId") int appId, @Param("envId") int envId, @Param("configId") int configId);

	@Query(value = "SELECT id, app_id, env_id, name, value, create_time FROM config WHERE app_id = :appId AND env_id = :envId", nativeQuery = true)
	List<Config> findByAppIdAndEvnId(@Param("appId") int appId, @Param("envId") int envId);
}
