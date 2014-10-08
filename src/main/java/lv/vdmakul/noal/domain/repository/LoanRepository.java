package lv.vdmakul.noal.domain.repository;

import lv.vdmakul.noal.domain.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Long> {

    List<Loan> findByUserAccount(String userAccount);

}
