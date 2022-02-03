package fr.isen.deluc.androiderestaurant.Detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.isen.deluc.androiderestaurant.R
import fr.isen.deluc.androiderestaurant.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentPhotoBinding

    companion object{

        const val URL = "url"

        fun newInstance(url:String): PhotoFragment{
            return PhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(PhotoFragment.URL, url)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(PhotoFragment.URL)

        if (url?.isNotEmpty() == true){
            Picasso
                .get().load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(binding.fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater,container,false)
        return binding.root
    }
}