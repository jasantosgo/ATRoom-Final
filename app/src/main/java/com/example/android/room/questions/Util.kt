import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.room.database.Question
import java.lang.StringBuilder

fun formatQuestions(questions: List<Question>, resources: Resources): Spanned {

    val sb = StringBuilder()
    var i = 1
    sb.apply {
        questions.forEach {
            append("<br>")
            append("Pregunta ${i++}:  ")
            append(it.text)
            append("<br>")
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}