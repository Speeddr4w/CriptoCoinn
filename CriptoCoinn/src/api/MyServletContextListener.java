package api;
import java.io.IOException;
import java.time.Instant;

import javax.servlet.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class MyServletContextListener implements ServletContextListener {

	   static Coin Cardano 	= new Coin();
	   static Coin Binance 	= new Coin();
	   static Coin Chiliz 	= new Coin();
	   static Coin Polkadot	= new Coin();
	   static Coin Solana 	= new Coin();

 public void contextInitialized(ServletContextEvent e) {
	 
	 
   coinGecko coinGecko = new coinGecko();
   
   Gson gson = new GsonBuilder().serializeNulls().create();
   Coin moeda = new Coin();
   
   long timeTo = Instant.now().getEpochSecond();
   long timeFromDaily = timeTo - 86400;
   long timeFromWeekly = timeTo - 604800;
   long timeFromMonthly = timeTo - 2592000;

   
   
   try {
	   
	   moeda = gson.fromJson(coinGecko.bitcoinInfo(), Coin.class); 
	   moeda.ConverterMarket_Data();
	   moeda.transferirMarket_Data();
	   

	   Cardano = gson.fromJson(coinGecko.cardanoInfo(), Coin.class); 
	   Cardano.ConverterMarket_Data();
	   Cardano.transferirMarket_Data();
	   Cardano.daily_values	 	= gson.fromJson(coinGecko.cardanoMarketValues(timeFromDaily,timeTo), Coin.market_values.class);
	   Cardano.weekly_values 	= gson.fromJson(coinGecko.cardanoMarketValues(timeFromWeekly,timeTo), Coin.market_values.class);
	   Cardano.monthly_values 	= gson.fromJson(coinGecko.cardanoMarketValues(timeFromMonthly,timeTo), Coin.market_values.class);
	   
	   /*Mostrando Valores
	   for(int i = 0; i < Cardano.daily_values.prices.size(); i = i + 72) {
		   System.out.println(Cardano.daily_values.prices.get(i)[1]);
	   }
	   System.out.println(Cardano.daily_values.prices.size());
	   
	   for(int i = 0; i < Cardano.weekly_values.prices.size(); i = i + 24) {
		   System.out.println(Cardano.weekly_values.prices.get(i)[1]);
	   }
	   System.out.println(Cardano.weekly_values.prices.size());
	   
	   for(int i = 0; i < Cardano.monthly_values.prices.size(); i = i + 24) {
		   System.out.println(Cardano.monthly_values.prices.get(i)[1]);
	   }
	   System.out.println(Cardano.monthly_values.prices.size());
	   */
	   
	   Binance = gson.fromJson(coinGecko.binanceInfo(), Coin.class); 
	   Binance.ConverterMarket_Data();
	   Binance.transferirMarket_Data();
	   
	   Chiliz = gson.fromJson(coinGecko.chilizInfo(), Coin.class); 
	   Chiliz.ConverterMarket_Data();
	   Chiliz.transferirMarket_Data();
	   
	   Polkadot = gson.fromJson(coinGecko.polkadotInfo(), Coin.class); 
	   Polkadot.ConverterMarket_Data();
	   Polkadot.transferirMarket_Data();
	   
	   Solana = gson.fromJson(coinGecko.solanaInfo(), Coin.class); 
	   Solana.ConverterMarket_Data();
	   Solana.transferirMarket_Data();
	   
} catch (JsonSyntaxException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
   
   //Calcular variação
   double variacaoDiaria = 0;
   double variacaoSemanal = 0;
   double variacaoMensal = 0;
   

   double variacaoDiariaPorcentagem = 0;
   double variacaoSemanalPorcentagem = 0;
   double variacaoMensalPorcentagem = 0;
   
   String analise;
   
   try {
	   variacaoDiaria = calcularVariacao(Cardano.daily_values.prices.get(0)[1],Cardano.daily_values.prices.get(Cardano.daily_values.prices.size()-1)[1]);
	   variacaoSemanal = calcularVariacao(Cardano.weekly_values.prices.get(0)[1],Cardano.weekly_values.prices.get(Cardano.weekly_values.prices.size()-1)[1]);
	   variacaoMensal = calcularVariacao(Cardano.monthly_values.prices.get(0)[1],Cardano.monthly_values.prices.get(Cardano.monthly_values.prices.size()-1)[1]);
	   
	   variacaoDiariaPorcentagem = calcularVariacaoPorcentagem(Cardano.daily_values.prices.get(0)[1],Cardano.daily_values.prices.get(Cardano.daily_values.prices.size()-1)[1]);
	   variacaoSemanalPorcentagem = calcularVariacaoPorcentagem(Cardano.weekly_values.prices.get(0)[1],Cardano.weekly_values.prices.get(Cardano.weekly_values.prices.size()-1)[1]);
	   variacaoMensalPorcentagem = calcularVariacaoPorcentagem(Cardano.monthly_values.prices.get(0)[1],Cardano.monthly_values.prices.get(Cardano.monthly_values.prices.size()-1)[1]);

   }finally{
	   System.out.println(variacaoDiaria);
	   System.out.println(variacaoSemanal);
	   System.out.println(variacaoMensal);
	   
	   System.out.println(variacaoDiariaPorcentagem + "%");
	   System.out.println(variacaoSemanalPorcentagem + "%");
	   System.out.println(variacaoMensalPorcentagem + "%");
   }
   
   if(variacaoMensalPorcentagem <= -10 && variacaoMensalPorcentagem >= -20 && variacaoDiariaPorcentagem < 10) {
	   analise = "HORA DE COMPRAR/ANALISAR";
   } else if(variacaoMensalPorcentagem >= 5 && variacaoMensalPorcentagem <= 10 && variacaoDiariaPorcentagem > 5) {
	   analise = "HORA DE COMPRAR";
   } else if(variacaoMensalPorcentagem >= 30 && variacaoDiariaPorcentagem <= -20) {
	   analise = "HORA DE COMPRAR";
   } else if(variacaoMensalPorcentagem <= -10 && variacaoMensalPorcentagem >= -20 && variacaoDiariaPorcentagem >= 20) {
	   analise = "HORA DE ANALISAR";
   } else if(variacaoMensalPorcentagem <= -20 && variacaoDiariaPorcentagem >= 50) {
	   analise = "HORA DE COMPRAR";
   } else if(variacaoMensalPorcentagem >= 20 && variacaoDiariaPorcentagem >= 50) {
	   analise = "HORA DE VENDER/ANALISAR";
   } else {
	   analise = "HORA DE ANALISAR";
   }

   System.out.println(analise);
   
   
 }
 
 public double calcularVariacao(double valorInicial, double valorFinal) {
	 double variacao = 0;
	 
	 variacao = valorFinal - valorInicial;
	 
	 return variacao;
 }
 
 public double calcularVariacaoPorcentagem(double valorInicial, double valorFinal) {
	 double variacao = 0;
	 
	 variacao = ((valorFinal / valorInicial) * 100) - 100;
	
	 return variacao;
 }

 public void contextDestroyed(ServletContextEvent e) {

 }
}