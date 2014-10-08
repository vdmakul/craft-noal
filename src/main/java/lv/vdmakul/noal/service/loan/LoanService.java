package lv.vdmakul.noal.service.loan;

import lv.vdmakul.noal.domain.Loan;
import lv.vdmakul.noal.domain.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Loan registerNewLoan(BigDecimal amount, LocalDateTime term, String userAccount) {
        Loan loan = new Loan(amount, term, userAccount);
        loanRepository.save(loan);
        return loan;
    }

    public Loan findLoan(Long loanId) {
        Loan loan = loanRepository.findOne(loanId);
        if (loan != null) {
            return loan;
        } else {
            throw new LoanNotFoundException("Loan not found", loanId);
        }
    }

    public List<Loan> findAllLoans() {
        return StreamSupport.stream(loanRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void deactivateByExtension(Loan loan, Loan extensionLoan) {
        loan.setExtensionLoan(extensionLoan);
        loan.deactivate();
        loanRepository.save(loan);
    }
}
