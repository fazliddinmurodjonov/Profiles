package com.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidlesson5databasetask.R
import com.example.androidlesson5databasetask.databinding.FragmentSignInBinding
import com.example.database.UserDatabase

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private val viewBinding: FragmentSignInBinding by viewBinding()
    lateinit var userDatabase: UserDatabase


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewBinding)
        {

            userDatabase = UserDatabase(requireContext())
            val userList = userDatabase.getAllUser()
            signInButton.setOnClickListener {
                if (userList.size == 0) {
                    errorTV.visibility = View.VISIBLE

                } else {
                    val phoneNumber = etPhoneNumber.text.toString()
                    val password = etPassword.text.toString()
                    for (user in userList) {
                        if (user.phoneNumber == phoneNumber && user.password == password) {
                            findNavController().navigate(R.id.signUpUserListFragment)
                        } else {
                            errorTV.visibility = View.VISIBLE

                        }
                    }
                }

            }

            signUpTv.setOnClickListener {
                findNavController().navigate(R.id.signUpFragment)
            }
        }
    }

}
