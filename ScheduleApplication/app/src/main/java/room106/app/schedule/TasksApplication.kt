package room106.app.schedule

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import room106.app.schedule.data.db.TasksDatabase
import room106.app.schedule.data.repositories.TasksRepository
import room106.app.schedule.ui.taskslist.viewmodel.TasksViewModelFactory

class TasksApplication: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@TasksApplication))
        bind() from singleton { TasksDatabase(instance()) }
        bind() from singleton { TasksRepository(instance()) }
        bind() from provider { TasksViewModelFactory(instance()) }
    }
}