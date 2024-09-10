package dev.venc.nbasample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.venc.nbasample.R
import dev.venc.nbasample.databinding.ActivityMainBinding
import dev.venc.nbasample.repository.datastore.AppSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appSettings = AppSettings(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                appSettings.userHasSeenIntroDialog.collect { userHasSeenIntroDialog ->
                    if (!userHasSeenIntroDialog) {
                        withContext(Dispatchers.Main) { showUserIntroDialog() }
                    }
                }
            }
        }
    }

    private fun showUserIntroDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.user_intro_dialog_title)
            .setMessage(R.string.user_intro_dialog_text)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                lifecycleScope.launch {
                    appSettings.userHasSeenIntroDialog(true)
                }
                dialog.dismiss()
            }.setCancelable(false).show()
    }
}