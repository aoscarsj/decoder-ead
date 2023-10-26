package authuser.core.user.rest.external.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.request.InstructorRequest
import authuser.core.user.service.UserService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/instructors")
class InstructorRestV1 (
    private val userService: UserService
){
    @PostMapping("/subscription")
    fun createInstructor(@RequestBody instructorRequest: InstructorRequest):RestResponse<User> {
        val user = userService.insertInstructor(instructorRequest)

        return RestResponse("User successfully updated to instructor.", user)
    }
}