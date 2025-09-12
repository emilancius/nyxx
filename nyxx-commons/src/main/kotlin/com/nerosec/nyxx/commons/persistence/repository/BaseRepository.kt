package com.nerosec.nyxx.commons.persistence.repository

import com.nerosec.nyxx.commons.persistence.entity.BaseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface BaseRepository<T : BaseEntity> : JpaRepository<T, String>, JpaSpecificationExecutor<T>