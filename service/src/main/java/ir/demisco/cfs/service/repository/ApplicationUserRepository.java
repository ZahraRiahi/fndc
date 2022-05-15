package ir.demisco.cfs.service.repository;

import ir.demisco.cloud.basic.model.entity.sec.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplicationUserRepository extends JpaRepository<User, Long> {

    @Query(value = "   select distinct (u.id), u.user_name, u.nick_name" +
            "  from sec.application_user u" +
            "  join sec.organization_user ou" +
            "    on ou.user_id = u.id" +
            "   and ou.enabled_flg = 1" +
            "   and ou.deleted_date is null" +
            "  join sec.user_job uj" +
            "    on uj.organization_user_id = ou.id" +
            "   and uj.disable_date is null" +
            "  left join org.department de" +
            "    on de.id = uj.department_id" +
            "  left join org.department dc" +
            "    on dc.root_id = de.root_id" +
            "   and dc.disable_date is null" +
            "  join org.organization o" +
            "    on o.id = ou.organization_id" +
            "  join org.organization oc" +
            "    on oc.root_id = o.root_id" +
            "   and oc.disable_date is null" +
            "  join org.org_position_type ps" +
            "    on ps.id = uj.org_position_type_id" +
            "    and ps.disable_date is null" +
            "  join sec.permission p" +
            "    on p.org_position_type_id = ps.id" +
            "    and p.disable_date is null" +
            "  join aps.operation op" +
            "    on op.id = p.operation_id" +
            "    and op.disable_date is null" +
            " where u.user_name LIKE '%FNC_USER%'" +
            "   and ps.code like '%MANAGER%' "
            , nativeQuery = true)
    List<Object[]> getUserList();
}
