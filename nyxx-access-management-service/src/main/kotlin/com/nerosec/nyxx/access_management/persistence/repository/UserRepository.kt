package com.nerosec.nyxx.access_management.persistence.repository

import com.nerosec.nyxx.access_management.persistence.entity.UserEntity
import com.nerosec.nyxx.commons.persistence.repository.BaseRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : BaseRepository<UserEntity>