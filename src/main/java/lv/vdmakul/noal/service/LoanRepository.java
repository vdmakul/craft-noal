package lv.vdmakul.noal.service;

import lv.vdmakul.noal.domain.Loan;

import java.util.List;

public interface LoanRepository {

    void save(Loan loan);

    List<Loan> findAll();
}
