package com.ids.markaz.controller.Adapters

import com.ids.markaz.model.Portfolio


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ids.markaz.R
import com.ids.markaz.controller.Adapters.RVOnItemClickListener.RVOnItemClickListener
import com.ids.markaz.controller.MyApplication
import com.ids.markaz.model.ResponseHoldingPosition

import com.ids.markaz.utils.AppConstants
import com.ids.markaz.utils.AppHelper


import java.util.*


class AdapterPortfolioPosition(val arrayHolding: ArrayList<ResponseHoldingPosition>, private val itemClickListener: RVOnItemClickListener, type:Int) :
    RecyclerView.Adapter<AdapterPortfolioPosition.VHprivacy>() {

    var type=type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHprivacy {
        return when (type) {
            AppConstants.TAB_POSITION -> VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_position, parent, false))
            AppConstants.TAB_GAIN_LOSS -> VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_gain, parent, false))
            AppConstants.TAB_INCOME -> VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_income, parent, false))
            AppConstants.TAB_PERFORMANCE -> VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_performane, parent, false))
            else -> VHprivacy(LayoutInflater.from(parent.context).inflate(com.ids.markaz.R.layout.item_portfolio_position, parent, false))
        }

    }

    override fun onBindViewHolder(holder: VHprivacy, position: Int) {

        when (MyApplication.selectedTabInside) {
            AppConstants.TAB_POSITION ->{
                try{holder.tvPositionName.text=arrayHolding[position].codeTickerEn.toString() }catch (e:Exception){}
                try{holder.tvPositionLastCLose.text= arrayHolding[position].lastPrice.toString() }catch (e:Exception){}
                try{holder.tvCurrentPecentage.text = arrayHolding[position].currentPrice.toString() }catch (e:Exception){}
                try{holder.tvPositionQty.text = arrayHolding[position].quantity.toString() }catch (e:Exception){}
                try{holder.tvPositionChg.text= arrayHolding[position].changeValue.toString() }catch (e:Exception){}
                try{holder.tvChgPercentage.text = arrayHolding[position].changePercent.toString()+"%" }catch (e:Exception){}
            }
            AppConstants.TAB_GAIN_LOSS ->{
                try{holder.tvGainEquity.text = arrayHolding[position].codeTickerEn.toString() }catch (e:Exception){}
                try{holder.tvGainAvgCost.text = arrayHolding[position].avgCost.toString() }catch (e:Exception){}
                try{holder.tvGainTotalCost.text = arrayHolding[position].totalCost.toString() }catch (e:Exception){}
                try{holder.tvGainPercentage.text = arrayHolding[position].costPercent.toString()+"%" }catch (e:Exception){}

                try{holder.tvGainQty.text =arrayHolding[position].quantity.toString() }catch (e:Exception){}
                try{holder.tvGainLastPrice.text = arrayHolding[position].currentPrice.toString() }catch (e:Exception){}
                try{holder.tvGainTotalValue.text = arrayHolding[position].totalCurrentValue.toString() }catch (e:Exception){}
                try{holder.tvGainQtyPercentage.text = arrayHolding[position].changePercent.toString()+"%" }catch (e:Exception){}

                try{holder.tvGainLossAvgCost.text = String.format("%.2f", (arrayHolding[position].lastPrice!!-arrayHolding[position].avgCost!!))}catch (e:Exception){}
                try{holder.tvGainLossTotalValue.text = arrayHolding[position].changePercent.toString() }catch (e:Exception){}
                try{holder.tvGainLossPercentage.text = arrayHolding[position].changePercent.toString()+"%"}catch (e:Exception){}

                if((arrayHolding[position].lastPrice!!-arrayHolding[position].avgCost!!)<0){
                   AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossTitle, R.color.red)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossAvgCost, R.color.red)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossTotalValue, R.color.red)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossPercentage, R.color.red)
                }else{
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossTitle, R.color.green)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossAvgCost, R.color.green)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossTotalValue, R.color.green)
                    AppHelper.setTextColor(holder.itemView.context,holder.tvGainLossPercentage, R.color.green)
                }
            }
            AppConstants.TAB_INCOME ->{
                //income
                try{holder.tvIncomeEquity.text =arrayHolding[position].codeTickerEn.toString() }catch (e:Exception){}
                try{holder.tvIncomeRealized.text =arrayHolding[position].realizedPL.toString() }catch (e:Exception){}
                try{holder.tvIncomeDistribution.text =arrayHolding[position].realizedPL.toString() }catch (e:Exception){}
                try{holder.tvIncomeQty.text =arrayHolding[position].quantity.toString() }catch (e:Exception){}
                try{holder.tvIncomeQtyDistribution.text =arrayHolding[position].currentPrice.toString() }catch (e:Exception){}

            }
            AppConstants.TAB_PERFORMANCE ->{

                //performance
                try{holder.tvPerformanceEquity.text  = arrayHolding[position].codeTickerEn.toString() }catch (e:Exception){}
                try{holder.tvPerformancePerw.text = arrayHolding[position].costPercent.toString() }catch (e:Exception){}
                try{holder.tvPerformanceBench.text = arrayHolding[position].changePercent.toString() }catch (e:Exception){}
                try{holder.tvPerformanceDiff.text = arrayHolding[position].costPercent.toString()+"%" }catch (e:Exception){}

                try{holder.tvPerformanceQty.text = arrayHolding[position].quantity.toString() }catch (e:Exception){}
                try{holder.tvPerformanceQtyPerR.text = arrayHolding[position].changePercent.toString() }catch (e:Exception){}
                try{holder.tvPerformanceQtyBenchPerR.text = arrayHolding[position].totalMarketValue.toString() }catch (e:Exception){}
                try{holder.tvPerformanceQtyDiff.text = arrayHolding[position].avgCost.toString()+"%" }catch (e:Exception){}
            }
            else ->{}
        }

    }

    override fun getItemCount(): Int {
        return arrayHolding.size
    }

    inner class VHprivacy(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        //position
        lateinit var tvPositionName: TextView
        lateinit var tvPositionLastCLose: TextView
        lateinit var tvCurrentPecentage: TextView
        lateinit var tvPositionQty: TextView
        lateinit var tvPositionChg: TextView
        lateinit var tvChgPercentage: TextView
 

        //Gain Loss
        lateinit var tvGainEquity: TextView
        lateinit var tvGainAvgCost: TextView
        lateinit var tvGainTotalCost: TextView
        lateinit var tvGainPercentage: TextView

        lateinit  var tvGainQty: TextView
        lateinit  var tvGainLastPrice: TextView
        lateinit  var tvGainTotalValue: TextView
        lateinit  var tvGainQtyPercentage: TextView
        lateinit  var tvGainLossTitle: TextView


        lateinit var tvGainLossAvgCost: TextView
        lateinit var tvGainLossTotalValue: TextView
        lateinit var tvGainLossPercentage: TextView

        //income
        lateinit  var tvIncomeEquity: TextView
        lateinit  var tvIncomeRealized: TextView
        lateinit  var tvIncomeDistribution: TextView

        lateinit  var tvIncomeQty: TextView
        lateinit  var tvIncomeQtyDistribution: TextView


        //performance
        lateinit  var tvPerformanceEquity: TextView
        lateinit  var tvPerformancePerw: TextView
        lateinit  var tvPerformanceBench: TextView
        lateinit  var tvPerformanceDiff: TextView

        lateinit  var tvPerformanceQty: TextView
        lateinit  var tvPerformanceQtyPerR: TextView
        lateinit  var tvPerformanceQtyBenchPerR: TextView
        lateinit  var tvPerformanceQtyDiff: TextView
   




        init {
            when (MyApplication.selectedTabInside) {
                 AppConstants.TAB_POSITION ->{
                     tvPositionName= itemView.findViewById(com.ids.markaz.R.id.tvPositionName) as TextView
                     tvPositionLastCLose= itemView.findViewById(com.ids.markaz.R.id.tvPositionLastCLose) as TextView
                     tvCurrentPecentage = itemView.findViewById(com.ids.markaz.R.id.tvCurrentPecentage) as TextView
                     tvPositionQty = itemView.findViewById(com.ids.markaz.R.id.tvPositionQty) as TextView
                     tvPositionChg = itemView.findViewById(com.ids.markaz.R.id.tvPositionChg) as TextView
                     tvChgPercentage = itemView.findViewById(com.ids.markaz.R.id.tvChgPercentage) as TextView
                 }
                 AppConstants.TAB_GAIN_LOSS ->{
                     tvGainEquity = itemView.findViewById(com.ids.markaz.R.id.tvGainEquity) as TextView
                     tvGainAvgCost = itemView.findViewById(com.ids.markaz.R.id.tvGainAvgCost) as TextView
                     tvGainTotalCost = itemView.findViewById(com.ids.markaz.R.id.tvGainTotalCost) as TextView
                     tvGainPercentage = itemView.findViewById(com.ids.markaz.R.id.tvGainPercentage) as TextView

                     tvGainQty = itemView.findViewById(com.ids.markaz.R.id.tvGainQty) as TextView
                     tvGainLastPrice = itemView.findViewById(com.ids.markaz.R.id.tvGainLastPrice) as TextView
                     tvGainTotalValue = itemView.findViewById(com.ids.markaz.R.id.tvGainTotalValue) as TextView
                     tvGainQtyPercentage = itemView.findViewById(com.ids.markaz.R.id.tvGainQtyPercentage) as TextView
                     tvGainLossTitle = itemView.findViewById(com.ids.markaz.R.id.tvGainLossTitle) as TextView


                     tvGainLossAvgCost = itemView.findViewById(com.ids.markaz.R.id.tvGainLossAvgCost) as TextView
                     tvGainLossTotalValue = itemView.findViewById(com.ids.markaz.R.id.tvGainLossTotalValue) as TextView
                     tvGainLossPercentage = itemView.findViewById(com.ids.markaz.R.id.tvGainLossPercentage) as TextView
                 }
                 AppConstants.TAB_INCOME ->{
                     //income
                     tvIncomeEquity = itemView.findViewById(com.ids.markaz.R.id.tvIncomeEquity) as TextView
                     tvIncomeRealized = itemView.findViewById(com.ids.markaz.R.id.tvIncomeRealized) as TextView
                     tvIncomeDistribution = itemView.findViewById(com.ids.markaz.R.id.tvIncomeDistribution) as TextView

                     tvIncomeQty = itemView.findViewById(com.ids.markaz.R.id.tvIncomeQty) as TextView
                     tvIncomeQtyDistribution = itemView.findViewById(com.ids.markaz.R.id.tvIncomeQtyDistribution) as TextView

                 }
                 AppConstants.TAB_PERFORMANCE ->{

                     //performance
                     tvPerformanceEquity = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceEquity) as TextView
                     tvPerformancePerw = itemView.findViewById(com.ids.markaz.R.id.tvPerformancePerw) as TextView
                     tvPerformanceBench = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceBench) as TextView
                     tvPerformanceDiff = itemView.findViewById(com.ids.markaz.R.id.tvPerformancePerw) as TextView

                     tvPerformanceQty = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceQty) as TextView
                     tvPerformanceQtyPerR = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceQtyPerR) as TextView
                     tvPerformanceQtyBenchPerR = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceQtyBenchPerR) as TextView
                     tvPerformanceQtyDiff = itemView.findViewById(com.ids.markaz.R.id.tvPerformanceQtyDiff) as TextView
                 }
                else ->{}
            }








            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            itemClickListener.onItemClicked(v, layoutPosition)
        }
    }



}
