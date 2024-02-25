package ntrllog.github.io.bagels

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ExitDialogFragment(private val context: Context) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(R.string.exit_message)
            .setPositiveButton(R.string.exit) { dialog, id ->
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton(R.string.stay) { dialog, id -> }
        return builder.create()
    }
}
