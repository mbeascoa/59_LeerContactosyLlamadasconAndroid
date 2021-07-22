package com.example.a59_leercontactosyllamadasconandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var buttonContacts: Button
    lateinit var textoContacts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonContacts=viewContactsButton
        textoContacts=viewContactsTextView

        buttonContacts.setOnClickListener {

            CheckPermisions()
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                leerContactos()
            textoContacts.text.length
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode==1)
            Toast.makeText(this,"",Toast.LENGTH_LONG).show()
    }
    private fun CheckPermisions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                val array = arrayOf(Manifest.permission.READ_CONTACTS)
                ActivityCompat.requestPermissions(this, array, 1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    fun leerContactos() {
        val sColumnas = arrayOf (
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER
        )
        val cursorContactos: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,//ruta de la base de datos
            sColumnas,//campos a comprobar
            null,//condicionales
            null,//filtro
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        val datos = StringBuffer()
        if (cursorContactos != null) {
            while (cursorContactos.moveToNext()) {
                val nombre: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val numero: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                val aaa: String =
                    cursorContactos.getString(cursorContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER))
                datos.append("Nombre -> ")
                datos.append(nombre)
                datos.append(", Número de teléfono -> ")
                datos.append(numero)
                datos.append(aaa)
                datos.append("\n")
            }
        }
        cursorContactos?.close()
        textoContacts.text = datos
    }
}