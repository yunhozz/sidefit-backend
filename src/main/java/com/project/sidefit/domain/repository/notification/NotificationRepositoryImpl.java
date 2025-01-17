package com.project.sidefit.domain.repository.notification;

import com.project.sidefit.api.dto.QNotificationDto_NotificationQueryDto;
import com.project.sidefit.domain.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.sidefit.domain.entity.QImage.*;
import static com.project.sidefit.domain.entity.QNotification.*;
import static com.project.sidefit.api.dto.NotificationDto.*;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NotificationQueryDto> findNotificationsWithReceiverId(Long receiverId) {
        QUser sender = new QUser("sender");
        QUser receiver = new QUser("receiver");

        return queryFactory
                .select(new QNotificationDto_NotificationQueryDto(
                        notification.id,
                        sender.id,
                        receiver.id,
                        notification.content,
                        notification.type,
                        notification.isChecked,
                        notification.createdDate,
                        notification.lastModifiedDate,
                        sender.nickname,
                        image.id,
                        image.imageUrl
                ))
                .from(notification)
                .join(notification.receiver, receiver)
                .join(notification.sender, sender)
                .join(sender.image, image)
                .where(receiver.id.eq(receiverId))
                .orderBy(notification.createdDate.desc())
                .fetch();
    }
}
