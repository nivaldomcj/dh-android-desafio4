package nivaldo.dh.exercise.firebase.shared.data

sealed class Response {

    class Success(val data: Any?) : Response()
    class Failure(val error: String?) : Response()

}