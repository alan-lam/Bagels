package ntrllog.github.io.bagels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val easyButton = findViewById<Button>(R.id.easy_button)
        easyButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("NUM_DIGITS", 3)
            startActivity(intent)
        }
        val mediumButton = findViewById<Button>(R.id.medium_button)
        mediumButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("NUM_DIGITS", 4)
            startActivity(intent)
        }
        val hardButton = findViewById<Button>(R.id.hard_button)
        hardButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("NUM_DIGITS", 5)
            startActivity(intent)
        }
        val impossibleButton = findViewById<Button>(R.id.impossible_button)
        impossibleButton.setOnClickListener {
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("NUM_DIGITS", 6)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finishAffinity() // exit app instead of going to previous activity
    }
}
