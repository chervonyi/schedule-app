package room106.app.schedule.other.operators

object TaskValidation {

    /**
     * 2 <= length <= 100
     */
    fun validateTaskTitle(title: String) : Boolean {
        return title.length in 2..100
    }
}