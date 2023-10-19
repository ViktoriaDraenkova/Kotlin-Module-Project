import java.util.*
import kotlin.collections.ArrayList

class Application {
    private val input = Scanner(System.`in`)
    private var archiveArray: ArrayList<Archive> = ArrayList()
    private var indArchive: Int = -1
    private var indNote: Int = -1
    private var state: MenuState = MenuState.CHOOSE_ARCHIVE

    private fun createArchive() {
        println("Введите название архива")

        var temp: String = getNonEmptyString()
        archiveArray.add(Archive(temp))
        state = MenuState.CHOOSE_ARCHIVE
    }

    protected fun show(array: ArrayList<out NamedListItem>, onIndexChosen: (Int) -> Unit, addState: MenuState, exitState: MenuState) {
        println("0. Добавить")
        for (i in 0 until array.size) {
            println((i + 1).toString() + ". " + array[i].name)
        }

        println((array.size + 1).toString() + ". Выход")
        val temp = readln()
        when (val num = temp.toIntOrNull()) {
            null -> {
                println("Введите числовое значение")
                return
            }
            in 1..array.size -> onIndexChosen(num)
            0 -> state = addState
            array.size + 1 -> state = exitState
            else-> println("Необходимо выбрать из меню!")
        }
    }

    private fun showArchives() {
        show(archiveArray, {temp: Int -> indArchive = temp-1; state = MenuState.CHOOSE_NOTE}, MenuState.CREATE_ARCHIVE, MenuState.EXIT)
    }

    private fun chooseNote() {
        val notesArray = archiveArray[indArchive].notesArray
        show(notesArray, { temp: Int -> indNote = temp-1; state = MenuState.VIEW_NOTE}, MenuState.CREATE_NOTE, MenuState.CHOOSE_ARCHIVE)
    }

    private fun getNonEmptyString(): String{
        var temp: String = readln()
        while(temp.isEmpty()){
            println("Пустое значение не допускается!")
            temp = readln()
        }
        return temp
    }

    private fun createNote() {
        println("Введите заголовок заметки")
        val title = getNonEmptyString()
        println("Введите заметку")
        val note = getNonEmptyString()
        archiveArray[indArchive].notesArray.add(Note(title, note))
        state = MenuState.CHOOSE_NOTE

    }

    private fun viewNote() {
        println("Заголовок: " + archiveArray[indArchive].notesArray[indNote].name)
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