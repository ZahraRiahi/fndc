package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<User, Long> {

    @Query(value = " select a.id, a.user_name, a.nick_name  " +
            "     from fnsc.financial_user FNUS " +
            "    inner join SEC.APPLICATION_USER A " +
            "       on FNUS.APPLICATION_USER_ID = A.ID " +
            "where (a.nick_name like '%'||:nickName||'%') "
            , nativeQuery = true)
    List<Object[]> getUserList(String nickName);
}
