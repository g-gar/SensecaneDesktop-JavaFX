package com.magc.sensecane.util;

import static com.magc.sensecane.util.NumberUtils.divide;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.Axis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

public class JavaFXChartUtils {

	public static <T, X extends Number & Comparable<X>, Y  extends Number & Comparable<Y>> XYChart.Series<X, Y> generate(String identifier, Collection<T> items, Function<T, XYChart.Data<X, Y>> fn) {
		ObservableList<Data<X, Y>> data = FXCollections.observableArrayList(items.stream().map(value -> fn.apply(value)).collect(Collectors.toList()));
		return new XYChart.Series<X, Y>(identifier, data);
	}
	
	public static <X extends Number & Comparable<X>, Y  extends Number & Comparable<Y>> void plotSerie(XYChart<X,Y> chart, XYChart.Series<X,Y> serie) {
		Axis<X> x = chart.getXAxis();
		
		chart.getData().add(serie);
		chart.getYAxis().setAutoRanging(true);
		
		X min = serie.getData().stream().map(Data::getXValue).min(NumberUtils::compare).orElse(null);
		X max = serie.getData().stream().map(Data::getXValue).max(NumberUtils::compare).orElse(null);
		Boolean autorange = true;
		
		if (min != null && max != null) {
			autorange = false;
			((NumberAxis) x).setLowerBound(min.doubleValue());
			((NumberAxis) x).setUpperBound(max.doubleValue());
			((NumberAxis) x).setTickUnit( divide(max.doubleValue() - min.doubleValue(), 25.0));
			((NumberAxis) x).setMinorTickCount(10);
			((NumberAxis) x).setMinorTickVisible(true);
		}
		x.setAutoRanging(autorange);
	}
	
	public static <X,Y> void cleanChartSeries(XYChart<X,Y> chart) {
		chart.getData().removeAll(chart.getData());
	}
	
}
