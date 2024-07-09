package pl.atk.bootcounter.presentation.screens.bootinfo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pl.atk.bootcounter.R
import pl.atk.bootcounter.databinding.ActivityBootInfoBinding
import pl.atk.bootcounter.utils.extensions.toReadableDateString

@AndroidEntryPoint
class BootInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBootInfoBinding

    private val viewModel: BootInfoViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            binding.tvPermissionError.isVisible = !isGranted
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBootInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleNotificationPermission()

        observeData()

        viewModel.scheduleNotifications()
    }

    private fun handleNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager = NotificationManagerCompat.from(this)
            if (!notificationManager.areNotificationsEnabled()) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun observeData() {
        viewModel.bootInfoUiState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { uiState -> renderUiState(uiState) }
            .launchIn(lifecycleScope)
    }

    private fun renderUiState(uiState: BootInfoUiState) {
        with(binding) {
            progressBar.isVisible = uiState.isLoading
            tvBootInfo.text = if (uiState.bootInfo.isEmpty()) {
                getString(R.string.boots_info_empty)
            } else {
                uiState.bootInfo.joinToString(
                    separator = "\n"
                ) {
                    getString(
                        R.string.boot_info_activity_format,
                        it.date.toReadableDateString(),
                        it.counter
                    )
                }
            }
        }
    }
}