package course.common.const

const val AUTHUSER_SERVICES = "ead-authuser-services"
const val AUTHUSER_PREFIX_NAME = "/ead-authuser"
const val AUTHUSER_SERVICES_USERS_BY_COURSE_RESOURCE =
    "$AUTHUSER_PREFIX_NAME/users/courses/{courseId}"
const val AUTHUSER_SERVICES_USER_BASE_RESOURCE = "$AUTHUSER_PREFIX_NAME/users/{userId}"
const val AUTHUSER_SERVICES_USER_SUBSCRIPTION_RESOURCE =
    "$AUTHUSER_PREFIX_NAME/internal/users/{userId}/courses/subscription"