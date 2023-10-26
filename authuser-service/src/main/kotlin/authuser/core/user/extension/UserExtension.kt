package authuser.core.user.extension

import authuser.core.user.data.User
import authuser.core.user.rest.external.v1.UserRestV1
import org.springframework.data.domain.Page
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder

fun Page<User>.insertHateoasLink(): Page<User> {

    if (this.isEmpty.not()) {
        for (user in this.toList()) {
            user.add(
                WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UserRestV1::class.javaObjectType).find(user.userId!!)
                ).withSelfRel()
            )
        }
    }

    return this
}