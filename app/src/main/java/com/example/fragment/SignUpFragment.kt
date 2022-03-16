package com.example.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.adapter.SpinnerAdapter
import com.example.androidlesson5databasetask.R
import com.example.androidlesson5databasetask.databinding.FragmentSignUpBinding
import com.example.database.Constant
import com.example.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*

import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.androidlesson5databasetask.BuildConfig
import com.example.androidlesson5databasetask.databinding.CustomPermissionBinding
import com.example.database.UserDatabase
import com.example.model.CheckNotEmpty
import com.example.model.User


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val binding: FragmentSignUpBinding by viewBinding()
    var imagePath: String? = null
    var photoURI: Uri? = null
    lateinit var userDatabase: UserDatabase
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding)
        {
            val countryList = loadCountryList()
            userDatabase = UserDatabase(requireContext())

            val spinnerAdapter = SpinnerAdapter(countryList)
            countrySpinner.adapter = spinnerAdapter

            AddImageButton.setOnClickListener {

                val dialog = Dialog(requireActivity())
                val dialogView =
                    CustomPermissionBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                dialogView.camera.setOnClickListener {
                    val imageFile = createImageFile()
                    photoURI =
                        FileProvider.getUriForFile(requireContext(),
                            BuildConfig.APPLICATION_ID,
                            imageFile)
                    getTakeImageContent.launch(photoURI)
                    dialog.dismiss()
                }
                dialogView.gallery.setOnClickListener {
                    pickImageFromGalleryNew()
                    dialog.dismiss()

                }
                dialog.show()
            }

            signUpButton.setOnClickListener {
                val spinnerPosition = countrySpinner.selectedItemPosition
                val name = etUserNames.text.toString()
                val phone = etPhoneNumber.text.toString()
                val country = countryList[spinnerPosition]
                val address = etAddress.text.toString()
                val password = etPassword.text.toString()
                var uniqueNum = true

                val nameBol = CheckNotEmpty.empty(name)
                val phoneBol = CheckNotEmpty.empty(phone)
                val addressBol = CheckNotEmpty.empty(address)
                val passwordBol = CheckNotEmpty.empty(password)
                val spaceName = CheckNotEmpty.space(name)
                val spacePhone = CheckNotEmpty.space(phone)
                val spaceAddress = CheckNotEmpty.space(address)
                val spacePassword = CheckNotEmpty.space(password)
                val textBol = nameBol && phoneBol && addressBol && passwordBol
                val spaceBol = spaceName && spacePhone && spaceAddress && spacePassword
                val allUser = userDatabase.getAllUser()
                for (user in allUser) {
                    if (user.phoneNumber == phone) {
                        uniqueNum = false
                        break
                    }
                }
                if (textBol && spaceBol && spinnerPosition != 0 && imagePath != null &&uniqueNum) {
                    val user = User(name, phone, country, address, password, imagePath)
                    userDatabase.insertUser(user)
                    findNavController().navigate(R.id.signUpUserListFragment)
                }


            }
        }
    }

    private fun loadCountryList(): ArrayList<String> {
        val gson = Gson()
        val loadJSONFromAsset = loadJSONFromAsset()
        val type: Type = object : TypeToken<ArrayList<Country>>() {}.type
        val list = gson.fromJson<ArrayList<Country>>(loadJSONFromAsset, type)
        var countryList = ArrayList<String>()
        countryList.add("Select country")
        for (country in list) {
            countryList.add(country.Name!!)
        }

        return countryList
    }

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        json = try {
            val inputStream: InputStream = requireActivity().assets.open("country_json.json")
            val sizeOfFile = inputStream.available()
            val bufferDate = ByteArray(sizeOfFile)
            inputStream.read(bufferDate)
            inputStream.close()
            String(bufferDate, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val externalFilesDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", externalFilesDir).apply {

        }
    }

    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                binding.imageProfile.setImageURI(photoURI)
                val openInputStream = requireActivity().contentResolver?.openInputStream(photoURI!!)
                val query = "select * from ${Constant.TABLE_NAME}"
                var numberText = "1234567890"
                val toCharArray = numberText.toCharArray()
                toCharArray.shuffle()
                var imageName: String = ""
                for (c in toCharArray) {
                    imageName += c
                }
                val file = File(requireActivity().filesDir, "$imageName.jpg")
                val fileOutputStream = FileOutputStream(file)
                openInputStream?.copyTo(fileOutputStream)
                openInputStream?.close()
                fileOutputStream.close()
                imagePath = file.absolutePath
            }
        }

    private fun pickImageFromGalleryNew() {
        getImageContent.launch("image/*")
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri ?: return@registerForActivityResult
            binding.imageProfile.setImageURI(uri)
            val openInputStream = requireActivity().contentResolver?.openInputStream(uri)
            val query = "select * from ${Constant.TABLE_NAME}"
            var numberText = "1234567890"
            val toCharArray = numberText.toCharArray()
            toCharArray.shuffle()

            var imageName: String = ""
            for (c in toCharArray) {
                imageName += c
            }
            val file = File(requireActivity().filesDir, "$imageName.jpg")
            val fileOutputStream = FileOutputStream(file)
            openInputStream?.copyTo(fileOutputStream)
            openInputStream?.close()
            fileOutputStream.close()
            val absolutePath = file.absolutePath
            imagePath = absolutePath
        }
}