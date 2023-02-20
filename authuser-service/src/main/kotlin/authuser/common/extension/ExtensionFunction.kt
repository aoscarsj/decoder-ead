package authuser.common.extension

import java.util.regex.Pattern

private const val REGEX_EMAIL_VALIDATION =
    "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@" +
            "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." +
            "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
            "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|" +
            "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
private const val REGEX_CPF_VALIDATION =
    "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"

fun String.maskEmail(): String =
    this.replace("(?<=.{3}).(?=.*@)".toRegex(), "*")

fun String.isEmail(): Boolean {

    val email = this.lowercase()
    return Pattern.compile(REGEX_EMAIL_VALIDATION).matcher(email).matches()
}

fun String.isCPF(): Boolean =
    Pattern.compile(REGEX_CPF_VALIDATION).matcher(this).matches()

fun String.isUsername(): Boolean =
    (isNullOrEmpty() || this[0].isLetter() || contains(' ')).not()