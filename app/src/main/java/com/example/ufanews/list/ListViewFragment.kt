package com.example.ufanews.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ufanews.R
import kotlinx.android.synthetic.main.list_view_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ListViewFragment : Fragment() {

    private val TAG = "myLogs"
    private lateinit var viewModel: ListViewViewModel
    private val listNews = mutableListOf<News>()
    private val adapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewViewModel::class.java)
        rec_view.layoutManager = LinearLayoutManager(context)
        rec_view.adapter = adapter
        GlobalScope.launch {
            getData()
        }
    }

    private fun getData() {
        try {
            val document = Jsoup.connect("http://www.bashinform.ru/goroda_bashkortostana/ufa/").get()
            val regex = Regex(".*ru")
            val baseUrl = regex.find(document.baseUri())?.value
            val newsElements = document.select("article[class=img-right]")
            for (newsElement in newsElements) {
                val title = newsElement.select("h2").text()
                val desription = newsElement.select("div[class=trailer]").text()
                val imgLink = baseUrl + newsElement.select("img").attr("src")
                listNews.add(News(title, desription, imgLink))
            }
            GlobalScope.launch(Dispatchers.Main) {
                adapter.setList(listNews)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getData: something wrong with get data. Error = $e")
        }
    }
}
