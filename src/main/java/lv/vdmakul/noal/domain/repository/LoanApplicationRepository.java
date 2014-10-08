package lv.vdmakul.noal.domain.repository;

import lv.vdmakul.noal.domain.LoanApplication;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {

    @Query("select application from LoanApplication application " +
            "where application.userAccount = ?1 and application.applicationTime between ?2 and ?3")
    List<LoanApplication> findByIpAddressAndPeriod(String userAccount, LocalDateTime from, LocalDateTime till);

    List<LoanApplication> findByUserAccount(String userAccount);

}
