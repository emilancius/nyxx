package com.nerosec.nyxx.commons.service

import com.nerosec.nyxx.commons.exceptions.EntityException
import com.nerosec.nyxx.commons.persistence.entity.BaseEntity
import com.nerosec.nyxx.commons.persistence.entity.BaseEntity_
import com.nerosec.nyxx.commons.persistence.entity.EntityType
import com.nerosec.nyxx.commons.persistence.repository.BaseRepository
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgument
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgumentIsEntityId
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgumentIsInCollection
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireArgumentIsSpecified
import com.nerosec.nyxx.commons.preconditions.Preconditions.requireState
import jakarta.persistence.metamodel.SingularAttribute
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

abstract class BaseService<T : BaseEntity>(private val repository: BaseRepository<T>) {

    companion object {
        const val DEFAULT_PAGE = 1
        const val DEFAULT_PAGE_SIZE = 50
        const val DEFAULT_PROPERTY_TO_SORT_BY = BaseEntity_.PROP_CREATED_AT
        val DEFAULT_SORT_ORDER = Sort.Direction.ASC
        val DEFAULT_PROPERTIES_TO_QUERY_BY = HashMap<String, Any?>()

        const val MIN_PAGE = 1
        const val MIN_PAGE_SIZE = 1
        const val MAX_PAGE_SIZE = 300
    }

    abstract fun getEntityType(): EntityType

    /**
     * Properties, that are supported to be used for sorting entities.
     *
     * @return set of property names, that are supported to be used for sorting entities in [getEntities] execution.
     */
    abstract fun getPropertiesToSortBy(): Set<String>

    /**
     * Properties, that are supported to be used for querying entities.
     *
     * @return set of property names, that are supported to be used for querying entities in [getEntities] execution.
     */
    abstract fun getPropertiesToQueryBy(): Set<String>

    /**
     * Entity property types mapped by property names.
     *
     * @return property name to type map, that is used to created query specification, to be used in [getEntities] execution.
     */
    abstract fun getPropertyTypes(): Map<String, SingularAttribute<in T, out Any>>

    /**
     * Custom entity querying specification, based on [queryBy] parameter, to be used in [getEntities] execution.
     *
     * @param queryBy property name to value map, used for creating query specification, to be used in [getEntities] execution.
     * @return query specification, that is used for [getEntities] execution.
     */
    abstract fun getListSpecification(queryBy: Map<String, Any?>): Specification<T>?

    /**
     * Retrieves entity by specified [id].
     *
     * @param id unique id of an entity to be retrieved.
     * @return entity, found by specified [id].
     * @throws com.nerosec.nyxx.commons.exceptions.ArgumentException if specified [id] is empty or is not entity id for an entity of required type.
     * @throws EntityException if entity could not be found or could not be retrieved by specified [id].
     */
    protected fun getEntityById(id: String): T {
        requireArgumentIsSpecified("id", id)
        requireArgumentIsEntityId("id", id, getEntityType())
        return try {
            repository.getReferenceById(id)
        } catch (exception: Exception) {
            throw EntityException("Entity \"$id\" could not be found.", exception)
        }
    }

    /**
     * Retrieves page of entities, based on specified parameters.
     *
     * @param page no. of page to be retrieved.
     * @param pageSize size of a page to be retrieved.
     * @param sortBy name of a property, that results are going to be sorted by.
     * @param sortOrder sort order, that results are going to be sorted by.
     * @param queryBy property name to value map, that results are going to be queried by.
     * @return page of found entities, based on specified parameters.
     */
    protected fun getEntities(
        page: Int = DEFAULT_PAGE,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        sortBy: String = DEFAULT_PROPERTY_TO_SORT_BY,
        sortOrder: Sort.Direction = DEFAULT_SORT_ORDER,
        queryBy: Map<String, Any?> = DEFAULT_PROPERTIES_TO_QUERY_BY
    ): Page<T> {
        requireArgument(page > (MIN_PAGE - 1)) { "Argument's \"page\" value ($page) cannot be less than $MIN_PAGE." }
        requireArgument(pageSize in MIN_PAGE_SIZE..MAX_PAGE_SIZE) { "Argument's \"pageSize\" value ($pageSize) must be in [$MIN_PAGE; $MAX_PAGE_SIZE] range." }
        requireArgumentIsInCollection("sortBy", sortBy, getPropertiesToSortBy()) { "Argument's \"sortBy\" value ($sortBy) is not a supported property to be sorted by." }
        val propertiesToQueryBy = getPropertiesToQueryBy()
        queryBy.keys.forEach {
            requireArgumentIsInCollection("queryBy", it, propertiesToQueryBy) { "Argument \"queryBy\" contains a property ($it), that is not supported to be queried by." }
        }
        val spec = getListSpecification(queryBy) ?: createListSpecification(queryBy)
        var sort = Sort.by(sortBy)
        sort = if (sortOrder === Sort.Direction.ASC) sort.ascending() else sort.descending()
        return repository.findAll(spec, PageRequest.of(page - 1, pageSize, sort))
    }

    /**
     * Checks if etag of an entity matches specified [etag]. Used for optimistic locking in mutating operations.
     *
     * @param entity target entity.
     * @param etag etag to be matched.
     * @param message custom error message.
     * @throws com.nerosec.nyxx.commons.exceptions.StateException if specified [etag] does not match etag of an [entity].
     */
    protected fun checkEtag(entity: T, etag: String?, message: (() -> String?)? = null) {
        etag?.let {
            requireState(it == entity.etag) {
                message?.invoke() ?: "Etag mismatch."
            }
        }
    }

    private fun createListSpecification(queryBy: Map<String, Any?>): Specification<T> =
        Specification<T> { root, _, cb ->
            val propertyTypes = getPropertyTypes()
            val predicates = queryBy.map { (property, value) ->
                propertyTypes[property]?.let {
                    cb.equal(root[it], value)
                }
            }
            cb.and(*predicates.toTypedArray())
        }
}