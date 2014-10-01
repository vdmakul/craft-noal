package lv.vdmakul.noal.service;

import lv.vdmakul.noal.domain.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, Long> {

}
