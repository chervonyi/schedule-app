package room106.app.schedule.other.operators

object TaskValidation {

    private const val MIN = 2
    private const val MAX = 300

    /**
     * 2 <= length <= 300
     */
    fun validateTaskTitle(title: String) : Boolean {
        return title.length in MIN..MAX
    }
}
