package fr.isen.deluc.androiderestaurant.User

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.deluc.androiderestaurant.LoginActivity
import fr.isen.deluc.androiderestaurant.LoginActivityFragmentInteraction
import fr.isen.deluc.androiderestaurant.R
import fr.isen.deluc.androiderestaurant.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    lateinit var binding: FragmentRegisterBinding
    var interactor: LoginActivityFragmentInteraction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interactor = context as? LoginActivityFragmentInteraction
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logInBtn.setOnClickListener{
            interactor?.showLogin()
        }

        binding.signUpBtn.setOnClickListener{
            interactor?.makeRequest(
                binding.email.text.toString(),
                binding.password.text.toString(),
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                false
            )
        }


    }
}

