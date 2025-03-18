package com.ontariotechu.sofe3980U;

import java.io.FileReader;
import java.util.List;
import com.opencsv.*;

/**
 * Evaluate Single Variable Continuous Regression
 */
public class App {
	public static void main(String[] args) {
		String[] modelFiles = { "model_1.csv", "model_2.csv", "model_3.csv" };
		double bestMSE = Double.MAX_VALUE;
		double bestMAE = Double.MAX_VALUE;
		double bestMARE = Double.MAX_VALUE;
		String bestModelMSE = "";
		String bestModelMAE = "";
		String bestModelMARE = "";

		for (String filePath : modelFiles) {
			FileReader filereader;
			List<String[]> allData;

			try {
				filereader = new FileReader(filePath);
				CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
				allData = csvReader.readAll();
			} catch (Exception e) {
				System.out.println("Error reading the CSV file: " + filePath);
				continue;
			}

			double mse = 0.0;
			double mae = 0.0;
			double mare = 0.0;
			int count = 0;

			for (String[] row : allData) {
				try {
					float y_true = Float.parseFloat(row[0]);
					float y_predicted = Float.parseFloat(row[1]);

					double error = y_true - y_predicted;
					mse += Math.pow(error, 2);
					mae += Math.abs(error);

					if (y_true != 0) {
						mare += Math.abs(error) / Math.abs(y_true);
					}
					count++;
				} catch (Exception e) {
					System.out.println("Error processing row in " + filePath + ": " + String.join(",", row));
				}
			}

			if (count > 0) {
				mse /= count;
				mae /= count;
				mare = (mare / count) * 100; // Convert to percentage
			}

			System.out.println("For " + filePath);
			System.out.println("MSE = " + mse);
			System.out.println("MAE = " + mae);
			System.out.println("MARE = " + mare + "%");
			System.out.println();

			if (mse < bestMSE) {
				bestMSE = mse;
				bestModelMSE = filePath;
			}
			if (mae < bestMAE) {
				bestMAE = mae;
				bestModelMAE = filePath;
			}
			if (mare < bestMARE) {
				bestMARE = mare;
				bestModelMARE = filePath;
			}
		}

		System.out.println("According to MSE, The best model is " + bestModelMSE);
		System.out.println("According to MAE, The best model is " + bestModelMAE);
		System.out.println("According to MARE, The best model is " + bestModelMARE);
	}
}
