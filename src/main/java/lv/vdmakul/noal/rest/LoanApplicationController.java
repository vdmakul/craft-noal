package lv.vdmakul.noal.rest;

import lv.vdmakul.noal.domain.LoanApplication;
import lv.vdmakul.noal.domain.repository.LoanApplicationRepository;
import lv.vdmakul.noal.domain.transfer.LoanApplicationTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class LoanApplicationController {

    @Autowired private LoanApplicationRepository loanApplicationRepository;

    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public List<LoanApplicationTO> findAll() {
        Iterable<LoanApplication> all = loanApplicationRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(LoanApplication::toTransferObject)
                .collect(Collectors.toList());
    }

}
