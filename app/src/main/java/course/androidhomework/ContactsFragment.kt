package course.androidhomework

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ContactsFragment : Fragment() {
    private lateinit var recyclerAdapter: ContactsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<Button?>(R.id.button).setOnClickListener {
           requestPermission()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                    getContacts()
            } else {
                Toast.makeText(context,
                    "Вы запретили доступ к контактам", Toast.LENGTH_LONG).show()
            }
        }
    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
                getContacts()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                val alertDialogBuilder = AlertDialog.Builder(requireContext())
                alertDialogBuilder.setTitle("Необходимо разрешение")
                alertDialogBuilder.setMessage("Пожалуйста, предоставьте доступ к контактам")
                alertDialogBuilder.setPositiveButton(android.R.string.ok) { _, _ ->
                    requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }

                alertDialogBuilder.setNegativeButton(android.R.string.cancel) { _, _ ->
                    Toast.makeText(context,
                        "Вы запретили доступ к контактам", Toast.LENGTH_LONG).show()
                }
                alertDialogBuilder.show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }


    @SuppressLint("Range")
    private fun getContacts() {
        val listContacts: MutableList<ContactModel> = mutableListOf()
        val cr: ContentResolver = requireActivity().contentResolver
        val cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        if (cur!!.count > 0) {
            while (cur.moveToNext()) {
                val id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID))
                val name : String = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)) ?: ""
                if (cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)).toInt() > 0) {

                    val pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    while (pCur!!.moveToNext()) {
                        val phone : String = pCur.getString(
                            pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                        listContacts.add(ContactModel(name, phone))
                    }
                    pCur.close()
                }
            }
        }
        cur.close()
        displayContactsNames(listContacts)
    }

    private fun displayContactsNames(list: List<ContactModel>) {
        recyclerView = requireActivity().findViewById(R.id.list)
        recyclerAdapter = ContactsRecyclerAdapter(list)
        recyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            it.adapter = recyclerAdapter
        }
    }
}