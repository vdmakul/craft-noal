package lv.vdmakul.noal.service;

import lv.vdmakul.noal.domain.Loan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanRepositoryInMemoryImpl implements LoanRepository {

    private final List<Loan> loans = new ArrayList<>();

    @Override
    public void save(Loan loan) {
        loans.add(loan);
    }

    @Override
    public List<Loan> findAll() {
        return new ArrayList<>(loans);
    }
}
