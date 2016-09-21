package cn.math.scalar;

import java.util.*;

import cn.math.PrettyMath;

public class Matrix {
	/**
	 * a double[][] that acts as a matrix
	 */
	protected double[][] matrix;

	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * Integer equal to number of rows
	 */
	protected int rows;

	public int getRows() {
		return rows;
	}

	/**
	 * Integer equal to number of cols
	 */
	protected int cols;

	public int getCols() {
		return cols;
	}

	/**
	 * number of columns on the right hand side of the augmented matrix
	 */
	private int aug = -1;

	public int getAug() {
		return aug;
	}

	protected Matrix() {

	}

	/**
	 * Initializes a new matrix using width and height
	 * 
	 * @param row
	 *            number of rows of the matrix
	 * @param col
	 *            number of columns of the matrix
	 * @return new double[][]
	 */
	public Matrix(int row, int col) {
		matrix = new double[row][col];
		rows = row;
		cols = col;
	}

	/**
	 * Initializes a new matrix using a 2nd dimension double array
	 * 
	 * @param entries
	 * @param isAug
	 */
	public Matrix(double[][] entries) {
		for (int i = 0; i < entries.length - 1; i++) {
			if (entries[i].length != entries[i + 1].length) {
				System.err.println("Inconsistent width on row " + i + " and " + (i + 1));
				System.exit(0);
			}
		}
		rows = entries.length;
		cols = entries[0].length;
		this.matrix = new double[rows][cols];
		for (int i = 0; i < entries.length; i++) {
			for (int j = 0; j < entries[i].length; j++) {
				this.matrix[i][j] = entries[i][j];
			}
		}
	}

