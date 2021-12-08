package com.example.android.room.game

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.android.room.R
import com.example.android.room.database.Question
import com.example.android.room.database.QuestionDAO
import kotlinx.coroutines.launch

class GameViewModel(
   nivel: Int,
   val database: QuestionDAO,
   application: Application
) : AndroidViewModel(application) {

    //nivel del juego
    private val _nivel = nivel
    val nivel : Int
        get() = _nivel
    //numero de preguntas  a contestar
    val numQuestions: Int = (nivel+1)*2
    //flag que indica que se ha ganado el juego.
    private val _gameWon = MutableLiveData<Boolean>()
    val gameWon: LiveData<Boolean>
        get() = _gameWon
    //flag que indica que se ha perdido el juego.
    private val _gameOver = MutableLiveData<Boolean>()
    val gameOver: LiveData<Boolean>
        get() = _gameOver
    //lista de preguntas
    var questions: List<Question> = listOf()
    //pregunta actual
    private val _currentQuestion = MutableLiveData<Question>()
    val  currentQuestion: LiveData<Question>
        get() = _currentQuestion
    //respuestas obtenidas.
    private var _answers = mutableListOf<String>("","","","")
    val answers: List<String>
        get() = _answers
    private val _answer_one = MutableLiveData<String>()
    val answer_one: LiveData<String>
        get() = _answer_one
    private val _answer_two = MutableLiveData<String>()
    val answer_two: LiveData<String>
        get() = _answer_two
    private val _answer_three = MutableLiveData<String>()
    val answer_three: LiveData<String>
        get() = _answer_three
    private val _answer_four = MutableLiveData<String>()
    val answer_four: LiveData<String>
        get() = _answer_four
    //indice de pregunta
    private var _questionIndex = 0
    val questionIndex : Int
        get() = _questionIndex
    //cadena con la pista
    private var _hint = ""
    val hint: String
        get() = _hint
    //bandera que indica si se ha usado pista.
    private var _hintUsed = false
    val hintUsed: Boolean
        get() = _hintUsed
    //puntuacion del juego
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
    //racha de respuestas consecutivas.
    private var _streak = 1
    val streak: Int
        get() = _streak


    init {
        _score.value = 0
        _questionIndex = 0
        onLoadQuestions(numQuestions)
        //cargaDB()
    }

    private fun onLoadQuestions(numQuestions: Int) {
        viewModelScope.launch {
            questions = selectQuestions(numQuestions)
            setQuestion()
        }
    }

    private suspend fun selectQuestions(numQuestions: Int): List<Question> {
        return database.selectQuestions(numQuestions)
    }

    private suspend fun insertQuestions(questions: List<Question>) {
        database.insertQuestions(questions)
    }

    private fun setQuestion() {
        _currentQuestion.value = questions[questionIndex]
        // randomize the answers into a copy of the array
        currentQuestion.value?.let {  _answers.set(0,it.answer_one) }
        currentQuestion.value?.let {  _answers.set(1,it.answer_two) }
        currentQuestion.value?.let {  _answers.set(2,it.answer_three) }
        currentQuestion.value?.let {  _answers.set(3,it.answer_four) }
        // and shuffle them
        _answers.shuffle()
        _answer_one.value = answers[0]
        _answer_two.value = answers[1]
        _answer_three.value = answers[2]
        _answer_four.value = answers[3]
        //actualizar pista a false
        _hint = currentQuestion.value?.hint ?: ""
        _hintUsed = false
    }

    fun comprobarAcierto(opcion: Int) {
        if (answers[opcion] == currentQuestion.value?.answer_one!!){
            //actualizamos la puntuacion...
            _score.value = if(hintUsed) _score.value?.plus(10*streak/2) else _score.value?.plus(10*streak)
            _streak++
            _questionIndex++
            // Advance to the next question
            if (questionIndex < numQuestions) {
                setQuestion()
            } else {
                onGameWon()
            }
        } else {
            onGameOver()
        }
    }

    fun pistaUsada() {
        _hintUsed = true
    }

    private fun onGameWon() {
        _gameWon.value = true
    }

    fun onGameWonComplete() {
        _gameWon.value = false
    }

    private fun onGameOver() {
        _gameOver.value = true
    }

    fun onGameOverComlete() {
        _gameOver.value = false
    }

    private fun cargaDB() {
        questions = listOf(
            Question(text = "What is Android Jetpack?",
                answer_one = "All of these",
                answer_two = "Tools",
                answer_three = "Documentation",
                answer_four = "Libraries",
                hint = "Hint 1"),
            Question(text = "What is the base class for layouts?",
                answer_one = "ViewGroup",
                answer_two = "ViewSet",
                answer_three = "ViewCollection",
                answer_four = "ViewRoot",
                hint = "Hint 2"),
            Question(text = "What layout do you use for complex screens?",
                answer_one = "ConstraintLayout",
                answer_two = "GridLayout",
                answer_three = "LinearLayout",
                answer_four = "FrameLayout",
                hint = "Hint 3"),
            Question(text = "What do you use to push structured data into a layout?",
                answer_one = "Data binding",
                answer_two = "Data pushing",
                answer_three = "Set text",
                answer_four = "An OnClick method",
                hint = "Hint 4"),
            Question(text = "What method do you use to inflate layouts in fragments?",
                answer_one = "onCreateView()",
                answer_two = "onActivityCreated()",
                answer_three = "onCreateLayout()",
                answer_four = "onInflateLayout()",
                hint = "Hint 5"),
            Question(text = "What's the build system for Android?",
                answer_one = "Gradle",
                answer_two = "Graddle",
                answer_three = "Grodle",
                answer_four = "Groyle",
                hint = "Hint 6"),
            Question(text = "Which class do you use to create a vector drawable?",
                answer_one = "VectorDrawable",
                answer_two = "AndroidVectorDrawable",
                answer_three = "DrawableVector",
                answer_four = "AndroidVector",
                hint = "Hint 7"),
            Question(text = "Which one of these is an Android navigation component?",
                answer_one = "NavController",
                answer_two = "NavCentral",
                answer_three = "NavMaster",
                answer_four = "NavSwitcher",
                hint = "Hint 8"),
            Question(text = "Which XML element lets you register an activity with the launcher activity?",
                answer_one = "intent-filter",
                answer_two = "app-registry",
                answer_three = "launcher-registry",
                answer_four = "app-launcher",
                hint = "Hint 9"),
            Question(text = "What do you use to mark a layout for data binding?",
                answer_one = "<layout>",
                answer_two = "<binding>",
                answer_three = "<data-binding>",
                answer_four = "<dbinding>",
                hint = "Hint 10")
        )
        viewModelScope.launch {
            insertQuestions(questions)
        }
    }
}