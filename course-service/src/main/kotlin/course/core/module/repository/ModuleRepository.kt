package course.core.module.repository

import course.core.module.data.Module
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface ModuleRepository : JpaRepository<Module, UUID> {
    @EntityGraph(attributePaths = ["course"]) // to search course in return (course is lazy)
    fun findByTitle(title: String): Module?

    @Query(
        value = """
        SELECT * 
          FROM tb_modules 
          WHERE course_course_id = :courseId 
            AND module_id = :moduleId 
      """, nativeQuery = true
    )
    fun findIntoCourse(@Param("courseId") courseId: UUID, @Param("moduleId") moduleId:
    UUID): Module?

    @Query(
        value = "SELECT * FROM tb_modules WHERE course_course_id = :courseId", nativeQuery = true
    )
    fun findAllModulesIntoCourse(@Param("courseId") courseId: UUID): List<Module>
}
//to update/delete, use @Modifying with the @Query