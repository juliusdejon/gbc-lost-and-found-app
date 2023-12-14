package com.example.lostandfound.ui.guest

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.adapters.CaseAdapter
import com.example.lostandfound.data.repositories.CaseRepository
import com.example.lostandfound.databinding.ActivityGuestBinding
import com.example.lostandfound.models.Case
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

var caseArrayList: ArrayList<Case> = ArrayList()

class GuestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGuestBinding

    private val TAG = "GuestActivity"
    private lateinit var caseRepository: CaseRepository
    private lateinit var caseAdapter: CaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //--------------MenuBar Init----------------------

        setSupportActionBar(this.binding.menuToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //--------------MenuBar Init----------------------

        //--------------Recycler View----------------------
        val search = intent.getStringExtra("search").toString()
        binding.etSearch.setText(search)

        caseAdapter = CaseAdapter(caseArrayList, { pos -> rowClicked(pos) })
        binding.rvProperties.layoutManager= LinearLayoutManager(this)
        binding.rvProperties.addItemDecoration(
            DividerItemDecoration(
                this.applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )
        //--------------Recycler View----------------------

        this.binding.rvProperties.adapter = caseAdapter

        caseRepository = CaseRepository(applicationContext)



        //Search Button
        binding.btnSearch.setOnClickListener {
            val searchFromUI = binding.etSearch.text.toString()
            if(searchFromUI.isNotEmpty()) {
                caseArrayList.clear()
                caseRepository.retrieveCasesbyName(searchFromUI)
            } else {
                caseRepository.retrieveAllCases()
            }
        }
        //Search Button//

        //--------------Spinner----------------------
        val categoryList:List<String> = listOf("All","Bag","Gadget","Apparel", "Accessories", "Other")

        val categoriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this,
            com.google.android.material.R.layout.support_simple_spinner_dropdown_item, categoryList
        )

        this.binding.guestSpinnerFilter.adapter = categoriesAdapter

        binding.guestSpinnerFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int,
                id: Long
            ) {
//                val snack = Snackbar.make(
//                    binding.root,
//                    "Filtered Items : ${categoryList[position]}",
//                    Snackbar.LENGTH_SHORT
//                )
//                snack.show()

                when (categoryList[position]) {
                    "All" -> caseRepository.retrieveAllCases()
                    "Bag" -> caseRepository.retrieveCasesbyType("Bag")
                    "Gadget" -> caseRepository.retrieveCasesbyType("Gadget")
                    "Apparel" -> caseRepository.retrieveCasesbyType("Apparel")
                    "Accessories" -> caseRepository.retrieveCasesbyType("Accessories")
                    "Other" -> caseRepository.retrieveCasesbyType("Other")
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

            //--------------Spinner----------------------
    }

    override fun onResume() {
        super.onResume()
        caseRepository.allCases.observe(this,
            androidx.lifecycle.Observer { caseList ->
            if(caseList != null){
                caseArrayList.clear()
                Log.d(TAG, "onResume: $caseList")
                caseArrayList.addAll(caseList)
                caseAdapter.notifyDataSetChanged()
            }
        })
    }

    fun rowClicked(position: Int) {
        if(caseArrayList.size > 0) {
            var intent = Intent(this@GuestActivity, ViewItemActivity::class.java)
            intent.putExtra("EXTRA_ID", caseArrayList[position].id)
            startActivity(intent)
//            val snackbar = Snackbar.make(binding.root, "Clicked Row : $position, ${caseArrayList[position].description}", Snackbar.LENGTH_LONG).show()
        }
    }

    //menuBar fun
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_guest, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId)
        {
           R.id.searchMapview -> {
                var intent = Intent(this@GuestActivity, MapViewActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    //menuBar fun/
}


