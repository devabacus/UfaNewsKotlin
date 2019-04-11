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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ListViewFragment : Fragment() {

    private val TAG = "myLogs"

    companion object {
        fun newInstance() = ListViewFragment()
    }

    private lateinit var viewModel: ListViewViewModel

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
        val adapter = NewsAdapter()
        rec_view.adapter = adapter


        GlobalScope.launch {
            getData()
        }
//        val list : MutableList<News> = mutableListOf()
//        list.add(News("title_news1", "description_news1", "http://www.bashinform.ru/upload/img_res1280/ec70f5a6b43bcd52/f_1_jpg_crop1554900657_ejw_1280.jpg"))
//        list.add(News("title_news2", "description_news2", "http://www.bashinform.ru/upload/img_res1014/57cf7777c2590986/sledkom_jpg_crop1552325137_ejw_1017_jpg_crop1554904872_ejw_1014.jpg"))
//        list.add(News("title_news3", "description_news3", "http://www.bashinform.ru/upload/img_res1280/5e699a8fb5e443fe/YROV5338_2_jpg_crop1547205705_ejw_1280_jpg_crop1554903171_ejw_1280.jpg"))
//        list.add(News("title_news4", "description_news4", "http://www.bashinform.ru/upload/img_res1280/d16d72fdfa1e950c/NkRcouKHtsk_jpg_crop1554899778_ejw_1280.jpg"))
//        list.add(News("title_news5", "description_news5", "http://www.bashinform.ru/upload/img_res1280/6997437d272bd84f/YROV8100_JPG_crop1554703689_ejw_1280_jpg_crop1554898311_ejw_1280.jpg"))
//
//        adapter.setList(list)

    }

    private fun getData() {
        try {
            val document = Jsoup.connect("http://www.bashinform.ru/goroda_bashkortostana/ufa/").get()
            val regex = Regex(".*ru")
            val baseUrl = regex.find(document.baseUri())?.value
          //  Log.d(TAG, baseUrl.toString())
            val newsElements = document.select("article[class=img-right]")

            for (newsElement in newsElements) {
                val title = newsElement.select("h2").text()
                val desription = newsElement.select("div[class=trailer]").text()

                val imgLink = baseUrl + newsElement.select("img").attr("src")
                Log.d(TAG, imgLink)
            }

            //Log.d(TAG, newsElements.toString())
//            val titles = document.select("h2")
//
//            for (title in titles) {
//                Log.d(TAG, title.text().toString())
//            }
            }
        catch (e: Exception) {
            Log.e(TAG, "getData: something wrong with get data. Error = $e")
        }
    }
}
