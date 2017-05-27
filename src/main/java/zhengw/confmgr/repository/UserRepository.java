package zhengw.confmgr.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import zhengw.confmgr.bean.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	
	@Query(value = "SELECT COUNT(1) FROM user WHERE email = :email", nativeQuery = true)
	long countByEmail(@Param("email") String email);
	
	@Query(value = "SELECT id,email,name,password FROM user WHERE email = :email", nativeQuery = true)
	User findByEmail(@Param("email") String email);
}
