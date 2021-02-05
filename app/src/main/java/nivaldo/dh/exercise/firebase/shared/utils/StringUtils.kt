package nivaldo.dh.exercise.firebase.shared.utils

object StringUtils {

    fun isEmailAddressValid(string: String): Boolean {
        return string.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(string).matches()
    }

}