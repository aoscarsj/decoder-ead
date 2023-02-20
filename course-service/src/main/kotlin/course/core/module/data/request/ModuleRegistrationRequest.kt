package course.core.module.data.request

import javax.validation.constraints.NotBlank

data class ModuleRegistrationRequest (
    @NotBlank
    val title: String,
    @NotBlank
    val description: String
)