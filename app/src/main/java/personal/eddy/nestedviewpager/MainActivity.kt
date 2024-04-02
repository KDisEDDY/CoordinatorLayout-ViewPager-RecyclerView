package personal.eddy.nestedviewpager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.StatefulAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tabLayout = findViewById(R.id.tab_layout)
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        val viewPager = findViewById<ViewPager2>(R.id.vp2)
        viewPager.adapter = FragmentViewPagerAdapter(this, mutableListOf("1", "2"))
        viewPager.offscreenPageLimit = 1
        TabLayoutMediator(tabLayout!!, findViewById<ViewPager2>(R.id.vp2)) { tab, position ->
            tab.text = "Tab $position"
        }.attach()
        viewPager.adapter?.notifyDataSetChanged()

        // Get the RecyclerView and set it's Adapter so that it can display items

    }

    // Implement RecyclerView.Adapter
    class MyAdapter(private val items: MutableList<String>): RecyclerView.Adapter<MyAdapter.PagerViewHolder>() {
        class PagerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
            // create a new view
            val textView = LayoutInflater.from(parent.context)
                .inflate(R.layout.my_text_view, parent, false)
            return PagerViewHolder(textView)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            holder.itemView.setBackgroundColor(randomColor())
        }

        // implement random color
        private fun randomColor(): Int {
            val r = (0..255).random()
            val g = (0..255).random()
            val b = (0..255).random()
            return android.graphics.Color.rgb(r, g, b)
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = items.size
    }


    class FragmentViewPagerAdapter(activity: AppCompatActivity, private val items: MutableList<String>): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun createFragment(position: Int): ChildFragment {
            return ChildFragment.newInstance(position)
        }
    }
}