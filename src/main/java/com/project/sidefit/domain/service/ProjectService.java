package com.project.sidefit.domain.service;

import com.project.sidefit.domain.entity.*;
import com.project.sidefit.domain.repository.ImageRepository;
import com.project.sidefit.domain.repository.ProjectUserRepository;
import com.project.sidefit.domain.repository.UserJpaRepo;
import com.project.sidefit.domain.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.sidefit.api.dto.ProjectDto.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;
    private final UserJpaRepo userRepository;
    private final ImageRepository imageRepository;

    public Long makeProject(Long userId, ProjectRequestDto projectRequestDto) {
        User user = userRepository.getReferenceById(userId);
        Image image = updateImage(projectRequestDto);
        Project project = createProject(projectRequestDto, user, image);
        ProjectUser projectUser = ProjectUser.createProjectUser(user, project);
        projectUserRepository.save(projectUser);

        return projectRepository.save(project).getId();
    }

    public void updateProject(Long projectId, ProjectRequestDto projectRequestDto) {
        Project project = findProject(projectId);
        Image image = updateImage(projectRequestDto);
        project.update(image,
                projectRequestDto.getTitle(),
                projectRequestDto.getType(),
                projectRequestDto.getField(),
                projectRequestDto.getIntroduction(),
                projectRequestDto.getPeriod(),
                projectRequestDto.getStack(),
                projectRequestDto.getMeetingPlan(),
                projectRequestDto.getHashtag()
        );
    }

    public void deleteProject(Long projectId) {

    }

    @Transactional(readOnly = true)
    public ProjectResponseDto findProjectDto(Long teamId) {
        return new ProjectResponseDto(findProject(teamId));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> findProjectDtoList() {
        return projectRepository.findAll().stream()
                .map(ProjectResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Project findProject(Long teamId) {
        return projectRepository.findById(teamId)
                .orElseThrow(() -> new IllegalStateException("This team is null: " + teamId));
    }

    private Project createProject(ProjectRequestDto projectRequestDto, User user, Image image) {
        return Project.builder()
                .user(user)
                .image(image)
                .title(projectRequestDto.getTitle())
                .type(projectRequestDto.getType())
                .field(projectRequestDto.getField())
                .introduction(projectRequestDto.getIntroduction())
                .period(projectRequestDto.getPeriod())
                .stack(projectRequestDto.getStack())
                .meetingPlan(projectRequestDto.getMeetingPlan())
                .hashtag(projectRequestDto.getHashtag())
                .build();
    }

    private Image updateImage(ProjectRequestDto projectRequestDto) {
        Image image = null;
        if (projectRequestDto.getImageId() != null) {
            image = imageRepository.getReferenceById(Long.valueOf(projectRequestDto.getImageId()));
        }
        return image;
    }
}