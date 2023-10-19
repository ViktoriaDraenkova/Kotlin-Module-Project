data class Archive(
        override val name: String,
        val notesArray: ArrayList<Note> = ArrayList()
) : NamedListItem()
