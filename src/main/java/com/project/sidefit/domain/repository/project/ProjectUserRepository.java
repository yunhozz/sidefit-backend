package com.project.sidefit.domain.repository.project;

import com.project.sidefit.domain.entity.ProjectUser;
import com.project.sidefit.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long>, ProjectUserRepositoryCustom {

    List<ProjectUser> findByUser(User user);
}
