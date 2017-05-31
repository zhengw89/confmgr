package zhengw.confmgr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.App;

public interface AppRepository extends PagingAndSortingRepository<App, Integer> {
	
	@Query(value = "SELECT COUNT(1) FROM app WHERE name = :name", nativeQuery = true)
	long countByName(@Param("name") String name);

	@Query(value = "SELECT id,name,description FROM app WHERE name = :name", nativeQuery = true)
	App findByName(@Param("name") String name);
}
