import java.util.*
import kotlin.collections.ArrayList

class Application {
    private val input = Scanner(System.`in`)
    private var archiveArray: ArrayList<Archive> = ArrayList()
    private var indArchive: Int = -1
    private var indNote: Int = -1
    private var state: MenuState = MenuState.CHOOSE_ARCHIVE

    private fun showArchives() {
        println("0. Добавить архив")
        for (i in 0..archiveArray.size - 1) {
            println((i + 1).toString() + ". " + archiveArray[i].name)
        }

        println((archiveArray.size + 1).toString() + ". Выход")
        var temp = input.nextInt()
        when (temp) {
            in 1..archiveArray.size -> {
                indArchive = temp-1
                state = MenuState.CHOOSE_NOTE
            }
            0 -> state = MenuState.CREATE_ARCHIVE
            archiveArray.size + 1 -> state = MenuState.EXIT
        }
    }

    private fun createArchive() {
        println("Введите название архива")
        val temp = readln()

        archiveArray.add(Archive(temp))
        state = MenuState.CHOOSE_ARCHIVE
    }

    private fun chooseNote() {
        println("0. Добавить заметку")
        val notesArray = archiveArray[indArchive].notesArray
        for (i in 0..notesArray.size - 1) {
            println((i + 1).toString() + ". " + notesArray[i].title)
        }
        println((notesArray.size + 1).toString() + ". Выход")
        var temp = input.nextInt()
        when (temp) {
            in 1..notesArray.size -> {
                indNote = temp-1
                state = MenuState.VIEW_NOTE
            }
            0 -> state = MenuState.CREATE_NOTE
            notesArray.size + 1 -> state = MenuState.CHOOSE_ARCHIVE
        }
    }

    private fun createNote() {
        println("Введите заголовок заметки")
        val title = readln()
        println("Введите заметку")
        val note = readln()
        archiveArray[indArchive].notesArray.add(Note(title, note))
        state = MenuState.CHOOSE_NOTE

    }

    private fun viewNote() {
        println("Заголовок: " + archiveArray[indArchive].notesArray[indNote].title)
        println("Заметка: " + archiveArray[indArchive].notesArray[indNote].content)
        state = MenuState.CHOOSE_NOTE
    }

    fun showMenu() {
        when (state) {
            MenuState.CHOOSE_ARCHIVE -> showArchives()
            MenuState.CREATE_ARCHIVE -> createArchive()
            MenuState.CHOOSE_NOTE -> chooseNote()
            MenuState.CREATE_NOTE -> createNote()
            MenuState.VIEW_NOTE -> viewNote()
            MenuState.EXIT -> return
        }
    }

    fun run() {
        while (state != MenuState.EXIT) {
            showMenu()
        }
    }


}