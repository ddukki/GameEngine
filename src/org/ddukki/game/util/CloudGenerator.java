package org.ddukki.game.util;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CloudGenerator extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		final int originalSize = 32;
		final int levels = 6;
		final int size = originalSize * (int) Math.pow(2, levels);

		JFrame j = new JFrame();
		JPanel p = new CloudGenerator();
		j.setContentPane(p);
		j.setSize(size, size);
		j.setLocationRelativeTo(null);
		j.setVisible(true);

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void printKernel(final double[] k, final int i) {
		System.out.println("Gaussian Kernel [" + i + "]");
		System.out.print("[");
		for (int h = 0; h < k.length; h++) {
			System.out.print(k[h] + " ");
		}
		System.out.println("]");

		System.out.println("=======");

	}

	@Override
	public void paintComponent(Graphics g) {
		final int originalSize = 32;
		final int levels = 6;

		final int size = originalSize * (int) Math.pow(2, levels);

		final double[][] m = new double[size][size];
		for (int i = 0; i < levels; i++) {
			final int nsize = originalSize * (int) Math.pow(2, i);

			// Create random matrix
			final double[][] rm = RandomNumberGenerator
					.generate(new double[nsize][nsize], 0, 255);

			// Upscale
			final double[][] um = MatrixUtil.upscale(rm, size / nsize);

			// Generate Gaussian filter
			final int width = (int) Math.pow(2, levels - i + 1);
			final int sigma = levels - i + 1;
			final double[] k = MatrixUtil.gaussianKernel(width, sigma);

			printKernel(k, i);

			// Filter
			final double[][] wm = MatrixUtil.waveletConvolve(k, um);

			// Add
			MathUtil.sumi(m, wm);
		}

		final double[][] n = MatrixUtil.threshold(MathUtil.normalize(m), 0.56);

		g.drawImage(MathUtil.toImage(n), 0, 0, size, size, null);
	}
}
