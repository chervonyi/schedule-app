package room106.app.schedule.other

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TaskValidationTest {

    @Test
    fun emptyTitle_False() {
        val title = ""
        val res = TaskValidation.validateTaskTitle(title)
        assertThat(res).isFalse()
    }

    @Test
    fun smallTitle_False() {
        val title = "1"
        val res = TaskValidation.validateTaskTitle(title)
        assertThat(res).isFalse()
    }

    @Test
    fun validTitle_True() {
        val title = "AA"
        val res = TaskValidation.validateTaskTitle(title)
        assertThat(res).isTrue()
    }

    @Test
    fun tooLongTitle_False() {
        // 101 chars
        val title = "01234567890123456789012345678901234567890123456789" +
                "01234567890123456789012345678901234567890123456789" +
                "1"
        val res = TaskValidation.validateTaskTitle(title)
        assertThat(res).isFalse()
    }
}