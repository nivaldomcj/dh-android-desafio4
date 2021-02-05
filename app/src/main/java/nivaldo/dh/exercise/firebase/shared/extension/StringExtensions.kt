package nivaldo.dh.exercise.firebase.shared.extension

fun String.isValidEmailAddress(): Boolean {
    return this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}