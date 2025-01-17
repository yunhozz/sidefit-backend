package com.project.sidefit.domain.service.dto;

import com.project.sidefit.domain.entity.Mbti;
import com.project.sidefit.domain.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {

    private Long id;
    private Mbti mbti;

    private List<String> currentStatuses;
    private List<String> favorites;
    private List<String> teches;
    private List<String> tags;

    private List<PortfolioDto> portfolios;

    private String imageUrl;

    // TODO Portfolio list 추가

    public UserDetailDto(User user, String imageUrl) {
        this.id = user.getId();
        this.mbti = user.getMbti();

        this.currentStatuses = user.getCurrentStatuses().stream()
                .map(status -> status.getStatus()).collect(Collectors.toList());
        this.favorites = user.getFavorites().stream()
                .map(favorite -> favorite.getField()).collect(Collectors.toList());
        this.teches = user.getTeches().stream()
                .map(tech -> tech.getStack()).collect(Collectors.toList());
        this.tags = user.getTags().stream()
                .map(tag -> tag.getName()).collect(Collectors.toList());

        this.portfolios = user.getPortfolios().stream()
                .map(portfolio -> new PortfolioDto(portfolio.getTitle(), portfolio.getUrl())).collect(Collectors.toList());

        this.imageUrl = imageUrl;
    }
}
