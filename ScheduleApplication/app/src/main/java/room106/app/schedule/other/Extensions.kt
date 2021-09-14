package room106.app.schedule.other

import android.content.Context
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setDivider(
    context: Context,
    res: Int,
    decoration: Int = DividerItemDecoration.VERTICAL
) {
    val divider = ContextCompat.getDrawable(context, res)!!
    val itemDecoration = DividerItemDecoration(context, decoration)
    itemDecoration.setDrawable(divider)
    addItemDecoration(itemDecoration)
}

fun EditText.setMultiLineCapSentencesAndDoneAction() {
    imeOptions = EditorInfo.IME_ACTION_DONE
    setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or InputType.TYPE_TEXT_FLAG_MULTI_LINE)
}