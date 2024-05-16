package view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import model.Exercise
import model.Media
import okhttp3.ResponseBody

class ExerciseFragment : Fragment() {

    private lateinit var exerciseList: List<Exercise>
    private var exerciseIndex = 0
    private var mediaIndex = 0
    companion object{
        fun newInstance(exerciseList: ResponseBody): ExerciseFragment{
            val fragment = ExerciseFragment()
            val args = Bundle().apply {
                putString("exerciseList", exerciseList.string())
            }

            fragment.arguments = args
            return fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonResponse = arguments?.getString("exerciseList")
        exerciseList  = Gson().fromJson(jsonResponse, object : TypeToken<List<Exercise>>() {}.type)

        displayNextFragment()
    }

    fun displayNextFragment() {
        val currentExercise = exerciseList.getOrNull(exerciseIndex)

        if (currentExercise != null) {
            val mediaList = currentExercise.getMedia()
            if (mediaList.isNotEmpty() && mediaIndex<mediaList.size) {
                displayMediaFragment(currentExercise, mediaList)
            } else {
                displayQuestionFragment(currentExercise)
            }
        } else{
            (activity as MainActivity).setFragment(EndFragment())
        }

    }

    private fun displayMediaFragment(currentExercise : Exercise, mediaList : List<Media>){
        val mediaFragment = MediaFragment.newInstance(mediaList[mediaIndex].getUrl(), mediaList[mediaIndex].getType().toString(), mediaList[mediaIndex].getTitle())
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_child, mediaFragment, "fragment_media")
            .addToBackStack("fragment_media")
            .commit()
        (activity as MainActivity).updateNextButtonVisibility(mediaFragment)
        mediaIndex++
    }

    private fun displayQuestionFragment(currentExercise : Exercise){
        val optionsList = currentExercise.getOptions()
        val questionFragment = QuestionFragment.newInstance(currentExercise.getDescription(), Gson().toJson(optionsList), currentExercise.getCurrentOptionId(), Gson().toJson(currentExercise))

        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_child, questionFragment)
            .commit()
        (activity as MainActivity).updateNextButtonVisibility(questionFragment)
        exerciseIndex++
        mediaIndex = 0
    }
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_exercise, container, false)
    }

}