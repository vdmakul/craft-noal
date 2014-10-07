package lv.vdmakul.noal.domain.repository;

import lv.vdmakul.noal.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

}
