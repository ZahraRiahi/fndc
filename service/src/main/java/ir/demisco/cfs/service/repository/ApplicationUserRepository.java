package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<User,Long> {

    @Query( " select distinct (u.id),u.username,u.nickName   from User u " +
            " join UserJob uj on uj.user.id=u.id " +
            " join DepartmentPosition  dp on  dp.id=uj.departmentPosition.id " +
            " join Department de on de.id=dp.department.id " +
            " join Organization o on o.id=de.organization.id " +
            " join OrganizationPositionType ps on ps.id=dp.organizationPositionType.id " +
            " join Permission p on p.organizationPositionType.id=ps.id " +
            " join Operation op on op.id=p.operation.id " +
            " where u.username LIKE '%FNC_USER%' " +
            " and  ps.code like '%MANAGER%' ")
    List<Object[]> getUserList();
}
