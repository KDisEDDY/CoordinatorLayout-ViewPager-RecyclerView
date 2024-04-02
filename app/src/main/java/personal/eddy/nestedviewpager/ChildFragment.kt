package personal.eddy.nestedviewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.FieldPosition


/**
 * created by Eddy.Liu
 * created time: 2024/3/30 16:07
 * @desc
 **/
class ChildFragment: Fragment() {

    companion object {
        fun newInstance(position: Int): ChildFragment {
            return ChildFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_child, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView= view.findViewById<RecyclerView>(R.id.rv_child)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = ChildAdapter(mutableListOf<ChildData>().apply {
            for (i in 0..10) {
                add(ChildData(i % 3).apply {
                    itemList = mutableListOf<String>().apply {
                        for (j in 0..10) {
                            add("item $j")
                        }
                    }
                })
            }
        })
        recyclerView.adapter?.notifyDataSetChanged()
    }

    inner class ChildAdapter(val list: MutableList<ChildData>) : RecyclerView.Adapter<BaseItemViewHolder>() {

        override fun getItemCount() = list.size

        override fun getItemViewType(position: Int): Int {
            return list[position].type
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemViewHolder {
            return BaseItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_child_horizonal_list, parent, false)).apply {
                when(viewType) {
                    0 -> {
                        // horizontal
                        this.itemView.findViewById<RecyclerView>(R.id.rv_inner_list)?.apply {
                            updateLayoutParams {
                                height = dp2px(180f)
                            }
                            this.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                        }
                    }
                    1 -> {
                        // vertical
                        this.itemView.findViewById<RecyclerView>(R.id.rv_inner_list)?.apply {
                            updateLayoutParams {
                                height = dp2px(260f)
                            }
                            this.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

                        }
                    }
                    2 -> {
                        // grid
                        this.itemView.findViewById<RecyclerView>(R.id.rv_inner_list)?.apply {
                            updateLayoutParams {
                                height = dp2px(360f)
                            }
                            this.layoutManager = GridLayoutManager(context, 2)
                        }
                    }
                }
            }
        }

        override fun onBindViewHolder(holder: BaseItemViewHolder, position: Int) {
            val data = list[position]
            holder.itemView.findViewById<RecyclerView>(R.id.rv_inner_list)?.apply {
                adapter = InnerAdapter(data.itemList)
                adapter?.notifyDataSetChanged()
            }

        }
    }

    inner class InnerAdapter(val itemList: MutableList<String>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_text_view, parent, false).apply {
                findViewById<ConstraintLayout>(R.id.csl_root)?.apply {
                    updateLayoutParams {
                        width = dp2px(100f)
                        height = dp2px(100f)
                    }
                }
            }
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun getItemCount() = itemList.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.findViewById<TextView>(R.id.text)?.text = itemList[position]
        }

    }

    private fun dp2px(fl: Float): Int {
        // transform dp to px
        return (fl * 3).toInt()
    }

    class ChildData(val type: Int) {
        var itemList = mutableListOf<String>()
    }
}