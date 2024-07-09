package pl.atk.bootcounter

class Constants {

    companion object {
        const val DB_NAME = "boot_info.db"

        const val DEFAULT_NOTIFICATIONS_INTERVAL_IN_MS = 15 * 60 * 1000L
        const val DEFAULT_NOTIFICATION_DISMISS_COUNT = 5

        const val DEFAULT_NOTIFICATION_AFTER_DISMISS_INTERVAL_IN_MS = 20 * 60 * 1000L
    }
}