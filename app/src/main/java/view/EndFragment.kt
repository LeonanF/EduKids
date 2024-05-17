package view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.app.R

class EndFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val jumpBtn = (activity as MainActivity).findViewById<ImageView>(R.id.jump_button)
        val likeBtn = (activity as MainActivity).findViewById<ImageView>(R.id.like_button)
        val validateBtn = (activity as MainActivity).findViewById<ImageView>(R.id.validate_button)
        val navOverlay = (activity as MainActivity).findViewById<View>(R.id.nav_overlay)

        jumpBtn.visibility=View.GONE
        likeBtn.visibility=View.GONE
        validateBtn.visibility=View.GONE
        navOverlay.visibility =View.GONE

        return inflater.inflate(R.layout.fragment_end, container, false)
    }

}