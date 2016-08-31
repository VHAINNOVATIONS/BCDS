package gov.va.vba.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.va.vba.persistence.entity.UserEditable;

public interface UserEditableRepository extends JpaRepository<UserEditable, Long> {

}