	/**
	 * Create a new identity matrix
	 * 
	 * @param idMatrixSize
	 * @throws Exception
	 */
	public Matrix(int idMatrixSize) {
		if (idMatrixSize < 0) {
			System.out.println("Matrix size must be at least 1x1!");
		}
		matrix = new double[idMatrixSize][idMatrixSize];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][i] = 1;
		}
		cols = idMatrixSize;
		rows = idMatrixSize;
	}

	/**
	 * Make a new Matrix item with same values as the parameter Matrix
	 * 
	 * @param original
	 */
	public Matrix(Matrix original) {
		this.rows = original.rows;
		this.cols = original.cols;
		this.aug = original.aug;
		this.matrix = new double[rows][cols];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				this.matrix[i][j] = original.matrix[i][j];
			}
		}
	}

	public Matrix(Set<Vector> v) {
		Vector[] temp = (Vector[]) (v.toArray());
		this.rows = temp[0].rows;
		this.cols = temp.length;
		this.matrix = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				matrix[i][j] = temp[i].matrix[0][j];
			}
		}
	}

	/**
	 * Merge 2 matrices into one augmented matrix
	 */
	private Matrix(Matrix A, Matrix B) {
		if (A.rows != B.rows) {
			System.err.println("Left and right matrices must have the same number of rows!");
		}
		this.rows = A.rows;
		this.cols = A.cols + B.cols;
		this.aug = A.cols;
		this.matrix = new double[rows][cols];
		for (int i = 0; i < A.rows; i++) {
			for (int j = 0; j < A.cols; j++) {
				this.matrix[i][j] = A.matrix[i][j];
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = A.cols; j < cols; j++) {
				this.matrix[i][j] = B.matrix[i][j - A.cols];
			}
		}
	}

	/**
	 * Converts double[][] to a matrix
	 */
	public String toString() {
		StringBuilder build = new StringBuilder();

		for (int i = 0; i < matrix.length; i++) {
			build.append('[');
			boolean needBar = true;
			for (int j = 0; j < matrix[i].length; j++) {
				build.append(PrettyMath.toFrac(matrix[i][j]));

				if (aug != -1 && j >= aug - 1 && needBar) {
					build.append("|");
					needBar = false;
				} else if (j != matrix[i].length - 1) {
					build.append("\t");
				}

			}
			build.append("]");
			if (i != matrix.length - 1) {
				build.append('\n');
			}
		}
		return build.toString();
	}

	/**
	 * Add a matrix to this matrix
	 * 
	 * @param mat
	 *            matrix to add this matrix to
	 * @return answer matrix or null if operation impossible
	 */
	public Matrix add(Matrix mat) {
		if (this.rows == mat.rows && this.cols == mat.cols) {
			Matrix ans = new Matrix(this.rows, this.cols);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					ans.matrix[i][j] = this.matrix[i][j] + mat.matrix[i][j];
				}
			}
			return ans;
		}
		return null;
	}

	/**
	 * Subtract a matrix from this matrix
	 * 
	 * @param mat
	 *            matrix subtracted from this matrix
	 * @return answer matrix or null if operation impossible
	 */
	public Matrix subtract(Matrix mat) {
		if (this.rows == mat.rows && this.cols == mat.cols) {
			Matrix ans = new Matrix(this.rows, this.cols);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					ans.matrix[i][j] = this.matrix[i][j] - mat.matrix[i][j];
				}
			}
			return ans;
		}
		return null;
	}

	/**
	 * Multiplies this matrix by a number
	 * 
	 * @param num
	 *            a real number
	 * @return answer matrix
	 */
	public Matrix multiply(double num) {
		Matrix ans = new Matrix(this.rows, this.cols);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				ans.matrix[i][j] = this.matrix[i][j] * num;
			}
		}
		return ans;
	}

	/**
	 * Multiplies this matrix by a matrix
	 * 
	 * @param mat
	 * @return answer matrix
	 */
	public Matrix multiply(Matrix mat) {
		if (this.cols == mat.rows) {
			Matrix ans = new Matrix(this.rows, mat.cols);

			for (int i = 0; i < ans.rows; i++) {
				for (int j = 0; j < ans.cols; j++) {
					double cell = 0;
					for (int j2 = 0; j2 < this.cols; j2++) {
						cell = cell + this.matrix[i][j2] * mat.matrix[j2][j];
					}
					ans.matrix[i][j] = cell;
				}
			}
			return ans;

		}
		return null;
	}

	/**
	 * Check if two matrices are equal. Matrices are equal if every entry with
	 * the same index have the same scalar value
	 * 
	 * @param mat
	 * @return true, if every entry with the same index have the same value
	 */
	public boolean equals(Matrix mat) {
		if ((this.rows != mat.rows) || (this.cols != mat.cols)) {
			return false;
		}
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (this.matrix[i][j] != mat.matrix[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Transpose this matrix
	 * 
	 * @return this Matrix instance
	 */
	public Matrix transpose() {
		double[][] mat = new double[cols][rows];
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				mat[j][i] = matrix[i][j];
			}
		}
		matrix = mat;
		return this;
	}

	/**
	 * Splits a matrix
	 * 
	 * @return
	 */
	private Matrix splitAug() {
		double[][] tmp = new double[rows][cols];
		for (int i = 0; i < tmp.length; i++) {
			for (int j = 0; j < tmp[i].length; j++) {
				tmp[i][j] = this.matrix[i][j];
			}
		}
		for (int i = 0; i < rows; i++) {
			this.matrix[i] = new double[aug];
			for (int j = 0; j < matrix[i].length; j++) {
				this.matrix[i][j] = tmp[i][j];
			}
		}
		Matrix ans = new Matrix(rows, cols - aug);
		for (int i = 0; i < rows; i++) {
			for (int j = aug; j < tmp[i].length; j++) {
				ans.matrix[i][j - aug] = tmp[i][j];
			}
		}
		this.cols = aug;
		this.aug = -1;

		return ans;
	}

	/**
	 * Add a multiple of row b to row a
	 * 
	 * @param a
	 * @param coef
	 * @param b
	 * @return
	 */
	private void rowAdd(int i, double coef, int j) {
		for (int a = 0; a < cols; a++) {
			matrix[i][a] = matrix[i][a] + coef * matrix[j][a];
		}
	}

	private double[] rowMulti(int i, double scalar) {
		double[] ans = new double[cols];
		for (int j = 0; j < ans.length; j++) {
			matrix[i][j] = matrix[i][j] * scalar;
		}
		return ans;
	}

	/**
	 * Quicksorts the matrix rows based on number of leading zeros
	 * 
	 * @param arr
	 * @param left
	 * @param right
	 * @return
	 */
	private double[][] rowSort(double[][] arr, int left, int right) {
		int i = left, j = right;
		double[] tmp;
		int pivot = countZeros(this.matrix[(left + right) / 2]);
		while (i <= j) {
			while (countZeros(this.matrix[i]) < pivot) {
				i++;
			}
			while (countZeros(this.matrix[j]) > pivot) {
				j--;
			}
			if (i <= j) {
				tmp = this.matrix[i];
				this.matrix[i] = this.matrix[j];
				this.matrix[j] = tmp;
				i++;
				j--;
			}
		}
		if (left < j) {
			rowSort(this.matrix, left, j);
		}
		if (right > i) {
			rowSort(this.matrix, i, right);
		}
		return arr;
	}

	/**
	 * Counts the number of leading zeros in a row
	 * 
	 * @param row
	 * @return number of leading zeros
	 */
	private int countZeros(double[] row) {
		int count;

		for (count = 0; count < row.length; count++) {
			if (row[count] != 0) {
				break;
			}
		}
		return count;
	}

	/**
	 * Gaussian algorithm for 1 cols augmented matrix
	 * 
	 * @return answer in RREF
	 */
	public Matrix RREF() {
		Matrix ans = new Matrix(this);
		// i defines # of finished rows
		// j defines current column being reduced.
		int j = 0;
		int limit;
		if (aug != -1) {
			limit = aug;
		} else {
			limit = cols;
		}
		ans.rowSort(ans.matrix, 0, ans.rows - 1);
		for (int i = 0; i < ans.rows && j < limit; i++, j++) {
			ans.rowSort(ans.matrix, 0, ans.rows - 1);
			double leadValue = 0;
			for (int j2 = j; j2 < limit; j2++) {
				if (ans.matrix[i][j2] != 0) {
					leadValue = ans.matrix[i][j2];
					j = j2;
					break;
				}
			}
			if (leadValue == 0) {
				continue;
			}
			// divide whole row by the leading value
			if (leadValue != 1) {
				ans.rowMulti(i, 1.0 / leadValue);
			}
			// reduce all other entries in same column to 0
			for (int i2 = 0; i2 < ans.rows; i2++) {
				if (i2 == i || ans.matrix[i2][j] == 0) {
					continue;
				}
				double z = -ans.matrix[i2][j];
				ans.rowAdd(i2, z, i);
			}
		}
		ans.rowSort(ans.matrix, 0, ans.rows - 1);
		return ans;
	}

	/**
	 * Perform a multi-cols aug mat matrix
	 * 
	 * @return
	 */
	public Matrix linked(Matrix b) {
		Matrix linked = new Matrix(this, b);
		linked = linked.RREF();
		b = linked.splitAug();
		return b;
	}

	/**
	 * Find the inverse
	 * 
	 * @return
	 */
	public Matrix inverse() {
		if (this.cols != this.rows) {
			System.err.println("Only square matrices have inverses.");
			return this;
		}
		Matrix linked = new Matrix(this, new Matrix(this.cols));
		linked = linked.RREF();
		Matrix ans = linked.splitAug();
		if (linked.equals(new Matrix(this.cols))) {
			return ans;
		} else {
			System.err.println("This matrix has no inverse.");
			return this;
		}

	}

	private double det(double[][] detMat) {
		// Step 1: Cofactor expansion
		double determinant = 0;

		if (detMat.length == 2 & detMat[0].length == 2) {
			return detMat[0][0] * detMat[1][1] - detMat[1][0] * detMat[0][1];
		}

		for (int i = 0; i < detMat.length; i++) {
			if (detMat[i][0] == 0) {
				continue;
			}
			double coef = Math.pow(-1, i) * detMat[i][0];

			double[][] tmp = new double[detMat.length - 1][detMat.length - 1];
			for (int i2 = 0; i2 < detMat.length; i2++) {
				for (int j2 = 1; j2 < detMat.length; j2++) {
					if (i2 < i) {
						tmp[i2][j2 - 1] = detMat[i2][j2];
					} else if (i2 > i) {
						tmp[i2 - 1][j2 - 1] = detMat[i2][j2];
					}
				}
			}
			// recursion
			for (int i2 = 0; i2 < tmp.length; i2++) {
				determinant = determinant + coef * det(tmp);
			}

		}

		return determinant;
	}

	public double determinant() {
		if (cols == rows) {
			return det(this.matrix);
		} else {
			System.err.println("This matrix has no determinant.");
			return 1;
		}
	}

	public static void main(String[] args) {
		double[][] value = new double[4][4];
		value[0] = new double[] { 1, 6, 4, 3 };
		value[1] = new double[] { 4, 0, 2, 5 };
		value[2] = new double[] { 2, 6, 0, 8 };
		value[3] = new double[] { 9, 2, 0, 7 };
		Matrix a = new Matrix(value);
		System.out.println(a.determinant());
	}
}
