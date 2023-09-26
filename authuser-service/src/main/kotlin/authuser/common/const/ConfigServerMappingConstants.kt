package authuser.common.const

const val COURSE_SERVICES = "course-services"

const val COURSE_SERVICES_COURSE_FIND_RESOURCE = "/courses/{courseId}"
const val COURSE_SERVICES_COURSES_BY_USERS_RESOURCE = "/courses/users/{userId}"
const val COURSE_SERVICES_SUBSCRIPTION_RESOURCE =
    "$COURSE_SERVICES_COURSE_FIND_RESOURCE/users/subscription"