package com.depromeet.whatnow.data.model

import com.depromeet.whatnow.data.model.response.GetPromisesProgressResponse
import com.depromeet.whatnow.data.model.response.GetPromisesUsersStatusResponse
import com.depromeet.whatnow.data.model.response.PromiseUsersResponse
import com.depromeet.whatnow.data.model.response.PromisesMonthlyUsersResponse
import com.depromeet.whatnow.data.model.response.PromisesProgressResponse
import com.depromeet.whatnow.data.model.response.TimeOverLocationsResponse
import com.depromeet.whatnow.data.model.response.UsersResponse
import com.depromeet.whatnow.domain.model.GetPromisesProgress
import com.depromeet.whatnow.domain.model.GetPromisesProgressList
import com.depromeet.whatnow.domain.model.GetPromisesUsersStatus
import com.depromeet.whatnow.domain.model.GetPromisesUsersStatusList
import com.depromeet.whatnow.domain.model.PromiseUsers
import com.depromeet.whatnow.domain.model.PromisesMonthlyUser
import com.depromeet.whatnow.domain.model.PromisesMonthlyUserList
import com.depromeet.whatnow.domain.model.PromisesProgress
import com.depromeet.whatnow.domain.model.TimeOverLocations
import com.depromeet.whatnow.domain.model.Users

@JvmName("PromisesMonthlyUsersResponse")
fun List<PromisesMonthlyUsersResponse>.toDomain(): PromisesMonthlyUserList {
    return PromisesMonthlyUserList(
        map {
            PromisesMonthlyUser(
                title = it.title,
                address = it.address,
                endTime = it.endTime,
                users = it.users.toDomain()
            )
        })
}

@JvmName("UsersResponse")
fun UsersResponse.toDomain(): Users {
    return Users(
        id = this.id,
        profileImg = this.profileImg,
        nickname = this.nickname,
        isDefaultImg = this.isDefaultImg
    )
}

@JvmName("GetPromisesUsersStatusResponse")
fun List<GetPromisesUsersStatusResponse>.toDomain(): GetPromisesUsersStatusList {
    return GetPromisesUsersStatusList(
        map {
            GetPromisesUsersStatus(
                title = it.title,
                date = it.date,
                promiseUsers = it.promiseUsers.toDomain(),
                promiseImageUrls = it.promiseImageUrls,
                timeOverLocations = it.timeOverLocations.toDomain()
            )
        })
}

@JvmName("PromiseUsersResponse")
fun List<PromiseUsersResponse>.toDomain(): List<PromiseUsers> {
    return map {
        PromiseUsers(
            profileImg = it.profileImg,
            nickname = it.nickname,
            isDefaultImg = it.isDefaultImg,
            promiseUserType = it.promiseUserType
        )
    }
}

@JvmName("TimeOverLocationsResponse")
fun List<TimeOverLocationsResponse>.toDomain(): List<TimeOverLocations> {
    return map {
        TimeOverLocations(
            userId = it.userId,
            coordinateVo = it.coordinateVo
        )
    }
}

@JvmName("PromisesProgressResponse")
fun PromisesProgressResponse.toDomain(): PromisesProgress {
    return PromisesProgress(
        User = this.User.toDomain(),
        currentProgress = this.currentProgress,
        beforeProgress = this.beforeProgress
    )
}

@JvmName("GetPromisesProgressListResponse")
fun List<GetPromisesProgressResponse>.toDomain(): GetPromisesProgressList {
    return GetPromisesProgressList(
        map {
            GetPromisesProgress(
                group = it.group,
                progresses = it.progresses

            )
        }
    )
}