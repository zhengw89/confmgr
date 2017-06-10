package zhengw.confmgr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.App;

public interface AppRepository extends PagingAndSortingRepository<App, Integer> {
	
	@Query(value = "SELECT CASE WHEN COUNT(1) > 0 THEN 'true' ELSE 'false' END FROM app WHERE name = :name", nativeQuery = true)
	boolean exists(@Param("name") String name);

	@Query(value = "SELECT id,name,description FROM app WHERE name = :name", nativeQuery = true)
	App findByName(@Param("name") String name);
}
